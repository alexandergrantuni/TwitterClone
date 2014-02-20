package test.Controller;

import java.io.IOException;

import test.General.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PasswordResetServlet
 */
@WebServlet("/PasswordResetServlet")
public class PasswordResetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordResetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	response.sendRedirect("passwordreset.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String emailAddress = request.getParameter("emailAddress");
		String username = request.getParameter("username");
		
		if(!Authentication.usernameRegistered(username))
		{
			//This username is already in use so set the error message to 'The username '"+username+"' does not exist.'
			request.setAttribute("errorMessage", "The username '"+username+"' has not been registered.");
			request.getRequestDispatcher(request.getContextPath()+"/passwordreset.jsp").forward(request, response);
			return;
		}
		if(!Authentication.emailAddressRegistered(emailAddress))
		{
			//This email address is already in use so set the error message to 'The email address '"+emailAddress+"' does not exist.'
			request.setAttribute("errorMessage", "The email address '"+emailAddress+"' has not been registered.");
			request.getRequestDispatcher(request.getContextPath()+"/passwordreset.jsp").forward(request, response);
			return;
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

	}

}
