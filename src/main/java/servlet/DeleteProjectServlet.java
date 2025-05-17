package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import dao.ProjectDAO;


@WebServlet("/DeleteProjectServlet")

public class DeleteProjectServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ProjectDAO projectDAO;

    @Override
    public void init() throws ServletException {
        projectDAO = new ProjectDAO(null);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("projectId"));

        try {
            boolean deleted = projectDAO.deleteProject(projectId);
            if (deleted) {
                response.sendRedirect("ProjectServlet"); // redirect to updated project list
            } else {
                response.getWriter().println("Project not found or couldn't be deleted.");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error while deleting project", e);
        }
    }
}

