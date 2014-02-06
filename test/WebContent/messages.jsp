<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- tag library which contains a number of useful jsp tags like c:if -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /> <!-- stylesheet -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js" type="text/javascript"></script><!-- jquery lib -->
<title>ChitChat - Broadcasts</title>
</head>
<jsp:include page="navigationbar.jsp" /> <!-- add the footer to the bottom of the page -->
<body>
<script type="text/javascript">
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
function deleteMessage(messageId)
{
	$.ajax({
	    url: "${pageContext.request.contextPath}/messages/"+messageId,
	    type:'DELETE',//Sends a DELETE request which tells the servlet to delete the message with the given messageId
	    success: 
	        function(msg){
	            alert("Your message has been deleted.");
	            location.reload();
	        }                   
	    });
}
</script>
<body>

<div id="profilearea">
    <h1>My Profile</h1>
    <div id="profilepicture">
    <img src="${pageContext.request.contextPath}/img/blank-profile-pic.png" alt="Profile picture" width="100" height="75">
    </div>
        <center><font color="#ffffff" size="+1">My Bio</font></center>
    <div id="bio">
    <font color="#ffffff">${activeUser.bio }</font>
    </div>
  </div>
 <div id="sendmessagecontainer">
 <h1>Send Message</h1>
 <div id="userMessageBox">
    <form name="sendMessageForm" method="post" action="messages" onsubmit="return messageValidation()">
      <p> <textarea name="message" rows="3" cols="50" maxlength="140"></textarea></p>
      <p class="submit"><input type="submit" name="loginButton" value="Send Message"></p>
    </form>
</div>
 </div>
 <div id="broadcastcontainer">
    <h1>${title}</h1>
    <c:forEach items="${messages}" var="individualMessage">
      <p><div id="message">
      <div id="messageProfilePicture"><img src="${pageContext.request.contextPath}/img/blank-profile-pic.png" alt="Profile picture" width="45" height="30"></div>
      <div id="messageText">${individualMessage.text }</div>
      <c:if test="${activeUser.username == individualMessage.owner.username}">
      <div id="deleteMessageButton"><button type="submit"onclick="deleteMessage('${individualMessage.messageId}')"><img src="${pageContext.request.contextPath}/img/bin.png" alt="Delete Message" width="21" height="25"></button></div>
      </c:if>
      <div id="timestampArea">Posted by <a href="${pageContext.request.contextPath}/profile/${individualMessage.owner.username}">${individualMessage.owner.username }</a> 5 hours ago.</div>
      </div>
</c:forEach>
 </div>

<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</body>
</body>
</html>