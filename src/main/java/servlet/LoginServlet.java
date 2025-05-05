package servlet;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();
        try {
            if (dao.validateUser(username, password)) {
                response.sendRedirect("jsp/dashboard.jsp");
            } else {
                response.sendRedirect("jsp/login.jsp?error=1");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}