This is a project to create a simple markdown parser

It is written in springboot and provides 2 endpoints -
1. /upload-file - which receives a text file upload in a 'markdown' format and returns the HTML formatted text
2. /upload-text - which receives a text input  'markdown' format and returns the HTML formatted text

RULES:

* "# Heading 1" trnslates to \<h1>Heading 1\</h1> 
* "txt" transalets to \<p>txt\</p>
* "txt \[linktxt]\(www.somelink.com) more text" translates to "txt \<a href="www.somelink.com">linktext\</a> more text"

Sample Inputs:

"# Sample Document

Hello!

This is sample markdown for the [Mailchimp](https://www.mailchimp.com) homework assignment."

