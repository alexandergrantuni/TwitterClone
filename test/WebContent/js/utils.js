function detectAndAddHashTags()
{
    var messages = document.getElementsByClassName("messageText");//Gets all messages by taking the messageText div classes which contain the text for each message
    for (var i = 0; i < messages.length; i++) //Go through each message checking if there are any hashtags
    {
    	var hashTagRegex = /^#\w\w+/;//A regular expression for identifying hash tags
    	var current = messages[i].innerHTML;//Put the html for this message into a variable
    	//if there is a string portion that matches the regular expression...
    	if(hashTagRegex.test(current))
    	{
		var regexResult = hashTagRegex.exec(current);//Get the hash tag
		current = current.replace(hashTagRegex, '<a href="/test/search/messages/'+regexResult[0].replace("#","")+'">' + regexResult[0] + '</a>');//The replace is done here because the search doesn't seem to support # in the url
		//# is a special character sometimes used to link to anchors in webpages so this might be why
    	}
        messages[i].innerHTML = current;
    }
}

function detectAndAddWebsiteLinks()
{
    var messages = document.getElementsByClassName("messageText");//Gets all messages by taking the messageText div classes which contain the text for each message
    for (var i = 0; i < messages.length; i++) //Go through each message checking if there are any urls
    {
    	//I did not write this regex! All credits to: http://stackoverflow.com/questions/833469/regular-expression-for-url
    	var urlRegex = /((([A-Za-z]{3,9}:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)/;//A regular expression for identifying urls
    	var current = messages[i].innerHTML;//Put the html for this message into a variable
    	//if there is a string portion that matches the regular expression...
    	if(urlRegex.test(current))
    	{
    		var regexResult = urlRegex.exec(current);//Get the hash tag
			current = current.replace(urlRegex, '<a href='+regexResult[0]+'>' + regexResult[0] + '</a>');//The replace is done here because the search doesn't seem to support # in the url
    	}
        messages[i].innerHTML = current;
    }
}

function formatMessages()
{
	detectAndAddWebsiteLinks();
	detectAndAddHashTags();
}

function deleteFollow(context, username)
{
	$.ajax({
	    url: context+"/following/"+username,
	    type:'DELETE',//Sends a DELETE request which tells the servlet to delete the message with the given messageId
	    success: 
	        function(msg){
	            alert("You have unfollowed "+username+".");
	            location.reload();
	        }                   
	    });
}
function follow(context, username)
{
	$.ajax({
	    url: context+"/following/"+username,
	    type:'POST',//Sends a POST request which tells the servlet to follow the user with the given username
	    success: 
	        function(msg){
	            alert("You are now following "+username+".");
	            location.reload();
	        }                   
	    });
}

function deleteMessage(context, messageId)
{
	$.ajax({
	    url: context+"/messages/"+messageId,
	    type:'DELETE',//Sends a DELETE request which tells the servlet to delete the message with the given messageId
	    success: 
	        function(msg){
	            alert("Your message has been deleted.");
	            location.reload();
	        }                   
	    });
}