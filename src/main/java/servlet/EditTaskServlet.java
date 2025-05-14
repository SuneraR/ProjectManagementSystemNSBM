package servlet;

import dao.ProjectDAO;
import dao.taskDAO;
import model.Project;
import model.Task;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/EditTaskServlet")
public class EditTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private taskDAO TaskDAO;
	private ProjectDAO projectDAO;

	@Override
	public void init() throws ServletException {
		TaskDAO = new taskDAO();
		projectDAO = new ProjectDAO(null);
	}

	// Show the form with task data (GET)
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		User currentUser = (User) request.getSession().getAttribute("user");

		try {
			Task task = null;
			try {
				task = taskDAO.getTaskById(taskId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (task == null) {
				response.sendRedirect("jsp/error.jsp?msg=Task not found");
				return;
			}

			Project project = projectDAO.getProjectById(task.getProjectId());
			if (project == null || project.getManagerId() != currentUser.getId()) {
				response.sendRedirect("jsp/error.jsp?msg=Unauthorized");
				return;
			}

			request.setAttribute("task", task);
			request.setAttribute("project", project);
			request.getRequestDispatcher("jsp/editTask.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("jsp/error.jsp?msg=Database error");
		}
	}

	// Process the form submission (POST)
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		User currentUser = (User) request.getSession().getAttribute("user");

		try {
			Task task = taskDAO.getTaskById(taskId);
			if (task == null) {
				response.sendRedirect("jsp/error.jsp?msg=Task not found");
				return;
			}

			Project project = projectDAO.getProjectById(task.getProjectId());
			if (project == null || project.getManagerId() != currentUser.getId()) {
				response.sendRedirect("jsp/error.jsp?msg=Unauthorized");
				return;
			}

			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String status = request.getParameter("status");
			String deadlineStr = request.getParameter("deadline");

			task.setTitle(title);
			task.setDescription(description);
			task.setStatus(status);

			if (deadlineStr != null && !deadlineStr.isEmpty()) {
				task.setDeadline(java.time.LocalDate.parse(deadlineStr));
			}

			taskDAO.updateTask(task);
			response.sendRedirect("ViewProject?projectId=" + task.getProjectId());

		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("jsp/error.jsp?msg=Update failed");
		}
	}
}
