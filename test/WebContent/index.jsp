<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
            <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- library which contains a number of useful jsp tags like c:if -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/style.css" type="text/css" /> <!-- stylesheet -->
<title>Main page</title>
</head>
<body>
<% 
	//If the user is logged in go to the login page, if not go to the user's messages
	if(request.getSession().getAttribute("activeUser") == null)
	{
		response.sendRedirect("login.jsp");
	}
	else
	{
		response.sendRedirect("messages");
	}
%>
</body>
<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</html>