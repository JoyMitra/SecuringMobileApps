package com.example.idost;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.CurrentAddressBean;
import com.example.idost.util.AppCallServiceUtilityClass;
import com.example.idost.util.AppCommonExceptionClass;
import com.example.idost.util.ShowAlertUtilityClass;

public class GetLocationClass extends Service{
	
	public static LocationManager locationManager = null;
	public static Location location;
	private long minDist = 0;
//	private long minTime = 30 * 60 * 1000;//30 min
	private long minTime = 1 * 60 * 1000;

	public void getLocation() throws AppCommonExceptionClass {
		try {
			if (locationManager == null) {
				locationManager = (LocationManager) AppCommonBean.mContext.getSystemService(LOCATION_SERVICE);
			}
			
			if(locationManager != null)
			{
				this.getProvider();
			}
			else
			{
				AppCommonBean.commonErrMsg = AppCommonConstantsClass.USR_LOC_MNG_NULL;
				throw new Exception(AppCommonConstantsClass.LOC_MNG_NULL);
			}

		} catch (Exception e) {
			throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		}

	}

	private void getProvider() throws AppCommonExceptionClass {
		try {
			Criteria crt = new Criteria();
			crt.setAccuracy(Criteria.ACCURACY_FINE);
			crt.setCostAllowed(false);
			String provider = "";

			provider = locationManager.getBestProvider(crt, true);

			if (provider != null) {
				
				if (AppCommonConstantsClass.PROVIDER_GPS.equalsIgnoreCase(provider)) {
					this.getGpsProvider();

				} else if (AppCommonConstantsClass.PROVIDER_NETWORK.equalsIgnoreCase(provider)) {
					this.getNetworkProvider();
				}

			} else {
				AppCommonBean.commonErrMsg = AppCommonConstantsClass.LOC_PROVIDER_NULL;
				throw new Exception(AppCommonConstantsClass.LOC_PROVIDER_NULL);
			}

		} catch (Exception e) {
			throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		}
	}

	private void getNetworkProvider() throws Exception {
		Boolean isNetworkEnabled = false;
		location = null;
		// getting network status
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		// get the location from network provider
		if (isNetworkEnabled) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,minTime, minDist,networkLocationListener);
			location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		} else {
			AppCommonBean.commonErrMsg = AppCommonConstantsClass.LOC_PROVIDER_NULL;
			throw new Exception(AppCommonConstantsClass.LOC_NET_PROVIDER_NULL);
		}
		
		if(location == null)
		{
			AppCommonBean.commonErrMsg = AppCommonConstantsClass.USR_LOC_MNG_NULL;
			throw new Exception(AppCommonConstantsClass.LOC_NULL);
		}

	}

	private void getGpsProvider() throws Exception {
		Boolean isGPSEnabled = false;
		location = null;
		// getting GPS status
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// get the location from GPS provider
		if (isGPSEnabled) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDist, gpsLocationListener);
			location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location == null) {
				this.getNetworkProvider();
			}
		} else {
			this.getNetworkProvider();
		}
	}

	
	public static final LocationListener networkLocationListener = new LocationListener(){

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		
		}

		@Override
		public void onProviderEnabled(String provider) {
			
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onLocationChanged(Location loc) {
			
			if(loc!= null && location!=null)
			{
				
				try {
			    	String prevaddressLine = CurrentAddressBean.curraddressLine;
					AppCallServiceUtilityClass.getService(AppCommonBean.mContext, AppCommonConstantsClass.CURR_ADD_SERVICE);
					if(prevaddressLine != null && !prevaddressLine.equalsIgnoreCase(CurrentAddressBean.curraddressLine))
					{
						Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.LOC_CHNG, Toast.LENGTH_SHORT).show();
						location = loc;
		      	   	    AppCallServiceUtilityClass.getService(AppCommonBean.mContext, AppCommonConstantsClass.POL_ADD_SERVICE);
			      	   if(AppCommonBean.msgBtnClicked)
				   		{
			      		   	AppCallServiceUtilityClass.getService(AppCommonBean.mContext, AppCommonConstantsClass.MSG_SERVICE);
				   		}

					}
			 	}catch(Exception e)
		  			{
		  				Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
		  				if(AppCommonBean.commonErrMsg.equalsIgnoreCase(AppCommonConstantsClass.LOC_PROVIDER_NULL))
		  				{
		  					ShowAlertUtilityClass.showSettingsAlert(AppCommonConstantsClass.NET_GPS_NOT_ENABLED);
		  				}
		  			}

			}
		
		}
	};

	public static LocationListener gpsLocationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onLocationChanged(Location loc) {
			
			
			if(loc!= null && location!=null)
			{
				
				try {
			    	String prevaddressLine = CurrentAddressBean.curraddressLine;
					AppCallServiceUtilityClass.getService(AppCommonBean.mContext, AppCommonConstantsClass.CURR_ADD_SERVICE);
					
					Toast.makeText(AppCommonBean.mContext,"curr add - "+CurrentAddressBean.curraddressLine, Toast.LENGTH_SHORT).show();
					Toast.makeText(AppCommonBean.mContext,"prev add - "+prevaddressLine, Toast.LENGTH_SHORT).show();
					Toast.makeText(AppCommonBean.mContext,"msg btn - "+AppCommonBean.msgBtnClicked, Toast.LENGTH_SHORT).show();
					
					if(prevaddressLine != null && !prevaddressLine.equalsIgnoreCase(CurrentAddressBean.curraddressLine))
					{
						location = loc;
						Toast.makeText(AppCommonBean.mContext,AppCommonConstantsClass.LOC_CHNG, Toast.LENGTH_SHORT).show();
						
		      	   	    AppCallServiceUtilityClass.getService(AppCommonBean.mContext, AppCommonConstantsClass.POL_ADD_SERVICE);
			      	   if(AppCommonBean.msgBtnClicked)
				   		{
			      		   	AppCallServiceUtilityClass.getService(AppCommonBean.mContext, AppCommonConstantsClass.MSG_SERVICE);
				   		}

					}
			 	}catch(Exception e)
		  			{
		  				Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
		  				if(AppCommonBean.commonErrMsg.equalsIgnoreCase(AppCommonConstantsClass.LOC_PROVIDER_NULL))
		  				{
		  					ShowAlertUtilityClass.showSettingsAlert(AppCommonConstantsClass.NET_GPS_NOT_ENABLED);
		  				}
		  			}

			}
			
			
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
