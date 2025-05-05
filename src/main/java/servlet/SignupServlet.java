package servlet;

import java.io.IOException;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import model.User.Role;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Input validation
        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("jsp/signup.jsp").forward(request, response);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("jsp/signup.jsp").forward(request, response);
            return;
        }
        
        if (password.length() < 8) {
            request.setAttribute("error", "Password must be at least 8 characters");
            request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
            return;
        }
        
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            request.setAttribute("error", "Invalid email format");
            request.getRequestDispatcher("jsp/signup.jsp").forward(request, response);
            return;
        }
        
        try {
            UserDAO userDao = new UserDAO();
            
            // Check if email already exists
            if (userDao.emailExists(email)) {
                request.setAttribute("error", "Email already registered");
                request.getRequestDispatcher("jsp/signup.jsp").forward(request, response);
                return;
            }
            
            // Hash password before storing
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            
            // Create new user with MEMBER role by default
            User newUser = new User(username, email, hashedPassword, Role.MEMBER);
            
            if (userDao.addUser(newUser)) {
                // Registration successful - set session and redirect
                HttpSession session = request.getSession();
                session.setAttribute("user", newUser);
                session.setAttribute("message", "Registration successful!");
                response.sendRedirect("jsp/dashboard.jsp");
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("jsp/signup.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred. Please try later.");
            request.getRequestDispatcher("jsp/signup.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error occurred");
            request.getRequestDispatcher("jsp/signup.jsp").forward(request, response);
        }
    }
}