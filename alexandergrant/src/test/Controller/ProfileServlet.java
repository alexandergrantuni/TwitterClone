package test.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.General.Authentication;
import test.General.Database;
import test.General.MessageMethods;
import test.General.Security;
import test.General.ServletMethods;
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
		//if there is no active user, do nothing with AJAX
		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) && activeUser == null)
		{
			return;
		}
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
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")))
			{
				if(request.getParameter("oldestMessageId") == null)
				{
					ServletMethods.processNewProfileMessagesAJAX(request, response, requestURI, username, activeUser);
					return;
				}
				//This is an AJAX request, process the AJAX request
				ServletMethods.processProfileMessagesAJAX(request,response, requestURI, username, activeUser);
				return;
			}
			if(requestURI.equals(request.getContextPath()+"/profile/"))//if the URI simply ends in /profile/ with no username appended
			{
				//Handle AJAX request
				//Display the active user's profile
				LinkedList<Message> activeUserMessages = MessageMethods.getUserMessages(activeUser.getUsername());
				if(activeUserMessages.size() == 0)
				{
					request.setAttribute("noMessages", "You haven't posted any messages yet!");
				}
				List<Message> cutList = new LinkedList<Message>();
				int k = 0;
				for(Message m : activeUserMessages)
				{
					if(k < 10)
					{
						cutList.add(m);
					}
					else
					{
						break;
					}
					k++;
				}
				request.setAttribute("profileUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
				request.setAttribute("messages", cutList);
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
				LinkedList<Message> activeUserMessages = MessageMethods.getUserMessages(profileUser.getUsername());
				if(activeUserMessages.size() == 0)
				{
					request.setAttribute("noMessages", profileUser.getUsername() +" hasn't posted any messages yet!");
				}
				List<Message> cutList = new LinkedList<Message>();
				int k = 0;
				for(Message m : activeUserMessages)
				{
					if(k < 10)
					{
						cutList.add(m);
					}
					else
					{
						break;
					}
					k++;
				}
				request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
				request.setAttribute("profileUser", profileUser);
				request.setAttribute("messages", cutList);
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
		String username = (String)request.getParameter("username");
		String email = (String)request.getParameter("email");
		String firstName = (String)request.getParameter("firstName");
		String lastName = (String)request.getParameter("lastName");
		
		String bio = (String)request.getParameter("bio");
		String oldPassword = (String)request.getParameter("oldPassword");
		String newPassword = (String)request.getParameter("newPassword");
		String confirmNewPassword = (String)request.getParameter("confirmNewPassword");
		
		User activeUser = (User)request.getSession().getAttribute("activeUser");
		
		if(activeUser == null)
		{
            response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		
		if(firstName.matches("^.*[^a-zA-Z ].*$"))
		{
			request.setAttribute("errorMessage", "Your first name must only contain letters.");
			request.getRequestDispatcher("editprofile.jsp").forward(request, response);
			return;
		}
		//Last name can only contain upper and lower case characters
		if(lastName.matches("^.*[^a-zA-Z ].*$"))
		{
			request.setAttribute("errorMessage", "Your last name must only contain letters.");
			request.getRequestDispatcher("editprofile.jsp").forward(request, response);
			return;
		}
	
		if(!username.equals(activeUser.getUsername()) || !email.equals(activeUser.getEmailAddress()))
		{
			request.setAttribute("errorMessage", "You are unable to edit another user's details.");
			request.getRequestDispatcher("/editprofile.jsp").forward(request, response);
			return;
		}
		//If any fields have been left empty..
		if(lastName.equals("") || firstName.equals("") || email.equals("") || username.equals(""))
		{
			request.setAttribute("errorMessage", "You cannot leave any fields blank.");
			request.getRequestDispatcher("/editprofile.jsp").forward(request, response);
			return;
		}
		//If the two entered passwords match
		if(!newPassword.equals(confirmNewPassword))
		{
			request.setAttribute("errorMessage", "The passwords you entered did not match.");
			request.getRequestDispatcher("/editprofile.jsp").forward(request, response);
			return;
		}
		//If the bio is empty
		if(bio.length() == 0)
		{
			request.setAttribute("errorMessage", "Please enter a bio.");
			request.getRequestDispatcher("/editprofile.jsp").forward(request, response);
			return;
		}
		//Verify that the username has the correct password
		if(Authentication.verifyUsernameAndPassword(username, oldPassword))
		{
			if(UserMethods.updateDetails(username,firstName, lastName, bio, confirmNewPassword))
			{
            	//Update the current session with the up to date details of the user
            	request.getSession().setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
                response.sendRedirect(request.getContextPath()+"/editprofilesuccess.jsp");
                return;
			}
			else
			{
	        	request.setAttribute("errorMessage", "Something went wrong.  Try again later.");
	    		request.getRequestDispatcher("/editprofile.jsp").forward(request, response);
	    		return;
			}
		}
		else
		{
        	request.setAttribute("errorMessage", "You entered your old password incorrectly.");
    		request.getRequestDispatcher("/editprofile.jsp").forward(request, response);
    		return;
		}
		
		
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User activeUser = (User)request.getSession().getAttribute("activeUser");
		if(activeUser == null)
		{
			//You can't search without being logged in, redirect to login screen
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		else
		{
			UserMethods.deleteAccount(activeUser);
			request.getSession().setAttribute("activeUser", null);
		}
	}

}
