package com.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ShowInfo extends HttpServlet 
{

	private static final long serialVersionUID = 1L;
    public String target="/test1.jsp";

    public void init(ServletConfig config) throws ServletException{
    	super.init(config);
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	//insert implementation of doGet function here
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    	//insert implementation of doPost function here
    }

    public void destroy(){
    
    }
}