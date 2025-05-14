package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Project;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.ProjectDAO;
import dao.UserDAO;
import dao.taskDAO;

/**
 * Servlet implementation class UserProjectServlet
 */
@WebServlet("/UserProjectServlet")
public class UserProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  
	    private ProjectDAO projectDAO;
	  

	    @Override
	    public void init() {
	        projectDAO = new ProjectDAO(null);
	    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
		int userId = 0 ;
		if(currentUser!=null) {
			userId=currentUser.getId();
		}
		List<Project> userProjects = null;
		try {
			userProjects = projectDAO.getProjectsByAssignedUser(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("projects", userProjects);
		request.getRequestDispatcher("jsp/userProjects.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
