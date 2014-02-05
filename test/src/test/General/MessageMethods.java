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
            	System.out.println("Message: '"+message+" was posted by "+username);
            }
            else
            {
            	System.out.println("Message failed to post.");
            }
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
	
	public static boolean deleteMessage(int MessageId)
	{
		 Connection connection = Database.getConnection();
	        System.out.println(connection);
	        PreparedStatement query = null;
	        try 
	    {
	            query = (PreparedStatement) connection.prepareStatement("call DeleteMessage(?)");
	            query.setInt(1, MessageId);
	            ResultSet resultSet = query.executeQuery();          
	            return resultSet.first();
	        
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
	
	public static boolean createdMessage(User user, int messageId)
	{
		 Connection connection = Database.getConnection();
	        System.out.println(connection);
	        PreparedStatement query = null;
	        try 
	    {
	            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM message WHERE MessageId=? AND Username=?;");
	            query.setInt(1, messageId);
	            query.setString(2, user.getUsername());
	            ResultSet resultSet = query.executeQuery();          
	            return resultSet.first();
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
	            	msg.setBroadcastId(resultSet.getInt("message.MessageId"));
	            	msg.setTimestamp(resultSet.getInt("message.Timestamp"));
	            	list.add(msg);
	            }
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
	
	public static List<Message> getFollowingMessages(String username)
	{
		 Connection connection = Database.getConnection();
	        PreparedStatement query = null;
	        try 
	    {
	            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM message AS msg INNER JOIN following AS follow ON msg.Username = follow.FollowingUserID WHERE follow.UserID = ? ORDER BY msg.Timestamp DESC;");
	            query.setString(1, username);
	            ResultSet resultSet = query.executeQuery();          
	           LinkedList<Message> list = new LinkedList<Message>();
	            while(resultSet.next())
	            {
	            	Message msg = new Message();
	            	msg.setOwner(UserMethods.getUserFromUsername(username));
	            	msg.setText(resultSet.getString("msg.Text"));
	            	msg.setBroadcastId(resultSet.getInt("msg.MessageId"));
	            	msg.setTimestamp(resultSet.getInt("msg.Timestamp"));
	            	list.add(msg);
	            }
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
	public static List<Message> getAllMessages()
	{
		return null;
	}

}
