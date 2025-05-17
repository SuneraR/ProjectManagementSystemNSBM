package servlet;

import jakarta.servlet.ServletException;
import model.Project;
import model.Task;
import model.User;
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


import dao.ProjectDAO;
import dao.taskDAO;

/**
 * Servlet implementation class ViewProject
 */
@WebServlet("/ViewProject")
public class ViewProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProjectDAO projectDAO;
	taskDAO TaskDAO;
	@Override
    public void init() throws ServletException {
			projectDAO = new ProjectDAO(null);
			TaskDAO=new taskDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
		 HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
	        
	        if (currentUser == null) {
	            response.sendRedirect("jsp/login.jsp");
	            return;
	        }
	    try {
	        
	        // Get and validate projectId
	        String projectIdParam = request.getParameter("projectId");
	        if (projectIdParam == null || projectIdParam.isEmpty()) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is required");
	            return;  // This ends the method execution
	        }
	        
	        int projectId;
	        try {
	            projectId = Integer.parseInt(projectIdParam);
	        } catch (NumberFormatException e) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Project ID format");
	            return;
	        }
	        
	        // Get project and tasks
	        Project project = projectDAO.getProjectById(projectId);
	        if (project == null) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Project not found");
	            return;
	        }
	        
	        List<Task> tasks = TaskDAO.getTasksByProject(projectId);
	        
	        
	       
	        request.setAttribute("currentUser", currentUser);
	        request.setAttribute("project", project);
	        request.setAttribute("tasks", tasks);
	       

	        request.getRequestDispatcher("jsp/projectView.jsp").forward(request, response);
	        
	    } catch (Exception e) {
	        // Log the error properly (use a logger instead of printStackTrace)
	        e.printStackTrace();
	        if (!response.isCommitted()) {
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
