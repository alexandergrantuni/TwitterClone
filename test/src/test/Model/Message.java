package test.Model;


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

}
