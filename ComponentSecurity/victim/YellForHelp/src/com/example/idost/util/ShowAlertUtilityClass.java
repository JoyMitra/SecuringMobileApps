package com.example.idost.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.widget.Toast;

import com.example.idost.GetLocationClass;
import com.example.idost.activity.AboutUsActivity;
import com.example.idost.activity.AppInfoActivity;
import com.example.idost.activity.ContactActivity;
import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.ContactBean;

public class ShowAlertUtilityClass {
	
	
public static void showSettingsAlert(String alertClassification){
		AlertDialog.Builder alertDialog = null;
		
		if(AppCommonConstantsClass.NET_GPS_NOT_ENABLED.equalsIgnoreCase(alertClassification))
		{
			alertDialog = new AlertDialog.Builder(AppCommonBean.mContext);
			 
	        // Setting Dialog Title
	        alertDialog.setTitle(AppCommonConstantsClass.ENB_PRO_SETTING);
	 
	        // Setting Dialog Message
	        alertDialog.setMessage(AppCommonConstantsClass.USR_SETTING_MSG);
	 
	        // On pressing Settings button
	        alertDialog.setPositiveButton(AppCommonConstantsClass.ALERT_SETTING, new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog,int which) {
	                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                AppCommonBean.mContext.startActivity(intent);
	            }
	            
	        });
		
	 	 // on pressing cancel button
	    alertDialog.setNegativeButton(AppCommonConstantsClass.ALERT_CANCEL, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        dialog.cancel();
	        ((Activity) AppCommonBean.mContext).finish();
	        }
	    });

		}
		
	// Showing Alert Message
    alertDialog.show();
	}

public static void showMenu(String[] dropdownMenuValues)
{
	AlertDialog.Builder menualertDialog = new AlertDialog.Builder(AppCommonBean.mContext);
	
	
	
	menualertDialog.setTitle("CHoose Any")
       .setItems(dropdownMenuValues, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
        	   System.out.println(which);
        switch(which){
   		    
   			case 0://Add Contact
   	    		if(ContactBean.ContactMap != null && ContactBean.ContactMap.size()<5)
   	    		{
   	    		 dialog.cancel();
   	    		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
   	    		((Activity)AppCommonBean.mContext).startActivityForResult(intent,1);
   	    		}else{
   	    			ContactBean.showMsg=AppCommonConstantsClass.CONT_CANT_ADD;
   	    			Toast.makeText(AppCommonBean.mContext, ContactBean.showMsg, Toast.LENGTH_LONG).show();
   	    		}
   	    		break;
   	    	case 1://View Contacts
   	    		dialog.cancel();
   	    		AppCommonBean.mContext.startActivity(new Intent(AppCommonBean.mContext,ContactActivity.class));
   	    		break;
   	    	case 2://Stop SMS
   	    		dialog.cancel();
   	    		AppCommonBean.msgBtnClicked = false;
   	    		GetLocationClass.locationManager.removeUpdates(GetLocationClass.networkLocationListener);
   	    		GetLocationClass.locationManager.removeUpdates(GetLocationClass.gpsLocationListener);
   	    		Toast.makeText(AppCommonBean.mContext, "SMS is stopped", Toast.LENGTH_LONG).show();
   	    		break;
   	    	case 3://About the App
   	    		dialog.cancel();
   	    		AppCommonBean.mContext.startActivity(new Intent(AppCommonBean.mContext,AppInfoActivity.class));
   	    		break;
   	    	case 4://About Us
   	    		dialog.cancel();
   	    		AppCommonBean.mContext.startActivity(new Intent(AppCommonBean.mContext,AboutUsActivity.class));
   	    		
   	    	}
       }
});
	menualertDialog.show();
}

/*

public static void exitAppAlert(){
	AlertDialog.Builder alertDialog = null;
	
	
		alertDialog = new AlertDialog.Builder(AppCommonBean.mContext);
		 
        // Setting Dialog Title
        alertDialog.setTitle("Alert");
 
        // Setting Dialog Message
        alertDialog.setMessage("Do you wanna Exit ?");
 
        // On pressing Settings button
        alertDialog.setPositiveButton("Return", new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog,int which) {
                AppCommonBean.mContext.startActivity(intent);
            }
            
        });
	
 	 // on pressing cancel button
    alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        ((Activity) AppCommonBean.mContext).finish();
        }
    });

	
	
// Showing Alert Message
alertDialog.show();
}*/


}
