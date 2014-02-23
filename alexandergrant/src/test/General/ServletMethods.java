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
	//gets older messages to display on a user's profile page
	public static void processProfileMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, String username, User activeUser) throws ServletException, IOException
	{
		if(Authentication.usernameRegistered(username) || requestURI.equals(request.getContextPath()+"/profile/"))
		{
			if(requestURI.equals(request.getContextPath()+"/profile/"))
			{
				username = activeUser.getUsername();
			}
			LinkedList<Message> messageList = MessageMethods.getUserMessages(username);
			int oldestMessageId = Integer.parseInt((request.getParameter("oldestMessageId")));
			
			request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
			request.setAttribute("messages", getOldMessages(messageList,oldestMessageId));
			request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
		}
	}
	//gets new messages to display on a user's profile page
	public static void processNewProfileMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, String username, User activeUser) throws ServletException, IOException
	{
		if(Authentication.usernameRegistered(username) || requestURI.equals(request.getContextPath()+"/profile/"))
		{
			if(requestURI.equals(request.getContextPath()+"/profile/"))
			{
				username = activeUser.getUsername();
			}
			LinkedList<Message> messageList = MessageMethods.getUserMessages(username);
			int newestMessageId = Integer.parseInt(request.getParameter("newestMessageId"));

			request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
			request.setAttribute("messages", getNewMessages(messageList, newestMessageId));
			request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
		}
	}
	
	//Handles AJAX requests from /messages/*
	public static void processMessageMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, User activeUser,String argument) throws ServletException, IOException
	{
		if(argument.equals("all"))
		{
			LinkedList<Message> messageList = MessageMethods.getAllMessages();
			int oldestMessageId = Integer.parseInt((request.getParameter("oldestMessageId")));
			request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
			request.setAttribute("messages", getOldMessages(messageList, oldestMessageId));
			request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
			return;
		}
		else if(argument.equals("messages"))
		{
			LinkedList<Message> messageList = MessageMethods.getFollowingMessages(activeUser.getUsername());
			int oldestMessageId = Integer.parseInt((request.getParameter("oldestMessageId")));
			request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
			request.setAttribute("messages", getOldMessages(messageList, oldestMessageId));
			request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
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
				request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
				request.setAttribute("messages", getNewMessages(messageList, newestMessageId));
				request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
				return;
			}
			else if(requestURI.contains(request.getContextPath() +"/messages"))
			{
				LinkedList<Message> messageList = MessageMethods.getFollowingMessages(activeUser.getUsername());
				int newestMessageId = Integer.parseInt(request.getParameter("newestMessageId"));
				request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
				request.setAttribute("messages", getNewMessages(messageList, newestMessageId));
				request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
				return;
			}
		}
		//displays older messages on the search page
	public static void processOldSearchMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, User activeUser,String searchTerm) throws ServletException, IOException
	{
		LinkedList<Message> messageList = SearchMethods.searchForMessages(searchTerm);
		int oldestMessageId = Integer.parseInt(request.getParameter("oldestMessageId"));
		request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
		request.setAttribute("messages", getOldMessages(messageList, oldestMessageId));
		request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
	}
	//displays new messages on the search page
	public static void processNewSearchMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, User activeUser,String searchTerm) throws ServletException, IOException
	{
		LinkedList<Message> messageList = SearchMethods.searchForMessages(searchTerm);
		int newestMessageId = Integer.parseInt(request.getParameter("newestMessageId"));
		request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
		request.setAttribute("messages", getNewMessages(messageList, newestMessageId));
		request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
	}
	//displays older messages on the message page
	private static LinkedList<Message> getOldMessages(LinkedList<Message> messageList, int oldestMessageId)
	{
		LinkedList<Message> newMessages = new LinkedList<Message>();
		int index = getIndexByMessageId(messageList, oldestMessageId);
		int nextMessageIndex = index+1;
		int j =0;
		for(int i = nextMessageIndex; i < nextMessageIndex+10;i++)
		{
			if(i == messageList.size())
			{
				break;
			}
			if(j == 10)
			{
				break;
			}
			newMessages.add(messageList.get(i));
			j++;
		}
		return newMessages;
	}
	//Returns a list of new messages based on a previous newest message value
	private static LinkedList<Message> getNewMessages(LinkedList<Message> messageList, int newestMessageId)
	{
		LinkedList<Message> newMessages = new LinkedList<Message>();
		for(int i = 0; i < messageList.size(); i++)
		{
			if(messageList.get(i).getMessageId() > newestMessageId)
			{
				messageList.get(i).setIsNew(true);
				newMessages.add(messageList.get(i));
			}
		}
		return newMessages;
	}
	//gets the index of a message in a given list by using a known message id
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
