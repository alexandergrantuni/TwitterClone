package test.Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.General.Authentication;
import test.General.MessageMethods;
import test.General.UserMethods;
import test.Model.User;

/**
 * Servlet implementation class FollowerServlet
 */
@WebServlet({"/followers","/followers/*"})
public class FollowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User activeUser = (User) request.getSession().getAttribute("activeUser");
		System.out.println((User)activeUser);
		if(activeUser == null)
		{
			//User is not logged in so redirect to login page
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		else
		{
			//http://stackoverflow.com/questions/14316487/java-getting-a-substring-from-a-string-starting-after-a-particular-character
			String requestURI = request.getRequestURI();
			String username = requestURI.substring(request.getRequestURI().lastIndexOf("/") + 1);
			System.out.println(requestURI);
			if(requestURI.equals(request.getContextPath()+"/followers/") || requestURI.equals(request.getContextPath()+"/followers"))//if the URI simply ends in /followers(/) with no username appended
			{
				//Display the active user's profile
				System.out.println(UserMethods.getUserFromUsername(activeUser.getUsername()).getEmailAddress());
				request.setAttribute("profileUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
				request.setAttribute("followerList", UserMethods.getFollowers(activeUser.getUsername()));
				request.getRequestDispatcher("/followers.jsp").forward(request, response);
				return;
			}
			if(Authentication.usernameRegistered(username))//Is the username valid?
			{
				request.setAttribute("profileUser", UserMethods.getUserFromUsername(username));
				request.setAttribute("followerList", UserMethods.getFollowers(username));
				request.getRequestDispatcher("/followers.jsp").forward(request, response);
				return;
			}
			//User not found, 404.
			response.sendRedirect(request.getContextPath()+"/404.jsp");
			}
			
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getSession().getAttribute("activeUser") != null)
		{
			String message = request.getParameter("message");
			User user = (User)request.getSession().getAttribute("activeUser");
			MessageMethods.sendMessage(user.getUsername(), message);
			response.sendRedirect("messages.jsp");
		}
		else
		{
			//User is not logged in
			response.sendRedirect("login.jsp");
		}
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
