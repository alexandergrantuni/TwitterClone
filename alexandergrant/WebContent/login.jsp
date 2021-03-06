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
<title>ChitChat - Login</title>
<script>
function loginFormValidation()
{
	var email=document.forms["loginForm"]["emailAddress"].value;
	var password=document.forms["loginForm"]["userPassword"].value;
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
</head>
<body>
<% if(request.getSession().getAttribute("activeUser") != null)
	{
		response.sendRedirect("messages");
	}
%>
<div class="smallcontainer">
    <h1>ChitChat Login</h1>
    <form name="loginForm" method="post" action="Login" onsubmit="return loginFormValidation()">
      <p><input type="text" name="emailAddress" value="" placeholder="Email Address"></p>
      <p><input type="password" name="userPassword" value="" placeholder="Password"></p>
      <p class="submit"><input type="submit" name="loginButton" value="Login"></p>
    </form>
    <c:if test="${errorMessage != null }">
    <p><center><font color="#ff0000" >Invalid email address and/or password. </font></center></p>
    </c:if>
</div>
 
  <div class="login-help">
    <p>Not registered? <a href="register.jsp">Register here!</a></p>
  </div>
<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</body>

</html>