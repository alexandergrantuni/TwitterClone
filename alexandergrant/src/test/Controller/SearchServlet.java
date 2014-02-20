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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			User activeUser = (User)request.getSession().getAttribute("activeUser");
			String requestURI = request.getRequestURI();
			System.out.println("in search1");
			if(activeUser == null)
			{
				//You can't search without being logged in, redirect to login screen
				response.sendRedirect(request.getContextPath()+"/login.jsp");
				return;
			}
			System.out.println("in search2");
			if(requestURI.equals(request.getContextPath()+"/search/") || requestURI.equals(request.getContextPath()+"/search"))
			{
				response.sendRedirect(request.getContextPath()+"/search.jsp");
				return;
			}
			System.out.println("in search3");
			String[] split = requestURI.split("/");
			String searchSelect = split[split.length-2];
			String searchTerm = split[split.length-1];
			searchTerm = searchTerm.replace("%20", " ");
			if(split.length < 5)
			{
				request.setAttribute("errorMessage", "You need to enter a search term.");
				request.getRequestDispatcher("/searchresults.jsp").forward(request, response);
				return;
			}
		
			if(split.length > 5)
			{
				request.setAttribute("errorMessage", "You are trying to perform an invalid search.");
				request.getRequestDispatcher("/searchresults.jsp").forward(request, response);
				return;
			}
			System.out.println("in search4");
			//User is logged in
			if(searchSelect.equals("users"))
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
				request.getRequestDispatcher("/searchresults.jsp").forward(request, response);
				return;
			}
			else if(searchSelect.equals("messages"))
			{
				System.out.println("in search5");
				if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")))
				{
					System.out.println("in search6");
					ServletMethods.processSearchMessagesAJAX(request, response, requestURI, activeUser, searchTerm);
					return;
				}
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
				request.setAttribute("totalMessages", messageList.size());
				request.getRequestDispatcher("/searchresults.jsp").forward(request, response);
				return;
			}
		}

	/**
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
		}
		else
		{
			response.sendRedirect(request.getContextPath()+"/search/"+searchSelect+"/"+searchTerm);
		}
	}

}
