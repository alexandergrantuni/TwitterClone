package test.Model;


public class Message {
	
	private int broadcastId;
	
	private User owner;
	private int timestamp;
	private String text;
	
	public int getBroadcastId()
	{
		return broadcastId;
	}
	
	public void setBroadcastId(int newBroadcastId)
	{
		broadcastId = newBroadcastId;
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
