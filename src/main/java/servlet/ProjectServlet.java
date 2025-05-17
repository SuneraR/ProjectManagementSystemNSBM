package servlet;

import dao.ProjectDAO;
import model.Project;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/ProjectServlet")
public class ProjectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProjectDAO projectDAO;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void init() throws ServletException {
        projectDAO = new ProjectDAO(null);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response, currentUser);
                    break;
                case "delete":
                    deleteProject(request, response, currentUser);
                    break;
                case "list":
                	listProjects(request, response, currentUser);
                default:
                    listProjects(request, response, currentUser);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (currentUser == null) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "create":
                    createProject(request, response, currentUser);
                    break;
                case "update":
                    updateProject(request, response, currentUser);
                    break;
                default:
                    listProjects(request, response, currentUser);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        } catch (Exception e) {
            throw new ServletException("Invalid input", e);
        }
    }

    private void listProjects(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws SQLException, ServletException, IOException {

        List<Project> tasksProjects = projectDAO.getProjectsByAssignedUser(currentUser.getId());
        for (Project p : tasksProjects) {
            boolean isCompleted = projectDAO.isProjectCompleted(p.getProjectId());
            p.setIsCompleted(isCompleted); 
        }

        List<Project> manageProjects = projectDAO.getProjectsByManager(currentUser.getId());
        for (Project p : manageProjects) {
            boolean isCompleted = projectDAO.isProjectCompleted(p.getProjectId());
            p.setIsCompleted(isCompleted);
        }

        request.setAttribute("tasksProjects", tasksProjects);
        request.setAttribute("manageProjects", manageProjects);
        request.getRequestDispatcher("/jsp/projectList.jsp").forward(request, response);
    }


    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/projectForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws SQLException, ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        Project project = projectDAO.getProjectById(projectId);
        
        // Verify current user is the manager of this project
        if (project.getManagerId() != currentUser.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You can only edit your own projects");
            return;
        }
        
        request.setAttribute("project", project);
        request.getRequestDispatcher("/jsp/projectForm.jsp").forward(request, response);
    }

    private void createProject(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws SQLException, IOException {
        Project project = extractProjectFromRequest(request);
        project.setManagerId(currentUser.getId());
        projectDAO.addProject(project);
        response.sendRedirect("ProjectServlet?action=list");
    }

    private void updateProject(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws SQLException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        Project existingProject = projectDAO.getProjectById(projectId);
        
        // Verify current user is the manager of this project
        if (existingProject.getManagerId() != currentUser.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You can only update your own projects");
            return;
        }
        
        Project updatedProject = extractProjectFromRequest(request);
        updatedProject.setProjectId(projectId);
        updatedProject.setManagerId(currentUser.getId()); // Ensure manager stays the same
        projectDAO.updateProject(updatedProject,true);
        response.sendRedirect("ProjectServlet?action=list");
    }

    private void deleteProject(HttpServletRequest request, HttpServletResponse response, User currentUser)
            throws SQLException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        Project project = projectDAO.getProjectById(projectId);
        
        // Verify current user is the manager of this project
        if (project.getManagerId() != currentUser.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You can only delete your own projects");
            return;
        }
        
        projectDAO.deleteProject(projectId);
        response.sendRedirect("ProjectServlet?action=list");
    }

    private Project extractProjectFromRequest(HttpServletRequest request) {
        Project project = new Project();
        project.setName(request.getParameter("name"));
        project.setDescription(request.getParameter("description"));
        
        // Parse dates
        String startDateStr = request.getParameter("start_date");
        if (startDateStr != null && !startDateStr.isEmpty()) {
            project.setStartDate(LocalDate.parse(startDateStr, dateFormatter));
        }
        
        String endDateStr = request.getParameter("end_date");
        if (endDateStr != null && !endDateStr.isEmpty()) {
            project.setEndDate(LocalDate.parse(endDateStr, dateFormatter));
        }
        
        return project;
    }
}