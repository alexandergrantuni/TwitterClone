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
			if(current.indexOf("/search/messages") == -1)
			{
				for(var j = 0; j < hashTagArray.length; j++)
    			{
    				current = current.replace(hashTagArray[j],'<a href=/alexandergrant/search/messages/'+hashTagArray[j].replace("#","")+'>' + hashTagArray[j] + '</a>');
    				messages[i].innerHTML = current;
    			}
			}
		//# is a special character sometimes used to link to anchors in webpages so this might be why
    	}
    }
}

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
		//# is a special character sometimes used to link to anchors in webpages so this might be why
    	}
    }
}


function formatMessages()
{
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
    if(newMessages.length > 0)
	{
    	newestMessage = newMessages[0].id;
	}
    else
    {
    	var messages = document.getElementsByClassName("message");
    	if(messages.length > 0)
    	{
    		newestMessage = messages[0].id;
    	}
    }
    $.ajax({
    	type:'GET',
    	cache: false,
    	data: {newestMessageId: newestMessage},
    		success: 
    			function(html){
    		    $("#newMessages").prepend(html);
    		    detectAndAddHashTags();
    		    },
    });
    setTimeout(fetchNewMessages, 5000);
}


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
