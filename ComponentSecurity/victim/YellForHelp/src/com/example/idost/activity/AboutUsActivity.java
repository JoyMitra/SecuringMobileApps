package com.example.idost.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.idost.R;
import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.util.AppCallServiceUtilityClass;

public class AboutUsActivity extends Activity
{
	
protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getActionBar().setDisplayHomeAsUpEnabled(true);
                      
 	}

protected void onstart()
{
	super.onStart();
	try{

		AppCallServiceUtilityClass.getService(AboutUsActivity.this, AppCommonConstantsClass.MSG_SERVICE);
		}
		catch(Exception e)
		{
			Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
			}

}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    // Respond to the action bar's Up/Home button
    case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
}


}
