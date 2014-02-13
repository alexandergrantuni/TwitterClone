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
<title>ChitChat - ${profileUser.username}'s Profile</title>
</head>
<script type="text/javascript">
function deleteFollow(username)
{
	$.ajax({
	    url: "${pageContext.request.contextPath}/following/"+username,
	    type:'DELETE',//Sends a DELETE request which tells the servlet to delete the message with the given messageId
	    success: 
	        function(msg){
	            alert("You have unfollowed "+username+".");
	            location.reload();
	        }                   
	    });
}
function follow(username)
{
	$.ajax({
	    url: "${pageContext.request.contextPath}/following/"+username,
	    type:'POST',//Sends a POST request which tells the servlet to follow the user with the given username
	    success: 
	        function(msg){
	            alert("You are now following "+username+".");
	            location.reload();
	        }                   
	    });
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

function viewMessage(Id)
{
            window.location = "${pageContext.request.contextPath}/messages/"+Id;
}
</script>

<body onload="formatMessages()">
<jsp:include page="navigationbar.jsp" /> <!-- add the navigation bar to the top of the page -->
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
<div id="broadcastcontainer">
    <h1>${profileUser.username }'s Messages</h1>
    <c:if test="${noMessages != null }">
    <div id="whiteText"><center>${noMessages}</center></div>
    </c:if>
    <c:forEach items="${messages}" var="individualMessage">
      <p><div class="message">
      <div class="messageProfilePicture"><img src="${pageContext.request.contextPath}/img/blank-profile-pic.png" alt="Profile picture" width="45" height="30"></div>
      <div class="messageText">${individualMessage.text }</div>
      <c:if test="${activeUser.username == individualMessage.owner.username || activeUser.isAdmin == true}">
      <div class="deleteMessageButton"><button type="submit"onclick="deleteMessage('${pageContext.request.contextPath}','${individualMessage.messageId}')"><img src="${pageContext.request.contextPath}/img/bin.png" alt="Delete Message" width="21" height="25"></button></div>
      </c:if>
      <div class="timestampArea">Posted by ${individualMessage.owner.username } ${individualMessage.timePostedAgo() }</div>
      <div class="viewIndividually"><u><a href="${pageContext.request.contextPath}/messages/${individualMessage.messageId }">View Individually</a></u></div>
      </div>
</c:forEach>
</div>

<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</body>
</html>