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
			if(requestURI.equals(request.getContextPath()+"/followers/") || requestURI.equals(request.getContextPath()+"/followers"))//if the URI simply ends in /followers(/) with no username appended
			{
				LinkedList<User> followerList = UserMethods.getFollowers(activeUser.getUsername());
				if(followerList.size() == 1)//1 because the user follows themselves so they can see their own message
				{
					request.setAttribute("noFollowers", "You don't appear to have any followers!");
				}
				//Sets whether users that are following the active user are being followed by the active user
				for(User u : UserMethods.getFollowing(activeUser.getUsername()))
				{
					for(User f : followerList)
					{
						if(u.getUsername().equals(f.getUsername()))
						{
							f.setIsActiveUserFollowing(true);
						}
					}
				}
				//Display the active user's profile
				request.setAttribute("profileUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
				request.setAttribute("followerList", followerList);
				request.getRequestDispatcher("/followers.jsp").forward(request, response);
				return;
			}
			if(Authentication.usernameRegistered(username))//Is the username valid?
			{
				LinkedList<User> followerList = UserMethods.getFollowers(username);
				User profileUser = UserMethods.getUserFromUsername(username);
				if(followerList.size() == 1)//1 because the user follows themselves so they can see their own message
				{
					request.setAttribute("noFollowers", "This user doesn't appear to have any followers!");
				}
				//Do a loop to determine whether the active user is following the users in this other user's follower list
				//This is done so that the correct follow/unfollow button can be displayed
				for(User u : UserMethods.getFollowing(activeUser.getUsername()))
				{
					for(User f : followerList)
					{
						if(u.getUsername().equals(f.getUsername()))
						{
							f.setIsActiveUserFollowing(true);
						}
					}
					if(profileUser.getUsername().equals(u.getUsername()))
					{
						profileUser.setIsActiveUserFollowing(true);
					}
				}
				request.setAttribute("profileUser", profileUser);
				request.setAttribute("followerList", followerList);
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
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
