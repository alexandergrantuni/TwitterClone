package test.General;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import test.Model.User;

public class UserMethods {

	//Returns the user object which has the given email address
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
            resultSet.close();
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
	//Returns the user object which has the given username
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
            resultSet.close();
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
	//Returns a list of followers for the user with the given username
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
	//Returns a list of users that are following the given user
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
            resultSet.close();
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
	//Makes 1 user (argument 1) follow another user (argument 2)
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
	//Makes 1 user (argument 1) unfollow another user (argument 2)
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
	//Returns true if 1 user (argument 1) is following another user (argument 2)
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
	//Deletes all followings and follows for a given user
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
	//Updates a user's details with the given parameter values
	public static boolean updateDetails(String username, String firstName, String lastName, String bio, String newPassword)
	{
		Connection connection = Database.getConnection();
		PreparedStatement query = null;
		newPassword = Security.getEncodedSha1Sum(newPassword);
		System.out.println(username + " " + firstName + " " + lastName + " " +bio + " " + newPassword);
        try 
        {
            query = (PreparedStatement) connection.prepareStatement("UPDATE users SET FirstName=?, LastName=?, bio=?, Password=? WHERE Username=?");
            query.setString(1, firstName);
            query.setString(2, lastName);
            query.setString(3, bio);
            query.setString(4, newPassword);
            query.setString(5, username);
            int result = query.executeUpdate();
            //if there is a result
            if(result == 1)
            {
            	connection.close();	   
    			return true;
            }
            connection.close();
            return false;
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
	//Deletes a user
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
	//Deletes a user's account
	public static void deleteAccount(User u)
	{
		deleteFollows(u);
		MessageMethods.deleteAllMessages(u);
		deleteUser(u);
	}
}
