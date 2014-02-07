<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js" type="text/javascript"></script><!-- jquery lib -->
<!-- stylesheet -->
<jsp:include page="navigationbar.jsp" />
<script>
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ChitChat - Edit Profile</title>
</head>
<body>
<% if(request.getSession().getAttribute("activeUser") == null)
	{
		response.sendRedirect("login.jsp");
	}
%>
<div id="editprofilecontainer"><h1>Edit Profile</h1>
    <form name="loginForm" method="post" action="profile" onsubmit="return profileFormValidation()">
      <p><input type="text" name="username" value="${activeUser.username }" placeholder="Username" readonly></p>
      <p><input type="text" name="email" value="${activeUser.emailAddress }" placeholder="Email Address" readonly></p>
      <p><input type="text" name="firstName" value="${activeUser.firstName }" placeholder="First Name"></p>
      <p><input type="text" name="lastName" value="${activeUser.lastName }" placeholder="Last Name"></p>
      <p><input type="text" name="bio" value="${activeUser.bio }" placeholder="Bio"></p>
      <p><input type="password" name="password" value="" placeholder="Password"></p>
      <p><input type="password" name="confirmPassword" value="" placeholder="Confirm Password"></p>
      <p class="submit"><input type="submit" name="editProfileButton" value="Submit"></p>
    </form>
    <c:if test="${errorMessage != null }">
    <div id="errorMessage"></div>
    </c:if>
<div id="deleteProfile">Delete Profile</div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>