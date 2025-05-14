package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Task;
import util.DBconnection;

public class taskDAO {
	public boolean createTask(Task task) throws SQLException {
	    String sql = "INSERT INTO tasks (project_id, assigned_to, title, description, status, deadline) VALUES (?, ?, ?, ?, ?, ?)";
	    try (Connection conn = DBconnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, task.getProjectId());
	        stmt.setInt(2, task.getAssignedTo());
	        stmt.setString(3, task.getTitle());
	        stmt.setString(4, task.getDescription());
	        stmt.setString(5, task.getStatus());

	        // Convert LocalDate to java.sql.Date
	        if (task.getDeadline() != null) {
	            stmt.setDate(6, java.sql.Date.valueOf(task.getDeadline()));
	        } else {
	            stmt.setDate(6, null);
	        }

	        return stmt.executeUpdate() > 0;
	    }
	}


	public List<Task> getTasksByProject(int projectId) throws SQLException {
	    List<Task> tasks = new ArrayList<>();
	    String sql = "SELECT t.*, u.name AS assignedToName FROM tasks t LEFT JOIN users u ON t.assigned_to = u.user_id WHERE t.project_id = ?";
	    
	    try (Connection conn =  DBconnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, projectId);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Task task = new Task();
	            task.setTaskId(rs.getInt("task_id"));
	            task.setProjectId(rs.getInt("project_id"));
	            task.setAssignedTo(rs.getInt("assigned_to"));
	            task.setTitle(rs.getString("title"));
	            task.setDescription(rs.getString("description"));
	            task.setStatus(rs.getString("status"));
	            java.sql.Date sqlDate = rs.getDate("deadline");
	            if (sqlDate != null) {
	                task.setDeadline(sqlDate.toLocalDate());
	            }
				 task.setAssignedToName(rs.getString("assignedToName")); 
	            tasks.add(task);
	        }
	    }
	    return tasks;
	}
	
	public static Task getTaskById(int taskId) throws SQLException {
	    String sql = "SELECT * FROM tasks WHERE task_id = ?";
	    try (Connection conn = DBconnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, taskId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            Task task = new Task();
	            task.setTaskId(rs.getInt("task_id"));
	            task.setProjectId(rs.getInt("project_id"));
	            task.setAssignedTo(rs.getInt("assigned_to"));
	            task.setTitle(rs.getString("title"));
	            task.setDescription(rs.getString("description"));
	            task.setStatus(rs.getString("status"));
	            java.sql.Date sqlDate = rs.getDate("deadline");
	            if (sqlDate != null) {
	                task.setDeadline(sqlDate.toLocalDate());
	            }
	            return task;
	        }
	    }
	    return null;
	}


	public static boolean updateTask(Task task) throws SQLException {
	    String sql = "UPDATE tasks SET assigned_to = ?, title = ?, description = ?, status = ?, deadline = ? WHERE task_id = ?";
	    try (Connection conn =  DBconnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, task.getAssignedTo());
	        stmt.setString(2, task.getTitle());
	        stmt.setString(3, task.getDescription());
	        stmt.setString(4, task.getStatus());
	        
	        if (task.getDeadline() != null) {
	            stmt.setDate(5, java.sql.Date.valueOf(task.getDeadline()));
	        } else {
	            stmt.setDate(5, null);
	        }
	        stmt.setInt(6, task.getTaskId());
	        return stmt.executeUpdate() > 0;
	    }
	}
	public static boolean updateTaskStatus(Task task) throws SQLException {
	    String status = task.getStatus();

	    // Validate ENUM values
	    if (!status.equals("pending") && !status.equals("in_progress") && !status.equals("completed")) {
	        throw new IllegalArgumentException("Invalid status value: " + status);
	    }

	    String sql = "UPDATE tasks SET status = ? WHERE task_id = ?";
	    try (Connection conn = DBconnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, status);
	        stmt.setInt(2, task.getTaskId());
	        return stmt.executeUpdate() > 0;
	    }
	}

	public boolean deleteTask(int taskId) throws SQLException {
	    String sql = "DELETE FROM tasks WHERE task_id = ?";
	    try (Connection conn =  DBconnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, taskId);
	        return stmt.executeUpdate() > 0;
	    }
	}

	public List<Task> getTasksByUser(int userId) throws SQLException {
	    List<Task> tasks = new ArrayList<>();
	    String sql = "SELECT t.*, p.name AS projectName FROM tasks t JOIN projects p ON t.project_id = p.project_id WHERE t.assigned_to = ?";
	    try (Connection conn =  DBconnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Task task = new Task();
	            task.setTaskId(rs.getInt("task_id"));
	            task.setProjectId(rs.getInt("project_id"));
	            task.setAssignedTo(rs.getInt("assigned_to"));
	            task.setTitle(rs.getString("title"));
	            task.setDescription(rs.getString("description"));
	            task.setStatus(rs.getString("status"));
	            task.setDeadline(LocalDate.parse((CharSequence) rs.getDate("deadline")));
	            // Optional: task.setProjectName(rs.getString("projectName"));
	            tasks.add(task);
	        }
	    }
	    return tasks;
	}




}
