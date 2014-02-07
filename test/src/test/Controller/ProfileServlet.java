package test.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.General.Authentication;
import test.General.Database;
import test.General.MessageMethods;
import test.General.Security;
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
		
		System.out.println(username);
		System.out.println(email);
		System.out.println(newPassword);
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
		if(newPassword.equals(confirmNewPassword))
		{
			request.setAttribute("errorMessage", "The passwords you entered did not match.");
			request.getRequestDispatcher("/editprofile.jsp").forward(request, response);
			return;
		}
		
		//Verify that the username has the correct password
		if(Authentication.verifyUsernameAndPassword(username, oldPassword))
		{
			Connection connection = Database.getConnection();
    		PreparedStatement query = null;
    		newPassword = Security.getEncodedSha1Sum(newPassword);
	        try 
	        {
	        		//"SELECT * FROM users WHERE Username = ? AND Password = ?;");
	                query = (PreparedStatement) connection.prepareStatement("UPDATE users SET FirstName=?, LastName=?, bio=?, Password=? WHERE Username=?");
	                query.setString(1, username);
	                query.setString(2, lastName);
	                query.setString(3, bio);
	                query.setString(4, newPassword);
	                query.setString(5, username);
	                int result = query.executeUpdate();
	                //if there is a result
	                if(result == 1)
	                {
	                	connection.close();	   
	                	//Update the current session with the up to date details of the user
	                	request.getSession().setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
		                response.sendRedirect(request.getContextPath()+"/editprofilesuccess.jsp");
	        			return;
	                }
	                connection.close();
                	request.setAttribute("errorMessage", "Something went wrong.  Try again later.");
        			request.getRequestDispatcher("/editprofile.jsp").forward(request, response);
        			return;
	        }
	        catch(Exception ex)
	        {
	                ex.printStackTrace();
	                try
	                {
	                    query.close();
	                    connection.close();
	                }
	                catch (SQLException sqle)
	                {
	                        sqle.printStackTrace();
	                }
	                
	        }
		}
		
		
	}

}
