package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import dao.taskDAO;

/**
 * Servlet implementation class DeleteTaskServlet
 */
@WebServlet("/DeleteTaskServlet")
public class DeleteTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    private taskDAO TaskDAO;

    @Override
    public void init() throws ServletException {
        TaskDAO = new taskDAO(); // Assuming TaskDAO has a no-arg constructor
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int taskId = Integer.parseInt(request.getParameter("taskId"));
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        boolean deleted = false;
        try {
			deleted = taskDAO.deleteTask(taskId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (deleted) {
		    response.sendRedirect("ViewProject?projectId="+projectId);
		} else {
		    response.getWriter().println("Task not found or couldn't be deleted.");
		}
    }

}
