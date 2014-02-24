Most javascript and AJAX should be easy to find. It is mostly found in utils.js otherwise it should be in the relevant jsp file e.g to load older messages this isn't in utils.js but in messages.jsp, profile.jsp and searchresults.jsp.

---- AJAX ----

- Following/unfollowing friends
- Deleting messages.
- AJAX is also used to delete the profile of a user.
- AJAX is used to load more old messages and to add new messages that other users have posted since the page was originally loaded.

---- Javascript ----

- Javascript is used to turn all hashtagged strings such as '#hello' into a link to a hashtag search using search's REST interfaces.  e.g /search/hashtag/hello for that hello hashtag.

- I used javascript to display a jquery dialog box asking the user if they really want to delete their account in the editprofile page

- Javascript is also used for form validation.

---- REST Interface ----

-- Types of Account --

Administrator - Can delete other people's messages

Example of Admin account: admin@admin.com - google123

Normal user - Can post and delete their own messages.

-- Admin -- 

GET - /admin - takes an administrator to the admin panel.  If the user is not an administrator throws a 404
POST - /admin - creates a new administrator account

-- Search --

The GET method of search is solely used for searches on hash tags, similar to facebook's facebook.com/hashtag/example

GET - /search/hashtag/example - shows a list of messages with the hash tag given by the passed string, e.g in this case messages containing #example
POST - /search - performs a search using POST

-- Following --

GET - /following/username - shows who the user with the given username is following
POST - /following/username - follows the user with the given username
DELETE - /following/username - unfollows the user with the given username

-- Messages --

GET - /messages/ - displays the messages of the people you are following
GET - /messages/MessageId - displays the message with the given message Id
GET - /messages/all - displays the messages of everyone on ChitChat
POST - /messages/ - sends a message from the active user with given text
DELETE - /messages/messageId - deletes a message with the given Id only if the active user created that message

-- Profile --

GET - /profile/ - displays the active user's profile
GET - /profile/username - display's the profile of the user with the given username
DELETE - /profile - deletes the active user's profile

-- Login --

GET - /Login - if the user is logged in it redirects them to their messages, if they are not logged in then they get redirected to the login form
POST - /Login - attempts to login a user

-- Logout --

GET - /Logout - logs the active user out

-- Register --

GET - /Register/ - if the user is already logged in it redirects them to /messages if not they will be redirected to the register page
POST - /Register/ - registers a new user so long as some requirements are met such as the username/email isn't already registered


---- Security ----

- Prepared statements.
- All passwords are encrypted using secure hash algorithm 1.
- Server-side and client-side validation for all user entry.
