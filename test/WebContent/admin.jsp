<%@page import="test.Model.User"%>
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
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ChitChat - Admin Panel</title>
</head>
<body>
<c:if test="${activeUser.isAdmin == false }">
<% response.sendRedirect("404.jsp");%>//Secure the admin area to only admins, 404 if any non-admins try and access
</c:if>
<div id="adminpanelcontainer"><h1>Admin Panel</h1>
Grant Admin Priveleges
</div>
<jsp:include page="footer.jsp" />
</body>
</html>