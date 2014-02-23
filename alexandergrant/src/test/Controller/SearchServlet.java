package test.Controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.General.SearchMethods;
import test.General.ServletMethods;
import test.General.UserMethods;
import test.Model.Message;
import test.Model.User;

/**
 * Servlet implementation class Search
 */
@WebServlet({"/search","/search/*"})
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * This method implements a RESTful interface so that hash tags can link to a search showing other messages with that hash tag
	 * Also handles AJAX requests so that older and new messages can be loaded
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			User activeUser = (User)request.getSession().getAttribute("activeUser");
			String requestURI = request.getRequestURI();
			//if no user is signed in and this is an ajax request do nothing
			if("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) && activeUser == null)
			{
				return;
			}
			//if no user is signed in and this request isn't an AJAX request
			if(activeUser == null & !"XMLHttpRequest".equals(request.getHeader("X-Requested-With")))
			{
				//You can't search without being logged in, redirect to login screen
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				return;
			}
			//if the user goes to /search or /search/ then redirect them to search.jsp
			if(requestURI.equals(request.getContextPath()+"/search/") || requestURI.equals(request.getContextPath()+"/search") & !"XMLHttpRequest".equals(request.getHeader("X-Requested-With")))
			{
				response.sendRedirect(request.getContextPath()+"/search.jsp");
				return;
			}
			String searchTerm = "";//the search term the user is searching for
			String searchSelect = "";//searchSelect is going to be messages or hashtag
			if(request.getParameter("searchTerm") != null) //if post was used
			{
				searchTerm = request.getParameter("searchTerm");
				searchSelect = request.getParameter("searchSelect");
			}
			else//if the get search was used to search for a hash tag i.e /search/hashtag/hello
			{
				String[] split = requestURI.split("/");//split up the url using / as the delimiter
				searchSelect = split[split.length-2];//search/X/searchTerm where X is searchSelect
				searchTerm = split[split.length-1];//search/searchSelect/X where X is searchTerm
			}
			//user requests for hashtags, this is done through links given by hash tags in user messages
			if(searchSelect.equals("hashtag") & !"XMLHttpRequest".equals(request.getHeader("X-Requested-With")))//RESTFUL hashtag search
			{
				searchTerm = "#"+searchTerm;//add # to the start of the searchTerm so only messages with #searchTerm are searched for
				LinkedList<Message> messageList = new LinkedList<Message>();
				messageList = SearchMethods.searchForMessages(searchTerm);
				if(messageList.size() == 0)
				{
					request.setAttribute("errorMessage", "Your search returned no results.");
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
				request.setAttribute("messages", cutList);
				request.getRequestDispatcher("/searchresults.jsp").forward(request, response);
				return;
			}
			//AJAX requests for messages or hashtags
			else if(searchSelect.equals("messages") || (searchSelect.equals("hashtag") && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))))//GET more messages
			{
				if(searchSelect.equals("hashtag"))
				{
					searchTerm = "#" + searchTerm;//add # to the start of the searchTerm so only messages with #searchTerm are searched for
				}
				if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) && activeUser != null)
				{
					if(request.getParameter("oldestMessageId") == null)
					{
						ServletMethods.processNewSearchMessagesAJAX(request, response, requestURI, activeUser, searchTerm);//retrieve new search messages
						return;
					}
					else if(request.getParameter("newestMessageId") == null)
					{
						
					ServletMethods.processOldSearchMessagesAJAX(request, response, requestURI, activeUser, searchTerm);//processes the AJAX request and sends back the old messages
					return;
					}
				}
			}
		}

	/**
	 * Performs a search on either messages or users using POST
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchTerm = request.getParameter("searchTerm");
		String searchSelect = request.getParameter("searchSelect");
		User activeUser = (User)request.getSession().getAttribute("activeUser");
		if(activeUser == null)
		{
			//You can't search without being logged in, redirect to login screen
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//User is logged in
		if(searchSelect.equals("users"))//if users are being searched for
		{
			LinkedList<User> userList = new LinkedList<User>();
			userList = SearchMethods.searchForUsers(searchTerm);
			if(userList.size() == 0)
			{
				request.setAttribute("errorMessage", "Your search returned no results.");
			}
			for(User followedUser : UserMethods.getFollowing(activeUser.getUsername()))
			{
				for(User u : userList)
				{
					if(u.getUsername().equals(followedUser.getUsername()))
					{
						u.setIsActiveUserFollowing(true);
					}
				}
			}
			request.setAttribute("userList",userList);
			request.setAttribute("searchTerm", searchTerm);
			request.setAttribute("searchSelect", searchSelect);
			request.getRequestDispatcher("/searchresults.jsp").forward(request, response);
			return;
		}
		else if(searchSelect.equals("messages"))//if messages are being searched for
		{
			LinkedList<Message> messageList = new LinkedList<Message>();
			messageList = SearchMethods.searchForMessages(searchTerm);
			if(messageList.size() == 0)
			{
				request.setAttribute("errorMessage", "Your search returned no results.");
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
			request.setAttribute("messages", cutList);
			request.setAttribute("searchTerm", searchTerm);
			request.setAttribute("searchSelect", searchSelect);
			request.getRequestDispatcher("/searchresults.jsp").forward(request, response);
			return;
		}
	}

}
