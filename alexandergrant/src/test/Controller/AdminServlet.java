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
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
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
		if(activeUser == null)
		{
			//if the user isn't logged in, send them to the login page
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		if(!activeUser.getIsAdmin())
		{
			//if they're not an admin, they can't use admin functionality
			response.sendRedirect(request.getContextPath()+"/404.jsp");
			return;
		}
		String username = request.getParameter("username");
		String emailAddress = request.getParameter("emailAddress");
		String hashedPassword = test.General.Security.getEncodedSha1Sum(request.getParameter("userPassword"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		//First name can only contain upper and lower case characters
		if(firstName.matches("^.*[^a-zA-Z ].*$"))
		{
			request.setAttribute("errorMessage", "Your first name must contain only letters.");
			request.getRequestDispatcher("admin.jsp").forward(request, response);
			return;
		}
		//Last name can only contain upper and lower case characters
		if(lastName.matches("^.*[^a-zA-Z ].*$"))
		{
			request.setAttribute("errorMessage", "Your last name must contain only letters.");
			request.getRequestDispatcher("admin.jsp").forward(request, response);
			return;
		}
		//Alphanumeric username regex, credit to http://stackoverflow.com/questions/8248277/how-to-determine-if-a-string-has-non-alphanumeric-characters
		if(username.matches("^.*[^a-zA-Z0-9 ].*$"))
		{
			request.setAttribute("errorMessage", "Usernames must be alphanumeric");
			request.getRequestDispatcher("admin.jsp").forward(request, response);
			return;
		}
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		//Again, I didn't write this regular expression. Credit to: http://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
		if(!emailAddress.matches(EMAIL_PATTERN))
		{
			request.setAttribute("errorMessage", "Invalid email address.");
			request.getRequestDispatcher("admin.jsp").forward(request, response);
			return;
		}
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
