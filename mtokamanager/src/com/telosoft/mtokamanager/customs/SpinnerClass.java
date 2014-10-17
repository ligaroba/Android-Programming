package com.telosoft.mtokamanager.customs;

public class SpinnerClass {    
	    private int id;
	    private String name;
	     
	    public SpinnerClass (){}
	     
	    public SpinnerClass(int id, String name){
	        this.id = id;
	        this.name = name;
	    }
	     
	    public void setId(int id){
	        this.id = id;
	    }
	     
	    public void setName(String name){
	        this.name = name;
	    }
	     
	    public int getId(){
	        return this.id;
	    }
	     
	    public String getName(){
	        return this.name;
	    }
	 
	}

