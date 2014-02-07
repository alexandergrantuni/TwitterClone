REST Interface:

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

Register

GET - /Register/ - if the user is already logged in it redirects them to /messages if not they will be redirected to the register page
POST - /Register/ - registers a new user so long as some requirements are met such as the username/email isn't already registered
PUT - /Register/ - same as POST