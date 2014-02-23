Most javascript IN utils.js

---- AJAX ----

- Following/unfollowing friends
- Deleting messages.
- AJAX is also used to delete the profile of a user.
- AJAX is used to load more old messages and to add new messages that other users have posted since the page was originally loaded.

---- Javascript ----

- Javascript is used to turn all hashtagged strings such as '#hello' into a link to a message search using search's REST interfaces.  e.g /search/messages/hello for that hello hashtag.

- I used javascript to display a jquery dialog box asking the user if they really want to delete their account in the editprofile page

- Javascript is also used for form validation.

---- REST Interface ----

-- Types of Account --

Administrator - Can delete other people's messages

Example of Admin account: admin@admin.com - google123

Normal user - Can post and delete their own messages.

Example of normal user: test3@test3.com - google123

-- Admin -- 

GET - /admin - takes an administrator to the admin panel.  If the user is not an administrator throws a 404
POST - /admin - creates a new administrator

-- Search --

This is only used so that hash tags in messages can be linked to the search, not used otherwise!

GET - /search/users/USERNAME - shows a list of users with a similar name
GET - /search/messages/messagetext - shows a list of messages with a similar message

-- Following --

GET - /following/USERNAME - shows who the user with the given username is following
POST - /following/USERNAME - follows the user with the given username
DELETE - /following/username - unfollows the user with the given username

-- Messages --

GET - /messages/ - displays the messages of the people you are following
GET - /messages/MessageId - displays the message with the given message Id
GET - /messages/all - displays the messages of everyone on ChitChat
POST - /messages/ - sends a message from the active user with given text
DELETE - /messages/messageId - deletes a message with the given Id only if the active user created that message

-- Profile --

GET - /profile/ - displays the active user's profile
GET - /profile/USERNAME - display's the profile of the user with the given username
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

- Stored procedures and prepared statements.
- All passwords are encrypted using secure hash algorithm 1.
- Server-side and client-side validation for all user commands
