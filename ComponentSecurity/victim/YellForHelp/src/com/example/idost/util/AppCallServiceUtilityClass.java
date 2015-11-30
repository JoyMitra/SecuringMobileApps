package com.example.idost.util;

import android.content.Context;
import android.content.Intent;

public class AppCallServiceUtilityClass{
	private static Intent intent;
	
	
	public static void getService(Context mContext,String classNm) throws AppCommonExceptionClass
	{
		
		intent = null;
		try {
				intent = new Intent(mContext, Class.forName(classNm));
				mContext.startService(intent);
			
			} catch (ClassNotFoundException e) {
				throw new AppCommonExceptionClass(mContext, e);
			}
			catch(Exception e)
			{
				throw new AppCommonExceptionClass(mContext, e);
			}
   }
	
	public static void stopService(Context mContext) throws AppCommonExceptionClass
	{
		try
		{
		mContext.stopService(intent);
		}
		catch(Exception e)
		{
			throw new AppCommonExceptionClass(mContext, e);
		}
	}

}
