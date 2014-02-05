package test.Controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.Date;

import test.General.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.General.*;
import test.Model.Message;
import test.Model.User;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailAddress = request.getParameter("emailAddress");
		String hashedPassword = test.General.Security.getEncodedSha1Sum(request.getParameter("userPassword"));//password concatenated with email address
		System.out.println("Authenticating user: "+emailAddress + " with password hash: "+hashedPassword);
		User authenticatedUser = Authentication.authenticateUser(emailAddress, hashedPassword);
		if(authenticatedUser != null)
		{
			//Login was successful
			System.out.println("Login successful");
			for(Message m : MessageMethods.getFollowingMessages("admin"))
			{
				System.out.println(m.getText());
			}
			request.getSession().setAttribute("activeUser", authenticatedUser);
			response.sendRedirect("messages.jsp");
		}
		else
		{
			//Login was unsuccessful
			System.out.println("Login unsuccessful");
			request.setAttribute("errorMessage", "Invalid email address and/or password");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);//Same as POST
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
