package test.Model;

import java.util.*;

public class User {
	
	private String username = "";
	private String emailAddress = "";
	private String firstName = "";
	private String lastName = "";
	private String bio = "";
	private boolean isAdmin = false;
	private boolean isActiveUserFollowing = false;//if true the active user is following this user, used to determine whether to show follow
	//or unfollow buttons
	
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String newUsername)
	{
		username = newUsername;
	}
	
	
	public String getEmailAddress()
	{
		return emailAddress;
	}
	
	public void setEmailAddress(String newEmailAddress)
	{
		emailAddress = newEmailAddress;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String newFirstName)
	{
		firstName = newFirstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String newLastName)
	{
		lastName = newLastName;
	}
	
	public String getBio()
	{
		return bio;
	}
	
	public void setBio(String newBio)
	{
		bio = newBio;
	}
	
	public boolean getIsAdmin()
	{
		return isAdmin;
	}
	
	public void setIsAdmin(boolean admin)
	{
		isAdmin = admin;
	}
	
	public boolean getIsActiveUserFollowing()
	{
		return isActiveUserFollowing;
	}

	public void setIsActiveUserFollowing(boolean isFollowing)
	{
		isActiveUserFollowing = isFollowing;
	}
}
