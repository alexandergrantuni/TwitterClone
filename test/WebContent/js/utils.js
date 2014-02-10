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

function test()
{
	alert("<%=request.getContextPath()%>")
	return "<%=request.getContextPath()%>";
}