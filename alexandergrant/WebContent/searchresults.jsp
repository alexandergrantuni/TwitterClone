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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ChitChat - Search Results</title>
<script>
$(function() {
    setTimeout(fetchNewMessages, 5000);//fetch new messages every 5 seconds
  });
$(function() {
	var total = ${totalMessages};//get the total number of messages that existed when the user loaded the page originally
	if(total > 10)
	{
		$("#moreResults").html("<center>There are more messages to be displayed.  Scroll down to the bottom to see more.</center>")
	}
  });
  
//This function checks for new messages posted by other users since the page was loaded.
//These new messages are displayed with "NEW!" just beside the 'Posted by' section
function fetchNewMessages()
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
  setTimeout(fetchNewMessages, 5000);
}
  
$(document).ready(function(){
	var fetching = false;//stops multiple requests from taking place (particularly on firefox)
    $(window).scroll(function(){ //called when the user scrolls
        if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight & !fetching) {//if the user is at the bottom of the page and a fetch is not going on
    	var messages = document.getElementsByClassName("message");//get all messages
    	var newMessages = document.getElementsByClassName("newmessage");//get all messages
    	var newMessageCount = newMessages.length;
    	var numMessages = messages.length;//get the number of messages
    	var total = ${totalMessages};//get the total number of messages that existed when the user loaded the page originally
    	if(messages.length < 10)
    		{
    			return;
    		}
    	var lastMessageId = "${messages[9].messageId}";//This is used so that we load messages only after this one because the index of the last message seen can change
    												   //if a new message is posted by someone else
    	fetching = true;//a new fetch is in progress set fetching to true
    	$.ajax({
    	    type:'GET',//Sends a DELETE request which tells the servlet to delete the message with the given messageId
    	    data: {messageCount: numMessages, lastMessage: lastMessageId, totalMessages: total, newMessages: newMessageCount},
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
</head>
<body onload="formatMessages()">
<jsp:include page="navigationbar.jsp" />
  <div id="dialog-confirm" title="Are you sure about this?" style="display: none;">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Your message will be permanently deleted.</p>
</div>
	<div id="broadcastcontainer">
		<h1>Search Results</h1>
		<c:if test="${errorMessage != null }">
			<div id="whiteText">
				<center>${errorMessage}</center>
			</div>
		</c:if>
		<div id="moreResults"></div>
		<c:choose>
			<c:when test="${userList == null}">
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
			</c:when>
			<c:otherwise>
<c:forEach items="${userList}" var="individualFollowing">
			<c:if test="${profileUser.username != individualFollowing.username}">
				<!-- Don't show that the user is following themselves, just show other users -->
				<p>
				<div class="user">
					<div class="userProfilePicture">
						<img
							src="${pageContext.request.contextPath}/img/blank-profile-pic.png"
							alt="Profile picture" width="45" height="30">
					</div>
					<div class="usernameArea">
						<a href="${pageContext.request.contextPath}/profile/${individualFollowing.username}">${individualFollowing.username}</a>
					</div>
					<c:if test="${activeUser.username != individualFollowing.username}">
						<c:choose>
							<c:when test="${individualFollowing.isActiveUserFollowing == true}">
								<div class="followButton">
									<button type="button" onclick="deleteFollow('${pageContext.request.contextPath}','${individualFollowing.username}')">Unfollow</button>
								</div>
							</c:when>
							<c:otherwise>
								<div class="followButton">
									<button type="button" onclick="follow('${pageContext.request.contextPath}','${individualFollowing.username}')">Follow</button>
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
					<div class="bioArea">${individualFollowing.username}'s bio: ${individualFollowing.bio}</div>
				</div>
			</c:if>
		</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>