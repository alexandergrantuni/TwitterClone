function detectAndAddHashTags()
{
    var messages = document.getElementsByClassName("messageText");//Gets all messages by taking the messageText div classes which contain the text for each message
    for (var i = 0; i < messages.length; i++) //Go through each message checking if there are any hashtags
    {
    	var hashTagRegex = /^#\w\w+/;//A regular expression for identifying hash tags
    	var current = messages[i].innerHTML;//Put the html for this message into a variable
    	//messages[i].innerHTML = "hello";
		var regexResult = hashTagRegex.exec(current);//Get the hash tag
		current = current.replace(hashTagRegex, '<a href="/test/search/messages/'+regexResult[0].replace("#","")+'">' + regexResult[0] + '</a>');//The replace is done here because the search doesn't seem to support # in the url
		//# is a special character sometimes used to link to anchors in webpages so this might be why
        messages[i].innerHTML = current;
    }
}

function deleteMessage(messageId)
{
	$.ajax({
	    url: "${pageContext.request.contextPath}/messages/"+messageId,
	    type:'DELETE',//Sends a DELETE request which tells the servlet to delete the message with the given messageId
	    success: 
	        function(msg){
	            alert("Your message has been deleted.");
	            location.reload();
	        }                   
	    });
}

function viewMessage(Id)
{
    $.ajax({
        url: "${pageContext.request.contextPath}/messages/"+messageId,
        success: function(){
            document.location = url;  // redirect browser to link
        }
    });
}

function deleteFollow(username)
{
	$.ajax({
	    url: "${pageContext.request.contextPath}/following/"+username,
	    type:'DELETE',//Sends a DELETE request which tells the servlet to delete the message with the given messageId
	    success: 
	        function(msg){
	            alert("You have unfollowed "+username+".");
	            location.reload();
	        }                   
	    });
}

function follow(path, username)
{
	alert("called follow");
	alert(path+username);
	$.ajax({
	    url: "${pageContext.request.contextPath}/following/"+username,
	    type:'POST',//Sends a POST request which tells the servlet to follow the user with the given username
	    success: 
	        function(msg){
	            alert("You are now following "+username+".");
	            location.reload();
	        }                   
	    });
}