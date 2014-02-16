<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<!-- https://jqueryui.com/dialog/#modal-confirmation for base -->
	  function showDialog()
	  {
	  $(function() {
	    $( "#dialog-confirm" ).dialog({
	      resizable: false,//I have left this as false since it can look a bit untidy if you resize it smaller than it is
	      height:190,
	      width:400,
	      modal: true,
	      draggable: false,//I want this to appear in only 1 position so this is set to not be draggable
	      buttons: {
	        "I'm sure!": function(){//If the button annotated with 'I'm sure!' is pressed...
	        deleteAccount();//Call the delete message function
	        $( this ).dialog( "close" );//Close the dialog box, not really needed since a redirect is done immediately after the account is deleted
	        },
	        Cancel: function() {//If the cancel button is pressed..
	          $( this ).dialog( "close" );//Close the dialog box
	        }
	      },
	      open: function() {//The open function is called when the dialog box is opened
	          $(this).siblings('.ui-dialog-buttonpane').find("button:contains('Cancel')").focus(); 
	        //This line makes the 'Cancel' button the default selected button so that the user is less likely to make an irreversible mistake
	      }
	    });
	  });
	  }
	  //As you can see there is no argument to this function, this function delete's the active user's account.
	  function deleteAccount()
	  {
	  	$.ajax({
	  	    url: "${pageContext.request.contextPath}/profile/",//no argument required here
	  	    type:'DELETE',//Sends a DELETE request which tells the servlet to delete the message with the given messageId
	  	    success: 
	  	        function(msg){
	  	            alert("Your profile has been deleted.");
	  	            location.reload();//reload the screen showing the user the login screen since they are no longer logged in
	  	        }                   
	  	    });
	  }
	  
	  $(function() {
		    $( "#dialog-confirm" ).toggle();//This is important. This line toggles the visibility of the 'dialog-confirm' div directly below so that it does not interefere
		    								//with the page before it is shown in the dialog box. 
		  });
	  function editProfileFormValidation()
	  {
			var email=document.forms["editProfileForm"]["email"].value;
			var password=document.forms["editProfileForm"]["oldPassword"].value;
			var firstName = document.forms["editProfileForm"]["firstName"].value;
			var lastName = document.forms["editProfileForm"]["lastName"].value;
			var newPassword = document.forms["editProfileForm"]["newPassword"].value;
			var confirmNewPassword = document.forms["editProfileForm"]["confirmNewPassword"].value;
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
			if(newPassword != confirmNewPassword)
			{
				alert("New passwords do not match!");
				return false;
			}
			if(firstName == "")
			{
				alert("First name cannot be left blank!");
				return false;
			}
			if(lastName == "")
			{
				alert("Last name cannot be left blank!");
				return false;
			}
			return true;
	  }
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ChitChat - Edit Profile</title>
</head>
<body>

  <div id="dialog-confirm" title="Are you sure about this?" >
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Your account will be permanently deleted and you will lose everything if you proceed.</p>
</div>
<% if(request.getSession().getAttribute("activeUser") == null)
	{
		response.sendRedirect("login.jsp");
	}
%>
<div id="editprofilecontainer"><h1>Edit Profile</h1>
    <form name="editProfileForm" method="post" action="profile" onsubmit="return editProfileFormValidation()">
      <p><input type="text" name="username" value="${activeUser.username }" placeholder="Username" readonly></p>
      <p><input type="text" name="email" value="${activeUser.emailAddress }" placeholder="Email Address" readonly></p>
      <p><input type="text" name="firstName" value="${activeUser.firstName }" placeholder="First Name"></p>
      <p><input type="text" name="lastName" value="${activeUser.lastName }" placeholder="Last Name"></p>
      <p><input type="text" name="bio" value="${activeUser.bio }" placeholder="Bio"></p>
      <p><input type="password" name="oldPassword" value="" placeholder="Old Password"></p>
      <p><input type="password" name="newPassword" value="" placeholder="New Password"></p>
      <p><input type="password" name="confirmNewPassword" value="" placeholder="Confirm New Password"></p>

      <p class="submit"><input type="submit" name="editProfileButton" value="Submit"></p>
    </form>
    <c:if test="${errorMessage != null }">
    <div id="errorMessage"><font color="red">${errorMessage }</font></div>
    </c:if>
    <div id="deleteAccount"><input type="submit" name="deleteAccountButton" onclick="showDialog()" value="Delete Account"></p></div>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>