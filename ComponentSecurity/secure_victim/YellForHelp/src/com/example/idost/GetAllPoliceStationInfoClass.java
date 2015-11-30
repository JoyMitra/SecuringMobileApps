package com.example.idost;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.idost.constant.AppCommonConstantsClass;
import com.example.idost.pojo.AllPoliceStationInfoBean;
import com.example.idost.pojo.AppCommonBean;
import com.example.idost.pojo.NearestPoliceInfoBean;
import com.example.idost.util.AppCommonExceptionClass;
import com.example.idost.util.AppReflectUtilityClass;
import com.example.idost.util.GetUrlUtilityClass;


public class GetAllPoliceStationInfoClass {

	private AllPoliceStationInfoBean allPlaceBean;
   
    public void findAllPoliceStationInfo(double latitude, double longitude,String placeSpacification) throws AppCommonExceptionClass 
    {
    	int policeSearchDist = AppCommonConstantsClass.SEARCH_PLC_DIST;
        try{ 
    	this.getAllPoliceInfo(latitude, longitude, placeSpacification,policeSearchDist);
            
        }catch(Exception e)
        {
        	throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
        }
    }
    
    private void getAllPoliceInfo(double latitude, double longitude,String placeSpacification,int policeSearchDist) throws AppCommonExceptionClass 
    {
    	
    	try {
    		String urlString = this.makeUrl(latitude, longitude,placeSpacification,policeSearchDist);
        
            AppReflectUtilityClass.invokeMethod(AppCommonConstantsClass.GET_URL_UTL_CLS,AppCommonConstantsClass.GET_URL_UTL_MTH,new Class[] {String.class}, new Object[] {urlString});
            String jsonAllPoliceStationInfo = GetUrlUtilityClass.urlContent;

            JSONObject jsonObj = new JSONObject(jsonAllPoliceStationInfo);
            JSONArray jsonObjArr = jsonObj.getJSONArray(AppCommonConstantsClass.JSON_RESULTS);
            if(jsonObjArr!= null && jsonObjArr.length()>0)
            {
            	for (int i = 0; i < jsonObjArr.length(); i++) {
                    
                    	AllPoliceStationInfoBean allPoliceStationInfoBean = this.jsonToAllPoliceRefBean((JSONObject) jsonObjArr.get(i));
                    	
                    	AppReflectUtilityClass.invokeMethod(AppCommonConstantsClass.NEAR_POL_CLS,AppCommonConstantsClass.NEAR_POL_MTH,new Class[] {AllPoliceStationInfoBean.class}, new Object[] {allPoliceStationInfoBean});
                    	 if(NearestPoliceInfoBean.policeFrmattedPhNo != null)
                     	{
                         	break;
                     	}
                	}
            	
            	if(NearestPoliceInfoBean.policeFrmattedPhNo == null)
             	{
            		getAllPoliceInfo(latitude,longitude,placeSpacification,policeSearchDist+AppCommonConstantsClass.INCR_PLC_DIST);
             	}
                
            }else{
            	getAllPoliceInfo(latitude,longitude,placeSpacification,policeSearchDist+AppCommonConstantsClass.INCR_PLC_DIST);
            }
    	} catch (JSONException ex) {
        	throw new AppCommonExceptionClass(AppCommonBean.mContext, ex);
        }catch(Exception e)
        {
        	throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
        }
    	
    }
    
    private String makeUrl(double latitude, double longitude,String placeSpacification,int policeSearchDist) throws AppCommonExceptionClass {
    	String retUrl= null;
    	try{
		    	String url = AppCommonConstantsClass.ALL_POL_URL;
		    	
		    	ArrayList<String> params = new ArrayList<String>();
		    	params.add(AppCommonConstantsClass.URL_LOC+Double.toString(latitude)+","+Double.toString(longitude));
		    	params.add(AppCommonConstantsClass.URL_RAD+policeSearchDist);
		    	params.add(AppCommonConstantsClass.URL_RANK);
		    	params.add(AppCommonConstantsClass.URL_TYPES+placeSpacification);
		    	params.add(AppCommonConstantsClass.URL_SENSOR);
		    	params.add(AppCommonConstantsClass.URl_KEY + AppCommonBean.API_KEY);
		    	
		    	AppReflectUtilityClass.invokeMethod(AppCommonConstantsClass.GET_URL_UTL_CLS, AppCommonConstantsClass.GET_URL_MTH,new Class[] {ArrayList.class,String.class}, new Object[] {params, url});
		    	retUrl = GetUrlUtilityClass.urlStr;
		    	
    	}catch(Exception e){
    		throw new AppCommonExceptionClass(AppCommonBean.mContext, e);
    	}
    	
    	return retUrl;
	}

    
   private AllPoliceStationInfoBean jsonToAllPoliceRefBean(JSONObject jsonObj) throws AppCommonExceptionClass {
        try {
        	allPlaceBean = new AllPoliceStationInfoBean();
        	Iterator<?> iter = jsonObj.keys();
        	 while(iter.hasNext()){
            
        	String jsonkey = (String)iter.next();
            String value = jsonObj.getString(jsonkey);
	            if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.ALL_POL_ATTR_ICON))
	            {
	            	allPlaceBean.icon = value;
	            }
	            if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.ALL_POL_ATTR_NM))
	            {
	            	allPlaceBean.name = value;
	            }
	            if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.ALL_POL_ATTR_VICINITY))
	            {
	            	allPlaceBean.vicinity = value;
	            }
	            if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.ALL_POL_ATTR_ID))
	            {
	            	allPlaceBean.id = value;
	            }
	            if(jsonkey.equalsIgnoreCase(AppCommonConstantsClass.ALL_POL_ATTR_REF))
	            {
	            	allPlaceBean.reference = value;
	            }
	               
        	 }
        	
            return allPlaceBean;
        } catch (JSONException ex) {
        	throw new AppCommonExceptionClass(AppCommonBean.mContext, ex);
        }
    }
	
    
}