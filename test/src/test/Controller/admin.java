package test.Controller;

import java.io.IOException;
import java.util.Date;

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
 * Servlet implementation class admin
 */
@WebServlet({"/admin","/admin/*"})
public class admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User activeUser = (User)request.getSession().getAttribute("activeUser");
		if(activeUser == null)
		{
			response.sendRedirect(request.getContextPath()+"/404.jsp");
			return;
		}
		if(!activeUser.getIsAdmin())
		{
			//if they're not an admin, they can't use admin functionality
			response.sendRedirect(request.getContextPath()+"/404.jsp");
			return;
		}
		response.sendRedirect(request.getContextPath()+"/admin.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User activeUser = (User)request.getSession().getAttribute("activeUser");
		if(!activeUser.getIsAdmin())
		{
			//if they're not an admin, they can't use admin functionality
			response.sendRedirect(request.getContextPath()+"/404.jsp");
			return;
		}
		Date now = new Date();
		String username = request.getParameter("username");
		String emailAddress = request.getParameter("emailAddress");
		String hashedPassword = test.General.Security.getEncodedSha1Sum(request.getParameter("userPassword"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String bio = "";
		String profilePicture = "";//TODO SET THIS TO DEFAULT IMG URL
		if(Authentication.usernameRegistered(username))
		{
			//This username is already in use so set the error message to 'Username is already in use.'
			request.setAttribute("errorMessage", "Username is already in use.");
			request.getRequestDispatcher("admin.jsp").forward(request, response);
			return;
		}
		if(Authentication.emailAddressRegistered(emailAddress))
		{
			//This email address is already in use so set the error message to 'Email address is already in use.'
			request.setAttribute("errorMessage", "Email address is already in use.");
			request.getRequestDispatcher("admin.jsp").forward(request, response);
			return;
		}
		if(Authentication.RegisterAdmin(emailAddress, username, hashedPassword, firstName, lastName))
		{
			if(activeUser != null)
			{
				//Registration was successful
				System.out.println("Registration successful");
				UserMethods.follow(username, username);//follow themselves so that they can see their own messages
				MessageMethods.sendMessage(username, "Hello everyone! I just registered.");
				response.sendRedirect(request.getContextPath()+"/registrationsuccessful.jsp");
			}
		}
		else
		{
			//Registration was unsuccessful
			System.out.println("Registration unsuccessful");
			request.setAttribute("errorMessage", "Something went wrong and we're not sure what.  Try again later.");
			request.getRequestDispatcher("admin.jsp").forward(request, response);
		}
	}

}
