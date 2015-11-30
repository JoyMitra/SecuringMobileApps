package com.example.idost.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.widget.Button;

import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.service.PoliceAddService;

public class ResponsePoliceInfoReceiver extends BroadcastReceiver{
	
	public static final String ACTION_POL_ADD_RESP = "com.example.idost.service.POLICERESPONSE";
	public static Button callPlcBtn;;
	
	@Override
	public void onReceive(Context context, Intent intent) {
				if(!PoliceAddService.isPoliceExceptionOccured)
				{
					callPlcBtn.setText(AppCommonConstantsClass.CALL_POL);
					AnimationDrawable callBtnLeft = (AnimationDrawable)callPlcBtn.getCompoundDrawables()[0];
					if(callBtnLeft != null)
					{
						callBtnLeft.stop();
					}
					callPlcBtn.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
					callPlcBtn.setEnabled(true);
				}
				else{
					callPlcBtn.setText(AppCommonConstantsClass.SERVICE_EXP);
					callPlcBtn.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0);
					callPlcBtn.setEnabled(false);
				}
	}

}
