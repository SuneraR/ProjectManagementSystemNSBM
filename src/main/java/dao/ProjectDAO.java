package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Project;
import util.DBconnection;

public class ProjectDAO {
    private Connection conn;

    public ProjectDAO() throws SQLException {
        this.conn = DBconnection.getConnection();
    }

    public List<Project> getProjectsByManager(int managerId) throws SQLException {
        String sql = "SELECT * FROM projects WHERE manager_id = ?";
        List<Project> projects = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, managerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Project project = new Project();
                    project.setProjectId(rs.getInt("project_id"));
                    project.setName(rs.getString("name"));
                    project.setDescription(rs.getString("description"));
                    project.setManagerId(rs.getInt("manager_id"));

                    Date startDate = rs.getDate("start_date");
                    if (startDate != null) {
                        project.setStartDate(startDate.toLocalDate());
                    }

                    Date endDate = rs.getDate("end_date");
                    if (endDate != null) {
                        project.setEndDate(endDate.toLocalDate());
                    }

                    projects.add(project);
                }
            }
        }

        return projects;
    }
    
    public List<Project> getProjectsByAssignedUser(int userId) throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT DISTINCT p.* " +
                     "FROM projects p " +
                     "JOIN tasks t ON p.project_id = t.project_id " +
                     "WHERE t.assigned_to = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                Project project = new Project();
                project.setProjectId(rs.getInt("project_id"));
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setManagerId(rs.getInt("manager_id"));
                project.setStartDate(rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null);
                project.setEndDate(rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);
                projects.add(project);
            }
        }

        return projects;
    }


    public void addProject(Project project) throws SQLException {
        String sql = "INSERT INTO projects (name, description, manager_id, start_date, end_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setInt(3, project.getManagerId());

            if (project.getStartDate() != null) {
                stmt.setDate(4, Date.valueOf(project.getStartDate()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            if (project.getEndDate() != null) {
                stmt.setDate(5, Date.valueOf(project.getEndDate()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setProjectId(generatedKeys.getInt(1));
                }
            }
        }
    }
    
    public Project getProjectById(int projectId) throws SQLException {
        String sql = "SELECT project_id, name, description, manager_id, start_date, end_date " +
                     "FROM projects WHERE project_id = ?";
        
        Project project = null;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    project = new Project();
                    project.setProjectId(rs.getInt("project_id"));
                    project.setName(rs.getString("name"));
                    project.setDescription(rs.getString("description"));
                    project.setManagerId(rs.getInt("manager_id"));
                    
                    // Handle possible null dates
                    java.sql.Date startDate = rs.getDate("start_date");
                    if (startDate != null) {
                        project.setStartDate(startDate.toLocalDate());
                    }
                    
                    java.sql.Date endDate = rs.getDate("end_date");
                    if (endDate != null) {
                        project.setEndDate(endDate.toLocalDate());
                    }
                }
            }
        }
        
        if (project == null) {
            throw new SQLException("Project not found with ID: " + projectId);
        }
        
        return project;
    }
    
    /**
     * Deletes a project from the database
     * @param projectId The ID of the project to delete
     * @return true if deletion was successful, false otherwise
     * @throws SQLException if a database error occurs
     */
   
    public boolean deleteProject(int projectId) throws SQLException {
    	if (!projectExists(projectId)) {
    		return false;
    	}
        String deleteCommentsSQL = "DELETE FROM comments WHERE task_id IN (SELECT task_id FROM tasks WHERE project_id = ?)";
        String deleteTasksSQL = "DELETE FROM tasks WHERE project_id = ?";
        String deleteProjectSQL = "DELETE FROM projects WHERE project_id = ?";

        try (Connection conn = DBconnection.getConnection()) {
            conn.setAutoCommit(false);

            try (
                PreparedStatement deleteCommentsStmt = conn.prepareStatement(deleteCommentsSQL);
                PreparedStatement deleteTasksStmt = conn.prepareStatement(deleteTasksSQL);
                PreparedStatement deleteProjectStmt = conn.prepareStatement(deleteProjectSQL)
            ) {
                // Delete comments
                deleteCommentsStmt.setInt(1, projectId);
                deleteCommentsStmt.executeUpdate();

                // Delete tasks
                deleteTasksStmt.setInt(1, projectId);
                deleteTasksStmt.executeUpdate();

                // Delete project
                deleteProjectStmt.setInt(1, projectId);
                int rowsAffected = deleteProjectStmt.executeUpdate();

                conn.commit();
                return rowsAffected > 0;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }



    /**
     * Helper method to check if a project exists
     */
    private boolean projectExists(int projectId) throws SQLException {
        String sql = "SELECT 1 FROM projects WHERE project_id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    /**
     * Updates a project with additional validation and transaction support
     * @param project The project to update
     * @param validateDates If true, validates start_date <= end_date
     * @return true if update successful
     * @throws SQLException for database errors
     * @throws IllegalArgumentException for invalid data
     */
    @SuppressWarnings("null")
	public boolean updateProject(Project project, boolean validateDates) 
        throws SQLException, IllegalArgumentException {
        
        // Validate dates if requested
        if (validateDates && project.getStartDate() != null && 
            project.getEndDate() != null &&
            project.getStartDate().isAfter(project.getEndDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        // Connection conn = null; // Removed, use this.conn
        PreparedStatement stmt = null;
        boolean success = false;
        boolean originalAutoCommitState = true; // Default to true
        
        try {
            originalAutoCommitState = this.conn.getAutoCommit(); // Store original auto-commit state
            this.conn.setAutoCommit(false); // Start transaction on this.conn
            
            // First verify project exists and get current manager_id
            String verifySql = "SELECT manager_id FROM projects WHERE project_id = ? FOR UPDATE";
            // stmt = conn.prepareStatement(verifySql); // Use this.conn
            stmt = this.conn.prepareStatement(verifySql);
            stmt.setInt(1, project.getProjectId());
            
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                rs.close(); // Close result set
                stmt.close(); // Close statement
                this.conn.rollback(); // Rollback if project not found
                return false; // Project not found
            }
            
            int currentManagerId = rs.getInt("manager_id");
            rs.close();   // Close ResultSet
            stmt.close(); // Close PreparedStatement
            
            // Update project
            String updateSql = "UPDATE projects SET " +
                             "name = ?, description = ?, " +
                             "start_date = ?, end_date = ? " +
                             "WHERE project_id = ?";
            
            // stmt = conn.prepareStatement(updateSql); // Use this.conn
            stmt = this.conn.prepareStatement(updateSql);
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            
            if (project.getStartDate() != null) {
                stmt.setDate(3, java.sql.Date.valueOf(project.getStartDate()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }
            
            if (project.getEndDate() != null) {
                stmt.setDate(4, java.sql.Date.valueOf(project.getEndDate()));
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }
            
            stmt.setInt(5, project.getProjectId());
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
            
            // If manager was changed (though typically shouldn't be allowed)
            if (project.getManagerId() != currentManagerId) {
                // Need to close the previous statement if reusing the variable 'stmt'
                if (stmt != null) stmt.close(); 
                String updateManagerSql = "UPDATE projects SET manager_id = ? WHERE project_id = ?";
                // stmt = conn.prepareStatement(updateManagerSql); // Use this.conn
                stmt = this.conn.prepareStatement(updateManagerSql);
                stmt.setInt(1, project.getManagerId());
                stmt.setInt(2, project.getProjectId());
                stmt.executeUpdate();
            }
            
            this.conn.commit(); // Commit if all went well using this.conn
            
        } catch (SQLException e) {
            if (this.conn != null) {
                this.conn.rollback(); // Rollback on error using this.conn
            }
            throw e;
        } finally {
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); /* Log this */ }
            }
            // Restore original auto-commit state for this.conn
            if (this.conn != null) {
                try {
                    this.conn.setAutoCommit(originalAutoCommitState);
                } catch (SQLException ex) {
                    ex.printStackTrace(); // Or use a proper logging mechanism
                }
            }
            // Removed: conn.close();
        }
        
        return success;
    }
    
    public int getManagerIdByProject(int projectId) throws SQLException {
        String sql = "SELECT manager_id FROM projects WHERE project_id = ?";
        // try (Connection conn = DBconnection.getConnection(); // Removed
        //      PreparedStatement stmt = conn.prepareStatement(sql)) { // Use this.conn
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            // ResultSet rs = stmt.executeQuery(); // Use try-with-resources for ResultSet
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("manager_id");
                }
            }
        }
        return -1; // Project not found or no manager
    }
    public boolean isProjectCompleted(int projectId) {
        String sql = "SELECT COUNT(*) = 0 FROM tasks WHERE project_id = ? AND status != 'completed'";
        // try (Connection conn = DBconnection.getConnection(); // Removed
        //      PreparedStatement stmt = conn.prepareStatement(sql)) { // Use this.conn
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, projectId);
            // ResultSet rs = stmt.executeQuery(); // Use try-with-resources for ResultSet
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);  // true if all tasks are completed
                }
            }
        } catch (SQLException e) {
            // e.printStackTrace();  // Removed
            throw e; // Re-throw the SQLException
        }
        // This line is now unreachable if an SQLException occurs, which is intended.
        // If the try block completes without returning and without an exception (e.g. rs.next() is false), 
        // it will fall through to here.
        return false;  // Return false if error or no data
    }


    // Other CRUD methods can be added here...
}
