# Predicate-Logic-to-SQL-Translator
A web application that translate a user formed predicate logic formula into related SQL query and executes the query on user specified database.

1. User goes to the web site through its URL.
2. User uploads a self-contained database, which he would like to retrieve.
3. The database would be saved on server for further usage.
4. Based on the uploaded database, server generates related predicates and refreshes the web page to show them. 
5. User uses the clickable predicates and operators to form a predicate formula.
6. Front-end reorganizes the formula and sends it back to server.
9. Server does the translation and executes the query on the user-uploaded database.
10. Server fills the result set into a XML file and sends it to front end.
11. Front end shows the result on web page.
