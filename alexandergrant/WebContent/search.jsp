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
<title>ChitChat - Search</title>
</head>
<jsp:include page="navigationbar.jsp" /> <!-- add the navigation bar to the top of the page -->
<script>
function searchFormValidation()
{
	var searchTerm=document.forms["searchForm"]["searchTerm"].value;
	if(searchTerm.length == 0)
	{
		alert("You need to enter a search term");
		return false;
	}
	return true;
}
</script>
<body>
<% 
	//If the user is logged in go to the login page, if not go to the user's messages
	if(request.getSession().getAttribute("activeUser") == null)
	{
		response.sendRedirect("login.jsp");
	}
%>
<div class="searchcontainer">
    <h1>Search ChitChat</h1>
    <form name="searchForm" method="post" action="search" onsubmit="return searchFormValidation()">
      <p><input type="text" name="searchTerm" value="" placeholder="Search term"></p>
      <p><select name="searchSelect" placeholder="Search term">
<option value="users">Search Users</option>
<option value="messages">Search Messages</option>
</select></p>
      <p class="submit"><input type="submit" name="searchButton" value="Search"></p>
    </form>
</div>
<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</body>

</html>