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
 * Servlet implementation class ProfileServlet
 */
@WebServlet({"/profile/","/profile/*"})
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			if(requestURI.equals(request.getContextPath()+"/profile/"))//if the URI simply ends in /profile/ with no username appended
			{
				//Display the active user's profile
				LinkedList<Message> activeUserMessages = MessageMethods.getUserMessages(activeUser.getUsername());
				if(activeUserMessages.size() == 0)
				{
					request.setAttribute("noMessages", "You haven't posted any messages yet!");
				}
				request.setAttribute("profileUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
				request.setAttribute("messages", activeUserMessages);
				request.getRequestDispatcher("/profile.jsp").forward(request, response);
				return;
			}
			if(Authentication.usernameRegistered(username))//Is the username valid?
			{
				User profileUser = UserMethods.getUserFromUsername(username);
				for(User u : UserMethods.getFollowing(activeUser.getUsername()))
				{
					if(u.getUsername().equals(profileUser.getUsername()))
					{
						profileUser.setIsActiveUserFollowing(true);
						break;
					}
				}
				request.setAttribute("profileUser", profileUser);
				request.setAttribute("messages", MessageMethods.getUserMessages(username));
				request.getRequestDispatcher("/profile.jsp").forward(request, response);
				return;
			}
			response.sendRedirect(request.getContextPath()+"/404.jsp");
			}
			
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
