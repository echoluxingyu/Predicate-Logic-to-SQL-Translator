package com.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class Upload extends HttpServlet {
	 /**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	//public String target="/test1.jsp";

	 public void init(ServletConfig config)
	  throws ServletException{
	  super.init(config);
	 }

	 public void doGet(HttpServletRequest request,HttpServletResponse response)
	  throws ServletException,IOException{
	  doPost(request,response);
	 }

	 public void doPost(HttpServletRequest request,HttpServletResponse response)
	  throws ServletException,IOException{
		 
	  System.out.println("called"); 
	  String username=request.getParameter("username");
	  String password=request.getParameter("password");
	  System.out.println("Message received"+username+password);
	  

	 // request.setAttribute("USER",username);
	  //request.setAttribute("PASSWORD",password);

	  response.setContentType("text/html");
	  PrintWriter out = response.getWriter();
	  out.println("<HTML>");
	  out.println("<HEAD><TITLE>Hello World</TITLE></HEAD>");
	  out.println("<BODY>");
	  out.println("<BIG>Hello World</BIG>");
	  out.println("<p>"+username+"</p>");
	  out.println("</BODY></HTML>");
	 }

	 public void destroy(){
	 }
	}