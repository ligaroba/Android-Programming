package com.telosoftapps.mtokamanager.validation;

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
}
