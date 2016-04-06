package com.javacode;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AutoTranslate {
	int index=0; // the number of table in the database
	int tableReffered=0;
	static Connection con; 
    static Statement stmt = null;
    static DatabaseMetaData meta;
	String sql=null;
	ResultSet rs=null;
	ArrayList<DataTable> tableList;  //all the relations in the database
	ArrayList<String> attriToSelect; //all the attributes that user would like to select
	ArrayList<String> realNameInSQL;
	ArrayList<String> userTypedName;
	ArrayList<String> tableToUse; //all the table mentioned in a users predicate sentence
	public AutoTranslate(Connection con){
	      try {
              stmt = con.createStatement();
              meta = con.getMetaData();      
	      }
	      catch(SQLException el){
			  System.out.println("there is some problem   "+el);
	   }	      

	}
	
	
	//get the name key and other information about the tables
	public void getTableInfo(){
		try {
			tableList=new ArrayList<DataTable>();
			rs = meta.getTables(null, null, null,new String[] { "TABLE" });
			 while (rs.next()) {
				 tableList.add(new DataTable(rs.getString(3)));
				 ResultSet tempKey = meta.getPrimaryKeys(null, null,rs.getString(3)); 
				 ArrayList<String> temp=new ArrayList<String>();
			     while( tempKey.next()) {
			    	 String tempK=(String) tempKey.getObject(4);
	                 temp.add(tempK);	
					 tableList.get(index).addKey(tempK);
	                 System.out.println("Key: "+tempKey.getObject(4)); 
	             } 
			     
				 ResultSet tempRs= meta.getColumns(null,"%",rs.getString(3),"%"); 
				 while(tempRs.next()) { 
					 String  colNam= tempRs.getString("COLUMN_NAME"); 
					 if(!temp.contains(colNam)){
						 tableList.get(index).addAtrribute(colNam);
						 System.out.println("Atrribute: "+colNam); 
					 }
					 
					 
				 }
			     System.out.println("table name:" + rs.getString(3));
			     tableList.get(index).organisePre();
			     index++;
			   }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    }			  
	}
	
    public void attriToSelect(String attri){
		attriToSelect=new ArrayList<String>();
    	String[] temp=attri.split(",");
		 for(int i=0; i<temp.length; i++) { 
			 System.out.print(temp[i]+"\t"); 
			 attriToSelect.add(temp[i]);
	     }	
	}
    
    
	public int preProcess(String userInput){
		int returnValue=0; //0-10 indicate the number of table affected in the userInput, larger 
		realNameInSQL=new ArrayList<String>();
        userTypedName=new ArrayList<String>();
        tableToUse=new ArrayList<String>();
        
		Pattern pattern1 = Pattern.compile("[a-z]+(?=\\()"); //get predicate name
	    Pattern pattern2 = Pattern.compile("(?<=\\w\\()(.+?)(?=\\))"); //get content of a predicate
	    Matcher matcher1 = pattern1.matcher(userInput);
	    Matcher matcher2 = pattern2.matcher(userInput);
	    while(matcher1.find()&&matcher2.find()){
	        String prediName=matcher1.group();
	        String prediCont=matcher2.group();
	        Iterator<DataTable> it1 = tableList.iterator();
	    		 
	    	while(it1.hasNext()){
                DataTable tempTable = it1.next(); //get a  table
    	        String tableName=tempTable.getName();  //get the name of a table
    	        ArrayList<String> predi= tempTable.getPredicate(); //get all the predicates of the table
    	        ArrayList<String> keys= tempTable.getPredicate(); //get all the keys of the table
    	        ArrayList<String> attri= tempTable.getPredicate(); //get all the attributes of the table
    	        int tempindex = predi.indexOf(prediName);
	    	       
    	        //predicate found in the table
    	        if(tempindex!=-1){
    	        	if(!tableToUse.contains(tableName)){
    	        		tableToUse.add(tableName);
    	        	}
    	        	String[] temp=prediCont.split(",");
    	        	Iterator<String> it2 = keys.iterator();
    	        	int i=0;
    	        	while(it2.hasNext()){
    	        		userTypedName.add(temp[i]);
    	        		realNameInSQL.add(tableName+"."+it2.next());  //the real name that can be retrieved using SQL 
    	        		i++;
    	        	}
    	        	userTypedName.add(temp[i]);
	        		realNameInSQL.add(tableName+"."+ attri.get(tempindex));
    	        }     
    	        
    	        
	    	}  	
	    }
	    return returnValue;
	}
	@SuppressWarnings("null")
	public String orgSelect(int tablenum){
		String sel="SELECT ";
		String fro="FROM ";
		String var[] = null;
		int index=0;
		Iterator<String> it =attriToSelect.iterator();
    	while(it.hasNext()){
    		int temp =  userTypedName.indexOf(it.next()); //get the index of variable to be select 
    		var[index]= realNameInSQL.get(temp);
    		index++;
    	}
    	if(var.length>0){
    	    for(int i=0;i<(var.length-1);i++){
    		    sel=sel+var[i]+",";
    	    }
    	    sel=sel+var[index-1]+" ";
    	}
    	if(tablenum==1){
    	   fro=fro+tableToUse.get(0)+" ";
    	}
    	if(tablenum>1){
    		int[] location=null;
    		int ind=0;
    		for(int i=0;i<userTypedName.size();i++){
				location[ind]=i;
    			//userTypedName.get(i);
    			for(int j=0;j<userTypedName.size();j++){
    				if(userTypedName.get(i).equals(userTypedName.get(j))){
    					ind++;
    					location[ind]=j;
    				}
    			}
    			ind=0;
    	   }
    	   String tempJ="("+tableToUse.get(0)+" JOIN "+tableToUse.get(1)+" ON "+realNameInSQL.get(location[0])+" = "+ realNameInSQL.get(location[1]);
    	   if(tablenum==2){
    		   fro=fro+tempJ;
    		   return sel+fro;
    	   }
    	   for(int i=3;i<tablenum;i++){
    		   tempJ="("+tempJ+" JOIN "+ tableToUse.get(i-1)+" ON "+realNameInSQL.get(location[i-2])+" = "+ realNameInSQL.get(location[i-1]);
    	   }
    	   
    	   fro=fro+tempJ;
    	   
    	}
    	
		return sel+fro;
	}

	public ArrayList<DataTable> getTableList(){
		return tableList;
	}
	
}
