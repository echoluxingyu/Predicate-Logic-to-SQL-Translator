package com.javacode;

import java.util.ArrayList;
import java.util.Iterator;


public class DataTable {
    String name;
    ArrayList<String> key;  //list to store key
    ArrayList<String> attribute;  //list to store attributes
    ArrayList<String> predicates;
    public DataTable(){
    	super();
    }
	public DataTable(String string) {
		// TODO Auto-generated constructor stub
		this.name=string;
		this.key=new ArrayList<String>();
		this.attribute=new ArrayList<String>(); 
		this.predicates=new ArrayList<String>();
		
	}
	
	public void addKey(String ki){
		this.key.add(ki);
	}
	
	public void addAtrribute(String attr){
		this.attribute.add(attr);
	}
	
	public void organisePre(){
		 Iterator<String> it1 = attribute.iterator();
		 String tableN=orgString(this.name);
	        while(it1.hasNext()){
	        	String tempAttri=orgString(it1.next());
	        	this.predicates.add(tableN+"_"+tempAttri);
	        	
	        
	            System.out.println();
	            
	        }
	}
	
	public String orgString(String input){
		String output=null;
		if(input.length()<=4){
			output=input.toLowerCase();
		}
		else{
			output=(input.substring(0,3)).toLowerCase();
		}
		return output;
	}
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<String> getAttribute(){
		return this.attribute;
	}
	public ArrayList<String> getKey(){
		return this.key;
	}
	public ArrayList<String> getPredicate(){
		return this.predicates;
	}
}

