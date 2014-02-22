package test.General;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.Model.Message;
import test.Model.User;

public class ServletMethods {
	public static void processProfileMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, String username, User activeUser) throws ServletException, IOException
	{
		if(Authentication.usernameRegistered(username) || requestURI.equals(request.getContextPath()+"/profile/"))
		{
			if(requestURI.equals(request.getContextPath()+"/profile/"))
			{
				username = activeUser.getUsername();
			}
			LinkedList<Message> messageList = MessageMethods.getUserMessages(username);
			int messageCount = Integer.parseInt((request.getParameter("messageCount")));
			int totalMessages = Integer.parseInt(request.getParameter("totalMessages"));
	
			int newCount = messageList.size() - totalMessages;
			LinkedList<Message> newMessages = new LinkedList<Message>();
			int j =0;
			if(messageCount < totalMessages)
			{
				for(int i = (messageCount+newCount); i < totalMessages+newCount;i++)
				{
					if(j == 10)
					{
						break;
					}
					if(messageList.get(i) != null)
					{
						newMessages.add(messageList.get(i));
					}
					j++;
				}
			}
			request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
			request.setAttribute("messages", newMessages);
			request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
		}
	}
	
	public static void processNewProfileMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, String username, User activeUser) throws ServletException, IOException
	{
		if(Authentication.usernameRegistered(username) || requestURI.equals(request.getContextPath()+"/profile/"))
		{
			System.out.println("fetching new profile messages");
			if(requestURI.equals(request.getContextPath()+"/profile/"))
			{
				username = activeUser.getUsername();
			}
			LinkedList<Message> messageList = MessageMethods.getUserMessages(username);
			int totalMessages = Integer.parseInt(request.getParameter("totalMessages"));

			LinkedList<Message> newMessages = new LinkedList<Message>();
			int j =0;
			int newCount = messageList.size() - totalMessages;
			for(int i = 0; i < newCount; i++)
			{
				messageList.get(i).setIsNew(true);
				newMessages.add(messageList.get(i));
			}
			request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
			request.setAttribute("messages", newMessages);
			request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
		}
	}
	
	//Handles AJAX requests from /messages/*
	public static void processMessageMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, User activeUser,String argument) throws ServletException, IOException
	{
		if(argument.equals("all"))
		{
			LinkedList<Message> messageList = MessageMethods.getAllMessages();
			int messageCount = Integer.parseInt((request.getParameter("messageCount")));
			int totalMessages = Integer.parseInt(request.getParameter("totalMessages"));

			int newCount = messageList.size() - totalMessages;
			LinkedList<Message> newMessages = new LinkedList<Message>();
			int j =0;
			if(messageCount < totalMessages)
			{
				for(int i = (messageCount+newCount); i < totalMessages+newCount;i++)
				{
					if(j == 10)
					{
						break;
					}
					if(messageList.get(i) != null)
					{
						newMessages.add(messageList.get(i));
					}
					j++;
				}
			}
			request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
			request.setAttribute("messages", newMessages);
			request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
			return;
		}
		else if(argument.equals("messages"))
		{
			LinkedList<Message> messageList = MessageMethods.getFollowingMessages(activeUser.getUsername());
			int messageCount = Integer.parseInt((request.getParameter("messageCount")));
			int totalMessages = Integer.parseInt(request.getParameter("totalMessages"));
			
			LinkedList<Message> newMessages = new LinkedList<Message>();
			int newCount = messageList.size() - totalMessages;
			int j =0;
			if(messageCount < totalMessages)
			{
				for(int i = messageCount+newCount; i < totalMessages+newCount;i++)
				{
					if(j == 10)
					{
						break;
					}
					if(messageList.get(i) != null)
					{
						newMessages.add(messageList.get(i));
					}
					j++;
				}
			}
				
				request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
				request.setAttribute("messages", newMessages);
				request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
				return;
			}
			return;
		}

	public static void createSchema(HttpServletResponse response){
		Connection conn = Database.getConnection();
		String sqlcreateSchema="Create database if not exists alexandergrantdb ;";
		try{
			java.sql.Statement statement=conn.createStatement();
			statement.execute(sqlcreateSchema);
			conn.close();
		}catch (Exception et){
			System.out.println("Can not create schema ");
			try {
				response.sendError(403);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		createUsers();
		createMessages();
		createFollowing();
		/*
		try {
			PreparedStatement query = null;
			query = conn.prepareStatement("DROP TABLE IF EXISTS `alexandergrantdb.secretquestion`;");
			query.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		*/


	}
	
	public static void createFollowing()
	{
		Connection conn = Database.getConnection();
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement("DROP TABLE IF EXISTS `following`;");

		query.executeUpdate();
		PreparedStatement q = null;
		q = conn.prepareStatement("CREATE TABLE `following` ("
+"`FollowId` int(11) NOT NULL auto_increment,"
+"`UserId` varchar(20) default NULL,"
+"`FollowingUserId` varchar(20) default NULL,"
+"PRIMARY KEY  (`FollowId`),"
+"KEY `UserId` (`UserId`),"
+"KEY `FollowingUserId` (`FollowingUserId`),"
+"CONSTRAINT `FollowingUserId` FOREIGN KEY (`FollowingUserId`) REFERENCES `users` (`Username`),"
+"CONSTRAINT `UserId` FOREIGN KEY (`UserId`) REFERENCES `users` (`Username`)"
+") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
		q.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createMessages()
	{
		
		Connection conn = Database.getConnection();
		try {
			PreparedStatement query = null;
			query = conn.prepareStatement("DROP TABLE IF EXISTS `message`;");
			query.executeUpdate();
			PreparedStatement query2 = null;
			query2 = conn.prepareStatement("CREATE TABLE `message` ("
					  +"`MessageId` int(11) NOT NULL auto_increment,"
					  +"`Text` varchar(140) default NULL,"
					  +"`Timestamp` int(11) default NULL,"
					  +"`Username` varchar(40) default NULL,"
					  +"PRIMARY KEY  (`MessageId`),"
					  +"KEY `Username` (`Username`),"
					  +"CONSTRAINT `Username` FOREIGN KEY (`Username`) REFERENCES `users` (`Username`)"
					+") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
			query2.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void createUsers()
	{
		Connection conn = Database.getConnection();
		try {
			PreparedStatement query2 = null;
			query2 = conn.prepareStatement("DROP TABLE IF EXISTS `following`;");
			query2.executeUpdate();
			PreparedStatement query = null;
			query = conn.prepareStatement("DROP TABLE IF EXISTS `users`;");
			query.executeUpdate();
			PreparedStatement q = null;
			q = conn.prepareStatement("CREATE TABLE `users` ("
  +"`Username` varchar(20) NOT NULL default '0',"
  +"`EmailAddress` varchar(255) NOT NULL default '',"
  +"`Password` varchar(50) default NULL,"
  +"`FirstName` varchar(255) default NULL,"
  +"`LastName` varchar(255) default NULL,"
  +"`bio` varchar(160) default NULL,"
  +"`ProfilePicture` varchar(255) default NULL,"
  +"`IsAdministrator` int(11) default NULL,"
  +"`AccountCreationTimestamp` int(11) default NULL,"
  +"PRIMARY KEY  (`Username`,`EmailAddress`)"
  +") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
			q.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}



			
	
	//Handles AJAX requests from /messages/*
		public static void getNewMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, User activeUser,String argument) throws ServletException, IOException
		{
			if(requestURI.equals(request.getContextPath() + "/messages/all"))
			{
				LinkedList<Message> messageList = MessageMethods.getAllMessages();
				int newestMessageId = Integer.parseInt(request.getParameter("newestMessageId"));

				LinkedList<Message> newMessages = new LinkedList<Message>();
				for(int i = 0; i < messageList.size(); i++)
				{
					System.out.println("messageList.get(i).getMessageId() = "+messageList.get(i).getMessageId()+ "newestMessageId = "+newestMessageId);
					if(messageList.get(i).getMessageId() > newestMessageId)
					{
						messageList.get(i).setIsNew(true);
						newMessages.add(messageList.get(i));
					}
				}
				request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
				request.setAttribute("messages", newMessages);
				request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
				return;
			}
			else if(requestURI.contains(request.getContextPath() +"/messages"))
			{
				LinkedList<Message> messageList = MessageMethods.getFollowingMessages(activeUser.getUsername());
				int newestMessageId = Integer.parseInt(request.getParameter("newestMessageId"));

				LinkedList<Message> newMessages = new LinkedList<Message>();
				for(int i = 0; i < messageList.size(); i++)
				{
					if(messageList.get(i).getMessageId() > newestMessageId)
					{
						messageList.get(i).setIsNew(true);
						newMessages.add(messageList.get(i));
					}
				}
				request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
				request.setAttribute("messages", newMessages);
				request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
				return;
			}
		}
	public static void processSearchMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, User activeUser,String searchTerm) throws ServletException, IOException
	{
		LinkedList<Message> messageList = SearchMethods.searchForMessages(searchTerm);
		if(request.getParameter("messageCount") == null)
		{
			int totalMessages = Integer.parseInt(request.getParameter("totalMessages"));

			LinkedList<Message> newMessages = new LinkedList<Message>();
			int j =0;
			int newCount = messageList.size() - totalMessages;
			for(int i = 0; i < newCount; i++)
			{
				messageList.get(i).setIsNew(true);
				newMessages.add(messageList.get(i));
			}
			request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
			request.setAttribute("messages", newMessages);
			request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
			return;
		}
		int messageCount = Integer.parseInt((request.getParameter("messageCount")));
		int totalMessages = Integer.parseInt(request.getParameter("totalMessages"));

		int newCount = messageList.size() - totalMessages;
		LinkedList<Message> newMessages = new LinkedList<Message>();
		int j =0;
		if(messageCount < totalMessages)
		{
			for(int i = (messageCount+newCount); i < totalMessages+newCount;i++)
			{
				if(j == 10)
				{
					break;
				}
				if(messageList.get(i) != null)
				{
					newMessages.add(messageList.get(i));
				}
				j++;
			}
		}
		request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
		request.setAttribute("messages", newMessages);
		request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
		return;
	}
	public static int getIndexByMessageId(LinkedList<Message> messageList, int messageId)
	{
		int k = 0;
		for(Message m : messageList)
		{
			if(m.getMessageId() == messageId)
			{
				return k;
			}
			k++;
		}
		return -1;
	}
}
