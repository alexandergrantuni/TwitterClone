<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/style.css" type="text/css" /> <!-- stylesheet -->
<title>Search</title>
</head>
<jsp:include page="navigationbar.jsp" /> <!-- add the navigation bar to the top of the page -->
<script>
function searchFormValidation()
{
	var email=document.forms["searchForm"]["emailAddress"].value;
	var password=document.forms["searchForm"]["userPassword"].value;
	var emailRegex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;//found on stackoverflow
	if ( email == "")
	{
		alert("You didn't enter an email address.");
		return false;
	}
	if(email.length < 4 && email != "")
	{
		alert("Your email address is too short, check it's valid and try again.");	
		return false;
	}
	if (!emailRegex.test(email))
	{
		alert("You entered an invalid email address.");
		return false;
	}
	if(password.length < 8 || password.length > 18 && password != "")
	{
		alert("You entered your password incorrectly.");
		return false;
	}
	if(password == "")
	{
		alert("You didn't enter a password.");
		return false;
	}
	
	return true;
}
</script>
<body>
<div class="container">
  <div class="login">
    <h1>Search ChitChat</h1>
    <form name="searchForm" method="post" action="Search" onsubmit="return searchFormValidation()">
      <p><input type="text" name="emailAddress" value="" placeholder="Search term"></p>
      <p><input type="password" name="userPassword" value="" placeholder="Password"></p>
      <p class="submit"><input type="submit" name="loginButton" value="Search"></p>
    </form>
    <%if (request.getAttribute("errorMessage")!=null){%>
      <p><center><font color="#ff0000" >Invalid email address and/or password. </font></center></p>
<%}%>
</div>
</div>
 
  <div class="login-help">
    <p>Forgot your password? <a href="passwordreset.jsp">Click here to reset it</a> | Not registered? <a href="register.jsp">Register here!</a></p>
  </div>
</div>
<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</body>

</html>