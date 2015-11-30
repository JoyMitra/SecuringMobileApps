package com.example.idost.receiver;

import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SmsDeliverIdostReceiver extends BroadcastReceiver{

	public static String SMS_DELIVER_RESP = "com.example.idost.sms.DELIVER";
	
	@Override
	public void onReceive(Context context, Intent intent) {
	
		switch(getResultCode())
        {
        case Activity.RESULT_OK:
            Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.SMS_DLV_Y, Toast.LENGTH_SHORT).show();
            break;

        case Activity.RESULT_CANCELED:
            Toast.makeText(AppCommonBean.mContext, AppCommonConstantsClass.SMS_DLV_N, Toast.LENGTH_SHORT).show();
            break;
        }
	}

}
