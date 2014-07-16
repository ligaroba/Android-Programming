package com.telosoftapps.mtokamanager.interfaces;

import java.util.List;

import com.telosoftapps.mtokamanager.customs.IndividualVehicleList;

public interface FetchDataListener {
	public void onFetchDataComplete(List<IndividualVehicleList> data);
	public void onFetchDataFailure(String msg);
}
