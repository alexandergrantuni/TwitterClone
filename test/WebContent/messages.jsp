<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /> <!-- stylesheet -->
<title>ChitChat - Broadcasts</title>
</head>
<jsp:include page="navigationbar.jsp" /> <!-- add the footer to the bottom of the page -->
<body>
<script>
function messageValidation()
{
	var message=document.forms["sendMessageForm"]["message"].value;
	
	if(message.length == 0)
	{
		alert("You didn't enter a message.");
		return false;
	}
	if(message.length > 140)
	{
		alert("Your message is too long.  Please enter a message which is less than 140 characters long.")
		return false;
	}
	
	return true;
}
</script>
<body>

<div id="profilearea">
    <h1>My Profile</h1>
    <div id="profilepicture">
    <img src="img\blank-profile-pic.png" alt="Profile picture" width="100" height="75">
    </div>
        <center><font color="#ffffff" size="+1">My Bio</font></center>
    <div id="bio">
    <font color="#ffffff">An example bio.</font>
    </div>
  </div>
 <div id="sendmessagecontainer">
 <h1>Send Message</h1>
 <div id="userMessageBox">

    <form name="sendMessageForm" method="post" action="Messages" onsubmit="return messageValidation()">
      <p> <textarea name="message" rows="3" cols="50" maxlength="140"></textarea></p>
      <p class="submit"><input type="submit" name="loginButton" value="Send Message"></p>
    </form>
</div>
 </div>
 <div id="broadcastcontainer">
    <h1>Broadcasts</h1>
      <p><div id="message">
      <div id="messageProfilePicture"><img src="img\blank-profile-pic.png" alt="Profile picture" width="45" height="30"></div>
      <div id="messageText">alligator</div>
      <div id="deleteMessageButton"><img src="img\bin.png" alt="Profile picture" width="21" height="25"></div>
      <div id="timestampArea">Posted by USERNAME 5 hours ago.</div>
      </div></p>
      <div id="message">
      <div id="messageProfilePicture"><img src="img\blank-profile-pic.png" alt="Profile picture" width="45" height="30"></div>
      <div id="messageText">Hello</div>
      <div id="deleteMessageButton"><img src="img\bin.png" alt="Profile picture" width="21" height="25"></div>
      <div id="timestampArea">Posted by USERNAME 10 hours ago.</div>
      </div>
 </div>

<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</body>
</body>
</html>