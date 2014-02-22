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
<title>ChitChat - ${profileUser.username}'s Profile</title>
</head>
<script type="text/javascript">
$(function() {
    $( "#dialog-confirm" ).toggle();//This is important. This line toggles the visibility of the 'dialog-confirm' div directly below so that it does not interefere
    								//with the page before it is shown in the dialog box. 
	setTimeout(fetchNewMessages, 5000);//fetch new messages every 5 seconds
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
      	$.ajax({
      		cache: false,//internet explorer support
      	    type:'GET',
      	    data: {oldestMessageId: oldestMessage},
      		    success: 
      		        function(msg){
      		            $("#largecontainer").append(msg);//add the retrieved messages to the page
      		            formatMessages();//add hash tag links etc to messages
      		            fetching = false;//no longer fetching, allow another fetch to occur
      		        }                  
      	    });
      }
  	});
  });
</script>

<body onload="formatMessages()">
<jsp:include page="navigationbar.jsp" /> <!-- add the navigation bar to the top of the page -->
  <div id="dialog-confirm" title="Are you sure about this?" >
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Your message will be permanently deleted.</p>
</div>
<div id="profilearea">
    <h1>${profileUser.username}'s Profile</h1>
    <div id="profilepicture">
    <img src="${pageContext.request.contextPath}/img/blank-profile-pic.png" alt="Profile picture" width="100" height="75">
    </div>
    <center><font color="#ffffff" size="+1">My Bio</font></center>
    <div id="bio">
    <font color="#ffffff">${profileUser.bio}</font>
    </div>
    <c:if test="${activeUser.username != profileUser.username}">
    						<c:choose>
							<c:when test="${profileUser.isActiveUserFollowing == true}">
								<div id="deleteMessageButton">
									<p class="submit"><input type="submit" name="followButton" onclick="deleteFollow('${pageContext.request.contextPath}','${profileUser.username}')" value="Unfollow ${profileUser.username}"></p>
								</div>
							</c:when>
							<c:otherwise>
								<div id="deleteMessageButton">
								<p class="submit"><input type="submit" name="followButton" onclick="follow('${pageContext.request.contextPath}','${profileUser.username}')" value="Follow ${profileUser.username}"></p>
								</div>
							</c:otherwise>
						</c:choose>
    </c:if>
    <p class="followButtons"><a href="${pageContext.request.contextPath}/followers/${profileUser.username }"><input type="submit" name="followerButton" onclick="" value="Followers"></a><a href="${pageContext.request.contextPath}/following/${profileUser.username }"><input type="submit" name="followingButton" onclick="" value="Following"></a></p>
	<c:if test="${profileUser.username == activeUser.username }">
	<p class="editProfile"><a href="${pageContext.request.contextPath}/editprofile.jsp"><input type="submit" name="editProfileButton" onclick="" value="Edit Profile"></a></p>
	</c:if>
</div>
<div id="largecontainer">
    <h1>${profileUser.username }'s Messages</h1>
    <c:if test="${noMessages != null }">
    <div id="whiteText"><center>${noMessages}</center></div>
    </c:if>
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
</div>

<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</body>
</html>