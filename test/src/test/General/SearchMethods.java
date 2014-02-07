package test.General;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import test.Model.Message;
import test.Model.User;

public class SearchMethods {

	public static LinkedList<User> searchForUsers(String searchTerm)
	{
		 Connection connection = Database.getConnection();
		    PreparedStatement query = null;
		    try 
		{
		        query = (PreparedStatement) connection.prepareStatement("SELECT Username FROM users WHERE Username LIKE ?;");
		        query.setString(1, "%"+searchTerm+"%");
		        ResultSet resultSet = query.executeQuery();          
		       LinkedList<User> list = new LinkedList<User>();
		        while(resultSet.next())
		        {
		        	User u = new User();
		        	u = UserMethods.getUserFromUsername(resultSet.getString("username"));
		        	list.add(u);
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
	
	public static LinkedList<Message> searchForMessages(String searchTerm)
	{
		 Connection connection = Database.getConnection();
		    PreparedStatement query = null;
		    try 
		{
		        query = (PreparedStatement) connection.prepareStatement("SELECT * FROM message WHERE Text LIKE ?;");
		        query.setString(1, "%"+searchTerm+"%");
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
}
