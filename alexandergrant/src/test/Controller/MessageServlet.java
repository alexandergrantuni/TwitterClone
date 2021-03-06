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
import test.General.ServletMethods;
import test.General.UserMethods;
import test.Model.Message;
import test.Model.User;

/**
 * Servlet implementation class BroadcastsServlet
 */
@WebServlet({"/messages", "/messages/*"})
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
		
		//if it's an ajax request and there is no active user quit, this is done so that ajax doesn't send back login page html etc and add 
		//them to pages
		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) && activeUser == null)
		{
			return;
		}
		//check if the user is logged in
		if(activeUser == null)
		{
			//User is not logged in
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		else
		{
			//This is how you check whether a request is AJAX or not according to http://stackoverflow.com/questions/14004877/jsp-servlet-how-to-identify-if-the-http-request-came-from-an-ajax-request
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
				String requestURI = request.getRequestURI();
				String argument = requestURI.substring(request.getRequestURI().lastIndexOf("/") + 1);
				if(request.getParameter("oldestMessageId") == null)
				{
					ServletMethods.getNewMessagesAJAX(request, response, requestURI, activeUser, argument);
					return;
				}
				ServletMethods.processMessageMessagesAJAX(request, response, requestURI, activeUser, argument);//processes the AJAX request and sends back the old messages
				return;
			}
			//User is logged in
			String requestURI = request.getRequestURI();
			String argument = requestURI.substring(request.getRequestURI().lastIndexOf("/") + 1);
			if(requestURI.equals(request.getContextPath()+"/messages/") || requestURI.equals(request.getContextPath()+"/messages"))//if the URI simply ends in /followers(/) with no username appended
			{
			//SHOW JUST FOLLOWING MESSAGES
			List<Message> messageList = MessageMethods.getFollowingMessages(activeUser.getUsername());
			if(messageList.size() == 0)
			{
				request.setAttribute("noMessages", "There are no messages here.  Get the ball rolling by posting one yourself!");
			}
			List<Message> cutList = new LinkedList<Message>();
			int k = 0;
			for(Message m : messageList)
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
			request.setAttribute("messages", cutList);
			request.setAttribute("title", "Messages from Followed Users");
			request.getRequestDispatcher("/messages.jsp").forward(request, response);
			return;
			}
			else if(argument.equals("all"))
			{
				//SHOW ALL MESSAGES
				List<Message> messageList = MessageMethods.getAllMessages();
				List<Message> cutList = new LinkedList<Message>();
				int k = 0;
				for(Message m : messageList)
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
				request.setAttribute("messages", cutList);
				request.setAttribute("title", "All Messages");
				request.getRequestDispatcher("/messages.jsp").forward(request, response);
				return;
			}
			else
			{
				try
				{
				if(MessageMethods.messageExists(Integer.parseInt(argument)))
				{
					LinkedList<Message> messageList = new LinkedList<Message>();
					messageList.add(MessageMethods.getMessageById(Integer.parseInt(argument)));
					request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
					request.setAttribute("messages", messageList);
					request.setAttribute("title", "Displaying Message "+Integer.parseInt(argument));
					request.getRequestDispatcher("/messages.jsp").forward(request, response);
					return;
				}
				else
				{
					//Message not found and it didn't fit into any other criteria so 404
					response.sendRedirect(request.getContextPath()+"/404.jsp");
					return;
				}
				}
				//The message Id is not a number and all other cases failed so 404
				catch(java.lang.NumberFormatException nfe)
				{
					response.sendRedirect(request.getContextPath()+"/404.jsp");
					return;
				}
			}
		}
	}
	

	/**
	 * Used to post a message
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(request.getSession().getAttribute("activeUser") != null)
		{
			String message = request.getParameter("message");
			if(message.length() == 0)
			{
				request.setAttribute("sendMessageError", "You need to enter at least 1 character.");
				request.getRequestDispatcher("/messages.jsp").forward(request, response);
				return;
			}
			if(message.length() > 140)
			{
				request.setAttribute("sendMessageError", "The message you entered is too long!");
				request.getRequestDispatcher("/messages.jsp").forward(request, response);
				return;
			}
			User user = (User)request.getSession().getAttribute("activeUser");
			MessageMethods.sendMessage(user.getUsername(), message);
			response.sendRedirect(request.getContextPath()+"/messages");
		}
		else
		{
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
	}
	
	/**
	 * Called when the user wants to delete a message
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String argument = requestURI.substring(request.getRequestURI().lastIndexOf("/") + 1);
		
		int messageId = Integer.parseInt(argument);
		User activeUser = (User)request.getSession().getAttribute("activeUser");
		try
		{
			if(request.getSession().getAttribute("activeUser") != null)
			{
				if(MessageMethods.createdMessage(activeUser, messageId) || activeUser.getIsAdmin())
				{
					MessageMethods.deleteMessage(messageId);
				}
				else
				{
					request.setAttribute("errorMessage", "You can only delete your own messages!");
				}
			}
		}
		catch(java.lang.NumberFormatException nfe)
		{
			request.setAttribute("errorMessage", "An error occured when deleting the message");
		}
		
	}

}
