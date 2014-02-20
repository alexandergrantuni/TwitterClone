package test.General;

import java.io.IOException;
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
			System.out.println("messageCount = " + messageCount + " totalMessages = " + totalMessages);
			if(messageCount < totalMessages)
			{
				for(int i = (messageCount+newCount); i < totalMessages+newCount;i++)
				{
					System.out.println("i = "+ i + " totalMessages+newCount = " + (totalMessages+newCount));
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
			int lastMessageId = Integer.parseInt(request.getParameter("lastMessage"));
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
			
	
	//Handles AJAX requests from /messages/*
		public static void getNewMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, User activeUser,String argument) throws ServletException, IOException
		{
			if(requestURI.equals("/test/messages/all/fetchNew"))
			{
				LinkedList<Message> messageList = MessageMethods.getAllMessages();
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
			else if(requestURI.contains("/test/messages/fetchNew"))
			{
				LinkedList<Message> messageList = MessageMethods.getFollowingMessages(activeUser.getUsername());
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
		}
	public static void processSearchMessagesAJAX(HttpServletRequest request, HttpServletResponse response, String requestURI, User activeUser,String searchTerm) throws ServletException, IOException
	{
	LinkedList<Message> messageList = SearchMethods.searchForMessages(searchTerm);
	int messageCount = Integer.parseInt((request.getParameter("messageCount")));
	int lastMessageId = Integer.parseInt(request.getParameter("lastMessage"));
	
	int index = getIndexByMessageId(messageList, lastMessageId);
	int tens = (messageCount/10)-1;
	LinkedList<Message> newMessages = new LinkedList<Message>();
	int j =0;
	if(messageCount < messageList.size()){
		for(int i = index+(tens*10)+1; i < messageList.size();i++)
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
		
		request.setAttribute("activeUser", UserMethods.getUserFromUsername(activeUser.getUsername()));
		request.setAttribute("messages", newMessages);
		request.getRequestDispatcher("/messageSet.jsp").forward(request, response);
	}
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
