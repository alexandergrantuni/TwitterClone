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
<title>ChitChat - Profile Update Successful</title>
</head>
<body>
<jsp:include page="navigationbar.jsp" />
<% if(request.getSession().getAttribute("activeUser") == null)
	{
		response.sendRedirect("login.jsp");
	}
%>
<div class="smallcontainer">
    <h1>Success!</h1>
    <div id="whiteText">Your details were changed successfully.  Click <a href="${pageContext.request.contextPath}/messages">here</a> to continue.</div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>