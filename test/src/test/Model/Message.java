package test.Model;

import java.util.Date;


public class Message {
	
	private int messageId;
	
	private User owner;
	private int timestamp;
	private String text;
	
	public int getMessageId()
	{
		return messageId;
	}
	
	public void setMessageId(int newMessageId)
	{
		messageId = newMessageId;
	}
	
	public User getOwner()
	{
		return owner;
	}
	
	public void setOwner(User newOwner)
	{
		owner = newOwner;
	}
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	public void setTimestamp(int newTimestamp)
	{
		timestamp = newTimestamp;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String newText)
	{
		text = newText;
	}
	
	public String timePostedAgo()
	{
    	Date now = new Date();
    	int nowTimestamp = (int)(now.getTime()/1000);
    	int difference = nowTimestamp - timestamp;
    	//less than or equal to 60 seconds ago
    	if(difference < 60)
    	{
    		return difference + " seconds ago.";
    	}
    	//If the difference is greater than a minute but less than an hour
    	if(difference >= 60 && difference < 3600)
    	{
    		return (difference/60) +" minutes ago.";
    	}
    	//if the difference is greater than or equal to an hour but less than a day
    	if(difference >= 3600 && difference < 86400)
    	{
    		return (difference/3600) +" hours ago.";
    	}
    	//if the difference is more than a day
    	if(difference >=86400)
    	{
    		return (difference/86400) +" days ago.";
    	}
    	return "";
	}

}
