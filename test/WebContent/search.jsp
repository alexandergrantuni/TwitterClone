<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/style.css" type="text/css" /> <!-- stylesheet -->
<title>ChitChat - Search</title>
</head>
<jsp:include page="navigationbar.jsp" /> <!-- add the navigation bar to the top of the page -->
<script>
function searchFormValidation()
{
	var searchTerm=document.forms["searchForm"]["searchTerm"].value;
	var type=document.forms["loginForm"]["searchSelect"].value;
	if(searchTerm == "")
	{
		alert("You need to enter a search term");
		return false;
	}
}
</script>
<body>
<div class="container">
  <div class="login">
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
</div>
<jsp:include page="footer.jsp" /> <!-- add the footer to the bottom of the page -->
</body>

</html>