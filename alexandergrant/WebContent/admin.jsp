<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" /><!-- stylesheet -->
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js" type="text/javascript"></script><!-- jquery lib -->
	 
	 <!-- Required for confirmation box -->
	  <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.9.1.js"></script>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
<jsp:include page="navigationbar.jsp" />
<script>
	  function inputValidation()
	  {
	  	var firstName=document.forms["registrationForm"]["firstName"].value;
	  	var lastName=document.forms["registrationForm"]["lastName"].value;
	  	var email=document.forms["registrationForm"]["emailAddress"].value;
	  	var password=document.forms["registrationForm"]["userPassword"].value;
	  	var username=document.forms["registrationForm"]["username"].value;
	  	var emailRegularExpression=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;//Found online, did not write this regular expression myself http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
	  	
	  	if(firstName == "")
	  	{
	  		alert("You didn't provide a first name!");
	  		return false;
	  	}
	  	if(firstName.length > 255)
	  	{
	  		alert("Your first name is too long to store, try abbreviating it.");
	  		return false;
	  	}
	  	if(lastName == "")
	  	{
	  		alert("You didn't provide a last name!");
	  		return false;
	  	}
	  	if(lastName > 255)
	  	{
	  		alert("Your last name is too long to store.  Try abbreviating it.");
	  		return false;
	  	}
	  	if ( email == "")
	  	{
	  		alert("You did not provide an email address.");
	  		return false;
	  	}
	  	if ( email != "" && email.length < 4)
	  	{
	  		alert("The email address you gave is too short.");
	  		return false;
	  	}
	  	if (!emailRegularExpression.test(email))
	  	{
	  		alert("Invalid Email Address.");
	  		return false;
	  	}
	  	if(password.length < 8)
	  	{
	  		alert("The password you entered is too short.  Passwords must be at least 8 characters long.");
	  		return false;
	  	}
	  	if(password.length > 18)
	  	{
	  		alert("Your chosen password is too long.  The maximum length of a password is 18 characters.");
	  		return false;
	  	}
	  	if(username == "")
	  	{
	  		alert("You didn't enter a username.");
	  		return false;
	  	}
	  	if(username != "" && username.length < 3 || username.length > 15)
	  	{
	  		alert("Usernames must be between 3 and 15 characters long.");
	  		return false;
	  	}
	  	showDialog();
	  	return true;
	  }
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ChitChat - Admin Panel</title>
</head>
<body>
<c:if test="${activeUser.isAdmin == false }">
<% response.sendRedirect("404.jsp");//Secure the admin area to only admins, 404 if any non-admins try and access%>
</c:if>
<div id="adminpanelcontainer"><h1>Admin Panel</h1>
    <p><font size="3"><center><b>Create a new Administrator</b></center></font></p>
    <form name="registrationForm" method="post" action="admin" onsubmit="return inputValidation()">
      <p><input type="text" name="firstName" value="" placeholder="First Name"></p>
      <p><input type="text" name="lastName" value="" placeholder="Last Name"></p>
      <p><input type="text" name="emailAddress" value="" placeholder="Email Address"></p>
      <p><input type="text" name="username" value="" placeholder="Enter a username"></p>
      <p><input type="password" name="userPassword" value="" placeholder="Password"></p>
      <p class="submit"><input type="submit" name="Register" value="Register"></p>
    </form>
    <c:if test="${errorMessage != null }">
	<font color="red"><center>${errorMessage }</center></font>
	</c:if>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>