package com.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.javacode.AutoTranslate;
import com.javacode.ConnectSQL;
import com.javacode.DataTable;





public class FileUploadServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String target="main.jsp";
    public AutoTranslate auto;
	public FileUploadServlet(){
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
	    // whether it is  multipart
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart){
		    try {
	            String path = "files\\";// directory to save file
                File attachementPath = new File(path);
		        if (!attachementPath.exists()){
		        	System.out.println("no path like that");
		            if(attachementPath.mkdirs()){
		            	System.out.println("path made");
		            }
		            System.out.println("fail to make the path");
		            
		        }
		        File tempPath = new File("AttachmentTemp\\");// temp location for file 
		        if (!tempPath.isDirectory()) {
		            tempPath.mkdirs();
		            System.out.println("Temp path maded");
		        }
		        DiskFileItemFactory factory = new DiskFileItemFactory();
		        factory.setSizeThreshold(1024 * 1024 * 1);// 初始内存大小1M，超出后则直接保存到临时目录中
		        factory.setRepository(tempPath);// 设置临时目录
                ServletFileUpload upload = new ServletFileUpload(factory);
		        upload.setHeaderEncoding("UTF-8");
		        // upload.setFileSizeMax(1024 * 1024 * 50);// 设置单个文件上传大小为50M
                List<FileItem> items = upload.parseRequest(request);
		        System.out.println("items=" + items);
		        // Process the uploaded items
		        Iterator iter = items.iterator();
		        while (iter.hasNext()){
		            FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
	                // 处理HTML表单域
                        if (item.isFormField()) {
                        	System.out.println("It is a form field!");
                            String name = item.getFieldName();
	                        String value = item.getString("UTF-8");
		                             System.out.println("FieldName=" + name + " \t FieldValue=" + value);
		                }
		            } 
                    else {
                    // 处理附件
                    	System.out.println("It is a file");
		                String fieldName = item.getFieldName();
		                System.out.println("fieldname= "+fieldName);
		                String _fileName = item.getName();
                        long _fileSize = item.getSize();
                        System.out.println("filesize= "+_fileSize);
		                if (_fileSize == 0){
		                    continue;
		                }
                        System.out.println("size of the file: " + (_fileSize / 1024.0) + " KB");
		                // save the file
		                _fileName = _fileName.substring(_fileName.lastIndexOf("\\") + 1);
		                System.out.println("filename= "+_fileName);
		                // File uploadedFile = new File(path+_fileName);
		                File uploadedFile = new File(path,_fileName);  //this is where problem happens!  maybe mkdir is not successful
		                if (!uploadedFile.exists()){
		                    item.write(uploadedFile);
		                    System.out.println("wirtten");
		                } 
		                else {
                            System.out.println("附件已经存在");
		                }
		                         
		                System.out.println("附件保存路径为:" + path + _fileName);
		                //System.out.println("当前路径"+System.getProperty("user.dir"));
		                ConnectSQL conS=new ConnectSQL(path+_fileName);
                        auto=new AutoTranslate(conS.con);
                        auto.getTableInfo();
		                
		            }
		        }
		    } catch (FileSizeLimitExceededException e) {
		        e.printStackTrace();
		    } catch (FileUploadException e) {
		        e.printStackTrace();
		    } catch (Exception e) {
                e.printStackTrace();
		    }

		}
		ArrayList<DataTable> tables=auto.getTableList();

//		ArrayList<String> headers=new ArrayList<String>();
		int tableN=0;
		for(int i=0;i<tables.size();i++){
			request.setAttribute("table"+i,tables.get(i));
			tableN++;
		}
		request.setAttribute("number",tableN);
		//System.out.println(tableN);

		RequestDispatcher rd=request.getRequestDispatcher(target);
	    
		try {
			rd.forward(request,response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println("Redirect");

    
	}
	
}