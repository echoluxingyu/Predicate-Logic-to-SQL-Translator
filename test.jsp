<%@page import="javax.sql.*"%>     
<%@page import="javax.naming.*"%> 
<%@page import="com.javacode.DataTable"%> 
<%@page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="assets/ico/favicon.ico">
  <title>Insert title here</title>

 <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/customer.css" rel="stylesheet">
    
</head>
<body>

  <%!
      int num=0;
      DataTable table= new DataTable();
      String name =null;
      String predi = null;
      ArrayList<String> prelist= null;
      int preN=0;
   %>
   
   <%
      num = (Integer)request.getAttribute("number");  //num of tables
      System.out.println( "Number of tables: "+num); 
   %>
   
   
</body>
</html>