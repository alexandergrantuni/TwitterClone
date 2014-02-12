<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- library which contains a number of useful jsp tags like c:if -->
<link rel="stylesheet" href="css/style.css" type="text/css" /> <!-- stylesheet -->
        <div id='nav-bar'>
            <ul>
                <li><a href='${pageContext.request.contextPath}/messages'>Home</a></li>
                <li><a href='${pageContext.request.contextPath}/profile/'>My Profile</a></li>
                <li><a href='${pageContext.request.contextPath}/following'>Following</a></li>
                <li><a href='${pageContext.request.contextPath}/followers'>Followers</a></li>
                <li><a href='${pageContext.request.contextPath}/search'>Search</a></li>
                <c:if test="${activeUser.isAdmin == true }">
                <li><a href='${pageContext.request.contextPath}/admin'>Admin</a></li>
                </c:if>
                <li><a href='${pageContext.request.contextPath}/Logout'>Sign Out</a></li>
            </ul>
        </div>