package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import dao.ProjectDAO;
import dao.UserDAO;
import dao.taskDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Project;
import model.Task;
import model.User;

@WebServlet("/CreateTaskServlet")
public class CreateTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private taskDAO taskDAO;
    private ProjectDAO projectDAO;
    private UserDAO userDAO;

    @Override
    public void init() {
        taskDAO = new taskDAO();
        projectDAO = new ProjectDAO(null);
        try {
			userDAO = new UserDAO();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // assuming this exists
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    	  HttpSession session = request.getSession();
          User currentUser = (User) session.getAttribute("user");
          
          if (currentUser == null) {
              response.sendRedirect("jsp/login.jsp");
              return;
          }
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        Project project = null;
		try {
			project = projectDAO.getProjectById(projectId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        List<User> users = null;
		try {
			users = userDAO.getUsersByRole("member");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        request.setAttribute("project", project);
        request.setAttribute("users", users);

        request.getRequestDispatcher("jsp/createTask.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        int projectId = Integer.parseInt(request.getParameter("projectId"));

        try {
            int managerId = projectDAO.getManagerIdByProject(projectId); // Add this method in your DAO

            // Check if current user is the manager of this project
            if (currentUser == null || currentUser.getId() != managerId) {
                request.setAttribute("errorMessage", "Only the assigned manager of this project can create tasks.");
                request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
                return;
            }

            // Proceed with task creation
            int assignedTo = Integer.parseInt(request.getParameter("assigned_to"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String deadlineStr = request.getParameter("deadline");

            Task task = new Task();
            task.setProjectId(projectId);
            task.setAssignedTo(assignedTo);
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus("pending");

            if (deadlineStr != null && !deadlineStr.isEmpty()) {
                task.setDeadline(LocalDate.parse(deadlineStr));
            }

            taskDAO.createTask(task);
            response.sendRedirect("ViewProject?projectId=" + projectId);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while creating task.");
        }
    }
    }

