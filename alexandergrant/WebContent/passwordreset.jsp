<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /> <!-- stylesheet -->
<title>Login</title>
</head>
<script>
function inputValidation()
{
	var email=document.forms["resetPasswordForm"]["emailAddress"].value;
	var username=document.forms["resetPasswordForm"]["username"].value;
	var emailRegex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;	
	if ( email == "")
	{
		alert("You didn't enter an email address.");
		return false;
	}
	if(email.length < 4 && email != "")
	{
		alert("The email address you entered is too short, check it's valid and try again.");	
		return false;
	}
	if (!emailRegex.test(email))
	{
		alert("You entered an invalid email address.");
		return false;
	}
	if(username == "")
	{
		alert("You did not enter a username.");
		return false;
	}
	if(username.length < 3 && username != "")
	{
		alert("The username you entered is too short.");
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
<div class="container">
  <div class="login">
    <h1>ChitChat Password Reset</h1>
    <form name="resetPasswordForm" method="post" action="PasswordResetServlet" onsubmit="return inputValidation()">
      <p><input type="text" name="emailAddress" value="" placeholder="Email Address"></p>
      <p><input type="text" name="username" value="" placeholder="Username"></p>
      <p class="submit"><input type="submit" name="resetPwButton" value="Reset Password"></p>
    </form>
    <%if(request.getAttribute("errorMessage") != null){%>
    <center><font color="FF0000"><%= request.getAttribute("errorMessage")%></font></center>
    <%} %>
  </div>
</div>
<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</body>
</html>