package com.example.idost.util;

import java.lang.reflect.InvocationTargetException;

import com.example.idost.pojo.AppCommonBean;

public class AppReflectUtilityClass {

	public static void invokeMethod(String classNm,String methodNm,Class<?>[] paramTypes, Object[] params) throws AppCommonExceptionClass
	{
		
		try {
			if(params != null)
			{
				Class.forName(classNm).getMethod(methodNm,paramTypes).invoke(Class.forName(classNm).newInstance(), params);
			}
			else
			{
				Class.forName(classNm).getMethod(methodNm).invoke(Class.forName(classNm).newInstance());
			}
			
			
		} catch (IllegalArgumentException e) {
			throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		} catch (IllegalAccessException e) {
			throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		} catch (InvocationTargetException e) {
			throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		} catch (NoSuchMethodException e) {
			throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		} catch (ClassNotFoundException e) {
			throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		} catch (InstantiationException e) {
			throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		}catch(Exception e){
			throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		}
		
   }
}
