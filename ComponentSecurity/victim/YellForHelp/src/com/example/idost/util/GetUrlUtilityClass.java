package com.example.idost.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.example.idost.pojo.AppCommonBean;

public class GetUrlUtilityClass{
	
	public static String urlStr;
	public static String urlContent;
	
	public void getUrl(ArrayList<String> params,String url) throws AppCommonExceptionClass {
		StringBuilder urlString = new StringBuilder("");
		try{
		
		urlString.append(url);
		for(String param:params)
		{
			urlString.append(param);
		}
		
		urlStr = urlString.toString();
		}catch (Exception e){
	    	throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
	    }

		
   }
	


	public void getUrlConnContents(String theUrl) throws AppCommonExceptionClass 
    {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);
             URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()), 8);
            String line;
            while ((line = bufferedReader.readLine()) != null) 
            {
                content.append(line + "\n");
            }

            bufferedReader.close();
            urlContent = content.toString();
        	}catch (Exception e){
        		throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
        	}
    }

}

