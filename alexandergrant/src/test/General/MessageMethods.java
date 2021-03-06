package test.General;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import test.Model.*;

public class MessageMethods {
	//sends a message from a given user
	public static boolean sendMessage(String username, String message)
	{
		 Connection connection = Database.getConnection();
    	Date now = new Date();
    	int registerTime = (int)(now.getTime()/1000);
        PreparedStatement query = null;
        try 
    {
            query = (PreparedStatement) connection.prepareStatement("INSERT INTO message (Text, Timestamp, Username) VALUES (?, ?, ?);");
            query.setString(1, message);
            query.setInt(2, registerTime);
            query.setString(3, username);
            int result = query.executeUpdate();      
            if(result ==1)
            {
            	System.out.println("Message: '"+message+"' was posted by "+username);
            }
            else
            {
            	System.out.println("Message failed to post.");
            }
            connection.close();
            return result == 1;
        
    }
    catch(Exception ex)
    {
            ex.printStackTrace();
            try
            {
                query.close();
                connection.close();
            }
            catch (SQLException sqle)
            {
                    sqle.printStackTrace();
            }
            
    }
        return false;
	}
	//Deletes the message with a given message id
	public static void deleteMessage(int MessageId)
	{
		 Connection connection = Database.getConnection();
	        PreparedStatement query = null;
	        try 
	    {
	            query = (PreparedStatement) connection.prepareStatement("DELETE FROM message WHERE MessageId=?;");
	            query.setInt(1, MessageId);
	            query.executeUpdate();  
	            connection.close();
	            return;
	    }
	    catch(Exception ex)
	    {
	            ex.printStackTrace();
	            try
	            {
	                query.close();
	                connection.close();
	            }
	            catch (SQLException sqle)
	            {
	                    sqle.printStackTrace();
	            }
	            
	    }
	}
	//Determines whether the passed user created the message with the given message Id
	public static boolean createdMessage(User user, int messageId)
	{
		 Connection connection = Database.getConnection();
	        PreparedStatement query = null;
	        try 
	    {
	            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM message WHERE MessageId=? AND Username=?;");
	            query.setInt(1, messageId);
	            query.setString(2, user.getUsername());
	            ResultSet resultSet = query.executeQuery();          
	            boolean createdMessage = resultSet.first();
	            connection.close();
	            return createdMessage;
	    }
	    catch(Exception ex)
	    {
	            ex.printStackTrace();
	            try
	            {
	                query.close();
	                connection.close();
	            }
	            catch (SQLException sqle)
	            {
	                    sqle.printStackTrace();
	            }
	            
	    }
	        return false;
	}
	//gets all information about a message using just the message Id
	public static Message getMessageById(int messageId)
	{
		 Connection connection = Database.getConnection();
	        PreparedStatement query = null;
	        try 
	    {
	            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM message WHERE MessageId=?;");
	            query.setInt(1, messageId);
	            ResultSet resultSet = query.executeQuery();
	            Message msg = new Message();
	            if(resultSet.next())
	            {
	            	msg.setOwner(UserMethods.getUserFromUsername(resultSet.getString("Username")));
	            	msg.setText(resultSet.getString("Text"));
	            	msg.setMessageId(resultSet.getInt("MessageId"));
	            	msg.setTimestamp(resultSet.getInt("Timestamp"));
	            }
	            connection.close();
	            return msg;
	    }
	    catch(Exception ex)
	    {
	            ex.printStackTrace();
	            try
	            {
	                query.close();
	                connection.close();
	            }
	            catch (SQLException sqle)
	            {
	                    sqle.printStackTrace();
	            }
	            
	    }
		return null;
	}
	//gets all messages posted by a user
	public static LinkedList<Message> getUserMessages(String username)
	{
		 Connection connection = Database.getConnection();
	        PreparedStatement query = null;
	        try 
	    {
	            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM message WHERE Username=? ORDER BY message.Timestamp DESC;");
	            query.setString(1, username);
	            ResultSet resultSet = query.executeQuery();          
	           LinkedList<Message> list = new LinkedList<Message>();
	            while(resultSet.next())
	            {
	            	Message msg = new Message();
	            	msg.setOwner(UserMethods.getUserFromUsername(username));
	            	msg.setText(resultSet.getString("message.Text"));
	            	msg.setMessageId(resultSet.getInt("message.MessageId"));
	            	msg.setTimestamp(resultSet.getInt("message.Timestamp"));
	            	list.add(msg);
	            }
	            connection.close();
	            return list;
	    }
	    catch(Exception ex)
	    {
	            ex.printStackTrace();
	            try
	            {
	                query.close();
	                connection.close();
	            }
	            catch (SQLException sqle)
	            {
	                    sqle.printStackTrace();
	            }
	            
	    }
		return null;
	}
	//gets all messages from people a user is following
	public static LinkedList<Message> getFollowingMessages(String username)
	{
		 Connection connection = Database.getConnection();
	        PreparedStatement query = null;
	        try 
	    {
	            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM message AS msg INNER JOIN following AS follow ON msg.Username = follow.FollowingUserId WHERE follow.UserId = ? ORDER BY msg.Timestamp DESC;");
	            query.setString(1, username);
	            ResultSet resultSet = query.executeQuery();          
	           LinkedList<Message> list = new LinkedList<Message>();
	            while(resultSet.next())
	            {
	            	Message msg = new Message();
	            	msg.setOwner(UserMethods.getUserFromUsername(resultSet.getString("msg.Username")));
	            	msg.setText(resultSet.getString("msg.Text"));
	            	msg.setMessageId(resultSet.getInt("msg.MessageId"));
	            	msg.setTimestamp(resultSet.getInt("msg.Timestamp"));
	            	list.add(msg);
	            }
	            resultSet.close();
	            connection.close();
	            return list;
	    }
	    catch(Exception ex)
	    {
	            ex.printStackTrace();
	            try
	            {
	                query.close();
	                connection.close();
	            }
	            catch (SQLException sqle)
	            {
	                    sqle.printStackTrace();
	            }
	            
	    }
		return null;
	}
	//returns the list of all messages
	public static LinkedList<Message> getAllMessages()
	{		 
		Connection connection = Database.getConnection();
	    PreparedStatement query = null;
	    try 
	    {
	        query = (PreparedStatement) connection.prepareStatement("SELECT * FROM message ORDER BY Timestamp DESC;");
	        ResultSet resultSet = query.executeQuery();          
	       LinkedList<Message> list = new LinkedList<Message>();
	        while(resultSet.next())
	        {
	        	Message msg = new Message();
	        	msg.setOwner(UserMethods.getUserFromUsername(resultSet.getString("Username")));
	        	msg.setText(resultSet.getString("Text"));
	        	msg.setMessageId(resultSet.getInt("MessageId"));
	        	msg.setTimestamp(resultSet.getInt("Timestamp"));
	        	list.add(msg);
	        }
	        connection.close();
	        return list;
		}
		catch(Exception ex)
		{
		        ex.printStackTrace();
		        try
		        {
		            query.close();
		            connection.close();
		        }
		        catch (SQLException sqle)
		        {
		                sqle.printStackTrace();
		        }
		        
		}
	return null;
	}
	//determines whether a message with a given message id exists
	public static boolean messageExists(int messageId)
	{
		Connection connection = Database.getConnection();
	    PreparedStatement query = null;
	    try 
	{
	        query = (PreparedStatement) connection.prepareStatement("SELECT messageId FROM message WHERE MessageId=?;");
	        query.setInt(1, messageId);
	        ResultSet resultSet = query.executeQuery();          
	        boolean exists = resultSet.first();
	        connection.close();
	        return exists;
	}
	catch(Exception ex)
	{
	        ex.printStackTrace();
	        try
	        {
	            query.close();
	            connection.close();
	        }
	        catch (SQLException sqle)
	        {
	                sqle.printStackTrace();
	        }
	        
	}
	return false;
	}
	//Deletes all messages posted by a user
	public static void deleteAllMessages(User u)
	{
		Connection connection = Database.getConnection();
	    PreparedStatement query = null;
	    try 
	{
	        query = (PreparedStatement) connection.prepareStatement("DELETE FROM message WHERE Username=?;");
	        query.setString(1, u.getUsername());
	        int result = query.executeUpdate();          
	        connection.close();
	}
	catch(Exception ex)
	{
	        ex.printStackTrace();
	        try
	        {
	            query.close();
	            connection.close();
	        }
	        catch (SQLException sqle)
	        {
	                sqle.printStackTrace();
	        }
	        
	}
	}

}
