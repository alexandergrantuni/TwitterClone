package test.General;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import test.Model.User;

public class UserMethods {

	
	public static User getUserFromEmailAddress(String emailAddress)
	{
        Connection connection = Database.getConnection();
        PreparedStatement query = null;
        try 
    {
            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM users WHERE EmailAddress = ?;");
            query.setString(1, emailAddress);
            ResultSet resultSet = query.executeQuery();
            User user = new User();
            while (resultSet.next())
            {
            user.setEmailAddress(resultSet.getString("EmailAddress"));
            user.setUsername(resultSet.getString("Username"));
            user.setFirstName(resultSet.getString("FirstName"));
            user.setLastName(resultSet.getString("LastName"));
            user.setBio(resultSet.getString("bio"));
            }
            return user;
        
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
	
	public static User getUserFromUsername(String username)
	{
        Connection connection = Database.getConnection();
        PreparedStatement query = null;
        try 
    {
            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM users WHERE Username = ?;");
            query.setString(1, username);
            ResultSet resultSet = query.executeQuery();
            User user = new User();
            while (resultSet.next())
            {
            user.setEmailAddress(resultSet.getString("EmailAddress"));
            user.setUsername(resultSet.getString("Username"));
            user.setFirstName(resultSet.getString("FirstName"));
            user.setLastName(resultSet.getString("LastName"));
            user.setBio(resultSet.getString("bio"));
            }
            return user;
        
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
	
	public static LinkedList<User> getFollowers(String username)
	{
        Connection connection = Database.getConnection();
        PreparedStatement query = null;
        try 
    {
            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM following WHERE FollowingUserId = ?;");
            query.setString(1, username);
            ResultSet resultSet = query.executeQuery();
            LinkedList<User> followerList = new LinkedList<User>();
            while (resultSet.next())
            {
            User user = new User();
            user = getUserFromUsername(resultSet.getString("UserId"));
            followerList.add(user);
            }
            return followerList;
        
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
	
	public static LinkedList<User> getFollowing(String username)
	{
        Connection connection = Database.getConnection();
        PreparedStatement query = null;
        try 
    {
            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM following WHERE UserId = ?;");
            query.setString(1, username);
            ResultSet resultSet = query.executeQuery();
            LinkedList<User> followerList = new LinkedList<User>();
            while (resultSet.next())
            {
            User user = new User();
            user = getUserFromUsername(resultSet.getString("FollowingUserId"));
            followerList.add(user);
            }
            return followerList;
        
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
