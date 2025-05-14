package servlet;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            UserDAO dao = new UserDAO();
            User user = dao.getUserByUsername(username);
            
            if (user == null) {
                // User not found
                response.sendRedirect("jsp/login.jsp?error=1");
                return;
            }
            
            try {
                // Verify password with BCrypt
                if (BCrypt.checkpw(password, user.getPasswordHash())) {
                    // Successful login
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    
                    // Redirect based on role
                    String redirectPage = user.getRole() == User.Role.ADMIN
                        ? "jsp/dashboard.jsp" 
                        : "jsp/dashboard.jsp";
                    response.sendRedirect(redirectPage);
                } else {
                    // Invalid password
                    response.sendRedirect("jsp/login.jsp?error=1");
                }
            } catch (IllegalArgumentException e) {
                // This catches cases where password hash is invalid
                response.sendRedirect("jsp/login.jsp?error=2");
            }

        } catch (SQLException e) {
            // Log the full error
            e.printStackTrace(); // This will show in server logs
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
        }
        }
    }
