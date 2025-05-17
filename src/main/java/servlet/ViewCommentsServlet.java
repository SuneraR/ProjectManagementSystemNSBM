package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Comment;
import model.User;
import dao.CommentDAO;

import java.io.IOException;
import java.util.List;
import java.sql.SQLException;

@WebServlet("/ViewCommentsServlet")
public class ViewCommentsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	  HttpSession session = request.getSession();
          User currentUser = (User) session.getAttribute("user");
          
          if (currentUser == null) {
              response.sendRedirect("jsp/login.jsp");
              return;
          }
        
        try {
            int taskId = Integer.parseInt(request.getParameter("taskId"));
            CommentDAO commentDAO = new CommentDAO();
            List<Comment> comments = commentDAO.getCommentsByTaskId(taskId);
            
            request.setAttribute("comments", comments);
            request.setAttribute("hasComments", !comments.isEmpty());
            request.setAttribute("taskId", taskId); // Make taskId available to JSP
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/viewComments.jsp");
            dispatcher.forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid task ID format");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
    }
}