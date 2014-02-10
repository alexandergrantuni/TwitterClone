REST Interface:

Types of Account 

Administrator - Can delete other people's messages

Example of Admin account: admin@admin.com - google123

Normal user - Can post and delete their own messages.

Example of normal user: test2@test.com - google123

Search

GET - /search/users/USERNAME - shows a list of users with a similar name
GET - /search/messages/messagetext - shows a list of messages with a similar message
POST - /search/messages/Username -- remove this

Following

GET - /following/USERNAME - shows who the user with the given username is following
POST - /following/USERNAME - follows the user with the given username
DELETE - /following/username - unfollows the user with the given username

Messages

GET - /messages/ - displays the messages of the people you are following
GET - /messages/all - displays the messages of everyone on ChitChat
POST - /messages/text - sends a message with the given text
DELETE - /messages/messageId - deletes a message with the given Id only if the active user created that message

Profile

GET - /profile/ - displays the active user's profile
GET - /profile/USERNAME - display's the profile of the user with the given username

Login

GET - /Login - if the user is logged in it redirects them to their messages, if they are not logged in then they get redirected to the login form
POST - /Login - attempts to login a user

Logout

GET - /Logout - logs the user out

Register

GET - /Register/ - if the user is already logged in it redirects them to /messages if not they will be redirected to the register page
POST - /Register/ - registers a new user so long as some requirements are met such as the username/email isn't already registered

AJAX

Following/unfollowing friends and deleting messages.

Javascript

Javascript is used to turn all hashtagged strings such as '#hello' into a link to a message search using search's REST interfaces.  e.g /search/messages/hello for that hello hashtag.

Javascript is also used for form validation.

Security:

Stored procedures and prepared statements.
All passwords are encrypted using secure hash algorithm 1.
