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
<title>ChitChat Registration</title>
</head>
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
	
	return true;
}
</script>
<body>
<div class="smallcontainer">
    <h1>ChitChat Registration</h1>
    <form name="registrationForm" method="post" action="Register" onsubmit="return inputValidation()">
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
</body>
<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</html>