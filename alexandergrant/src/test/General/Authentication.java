package test.General;

import java.sql.*;
import java.util.Date;

import test.Model.User;

public class Authentication {
	
	private static Connection connection;
	//Checks whether a username and password combination are correct
	public static User authenticateUser(String emailAddress, String hashedPassword)
	{
        connection = Database.getConnection();
        PreparedStatement query = null;
        try 
    {
            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM users WHERE EmailAddress = ? AND Password = ?;");
            query.setString(1, emailAddress);
            query.setString(2, hashedPassword);
            ResultSet resultSet = query.executeQuery();
            User user = new User();
            //if there is a result
            if(resultSet.next())
            {
            user.setEmailAddress(resultSet.getString("EmailAddress"));
            user.setUsername(resultSet.getString("Username"));
            user.setFirstName(resultSet.getString("FirstName"));
            user.setLastName(resultSet.getString("LastName"));
            user.setBio(resultSet.getString("bio"));
            user.setIsAdmin(resultSet.getBoolean("IsAdministrator"));
            connection.close();
            return user;
            }
            connection.close();
            return null; 
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
	//Checks whether a username and password combination are correct
	public static boolean verifyUsernameAndPassword(String username, String password)
	{
        connection = Database.getConnection();
        PreparedStatement query = null;
        try 
    {
        	password = Security.getEncodedSha1Sum(password);
            query = (PreparedStatement) connection.prepareStatement("SELECT * FROM users WHERE Username = ? AND Password = ?;");
            query.setString(1, username);
            query.setString(2, password);
            ResultSet resultSet = query.executeQuery();
            //if there is a result
            if(resultSet.next())
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

	//checks if a username has already been registered
	public static boolean usernameRegistered(String username)
	{
		PreparedStatement query = null;
        try 
        {
        	connection = Database.getConnection();
			query = (PreparedStatement) connection.prepareStatement("SELECT * FROM users WHERE Username = ?");
	        query.setString(1, username);
	        ResultSet resultSet = query.executeQuery();   
	        boolean usernameRegistered = resultSet.first();
	        connection.close();
	        return usernameRegistered;
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
			
			return false;
		}
	}
	//Checks if an email address has already been registered
	public static boolean emailAddressRegistered(String emailAddress)
	{
		PreparedStatement query = null;
        try 
        {
        	connection = Database.getConnection();
			query = (PreparedStatement) connection.prepareStatement("SELECT * FROM users WHERE EmailAddress = ?");
	        query.setString(1, emailAddress);
	        ResultSet resultSet = query.executeQuery();   
	        boolean emailRegistered = resultSet.first();
	        connection.close();
	        return emailRegistered;
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
			return false;
		}
	}
	//Registers a regular account
	public static boolean Register(String emailAddress, String username, String hashedPassword,String firstName, String lastName)
	{

        connection = Database.getConnection();
        PreparedStatement query = null;
        try 
    {
        	Date now = new Date();
        	int registerTime = (int)(now.getTime()/1000);
            query = (PreparedStatement) connection.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?,?,?,?);");
            query.setString(1, username);//username
            query.setString(2, emailAddress);//email address
            query.setString(3, hashedPassword);//hashed password
            query.setString(4, firstName);//first name
            query.setString(5, lastName);//last name
            query.setString(6, "I haven't set a bio yet!");//bio
            query.setString(7, "img/blank-profile-pic.png");//profile picture path
            query.setInt(8, 0);//isAdmin
            query.setInt(9, registerTime);//Register timestamp
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
	
	//Registers an admin account
	public static boolean RegisterAdmin(String emailAddress, String username, String hashedPassword,String firstName, String lastName)
	{

        connection = Database.getConnection();
        PreparedStatement query = null;
        try 
    {
        	Date now = new Date();
        	int registerTime = (int)(now.getTime()/1000);
            query = (PreparedStatement) connection.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?,?,?,?);");
            query.setString(1, username);//username
            query.setString(2, emailAddress);//email address
            query.setString(3, hashedPassword);//hashed password
            query.setString(4, firstName);//first name
            query.setString(5, lastName);//last name
            query.setString(6, "I haven't set a bio yet!");//bio
            query.setString(7, "img/blank-profile-pic.png");//profile picture path
            query.setInt(8, 1);//isAdmin
            query.setInt(9, registerTime);//Register timestamp
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
	
	

}
