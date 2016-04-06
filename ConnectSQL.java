package com.javacode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ConnectSQL {
	//DataSource ds = null;
	public Connection con; 
    Statement stmt = null;
	String sql=null;
	ResultSet rs=null;
	InputStreamReader fileReader;
	public ConnectSQL(String filePath) throws Exception{
		try{       
		    //InitialContext ctx=new InitialContext();   
     		//ds=(DataSource)ctx.lookup("java:comp/env/jdbc/mysql");
			Class.forName("com.mysql.jdbc.Driver");     // loading driver of MySQL
	        System.out.println("Success loading Mysql Driver!");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company","root","12345"); //����URLΪ   jdbc:mysql//��������ַ/���ݿ���  �������2�������ֱ��ǵ�½�û���������
            System.out.println("Success connect Mysql server!");
		    stmt = con.createStatement();
		    System.out.println("current location:"+System.getProperty("user.dir"));
		    this.readSQLFile(filePath);
		    System.out.println("created");
		}catch (Exception e){
	        System.out.print("Error loading Mysql Driver!");
	        e.printStackTrace();
	    }	  
    }
	
	public void readSQLFile(String path){
		File file = new File(path);  
		try{  
	        if (!file.exists()) {  
	            file.createNewFile();  
	        }  
	        fileReader = new InputStreamReader(new FileInputStream(file),"UTF-8");  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	    } 
		BufferedReader bufferedReader = new BufferedReader(fileReader);  
        try {  
            StringBuilder sBuilder = new StringBuilder("");  
            String str = bufferedReader.readLine();  
            while (str != null) {  
                // ȥ��һЩע�ͣ���һЩû�õ��ַ�  
                if (!str.startsWith("#") && !str.startsWith("/*")  
                        && !str.startsWith("�C") && !str.startsWith("\n"))  
                    sBuilder.append(str);  
                str = bufferedReader.readLine();  
            }  
            String[] strArr = sBuilder.toString().split(";");  
            List<String> strList = new ArrayList<String>();  
            for (String s : strArr) {  
                strList.add(s);  
                //System.out.println(s);  
            }  
            
            
            stmt.executeBatch(); 
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                bufferedReader.close();  
                fileReader.close();  
            } catch (Exception e) {  
            }  
        }  
	}
	/*
	public String[] returnPredicate(){
		
		return path;
 
	}
	*/
}
	
	
	  
	  
	  
