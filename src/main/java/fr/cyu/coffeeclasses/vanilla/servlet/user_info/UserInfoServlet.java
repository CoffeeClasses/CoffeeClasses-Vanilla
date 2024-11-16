package fr.cyu.coffeeclasses.vanilla.servlet.user_info;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import fr.cyu.coffeeclasses.vanilla.database.dao.UserDAO;
import fr.cyu.coffeeclasses.vanilla.entity.user.User;

/**
 * Servlet implementation class UserInfoServlet
 */
@WebServlet("/user-info")
public class UserInfoServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("userId")==null){
			response.sendRedirect(getServletContext().getContextPath() + "/login");
		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/pages/user-info.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
