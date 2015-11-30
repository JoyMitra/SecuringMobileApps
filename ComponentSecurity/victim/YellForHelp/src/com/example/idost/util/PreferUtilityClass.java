package com.example.idost.util;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.ContactBean;

public class PreferUtilityClass {
	SharedPreferences shrpref;

public static void StoreContact(Context context, String name,String Phone) throws AppCommonExceptionClass 
{
	try{
	ContactBean.showMsg="";
	if ((ContactBean.ContactMap == null)
			|| (!ContactBean.ContactMap.containsKey(Phone))) 
	{
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor edit = prefs.edit();
		String phndata = getData(context);
		if (phndata==null) {
			edit.putString(AppCommonConstantsClass.CONTACT_VAL,
					name + ":" + Phone + ";");
		} else 
		{
			edit.putString(AppCommonConstantsClass.CONTACT_VAL, phndata
					+ name + ":" + Phone + ";");
			
			
		}
		edit.commit();
		ContactBean.ContactMap.put(Phone, name);
		ContactBean.showMsg=AppCommonConstantsClass.CNTC_ADD;
	}
	else
		ContactBean.showMsg=AppCommonConstantsClass.CNTC_EXIST;
	
	}catch(Exception e)
	{
		throw new AppCommonExceptionClass(context, e);
	}
	
}



public static void UpdateContact(Context context, ContactBean conobj) throws AppCommonExceptionClass 
{
	try{
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	Editor edit = prefs.edit();
	String phndata=getData(context);
	if(phndata==null)
	{
		edit.putString(AppCommonConstantsClass.CONTACT_VAL,conobj.getName()+":"+conobj.getPhn());
	}
	else
	edit.putString(AppCommonConstantsClass.CONTACT_VAL,phndata+";"+conobj.getName()+":"+conobj.getPhn());
	edit.commit();
	}catch(Exception e)
	{
		throw new AppCommonExceptionClass(context, e);
	}
}

public static void UpdateContactDetails(Context context) throws AppCommonExceptionClass
{

	try{
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	Editor edit = prefs.edit();
	edit.putString(AppCommonConstantsClass.CONTACT_VAL,StoreContactData(GetContactListFromMap()));
	edit.commit();
	}catch(Exception e)
	{
		throw new AppCommonExceptionClass(context, e);
	}
}

private static ArrayList<String> GetContactListFromMap() throws AppCommonExceptionClass
{
	try
	{
	ArrayList<String> conList=new ArrayList<String>();
	for(String key: ContactBean.ContactMap.keySet())
	{
		conList.add(key+":"+ContactBean.ContactMap.get(key));
	}
	return conList;
	}catch(Exception e)
	{
		throw new AppCommonExceptionClass(AppCommonBean.cContext, e);
	}
}

private static String StoreContactData(ArrayList<String> contactList) throws AppCommonExceptionClass
{
	try{
	String conListData="";
	for(int i=0;i<contactList.size();i++)
	{
		conListData+=contactList.get(i).split(":")[1]+":"+contactList.get(i).split(":")[0]+";";
	}
	return conListData;
	}catch(Exception e)
	{
		throw new AppCommonExceptionClass(AppCommonBean.cContext, e);
	}
}

public static String GetContact(Context context) throws AppCommonExceptionClass {
	try{
	SharedPreferences prefs = PreferenceManager
			.getDefaultSharedPreferences(context);
	return prefs.getString(AppCommonConstantsClass.CONTACT_VAL, "");
	}catch(Exception e)
	{
		throw new AppCommonExceptionClass(context, e);
	}
}

public static String getData(Context context) throws AppCommonExceptionClass
{
	try{
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	String data = prefs.getString(AppCommonConstantsClass.CONTACT_VAL, null);
	
	if(data==null)
		return null;
	else
	return data;
	}catch(Exception e)
	{
		throw new AppCommonExceptionClass(context, e);
	}
}

public static void delData(Context context) throws AppCommonExceptionClass 
{
	try{
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	Editor edit = prefs.edit();
	edit.putString(AppCommonConstantsClass.CONTACT_VAL,null);
	edit.commit();
	}catch(Exception e)
	{
		throw new AppCommonExceptionClass(context, e);
	}
}

public static void PopulateMap(Context context) throws AppCommonExceptionClass 
{
	try{
	ContactBean.ContactMap=new HashMap<String, String>();
	String conTactData=getData(context);
	if(conTactData!=null && !conTactData.equalsIgnoreCase(""))
	{
		String[] conTactListArr=conTactData.split(";");
		for(int i=0;i<conTactListArr.length;i++)
		{
			ContactBean.ContactMap.put(conTactListArr[i].split(":")[1], conTactListArr[i].split(":")[0]);
		}
	}
	}catch(Exception e)
	{
		throw new AppCommonExceptionClass(context, e);
	}
}
	
}
