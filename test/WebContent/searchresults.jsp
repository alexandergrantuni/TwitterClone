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
    $( "#dialog-confirm" ).toggle();//This is important. This line toggles the visibility of the 'dialog-confirm' div directly below so that it does not interefere
    								//with the page before it is shown in the dialog box. 
  });
</script>
</head>
<body onload="formatMessages()">
<jsp:include page="navigationbar.jsp" />
  <div id="dialog-confirm" title="Are you sure about this?" >
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Your message will be permanently deleted.</p>
</div>
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