package test.Model;

import java.util.*;

public class User {
	
	private String username = "";
	private String emailAddress = "";
	private String firstName = "";
	private String lastName = "";
	private String bio = "";
	private boolean isActiveUserFollowing = false;
	
	
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
	
	public boolean isFollowing(User user)
	{
		return false;
	}
	
	public boolean follows(User user)
	{
		return false;
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
