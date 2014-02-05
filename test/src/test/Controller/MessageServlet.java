package test.Controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.General.MessageMethods;
import test.General.UserMethods;
import test.Model.Message;
import test.Model.User;

/**
 * Servlet implementation class BroadcastsServlet
 */
@WebServlet("/Messages")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
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
			//User is not logged in
			response.sendRedirect("login.jsp");
			return;
		}
		else
		{
			//User is logged in
			//SHOW JUST FOLLOWER MESSAGES
			List<Message> messageList = MessageMethods.getFollowingMessages(activeUser.getUsername());
			request.setAttribute("profileUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
			request.setAttribute("messages", messageList);
			request.getRequestDispatcher("/messages.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(request.getSession().getAttribute("activeUser") != null)
		{
			String message = request.getParameter("message");
			User user = (User)request.getSession().getAttribute("activeUser");
			MessageMethods.sendMessage(user.getUsername(), message);
			response.sendRedirect("messages.jsp");
		}
		else
		{
			response.sendRedirect("login.jsp");
		}
	}
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("activeUser") != null)
		{
			if(MessageMethods.createdMessage((User)request.getSession().getAttribute("activeUser"), (int)request.getAttribute("messageId")))
			{
				MessageMethods.deleteMessage((int)request.getAttribute("messageId"));
			}
			else
			{
				request.setAttribute("errorMessage", "You can only delete your own messages!");
			}
		}
	}

}
