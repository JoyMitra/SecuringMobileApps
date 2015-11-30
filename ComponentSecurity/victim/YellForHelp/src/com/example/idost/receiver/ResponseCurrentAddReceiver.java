package com.example.idost.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.widget.Button;

import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.service.CurrentAddressService;

public class ResponseCurrentAddReceiver extends BroadcastReceiver{

	public static final String ACTION_COMM_ADD_RESP = "com.example.idost.service.CURRADDRESPONSE";
	public static Button msgBtn;
	@Override
	public void onReceive(Context context, Intent intent) {
		if(!CurrentAddressService.isExceptionOccured)
		{
			msgBtn.setText(AppCommonConstantsClass.SND_MSG);
			AnimationDrawable msgBtnLeft = (AnimationDrawable)msgBtn.getCompoundDrawables()[0];
			if(msgBtnLeft != null)
			{
				msgBtnLeft.stop();
			}
			msgBtn.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
			msgBtn.setEnabled(true);
		}
		else{
			msgBtn.setText(AppCommonConstantsClass.SERVICE_EXP);
			msgBtn.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
			msgBtn.setEnabled(false);
		}
		
	}

}
