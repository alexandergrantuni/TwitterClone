<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" /> <!-- stylesheet -->
<title>ChitChat - Profile Update Successful</title>
</head>
<body>
<jsp:include page="navigationbar.jsp" />
<% if(request.getSession().getAttribute("activeUser") == null)
	{
		response.sendRedirect("login.jsp");
	}
%>
<div class="container">
  <div class="login">
    <h1>Success!</h1>
    <div id="whiteText">Your details were changed successfully.  Click <a href="${pageContext.request.contextPath}/messages">here</a> to continue.</div>
</div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>