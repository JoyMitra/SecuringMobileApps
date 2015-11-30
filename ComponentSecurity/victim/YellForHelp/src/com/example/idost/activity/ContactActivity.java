package com.example.idost.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idost.R;
import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.ContactBean;
import com.example.idost.util.AppCommonExceptionClass;
import com.example.idost.util.PreferUtilityClass;

public class ContactActivity extends Activity {

	private TextView txtvw;
	private ListView contactView=null;
	private ArrayList<String> ConList=null;
	private Button btndel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_contact);
		this.txtvw=(TextView)findViewById(R.id.txtViewContact);
		
		btndel=(Button)findViewById(R.id.btnDelete);
		this.LoadContactList();
		btndel.setOnClickListener(delBtnListn);

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
	
	private OnClickListener delBtnListn = new OnClickListener() {
		public void onClick(View v)
		{
			DeleteSelections();
		}};
	
	 private void DeleteSelections() 
	 {
	
		 if(this.contactView != null && this.contactView.getCheckedItemPositions().size()==0)
			Toast.makeText(ContactActivity.this,AppCommonConstantsClass.CONCT_DEL_MSG,Toast.LENGTH_SHORT).show();
		else
		{
			for(int index=0;index<=this.contactView.getCount();index++)
			{
				if(this.contactView.isItemChecked(index))
				{
					ContactBean.ContactMap.remove(this.contactView.getItemAtPosition(index).toString().split(":")[1]);
				}
			}
			
			try {
				PreferUtilityClass.delData(ContactActivity.this);
				PreferUtilityClass.UpdateContactDetails(ContactActivity.this);
			} catch (AppCommonExceptionClass e) {
				Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
			}
			
			LoadContactList();
		}
		
	}
	public void ShowContactData(String condata)
	 {
		try{
		if(condata!= null && "".equalsIgnoreCase(condata))
		{
		String[] strarr=condata.split(";");
		 txtvw.setText("");
		 for(int i=0;i<strarr.length;i++)
			 txtvw.append(strarr[i]+"\n");
		}
		}catch(Exception e)
		{
			Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
		}
	 }
	 
	 private ArrayList<String> GetContactList(String condata)
	 {
		 ConList=new ArrayList<String>();
		try{
		 
		 if(condata!=null && !"".equalsIgnoreCase(condata) && condata.length()!=0)
		 {
		 String[] strarr=condata.split(";");

		 for(int i=0;i<strarr.length;i++)
		 {
			ConList.add(strarr[i]);
		 }
		 }
		}catch(Exception e)
		{
			Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
		}
		 return ConList;

		 
	 }
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact, menu);
		return true;
	}

	private void LoadContactList()
	{
		this.contactView=(ListView)findViewById(R.id.listView1);
		
		try {
			ArrayList<String> condata=new ArrayList<String>();
			condata=GetContactList(PreferUtilityClass.GetContact(ContactActivity.this));
			if(condata.size()!=0)
			{
			this.txtvw.setText("");
			this.btndel.setEnabled(true);
			ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(ContactActivity.this,
					android.R.layout.simple_list_item_multiple_choice,condata);
			contactView.setAdapter(dataAdapter);
			dataAdapter.notifyDataSetChanged();
			this.contactView.setItemsCanFocus(false);
			this.contactView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			}
			else
			{
				 txtvw.setText(AppCommonConstantsClass.NO_CONCT_ADDED);
				 this.contactView.setAdapter(null);
				 this.btndel.setEnabled(false);
			}
		} catch (Exception e) {
			Toast.makeText(AppCommonBean.mContext, AppCommonBean.commonErrMsg, Toast.LENGTH_SHORT).show();
		}
	}
}