package test.Model;

import java.util.*;

public class User {
	
	private String username;
	private String emailAddress;
	private String password;
	private String firstName;
	private String lastName;
	private String bio;
	private List<User> friendList;
	private List<User> followingList;
	
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String newUsername)
	{
		username = newUsername;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String newPassword)
	{
		password = newPassword;
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
	
	public List<User> getFollowers()
	{
		return friendList;
	}
	
	public List<User> getFollowing()
	{
		return followingList;
	}

}
