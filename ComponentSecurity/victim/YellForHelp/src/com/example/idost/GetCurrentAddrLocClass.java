package com.example.idost;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Geocoder;

import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.CurrentAddressBean;
import com.example.idost.util.AppCommonExceptionClass;
import com.example.idost.util.AppReflectUtilityClass;
import com.example.idost.util.GetUrlUtilityClass;

public class GetCurrentAddrLocClass {

	double latitude;
	double longitude;

	public void getGeoLocation(double latitude, double longitude)
			throws AppCommonExceptionClass {
		try {

			Geocoder geocoder = new Geocoder(AppCommonBean.mContext);

			if (geocoder != null) {

				/*List<Address> addresses = geocoder.getFromLocation(latitude,longitude, 1);

				if (addresses != null && addresses.size() > 0 && addresses.get(0).getAddressLine(0) != null) {
						CurrentAddressBean.curraddressLine = addresses.get(0).getAddressLine(0);
						CurrentAddressBean.locality = addresses.get(0).getLocality();
						CurrentAddressBean.adminArea = addresses.get(0).getAdminArea();
						CurrentAddressBean.countryCode = addresses.get(0).getCountryCode();
						CurrentAddressBean.countryNm = addresses.get(0).getCountryName();
						CurrentAddressBean.postalCode = addresses.get(0).getPostalCode();
						CurrentAddressBean.postalCode = addresses.get(0).getPhone();
				} else {
					AppCommonBean.commonErrMsg = AppCommonConstantsClass.CURR_ADDR;
					throw new Exception(AppCommonConstantsClass.ADDR_NULL);
				}*/
				
				
				this.getLocFrmUrl(latitude,longitude);
			}
			
		} 
		catch (Exception e) {
			throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		}

	}
	
	private void getLocFrmUrl(double latitude, double longitude) throws AppCommonExceptionClass
	{
		try {
    		String urlString = this.makeUrl(latitude, longitude);
    		
    		AppReflectUtilityClass.invokeMethod(AppCommonConstantsClass.GET_URL_UTL_CLS,AppCommonConstantsClass.GET_URL_UTL_MTH,new Class[] {String.class}, new Object[] {urlString});
            String currAddrInfo = GetUrlUtilityClass.urlContent;

            JSONObject jsonObj = new JSONObject(currAddrInfo);
            JSONArray jsonObjArr = jsonObj.getJSONArray(AppCommonConstantsClass.JSON_RESULTS);
            if(jsonObjArr!= null && jsonObjArr.length()>0)
            {
            	this.jsonToAllPoliceRefBean((JSONObject) jsonObjArr.get(0));
            }
    		

		} catch (JSONException ex) {
        	throw new AppCommonExceptionClass(AppCommonBean.mContext, ex);
        }catch(Exception e)
        {
        	throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
        }
    	
	}
	
	private String makeUrl(double latitude, double longitude) throws AppCommonExceptionClass {
    	String retUrl= null;
    	try{
		    	String url = AppCommonConstantsClass.CURR_ADDR_URL+Double.toString(latitude)+","+Double.toString(longitude);
		    	
		    	ArrayList<String> params = new ArrayList<String>();
		    	params.add(AppCommonConstantsClass.URL_SENSOR);
		    	
		    	AppReflectUtilityClass.invokeMethod(AppCommonConstantsClass.GET_URL_UTL_CLS, AppCommonConstantsClass.GET_URL_MTH,new Class[] {ArrayList.class,String.class}, new Object[] {params, url});
		    	retUrl = GetUrlUtilityClass.urlStr;
		    	
    	}catch(Exception e){
    		throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
    	}
    	
    	return retUrl;
	}
	
	 private void jsonToAllPoliceRefBean(JSONObject jsonObj) throws AppCommonExceptionClass {
	        try {
	        	
	        	Iterator<?> iter = jsonObj.keys();
	        	 while(iter.hasNext()){	            
	        	String jsonkey = (String)iter.next();
	            String value = jsonObj.getString(jsonkey);
		            if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.CURR_ADDR_FORMT))
		            {
		            	CurrentAddressBean.curraddressLine = value;
		            	break;
		            }
		         }
	        	
	        
	        } catch (JSONException ex) {
	        	throw new AppCommonExceptionClass(AppCommonBean.mContext, ex);
	        }
	    }


}
