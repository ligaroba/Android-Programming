package com.telosoft.mazingira.validation;

public class Validation {

		
	public boolean checkNullity(String fieldname){
		if(fieldname.length()>0 && fieldname!="" && !fieldname.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
		public boolean checkselection(String fieldname){
			if(!fieldname.equals("Select type of asset")||fieldname.equals("Select asset use")){
				
				return false;
			}else{
				return true;
			}
			
			
		
			
		}
		
	public  boolean matchCapcity(String category,int capacity){
		if(category.equalsIgnoreCase("cars")&& (capacity >Integer.parseInt("3"))){
			return false;
		}else if(category.equalsIgnoreCase("Matatu")&& (capacity >Integer.parseInt("14"))){
			return false;
		}else if(category.equalsIgnoreCase("Bus")&& (capacity >Integer.parseInt("62"))){
			return false;
		}else if(category.equalsIgnoreCase("Lorries")&& (capacity >Integer.parseInt("3"))){
			return false;
		}
		else if(category.equalsIgnoreCase("Heavy commercial")&& (capacity >Integer.parseInt("3"))){
			return false;
		}
			else {
			return true;
		}		
	}
}
