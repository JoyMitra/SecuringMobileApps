package com.example.idost.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class CheckCertificate {

private Map<String,String> mWhiteList = new HashMap<String, String>();
	
	public Boolean addCertHashKey(String pkgName, String hashcode)
	{
		if(pkgName==null) return false;
		if(hashcode==null) return false;
		
		hashcode = hashcode.replaceAll(" ", "");
		if(hashcode.length()!=64) return false;
		hashcode = hashcode.toUpperCase(Locale.getDefault());
		if(hashcode.replaceAll("[0-9A-F]+", "").length()!=0) return false;
		
		this.mWhiteList.put(pkgName, hashcode);
		
		return true;
	}
	
	
	public boolean test(Context ctx, String pkgname)
	{
		try
		{
			String correctHash = null;
			if(pkgname==null) return false;
			if(this.mWhiteList.get(pkgname)==null) return false;
			correctHash = this.mWhiteList.get(pkgname);
			if (correctHash == null) return false;
			correctHash = correctHash.replaceAll(" ", "");
			return correctHash.equals(hash(ctx, pkgname));
		}
		catch (NullPointerException ne)
		{
			return false;
		}
		
	}
	
	public static String hash(Context ctx, String pkgname) 
	{
		if (pkgname == null) return null;
		try 
		{
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pkginfo = pm.getPackageInfo(pkgname, PackageManager.GET_SIGNATURES);
			if (pkginfo.signatures.length != 1) return null; // Will not handle multiple signatures.
			Signature sig = pkginfo.signatures[0];
			byte[] cert = sig.toByteArray();
			byte[] sha256 = computeSha256(cert);
			return byte2hex(sha256);
		} 
		catch (NameNotFoundException e) 
		{
			return null;
		}
		catch(NullPointerException ne)
		{
			return null;
		}
		
	}
	
	
	private static byte[] computeSha256(byte[] data) 
	{
		try 
		{
			return MessageDigest.getInstance("SHA-256").digest(data);
		} 
		catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	private static String byte2hex(byte[] data) 
	{
		if (data == null) return null;
		final StringBuilder hexadecimal = new StringBuilder();
		for (final byte b : data) 
		{
			hexadecimal.append(String.format("%02X", b));
		}
		return hexadecimal.toString();
	}
	
	public String getPackageName(Context context,int callingPid) throws NullPointerException
	{
		String pkgName = null;
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> procList = am.getRunningAppProcesses();
		//int callingPid = Binder.getCallingPid();
		if (procList != null) 
		{
			for (RunningAppProcessInfo proc : procList) 
			{
				if (proc.pid == callingPid) 
				{
					pkgName = proc.pkgList[proc.pkgList.length - 1];
					break;
				}
			}
		}  
		return pkgName;
	}
	

	public static boolean testInHouse(Context ctx, String sigPermName, String correctHash) 
	{
		if (correctHash == null) return false;
		correctHash = correctHash.replaceAll(" ", "");
		return correctHash.equals(hashPermName(ctx, sigPermName));
	}
	
	public static String hashPermName(Context ctx, String sigPermName) 
	{
		if (sigPermName == null) return null;
		try 
		{
		// Get the package name of the application which declares a permission named sigPermName.
			PackageManager pm = ctx.getPackageManager();
			PermissionInfo pi;
			pi = pm.getPermissionInfo(sigPermName, PackageManager.GET_META_DATA);
			String pkgname = pi.packageName;
		// Fail if the permission named sigPermName is not a Signature Permission
			if (pi.protectionLevel != PermissionInfo.PROTECTION_SIGNATURE) return null;
		// Return the certificate hash value of the application which declares a permission named sigPermName.
			return hash(ctx, pkgname);
		} 
		catch (NameNotFoundException e) 
		{
			return null;
		}
	}
	
	public boolean checkPermIdentity(Context ctx, int callerPid, int callerUid, int myUid, String permission, int protectionLevel, PackageManager pm)
	{
		String pkgName = null;
		if(callerUid != myUid)
		{
			PermissionInfo pi = null;
			try {
				pi = pm.getPermissionInfo(permission, PackageManager.GET_META_DATA);
				if(pi!=null)
				{
					if (pi.protectionLevel == protectionLevel)
					{
						if(protectionLevel==PermissionInfo.PROTECTION_SIGNATURE)
						{
							return true;
						}
						else		//check in white list
						{
							pkgName = this.getPackageName(ctx, callerPid);
							if((this.test(ctx, pkgName)) || (this.mWhiteList == null))
							{
								return true;
							}
							else
								return false;
						}
						
					}
					else
						return false;
				}
				else
					return false;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				Log.e("PermError", "Permission name not found");
				return true;
			}
		}
		else
			return true;
		
	}
	
}
