<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- library which contains a number of useful jsp tags like c:if -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js" type="text/javascript"></script><!-- jquery lib -->
	<script src="${pageContext.request.contextPath}/js/utils.js"></script><!-- My utils javascript file with useful functions I've created. -->
<!-- stylesheet -->
<title>ChitChat - Followers for ${profileUser.username}</title>
</head>
<jsp:include page="navigationbar.jsp" />
<!-- add the navigation bar to the top of the page -->
<body>
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
</script>
	<div id="profilearea">
		<h1>${profileUser.username}'s Profile</h1>
		<div id="profilepicture">
			<img
				src="${pageContext.request.contextPath}/img/blank-profile-pic.png"
				alt="Profile picture" width="100" height="75">
		</div>
		<center>
			<font color="#ffffff" size="+1">${profileUser.username}'s Bio</font>
		</center>
		<div id="bio">
			<font color="#ffffff">${profileUser.bio}</font>
		</div>
		<c:if test="${activeUser.username != profileUser.username}">
    						<c:choose>
							<c:when test="${profileUser.isActiveUserFollowing == true}">
								<div id="deleteMessageButton">
									<p class="submit"><input type="submit" name="followButton" onclick="deleteFollow('${profileUser.username}')" value="Unfollow ${profileUser.username}"></p>
								</div>
							</c:when>
							<c:otherwise>
								<div id="deleteMessageButton">
								<p class="submit"><input type="submit" name="followButton" onclick="follow('${profileUser.username}')" value="Follow ${profileUser.username}"></p>
								</div>
							</c:otherwise>
						</c:choose>
		</c:if>
<p class="followButtons"><a href="${pageContext.request.contextPath}/followers/${profileUser.username }"><input type="submit" name="followerButton" onclick="" value="Followers"></a><a href="${pageContext.request.contextPath}/following/${profileUser.username }"><input type="submit" name="followingButton" onclick="" value="Following"></a></p>	
</div>

	<div id="broadcastcontainer">
		<h1>${profileUser.username}'s followers</h1>
		<c:if test="${noFollowers != null }">
		<div id="whiteText"><center>${noFollowers}</center></div>
		</c:if>
		<c:forEach items="${followerList}" var="individualFollower">
			<c:if test="${profileUser.username != individualFollower.username}">
				<!-- Don't show that the user is following themselves, just show other users -->
				<p>
				<div class="user">
					<div class="userProfilePicture">
						<img
							src="${pageContext.request.contextPath}/img/blank-profile-pic.png"
							alt="Profile picture" width="45" height="30">
					</div>
					<div class="usernameArea">
						<a href="profile/${individualFollower.username}">${individualFollower.username}</a>
					</div>
					
					<c:if test="${activeUser.username != individualFollower.username}">
					<c:choose>
							<c:when test="${individualFollower.isActiveUserFollowing == true}">
								<div class="followButton">
									<button type="button" onclick="deleteFollow('${individualFollower.username}')">Unfollow</button>
								</div>
							</c:when>
							<c:otherwise>
								<div class="followButton">
									<button type="button" onclick="follow('${individualFollower.username}')">Follow</button>
								</div>
							</c:otherwise>
					</c:choose>
					</c:if>
					<div class="bioArea">${individualFollower.username}'s bio:
						${individualFollower.bio}</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
	<jsp:include page="footer.jsp" />
	<!-- add the footer to the bottom of the page -->
</body>
</html>