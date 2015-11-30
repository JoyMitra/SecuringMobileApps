package com.example.idost.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.idost.R;
import com.example.idost.util.HorizontalPagerUtilityClass;

public class AppInfoActivity extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        HorizontalPagerUtilityClass realViewSwitcher = new HorizontalPagerUtilityClass(getApplicationContext());

		ImageView img1 = new ImageView(getApplicationContext());
		ImageView img2 = new ImageView(getApplicationContext());
		ImageView img3 = new ImageView(getApplicationContext());

		img1.setImageResource(R.drawable.one);
		img2.setImageResource(R.drawable.two);
		img3.setImageResource(R.drawable.three);

		realViewSwitcher.addView(img1);
		realViewSwitcher.addView(img2);
		realViewSwitcher.addView(img3);
		
		setContentView( realViewSwitcher);
        
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
