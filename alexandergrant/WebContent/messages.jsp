<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /> <!-- stylesheet -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js" type="text/javascript"></script><!-- jquery lib -->
<script src="${pageContext.request.contextPath}/js/utils.js"></script><!-- My utils javascript file with useful functions I've created. -->
	 <!-- Required for confirmation box -->
	  <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.9.1.js"></script>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
<title>ChitChat - Messages</title>
</head>
<jsp:include page="navigationbar.jsp" /> <!-- add the footer to the bottom of the page -->
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


//This function checks for new messages posted by other users since the page was loaded.
//These new messages are displayed with "NEW!" just beside the 'Posted by' section
$(document).ready(function(){
	var isfetching = false;
    $(window).scroll(function(){ 
    	if(!isfetching)
    	{
	    	var newMessages = document.getElementsByClassName("newmessage");//get all messages
	    	var total = ${totalMessages} + newMessages.length;
	    	isfetching = true;
	    	$.ajax({
	    	    type:'GET',
	    	    data: {totalMessages: total},
	    		    success: 
	    		        function(html){
	    		            $("#newMessages").prepend(html);
	    		            detectAndAddHashTags();
	    		            isfetching = false;
	    		        },
	    	    error:
	    	    	function(html){
	    	    	isfetching = false;
	    	    	}
	    	    });
	    	}
    	});
    });

$(function() {
    $( "#dialog-confirm" ).toggle();//This is important. This line toggles the visibility of the 'dialog-confirm' div directly below so that it does not interefere
    								//with the page before it is shown in the dialog box. 
  });
$(document).ready(function(){
	var fetching = false;//stops multiple requests from taking place (particularly on firefox)
    $(window).scroll(function(){ //called when the user scrolls
        if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight & !fetching) {//if the user is at the bottom of the page and a fetch is not going on
    	var messages = document.getElementsByClassName("message");//get all messages
    	var numMessages = messages.length;//get the number of messages
    	var total = ${totalMessages};//get the total number of messages that existed when the user loaded the page originally
    	if(messages.length < 10)
    		{
    			return;
    		}
    													  
    	fetching = true;//a new fetch is in progress set fetching to true
    	$.ajax({
    	    type:'GET',
    	    data: {messageCount: numMessages, totalMessages: total},
    		    success: 
    		        function(msg){
    		            $("#broadcastcontainer").append(msg);//add the retrieved messages to the page
    		            formatMessages();//add hash tag links etc to messages
    		            fetching = false;//no longer fetching, allow another fetch to occur
    		        }                  
    	    });
        }
    	});
    });
</script>
<body onload="formatMessages()">
  <div id="dialog-confirm" title="Are you sure about this?" >
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Your message will be permanently deleted.</p>
</div>
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
    <c:if test="${noMessages != null }">
    <div id="whiteText"><center>${noMessages}</center></div>
    </c:if>
    <c:if test="${noMessages == null}">
    <c:if test="${title == 'All Messages' }">
    Currently displaying all user messages.  Click <a href="${pageContext.request.contextPath}/messages/">here</a> to see the messages of people you're following.  <p>Scroll down to load older messages.</p>
    </c:if>
    <c:if test="${title == 'Messages from Followed Users' }">
    Currently displaying followed user messages.  Click <a href="${pageContext.request.contextPath}/messages/all">here</a> to see all user messages. <p>Scroll down to load older messages.</p>
    </c:if>
    </c:if>
    <div id="newMessages"></div>
    <c:forEach items="${messages}" var="individualMessage">
      <p><div class="message">
      <div class="messageProfilePicture"><img src="${pageContext.request.contextPath}/img/blank-profile-pic.png" alt="Profile picture" width="45" height="30"></div>
      <div class="messageText">${individualMessage.text }</div>
      <c:if test="${activeUser.username == individualMessage.owner.username || activeUser.isAdmin == true}">
      <div class="deleteMessageButton" onclick="hello"><button type="submit"onclick="showDeleteMessageDialog('${pageContext.request.contextPath}','${individualMessage.messageId }')"><img src="${pageContext.request.contextPath}/img/bin.png" alt="Delete Message" width="21" height="25"></button></div>
      <div class="viewIndividually" style="margin-top:10px;"><u><a href="${pageContext.request.contextPath}/messages/${individualMessage.messageId }">View Individually</a></u></div>
      </c:if>
      <c:if test="${activeUser.username != individualMessage.owner.username && activeUser.isAdmin != true}">
      <div class="viewIndividually" style="margin-right:-20px; margin-top:10px;"><u><a href="${pageContext.request.contextPath}/messages/${individualMessage.messageId }">View Individually</a></u></div>
      </c:if>
      <div class="timestampArea">Posted by <a href="${pageContext.request.contextPath}/profile/${individualMessage.owner.username}">${individualMessage.owner.username }</a> ${individualMessage.timePostedAgo() }</div>
      
      </div>

</c:forEach>
 </div>

<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</body>
</html>