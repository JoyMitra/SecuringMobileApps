package com.example.idost.util;

import android.content.Context;

public class AppCommonExceptionClass extends Exception{

	private static final long serialVersionUID = 3602265767031781701L;
	
	public AppCommonExceptionClass(Context context,Exception ex)
	{
		super(ex);
	}
	
}
