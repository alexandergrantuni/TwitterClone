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
            connection.close();
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
            	user.setIsAdmin(resultSet.getBoolean("IsAdministrator"));
            }
            connection.close();
            return user;
        
    }
    catch(Exception ex)
    {
            ex.printStackTrace();
            try
            {
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
            connection.close();
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
            connection.close();
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
	public static boolean follow(String username, String usernameToFollow)
	{
        Connection connection = Database.getConnection();
        PreparedStatement query = null;
        try 
    {
            query = (PreparedStatement) connection.prepareStatement("INSERT INTO following (UserId, FollowingUserId) VALUES(?,?);");
            query.setString(1, username);
            query.setString(2, usernameToFollow);
            int result = query.executeUpdate(); 
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
	public static boolean unfollow(String username, String usernameToUnfollow)
	{
        Connection connection = Database.getConnection();
        PreparedStatement query = null;
        try 
    {
            query = (PreparedStatement) connection.prepareStatement("DELETE FROM following WHERE UserId=? AND FollowingUserId=?");
            query.setString(1, username);
            query.setString(2, usernameToUnfollow);
            int result = query.executeUpdate(); 
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
	public static boolean isFollowing(String username, String usernameBeingFollowed)
	{
        Connection connection = Database.getConnection();
        PreparedStatement query = null;
        try 
    {
            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM following WHERE UserId = ? AND FollowingUserId = ?;");
            query.setString(1, username);
            query.setString(2, usernameBeingFollowed);
            boolean success = query.executeQuery().next();
            connection.close();
            return success;
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
	
	public static void deleteFollows(User u)
	{
		Connection connection = Database.getConnection();
	    PreparedStatement query = null;
	    try 
	{
	        query = (PreparedStatement) connection.prepareStatement("DELETE FROM following WHERE UserId=? OR FollowingUserId=?;");
	        if(u.getUsername().equals(""))
	        {
	        	return;
	        }
	        query.setString(1, u.getUsername());
	        query.setString(2, u.getUsername());
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
	
	public static void deleteUser(User u)
	{
		Connection connection = Database.getConnection();
	    PreparedStatement query = null;
	    try 
	{
	        query = (PreparedStatement) connection.prepareStatement("DELETE FROM users WHERE Username=?;");
	        if(u.getUsername().equals(""))
	        {
	        	return;
	        }
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
	public static void deleteAccount(User u)
	{
		deleteFollows(u);
		MessageMethods.deleteAllMessages(u);
		deleteUser(u);
	}
}
