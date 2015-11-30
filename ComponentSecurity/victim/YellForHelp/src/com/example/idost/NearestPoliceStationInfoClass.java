package com.example.idost;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AllPoliceStationInfoBean;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.NearestPoliceInfoBean;
import com.example.idost.util.AppCommonExceptionClass;
import com.example.idost.util.AppReflectUtilityClass;
import com.example.idost.util.GetUrlUtilityClass;

public class NearestPoliceStationInfoClass {
	
		
	public void findNearPoliceStationInfo(AllPoliceStationInfoBean allPoliceStationInfoBean) throws AppCommonExceptionClass 
	    {
		 
		     try {
	        	this.getNearestPoliceInfo(allPoliceStationInfoBean);
	       } catch (AppCommonExceptionClass e) {
	        	throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		 	       
			}
	      
	    }
	 
	 private void getNearestPoliceInfo(AllPoliceStationInfoBean allPoliceStationInfoBean) throws AppCommonExceptionClass
	 {
		 
		 try{
			 String urlString = this.makeUrl(allPoliceStationInfoBean);
		  
			 AppReflectUtilityClass.invokeMethod(AppCommonConstantsClass.GET_URL_UTL_CLS, AppCommonConstantsClass.GET_URL_UTL_MTH,new Class[] {String.class}, new Object[] {urlString});
			 String jsonNearestPoliceStationInfo = GetUrlUtilityClass.urlContent;

		  
			 JSONObject jsonObj = new JSONObject(jsonNearestPoliceStationInfo);
			 JSONObject jsonObjResult = jsonObj.getJSONObject(AppCommonConstantsClass.JSON_RESULT);
			 this.jsonToNearestPoliceInfoBean(jsonObjResult);
          
		 }catch(JSONException jse)
		 {
			 throw new AppCommonExceptionClass(AppCommonBean.mContext, jse);
		 }
		 catch(Exception e)
		 {
			 throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
		 }
         
         
     }
	 
	   private String makeUrl(AllPoliceStationInfoBean allPoliceStationInfoBean) throws AppCommonExceptionClass {
	        String retUrl;
	    	try{
	    	String url = AppCommonConstantsClass.NEAR_POL_URL;
	    	
	    	ArrayList<String> params = new ArrayList<String>();
	    	params.add(AppCommonConstantsClass.URL_REF+allPoliceStationInfoBean.reference);
	    	params.add(AppCommonConstantsClass.URL_SENSOR);
	    	params.add(AppCommonConstantsClass.URl_KEY + AppCommonBean.API_KEY);
	    	
	    	
	    	AppReflectUtilityClass.invokeMethod(AppCommonConstantsClass.GET_URL_UTL_CLS, AppCommonConstantsClass.GET_URL_MTH,new Class[] {ArrayList.class,String.class}, new Object[] {params, url});
	    	retUrl = GetUrlUtilityClass.urlStr;
	    	
	    	}catch(Exception e)
	          {
	    		throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
	        	
	          }
	    	return retUrl;
	    }

	 
	   private void jsonToNearestPoliceInfoBean(JSONObject jsonObj) throws AppCommonExceptionClass {
	    	try {
	        	
	        	Iterator<?> iter = jsonObj.keys();
	            while(iter.hasNext()){
	                String jsonkey = (String)iter.next();
	                String value = jsonObj.getString(jsonkey);
	                if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.NEAR_POL_ATTR_ID))
	                {
	                	NearestPoliceInfoBean.policeId = value;
	                }
	                if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.NEAR_POL_ATTR_NM))
	                {
	                	NearestPoliceInfoBean.policeNm = value;
	  		        }
	                if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.NEAR_POL_ATTR_FRMADD))
	                {
	                	NearestPoliceInfoBean.policeFrmattedAdd = value;
		  		    }
	                if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.NEAR_POL_ATTR_INPHNO))
	                {
	                	NearestPoliceInfoBean.policeIntFrmattedPhNo = value;
		  		    }
	                if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.NEAR_POL_ATTR_PHNO))
	                {
	                	NearestPoliceInfoBean.policeFrmattedPhNo = value;
		  		    }
	                if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.NEAR_POL_ATTR_VICINITY))
	                {
	                	NearestPoliceInfoBean.policeVicinity = value;
			  	    }
	                
	             }
	        	
	        } catch (JSONException ex) {
	        	throw new AppCommonExceptionClass(AppCommonBean.mContext, ex);
	 	        }
	    
	    }
		
}
