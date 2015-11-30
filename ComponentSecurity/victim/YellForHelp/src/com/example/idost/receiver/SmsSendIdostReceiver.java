package com.example.idost.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;

public class SmsSendIdostReceiver extends BroadcastReceiver{

	public static String SMS_SEND_RESP = "com.example.idost.sms.SEND";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		switch(getResultCode())
        {

        case Activity.RESULT_OK:
            Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.SMS_SEND_Y, Toast.LENGTH_LONG).show();
        break;
  
        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
            Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.SMS_SEND_GEN_ERR, Toast.LENGTH_LONG).show();
            break;

        case SmsManager.RESULT_ERROR_NO_SERVICE:
            Toast.makeText(AppCommonBean.mContext,AppCommonConstantsClass.SMS_SERV_NOT_AVAIL, Toast.LENGTH_LONG).show();
            break;

        case SmsManager.RESULT_ERROR_NULL_PDU:
            Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.SMS_NO_PDU, Toast.LENGTH_LONG).show();
            break;
        default:
        	Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.SMS_NOT_SENT, Toast.LENGTH_SHORT).show();

        }
	}

}
