TwitterClone
============

This does not run on the server because of a database problem (the 'following' table was deleted whilst the rest weren't which is causing problems) so I've added a context.xml to this directory which can be used to replace the context.xml in the META-INF folder.  This runs a bit slower (takes a few seconds to load pages and AJAX stuff) but it shows a fully working website whereas the one on the server does not.  

Testing AJAX:

Testing that the AJAX works will take a few seconds for each request.  The server will load more messages if the user has scrolled to the bottom and there are more messages available on the server.  (It will initially show 10 messages).  

New messages are loaded if another user posts a message after the page was loaded.  This can be tested by running 2 browser windows and posting a message on one and checking it displays on the other.

Login Details:

admin@admin.com : google123 - admin account

Please ensure you run this locally under the directory alexandergrant or hashtags won't link to the search correctly.
