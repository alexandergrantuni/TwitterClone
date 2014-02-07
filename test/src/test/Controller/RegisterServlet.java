package test.Controller;

import java.io.IOException;
import java.util.Date;

import test.General.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.General.Authentication;
import test.Model.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("activeUser") != null)
		{
			//Already logged in
			response.sendRedirect("messages.jsp");
		}
		else
		{
			//Not logged in
			response.sendRedirect("register.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		if(Authentication.emailAddressRegistered(emailAddress))
		{
			//This email address is already in use so set the error message to 'Email address is already in use.'
			request.setAttribute("errorMessage", "Email address is already in use.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		if(Authentication.Register(emailAddress, username, hashedPassword, firstName, lastName))
		{
			User activeUser = Authentication.authenticateUser(emailAddress, hashedPassword);
			if(activeUser != null)
			{
				//Registration was successful
				System.out.println("Registration successful");
				request.getSession().setAttribute("activeUser", activeUser);
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
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}
	}
	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);//Same as POST
	}

}
