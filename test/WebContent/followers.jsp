<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- library which contains a number of useful jsp tags like c:if -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /> <!-- stylesheet -->
<title>ChitChat - Followers for ${profileUser.username}</title>
</head>
<jsp:include page="navigationbar.jsp" /> <!-- add the navigation bar to the top of the page -->
<body>
<body>
<script type="text/javascript">
function deleteFollower(username)
{
	$.ajax({
	    url: "${pageContext.request.contextPath}/followers/"+username,
	    type:'DELETE',//Sends a DELETE request which tells the servlet to delete the message with the given messageId
	    success: 
	        function(msg){
	            alert("The selected follower has been deleted.");
	            location.reload();
	        }                   
	    });
}
</script>
<div id="profilearea">
    <h1>${profileUser.username}'s Profile</h1>
    <div id="profilepicture">
    <img src="${pageContext.request.contextPath}/img/blank-profile-pic.png" alt="Profile picture" width="100" height="75">
    </div>
        <center><font color="#ffffff" size="+1">${profileUser.username}'s Bio</font></center>
    <div id="bio">
    <font color="#ffffff">${profileUser.bio}</font>
    </div>
    <c:if test="${activeUser.username != profileUser.username}">
     <p class="submit"><input type="submit" name="followButton" value="Follow ${profileUser.username}"></p>
    </c:if>
  </div>

  <div id="broadcastcontainer">
    <h1>${profileUser.username}'s followers</h1>
      <c:forEach items="${followerList}" var="individualFollower">
      <c:if test="${profileUser.username != individualFollower.username}"> <!-- Don't show that the user is following themselves, just show other users -->
      <p><div id="message">
      <div id="messageProfilePicture"><img src="${pageContext.request.contextPath}/img/blank-profile-pic.png" alt="Profile picture" width="45" height="30"></div>
      <div id="messageText"><a href="profile/${individualFollower.username}">${individualFollower.username}</a></div>
      <div id="deleteMessageButton"><img src="${pageContext.request.contextPath}/img/bin.png" alt="Profile picture" width="21" height="25"></div>
      <div id="timestampArea">${individualFollower.username}'s bio: ${individualFollower.bio}</div>
      </div>
      </c:if>
</c:forEach>
  </div>
<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</body>
</body>
</html>