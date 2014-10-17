package com.telosoftapps.mtokamanager.customs;

import android.util.Log;

public class IndividualVehicleList {    
	   private String assetid;
	    private String dateOfReport;
	    private int tripsrushhr;
	    private double chargesrush;
	    private int tripnormalhr;
	    private double chargesnormal;
	    private double fuelcost;
	    private double maintenacecost;
	    private double insurance;
	    private double packingfee;
	    private String createdat;
	    private String group;
	    private int capacity;
	    private String telno;
	    private String typeUse;
	    private double TParking;
	    
	    
	    public String getTypeUse() {
			return typeUse;
		}

		public void setTypeUse(String typeUse) {
			this.typeUse = typeUse;
		}

		public String getTelno() {
			return telno;
		}

		public void setTelno(String telno) {
			this.telno = telno;
		}





		public int getCapacity() {
			//Log.d("capacity from individual", String.valueOf(this. capacity));
			return capacity;
		}





		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}





		public String getGroup() {
			return group;
		}





		public void setGroup(String group) {
			this.group = group;
		}
		private double startamount;
	    private double others;
	    
	    
	    public IndividualVehicleList (){}
	    
	 

	 
		
	 public String getAssetid() {
			return assetid;
		}





		public void setAssetid(String assetid) {
			this.assetid = assetid;
		}





		public String getDateOfReport() {
			return dateOfReport;
		}





		public void setDateOfReport(String dateOfReport) {
			this.dateOfReport = dateOfReport;
		}





		public int getTripsrushhr() {
		//	Log.d("trip rush from individual", String.valueOf(this. tripsrushhr));
			return tripsrushhr;
		}





		public void setTripsrushhr(int tripsrushhr) {
			this.tripsrushhr = tripsrushhr;
		}





		public double getChargesrush() {
		//	Log.d("chargesrush from individual", String.valueOf(this. chargesrush));
			return chargesrush;
		}





		public void setChargesrush(double chargesrush) {
			this.chargesrush = chargesrush;
		}





		public int getTripnormalhr() {
			//Log.d("tripnormalhr from individual", String.valueOf(this. tripnormalhr));
			return tripnormalhr;
		}





		public void setTripnormalhr(int tripnormalhr) {
			this.tripnormalhr = tripnormalhr;
		}





		public double getChargesnormal() {
			//Log.d("chargesnormal from individual", String.valueOf(this. chargesnormal));
			return chargesnormal;
		}





		public void setChargesnormal(double chargesnormal) {
			this.chargesnormal = chargesnormal;
		}





		public double getFuelcost() {
			//Log.d("fuelcost from individual", String.valueOf(this. fuelcost));
			return fuelcost;
		}





		public void setFuelcost(double fuelcost) {
			this.fuelcost = fuelcost;
		}





		public double getMaintenacecost() {
			//Log.d(" maintenacecost from individual", String.valueOf(this.maintenacecost));
			return maintenacecost;
		}





		public void setMaintenacecost(double maintenacecost) {
			this.maintenacecost = maintenacecost;
		}





		public double getInsurance() {
			//Log.d("insurance from individual", String.valueOf(this.insurance));
			return insurance;
		}





		public double getOthers() {
			//Log.d("others from individual", String.valueOf(this.others));
			return others;
		}





		public void setOthers(double others) {
			this.others = others;
		}





		public void setInsurance(double insurance) {
			this.insurance = insurance;
		}





		public double getPackingfee() {
			//Log.d("Packingfee from individual", String.valueOf(packingfee));
			
			return packingfee;
		}





		public void setPackingfee(double packingfee) {
			this.packingfee = packingfee;
		}





		public String getCreatedat() {
			return createdat;
		}





		public void setCreatedat(String createdat) {
			this.createdat = createdat;
		}





		public double getStartamount() {
			//Log.d("startamount from individual", String.valueOf(this.startamount));
			return startamount;
		}





		public void setStartamount(double startamount) {
			this.startamount = startamount;
		}

		public double expenses(){
					
			 
			//Log.d("expenses() from individual", String.valueOf(getPackingfee()+getFuelcost()+getInsurance()+getMaintenacecost()+getStartamount()+getOthers()));
			 return getPackingfee()+getFuelcost()+getInsurance()+getMaintenacecost()+getStartamount()+getOthers();
			 
		 }
		public double revenue(){
			if(getTypeUse().equals("personal")){
				
				return 0;
			}else{
			//Log.d("revenue from individual", String.valueOf(((getTripnormalhr()*getChargesnormal() * getCapacity())+(getTripsrushhr()*getChargesrush() * getCapacity()))));
			  return ((getTripnormalhr()*getChargesnormal() * getCapacity())+(getTripsrushhr()*getChargesrush() * getCapacity()));
			}
		}
		public double profitLoss(){
			if(revenue()!=0){
			return revenue()-expenses();
			  }else{
			  return 0;
			}
		}





	    
	
	     
	  	
	 
	 
	}

