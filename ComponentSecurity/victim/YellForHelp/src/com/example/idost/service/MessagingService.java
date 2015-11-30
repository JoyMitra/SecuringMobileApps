package com.example.idost.service;

import java.util.ArrayList;

import android.Manifest;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PermissionInfo;
import android.os.Binder;
import android.telephony.SmsManager;

import com.example.idost.GetLocationClass;
import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.ContactBean;
import com.example.idost.pojo.CurrentAddressBean;
import com.example.idost.receiver.SmsDeliverIdostReceiver;
import com.example.idost.receiver.SmsSendIdostReceiver;
import com.example.idost.util.CheckCertificate;

public class MessagingService extends IntentService {

	private SmsSendIdostReceiver smssendreceiver;
	private SmsDeliverIdostReceiver smsdeliverreceiver;
	
	public MessagingService() {
		super("MessagingService");
	}
	
	@Override
	  public void onCreate() {
	    super.onCreate();   
	 smssendreceiver = new SmsSendIdostReceiver();
     IntentFilter intFltrSmsSend = new IntentFilter(SmsSendIdostReceiver.SMS_SEND_RESP);
     registerReceiver(smssendreceiver,intFltrSmsSend);

     smsdeliverreceiver = new SmsDeliverIdostReceiver();
     IntentFilter intFltrSmsDelivered = new IntentFilter(SmsDeliverIdostReceiver.SMS_DELIVER_RESP);
     registerReceiver(smsdeliverreceiver,intFltrSmsDelivered);
     
     CheckCertificate chkCert = new CheckCertificate();
     chkCert.addCertHashKey(AppCommonConstantsClass.PACKAGE_NAME_1, AppCommonConstantsClass.PACKAGE_KEY_1);
     
	}
	
	
	
	
	@Override
	protected void onHandleIntent(Intent intent) {
		
		
		
		
			try{
				
				String msgContent = AppCommonConstantsClass.MSG_CONT1;
				if(CurrentAddressBean.curraddressLine !=null)
				{
					msgContent = msgContent+AppCommonConstantsClass.MSG_CONT2+CurrentAddressBean.curraddressLine ;
					msgContent = msgContent + "\n " + AppCommonConstantsClass.GOOGLE_MAPS_URL + GetLocationClass.location.getLatitude()+","+GetLocationClass.location.getLongitude(); 

				}
						
				
				SmsManager smsManager = SmsManager.getDefault();
				
				
				
				if(ContactBean.ContactMap!=null && ContactBean.ContactMap.size()>0)
				{
					for(String phoneNo : ContactBean.ContactMap.keySet())
					{
							smsManager.sendTextMessage(phoneNo, null, msgContent, null, null);
							//Thread.sleep(2000);
					}
				}
				
				
				ArrayList<String> smsParts =smsManager.divideMessage(msgContent);
				
				ArrayList<PendingIntent> piSent = new ArrayList<PendingIntent>();
				ArrayList<PendingIntent> piDeliver = new ArrayList<PendingIntent>();
				
				for (int index = 0; index < smsParts.size(); index++) {
					piSent.add(PendingIntent.getBroadcast(AppCommonBean.mContext, 0, new Intent(SmsSendIdostReceiver.SMS_SEND_RESP), 0));
					piDeliver.add(PendingIntent.getBroadcast(AppCommonBean.mContext, 0, new Intent(SmsDeliverIdostReceiver.SMS_DELIVER_RESP), 0));
					}
				
				
				if(ContactBean.ContactMap!=null && ContactBean.ContactMap.size()>0)
				{
					for(String phoneNo : ContactBean.ContactMap.keySet())
					{
							smsManager.sendMultipartTextMessage(phoneNo, null, smsParts, piSent, piDeliver);
							Thread.sleep(2000);
					}
				}
				else{
					AppCommonBean.commonErrMsg = AppCommonConstantsClass.NO_CONCT_ADDED;
				}
				
				}catch(Exception e)
				{
					AppCommonBean.commonErrMsg = AppCommonConstantsClass.COMMON_ERR_MSG;
				}
		
		
		
		
		stopSelf();
		stopService(intent);
		}
	
	
	@Override
	  public void onDestroy() {
	    super.onDestroy();
        unregisterReceiver(smsdeliverreceiver);
        unregisterReceiver(smssendreceiver);
  
	  }

}
