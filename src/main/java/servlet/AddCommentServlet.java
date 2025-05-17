package servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Comment;
import model.User;
import dao.CommentDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AddCommentServlet")
public class AddCommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	  HttpSession session = request.getSession();
          User currentUser = (User) session.getAttribute("user");
          
          if (currentUser == null) {
              response.sendRedirect("jsp/login.jsp");
              return;
          }
        // Get parameters from request
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        String commentText = request.getParameter("comment");

        // Create and populate Comment object
        Comment comment = new Comment();
        comment.setTaskId(taskId);
        comment.setUserId(userId);
        comment.setComment(commentText);

        try {
            // Insert comment into database
            CommentDAO commentDAO = new CommentDAO();
            commentDAO.insertComment(comment);
            
            // Redirect back to the appropriate page
            response.sendRedirect("ViewCommentsServlet?taskId=" + taskId);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid task or user ID format");
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
    }
}