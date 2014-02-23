package test.Controller;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.General.Authentication;
import test.General.MessageMethods;
import test.General.UserMethods;
import test.Model.Message;
import test.Model.User;

/**
 * Servlet implementation class FollowingServlet
 */
@WebServlet({ "/following", "/following/*" })
public class FollowingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FollowingServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Used to display the users that a user is following
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User activeUser = (User) request.getSession()
				.getAttribute("activeUser");
		if (activeUser == null) {
			// User is not logged in so redirect to login page
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		} 
		else 
		{
			// http://stackoverflow.com/questions/14316487/java-getting-a-substring-from-a-string-starting-after-a-particular-character
			String requestURI = request.getRequestURI();
			String username = requestURI.substring(request.getRequestURI().lastIndexOf("/") + 1);
			if (requestURI.equals(request.getContextPath() + "/following/") || requestURI.equals(request.getContextPath()+ "/following"))// if the URI simply ends in
											// /followers(/) with no username
											// appended
			{
				LinkedList<User> followingList = UserMethods.getFollowing(activeUser.getUsername());//get all users the active user is following
				if(followingList.size() == 1)// 1 because the user follows themselves so that they can see their own messages
				{
					request.setAttribute("notFollowing", "You don't appear to be following anyone!");
				}
				//Declare that all users in the followinglist are being followed by the active user
				for (User u : followingList) {
					u.setIsActiveUserFollowing(true);
				}
				// Display the active user's profile
				request.setAttribute("profileUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
				request.setAttribute("followingList",followingList);
				request.getRequestDispatcher("/following.jsp").forward(request,response);
				return;
			}
			if (Authentication.usernameRegistered(username))// Is the username
															// valid?
			{
				User profileUser = UserMethods.getUserFromUsername(username);//profile user is the user we are trying to find the users they are following for
				LinkedList<User> followingList = UserMethods.getFollowing(username);
				if (followingList.size() == 1)// 1 because the user follows themselves so that they can see their own messages
				{
					request.setAttribute("notFollowing","This user doesn't appear to be following anyone!");
				} 
				// Do a loop to determine whether the active user is
				// following the users in this other user's follower list
				// This is done so that the correct follow/unfollow button
				// can be displayed
				for (User u : UserMethods.getFollowing(activeUser.getUsername())) {
					for (User f : followingList) {
						if (u.getUsername().equals(f.getUsername())) {
							f.setIsActiveUserFollowing(true);
						}
					}
					if(profileUser.getUsername().equals(u.getUsername()))
					{
						profileUser.setIsActiveUserFollowing(true);
					}
				}
				request.setAttribute("profileUser",profileUser);
				request.setAttribute("followingList",followingList);
				request.getRequestDispatcher("/following.jsp").forward(request,response);
				return;
			}
			// User not found, 404.
			response.sendRedirect(request.getContextPath() + "/404.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User activeUser = (User) request.getSession().getAttribute("activeUser");
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
			if(Authentication.usernameRegistered(username))//Is the username valid?
			{
				//if the active user is not already following the user then follow that user
				if(!UserMethods.isFollowing(activeUser.getUsername(), username))
				{
					UserMethods.follow(activeUser.getUsername(), username);
				}
				return;
			}
			else
			{
			response.sendRedirect(request.getContextPath()+"/404.jsp");
			}
			}
		}

	/**
	 * Used to delete a follower
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User activeUser = (User) request.getSession().getAttribute("activeUser");
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
			if(Authentication.usernameRegistered(username))//Is the username valid?
			{
				//if the active user is following the user then stop following that user
				if(UserMethods.isFollowing(activeUser.getUsername(), username))
				{
					UserMethods.unfollow(activeUser.getUsername(), username);
				}
				return;
			}
			else
			{
			response.sendRedirect(request.getContextPath()+"/404.jsp");
			}
			}
		}
	}

