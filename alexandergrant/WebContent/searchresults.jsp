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
//checks for new messages every 5 seconds and adds them to the page if they exist
//This is useful because the user does not have to refresh the page to see new messages
function fetchNewMessages()
{
	//Goes through all div message classes and retrieves the div id which holds the message id for the message in that div
	//finds the 'newest' shown message, i.e the top currently shown message on the page
  	var newMessages = document.getElementsByClassName("newmessage");//get all messages
 	var newestMessage = -1;
 	if(newMessages.length > 0)//if a new message exists, the first one is the newest overall message
	{
  		newestMessage = newMessages[0].id;
	}
  	else
  	{
  		var messages = document.getElementsByClassName("message");
  		if(messages.length > 0)//if there are more than 1 message
  		{
  			newestMessage = messages[0].id;
  		}
 	}
	var search = "${searchTerm}";//get search term from POST search
  	var select = "${searchSelect}";//get search select from POST search
	
  	//So, if select has a value. This means that a post search is being displayed so send the searchTerm and searchSelect to the server
  	//so that new messages can be displayed
  	if(select)
  	{
  		$.ajax({
  			type:'GET',
  			cache: false,//internet explorer support
  			data: {newestMessageId: newestMessage, searchTerm: search, searchSelect: select},
  				success: 
  					function(messages){
  		   			 $("#newMessages").prepend(messages);
  		   			 detectAndAddHashTags();//add hash tag links to new messages
  		   			 },
 		 	});
  	}
  	//So, if search does not have a value. This means that a GET (hashtag) search is being displayed so just send the newest message Id
  	//to the server
  	else
  	{
  		$.ajax({
  			type:'GET',
  			cache: false,//internet explorer support
  			data: {newestMessageId: newestMessage},
  				success: 
  					function(messages){
  		   			 $("#newMessages").prepend(messages);
  		   			 detectAndAddHashTags();//add hash tag links to new messages
  		   			 },
 		 	});
  	}
  setTimeout(fetchNewMessages, 5000);//check for more new messages again in 5 seconds
}

$(function() {
	var total = document.getElementsByClassName("message").length;//get all messages
	if(total > 10)
	{
		$("#moreResults").html("<center>There are more messages to be displayed.  Scroll down to the bottom to see more.</center>")
	}
	setTimeout(fetchNewMessages, 5000);//start fetching
  });
  
//Fetches older messages, this MUST be repeated in each jsp to support firefox.
//The fetching variable is used so that only 1 fetch can take place at a time, on firefox if this is not here then multiple requests
//will be returned and multiple copies of the same data will be displayed
$(document).ready(function(){
	var fetching = false;//stops multiple requests from taking place (particularly on firefox)
  $(window).scroll(function(){ //called when the user scrolls
  	if($(window).scrollTop() + $(window).height() == $(document).height() & !fetching) {//if the user is at the bottom of the page and a fetch is not going on
          //This part gets the oldest currently shown message	
        var messages = document.getElementsByClassName("message");//get all messages
        var oldestMessage = -1;
        if(messages.length > 0)
      	{
          	oldestMessage = messages[messages.length-1].id;
      	}
        else
        {
         	var newMessages = document.getElementsByClassName("message");
          		if(newMessages.length > 0)
          		{
          			oldestMessage = newMessages[messages.length-1].id;
          		}
       	}
      	var numMessages = messages.length;//get the number of messages
      	if(numMessages.length < 10)
      	{
      		return;//there are no older messages
      	}									  
      	fetching = true;//a new fetch is in progress set fetching to true
      	//This part sends the oldest currently shown message to the server so that more can be fetched
      	
      	var search = "${searchTerm}";//get search term from POST search
      	var select = "${searchSelect}";//get search select from POST search
  		
      	if(select)//if the POST search was used
      	{
          	$.ajax({
          		cache: false,//internet explorer support
          	    type:'GET',
          	    //Data holds the oldest message id, search term and the type of search e.g users or messages
          	    data: {oldestMessageId: oldestMessage, searchTerm: search, searchSelect: select},
          		    success: 
          		        function(messages){
          		            $("#largecontainer").append(messages);//add the retrieved messages to the page
          		            formatMessages();//add hash tag links etc to messages
          		            fetching = false;//no longer fetching, allow another fetch to occur
          		        }                  
          	    });
      	}
      	else
      	{
      		//if the RESTful GET search was used (hash tags)
          	$.ajax({
          		cache: false,//internet explorer support
          	    type:'GET',
          	    data: {oldestMessageId: oldestMessage},
          		    success: 
          		        function(messages){
          		            $("#largecontainer").append(messages);//add the retrieved messages to the page
          		            formatMessages();//add hash tag links etc to messages
          		            fetching = false;//no longer fetching, allow another fetch to occur
          		        }                  
          	    });
      	}
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
	<div id="largecontainer">
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
      <p><div class="message" id="${individualMessage.messageId }">
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
<c:forEach items="${userList}" var="individualUser">
			<c:if test="${profileUser.username != individualUser.username}">
				<!-- Don't show that the user is following themselves, just show other users -->
				<p>
				<div class="user">
					<div class="userProfilePicture">
						<img
							src="${pageContext.request.contextPath}/img/blank-profile-pic.png"
							alt="Profile picture" width="45" height="30">
					</div>
					<div class="usernameArea">
						<a href="${pageContext.request.contextPath}/profile/${individualUser.username}">${individualUser.username}</a>
					</div>
					<c:if test="${activeUser.username != individualUser.username}">
						<c:choose>
							<c:when test="${individualUser.isActiveUserFollowing == true}">
								<div class="followButton">
									<button type="button" onclick="deleteFollow('${pageContext.request.contextPath}','${individualUser.username}')">Unfollow</button>
								</div>
							</c:when>
							<c:otherwise>
								<div class="followButton">
									<button type="button" onclick="follow('${pageContext.request.contextPath}','${individualUser.username}')">Follow</button>
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
					<div class="bioArea">${individualUser.username}'s bio: ${individualUser.bio}</div>
				</div>
			</c:if>
		</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>