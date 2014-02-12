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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ChitChat - Search Results</title>
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
</script>
<body onload="detectAndAddHashTags()">
<jsp:include page="navigationbar.jsp" />
	<div id="broadcastcontainer">
		<h1>Search Results</h1>
		<c:if test="${errorMessage != null }">
			<div id="whiteText">
				<center>${errorMessage}</center>
			</div>
		</c:if>
		<c:choose>
			<c:when test="${userList == null}">
<c:forEach items="${messageList}" var="individualMessage">
			<p>
			<div class="message">
				<div class="messageProfilePicture">
					<img
						src="${pageContext.request.contextPath}/img/blank-profile-pic.png"
						alt="Profile picture" width="45" height="30">
				</div>
				<div class="messageText">${individualMessage.text }</div>
				<c:if test="${activeUser.username == individualMessage.owner.username || activeUser.isAdmin == true}">
					<div class="deleteMessageButton">
						<button type="submit"
							onclick="deleteMessage('${individualMessage.messageId}')">
							<img src="${pageContext.request.contextPath}/img/bin.png"
								alt="Delete Message" width="21" height="25">
						</button>
					</div>
				</c:if>
				<div class="timestampArea">
					Posted by <a
						href="${pageContext.request.contextPath}/profile/${individualMessage.owner.username}">${individualMessage.owner.username }</a>
					${individualMessage.timePostedAgo() }
				</div>
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
									<button type="button" onclick="deleteFollow('${individualFollowing.username}')">Unfollow</button>
								</div>
							</c:when>
							<c:otherwise>
								<div class="followButton">
									<button type="button" onclick="follow('${individualFollowing.username}')">Follow</button>
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