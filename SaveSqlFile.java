package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveSqlFile extends HttpServlet 
{
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public String target="/saveSqlFile.jsp";

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

	 }



 public void destroy(){
 }
}