function detectAndAddHashTags(context)
{
    var messages = document.getElementsByClassName("messageText");//Gets all messages by taking the messageText div classes which contain the text for each message
    for (var i = 0; i < messages.length; i++) //Go through each message checking if there are any hashtags
    {
    	var hashTagRegex = /(\S*#\[[^\]]+\])|(\S*#\S+)/gi;//A regular expression for identifying hash tags
    	var current = messages[i].innerHTML;//Put the html for this message into a variable
    	//if there is a string portion that matches the regular expression...
    	if(hashTagRegex.test(current))
    	{
    		var myRe = /(\S*#\[[^\]]+\])|(\S*#\S+)/gi;
    		var myArray;
    		var hashTagArray = new Array();
    		var k = 0;
    		while ((myArray = myRe.exec(current)) !== null)
    		{
    		  hashTagArray[k] = myArray[0];
    		  k++;
    		}
			if(current.indexOf("/search/hashtag/") == -1)//if we've not already formatted this hashtag then turn it into a link
			{
				for(var j = 0; j < hashTagArray.length; j++)
    			{
					//Create the link and remove the hashtag so it links to /search/hashtag/example
    				current = current.replace(hashTagArray[j],'<a href=/alexandergrant/search/hashtag/'+hashTagArray[j].replace("#","")+'>' + hashTagArray[j] + '</a>');
    				messages[i].innerHTML = current;
    			}
			}
    	}
    }
}
//NOT CURRENTLY WORKING, regular expression doesn't appear to support multiple website links in 1 message
function detectAndAddWebsiteLinks()
{
    var messages = document.getElementsByClassName("messageText");//Gets all messages by taking the messageText div classes which contain the text for each message
    for (var i = 0; i < messages.length; i++) //Go through each message checking if there are any hashtags
    {
    	var hashTagRegex = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;//A regular expression for identifying hash tags
    	var current = messages[i].innerHTML;//Put the html for this message into a variable
    	//if there is a string portion that matches the regular expression...
    	if(hashTagRegex.test(current))
    	{
    		var myRe = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
    		var myArray;
    		var hashTagArray = new Array();
    		var k = 0;
    		while ((myArray = myRe.exec(current)) !== null)
    		{
    		  hashTagArray[k] = myArray[0];
    		  k++;
    		}
			if(current.indexOf("/search/messages") == -1)
			{
    		for(var j = 0; j < hashTagArray.length; j++)
    			{
    				
    				current = current.replace(hashTagArray[j],'<a href="'+hashTagArray[j].replace("#","")+'">' + hashTagArray[j] + '</a>');
    				messages[i].innerHTML = current;
    				
    			}
			}
    	}
    }
}


function formatMessages()
{
	detectAndAddHashTags();
}


//AJAX for unfollowing a user
//context is the base start of the url, in this case alexandergrant/
//username is the username of the user to follow
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
//AJAX for following a user
//context is the base start of the url, in this case alexandergrant/
//username is the username of the user to follow
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
//AJAX for deleting a message
//context is the base start of the url, in this case alexandergrant/
//messageId is the identifier of the message to delete
function deleteMessage(context, messageId)
{
	$.ajax({
	    url: context+"/messages/"+messageId,
	    type:'DELETE',//Sends a DELETE request which tells the servlet to delete the message with the given messageId
	    success: 
	        function(msg){
	            location.reload();
	        }                   
	    });
}
//checks for new messages every 5 seconds and adds them to the page if they exist
//This is useful because the user does not have to refresh the page to see new messages
function fetchNewMessages()
{
	//This first part gets the newest message's messageId
    var newMessages = document.getElementsByClassName("newmessage");//get all messages
    var newestMessage = -1;
    if(newMessages.length > 0)//if a new message exists, the first one is the newest overall message
	{
    	newestMessage = newMessages[0].id;
	}
    else
    {
    	var messages = document.getElementsByClassName("message");
    	if(messages.length > 0)//if there are more than 1 message
    	{
    		newestMessage = messages[0].id;
    	}
    }
    $.ajax({
    	type:'GET',
    	cache: false,//internet explorer support
    	data: {newestMessageId: newestMessage},
    		success: 
    			function(messages){
    		    $("#newMessages").prepend(messages);
    		    detectAndAddHashTags();//add hash tag links to new messages
    		    },
    });
    setTimeout(fetchNewMessages, 5000);//check for more new messages again in 5 seconds
}

//Displays a dialog using jquery ui asking the user if they are sure that they want to delete a message
function showDeleteMessageDialog(context, messageId)
{
  $( "#dialog-confirm" ).dialog({
    resizable: false,//I have left this as false since it can look a bit untidy if you resize it smaller than it is
    height:190,
    width:400,
    modal: true,
    draggable: false,//I want this to appear in only 1 position so this is set to not be draggable
    buttons: {
      "Delete": function(){//If the button annotated with 'I'm sure!' is pressed...
      deleteMessage(context, messageId);//Call the delete message function
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
}
