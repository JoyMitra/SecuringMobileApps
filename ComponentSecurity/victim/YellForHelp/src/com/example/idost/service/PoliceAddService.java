package com.example.idost.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.idost.GetLocationClass;
import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.receiver.ResponsePoliceInfoReceiver;
import com.example.idost.util.AppReflectUtilityClass;

public class PoliceAddService extends IntentService{

	public static boolean isPoliceExceptionOccured;
	private ResponsePoliceInfoReceiver policeReceiver;
	
	public PoliceAddService() {
		super("PoliceAddService");
	}

	@Override
	  public void onCreate() {
	    super.onCreate();
	    policeReceiver = new ResponsePoliceInfoReceiver();
        IntentFilter intFltrPolice = new IntentFilter();
        intFltrPolice.addAction(ResponsePoliceInfoReceiver.ACTION_POL_ADD_RESP);
        registerReceiver(policeReceiver,intFltrPolice);
       
	  }
	
	
	@Override
	protected void onHandleIntent(Intent intent) {
		isPoliceExceptionOccured = false;
		
		try{
			AppReflectUtilityClass.invokeMethod(AppCommonConstantsClass.ALL_POL_CLS, AppCommonConstantsClass.ALL_POL_MTH,new Class[] {Double.TYPE,Double.TYPE,String.class}, new Object[] {GetLocationClass.location.getLatitude(),GetLocationClass.location.getLongitude(),"police"});
			
		}catch(Exception ex)
		{
			isPoliceExceptionOccured = true;
		}
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ResponsePoliceInfoReceiver.ACTION_POL_ADD_RESP);
		sendBroadcast(broadcastIntent);
		
		stopSelf();
		stopService(intent);
				
	}
	
	@Override
	  public void onDestroy() {
	    super.onDestroy();
	    unregisterReceiver(policeReceiver);
	  }

}
