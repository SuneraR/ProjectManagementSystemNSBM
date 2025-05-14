package servlet;

import dao.taskDAO;
import model.Task;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/SaveTaskServlet")
public class SaveTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private taskDAO TaskDAO;

    @Override
    public void init() throws ServletException {
        TaskDAO = new taskDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        String action=request.getParameter("status");
        User currentUser = (User) request.getSession().getAttribute("user");

        try {
            Task task = taskDAO.getTaskById(taskId);
            if (task == null) {
                response.sendRedirect("error.jsp?msg=Task not found");
                return;
            }

            if (task.getAssignedTo() != currentUser.getId()) {
                response.sendRedirect("error.jsp?msg=Unauthorized access");
                return;
            }

            // Mark the task as completed (or any business logic)
            task.setStatus(action);
            taskDAO.updateTaskStatus(task);

            response.sendRedirect("ViewProject?projectId=" + task.getProjectId());
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?msg=Database error");
        }
    }
}
