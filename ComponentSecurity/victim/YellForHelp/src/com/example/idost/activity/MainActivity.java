package com.example.idost.activity;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.idost.GetLocationClass;
import com.example.idost.R;
import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.ContactBean;
import com.example.idost.pojo.NearestPoliceInfoBean;
import com.example.idost.receiver.ResponseCurrentAddReceiver;
import com.example.idost.receiver.ResponsePoliceInfoReceiver;
import com.example.idost.util.AppCallServiceUtilityClass;
import com.example.idost.util.AppCommonExceptionClass;
import com.example.idost.util.AppReflectUtilityClass;
import com.example.idost.util.PreferUtilityClass;
import com.example.idost.util.ShowAlertUtilityClass;



public class MainActivity extends Activity{

    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
       
        
      try{ 
       Button buttonSms = (Button)findViewById(R.id.btnSMS);
       ResponseCurrentAddReceiver.msgBtn = buttonSms;
       buttonSms.setText(AppCommonConstantsClass.FETCH_CURR_ADDR);
       buttonSms.setEnabled(false);
       buttonSms.setOnClickListener(startSmsListener);
       buttonSms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.button_anim, 0, 0, 0);
       ((AnimationDrawable)buttonSms.getCompoundDrawables()[0]).start();
       
       Button buttonPhone = (Button)findViewById(R.id.btnCall);
       ResponsePoliceInfoReceiver.callPlcBtn = buttonPhone;
       buttonPhone.setText(AppCommonConstantsClass.FETCH_POL_INF);
       buttonPhone.setEnabled(false);
       buttonPhone.setOnClickListener(startCallListener);
       buttonPhone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.button_anim, 0, 0, 0);
       ((AnimationDrawable)buttonPhone.getCompoundDrawables()[0]).start();
       
      
       
       Button buttonMore = (Button)findViewById(R.id.btnMore);
       buttonMore.setText("More");
       buttonMore.setOnClickListener(startModalMenuListener);
       
      }catch(Exception e)
      {
    	  Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.COMMON_ERR_MSG, Toast.LENGTH_SHORT).show();
      } 
	
      
    }

	//@Override
	
	  protected void onStart() {
	    	super.onStart();
	    	 
	     	 try {
	      	   
	    		 AppCommonBean.mContext = MainActivity.this; 
	    		 PreferUtilityClass.PopulateMap(AppCommonBean.mContext);
	    		 
	      	   	 AppReflectUtilityClass.invokeMethod(AppCommonConstantsClass.GET_LOC_CLS,AppCommonConstantsClass.GET_LOC_MTH,null, null);
	      	   	 
	      	   	 AppCallServiceUtilityClass.getService(AppCommonBean.mContext, AppCommonConstantsClass.CURR_ADD_SERVICE);
		      	 
	      	   	 AppCallServiceUtilityClass.getService(AppCommonBean.mContext, AppCommonConstantsClass.POL_ADD_SERVICE);
		      	 
	      	   	   
	         	}catch(Exception e)
	  			{
	  				if(AppCommonBean.commonErrMsg.equalsIgnoreCase(AppCommonConstantsClass.LOC_PROVIDER_NULL))
	  				{
	  					ShowAlertUtilityClass.showSettingsAlert(AppCommonConstantsClass.NET_GPS_NOT_ENABLED);
	  				}else{
	  					Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
	  				}
	  			}
	    	 
	    	 
	  	}
	    
	    protected void onRestart() {
	    	super.onRestart();
		}

	    protected void onResume() {
	    	super.onResume();
		}

	    protected void onPause() {
	    	super.onPause();
		}

	    protected void onStop() {
	    	super.onStop();
		}

	   
		@Override
	    protected void onDestroy() {
			super.onDestroy();
						
	        try {
				AppCallServiceUtilityClass.stopService(MainActivity.this);
			} catch (AppCommonExceptionClass e) {
				
				Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
  			}
	        
	        AppCommonBean.msgBtnClicked = false;
	        GetLocationClass.locationManager.removeUpdates(GetLocationClass.networkLocationListener);
    		GetLocationClass.locationManager.removeUpdates(GetLocationClass.gpsLocationListener);
	        
	    }

	
		
	/**
	 * code to send SMS
	 */
	private OnClickListener startSmsListener = new OnClickListener() {
		public void onClick(View v)
		{
			
			if(ContactBean.ContactMap!=null && ContactBean.ContactMap.size()>0)
			{
				AppCommonBean.msgBtnClicked = true;
				
				try{

					AppCallServiceUtilityClass.getService(MainActivity.this, AppCommonConstantsClass.MSG_SERVICE);
					}
					catch(Exception e)
					{
						Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
		  			}
			}else{
				Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.NO_CONCT_ADDED, Toast.LENGTH_SHORT).show();
			}
		}
	};

	
	
	/**
	 * code to call police
	 */
	private OnClickListener startCallListener = new OnClickListener() {
		public void onClick(View v)
		{
			
			try{
				String nearestPolPhn = NearestPoliceInfoBean.policeIntFrmattedPhNo;
				if(nearestPolPhn!=null && !("".equalsIgnoreCase(nearestPolPhn)))
				{
					String uriString = AppCommonConstantsClass.POL_CALL_TEL+nearestPolPhn;
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse(uriString));
					startActivity(intent);
				}
				else
				{
					Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.POL_CALL_ERR_MSG, Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception e)
			{
				Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();

			}
		}
	};
		

	/**
	 * code to manage main menu list
	 */
	private OnClickListener startModalMenuListener = new OnClickListener() {

		public void onClick(View v)
		{
			ShowAlertUtilityClass.showMenu(getResources().getStringArray(R.array.main_menu_list_arr));
		}
			
	};
	
	   
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
	    return true;
    }
	
    /* (non-Javadoc)
	 * stores the contacts in Preference
	 */
        protected void onActivityResult(int requestCode, int resultCode, Intent data)
        {  
            if (resultCode == RESULT_OK) 
            {  
            	try
            	{
            		Uri contactData = data.getData();  
                	ContentResolver cr=getContentResolver();
                	if(cr!= null)
                	{
                		Cursor cur=cr.query(contactData, null, null, null, null);
                		
                			while(cur.moveToNext())
                        	{
                        		String cid=cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        		String name = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                        		if(Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))>0)
                        		{
                        			Cursor pcur=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ cid,null, null);
                        			while (pcur.moveToNext())
                        			 {
                        				 String phoneNumber= pcur.getString(pcur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        				 PreferUtilityClass.StoreContact(MainActivity.this, name, phoneNumber);
                        			 }
                        		}
                        		else
                        		{
                        			ContactBean.showMsg=AppCommonConstantsClass.CONCT_ADD_ERR;
                        		}
                        	}
                		
                    	Toast.makeText(AppCommonBean.mContext, ContactBean.showMsg, Toast.LENGTH_LONG).show();
                		
                	}else
                	{
                		Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
                	}
                	
            	}
            	catch(Exception ex)
            	{
            		Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();            	}
            
            	}
        }

	           
}
