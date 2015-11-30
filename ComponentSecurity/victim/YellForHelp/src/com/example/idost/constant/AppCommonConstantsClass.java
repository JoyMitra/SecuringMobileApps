package com.example.idost.constant;

public interface AppCommonConstantsClass {
	
	String LOC_MNG_NULL = "location manager is null";
	String USR_LOC_MNG_NULL = "location not available, check ur data settings or restart the phone and try again";
	String LOC_PROVIDER_NULL = "location provider is null";
	String LOC_NET_PROVIDER_NULL = "network provider is null";
	String LOC_GPS_PROVIDER_NULL = "gps provider is null";
	String LOC_NULL = "location is null";
	String ADDR_NULL = "address is null";
	
	String CNTC_ADD = "Contact added successfully";
	String CNTC_EXIST="Contact already exits";
	String LOC_CHNG="location has been changed";
	String FETCH_POL_INF = "Fetching Police Information...";
	String FETCH_CURR_ADDR = "Fetching ur current Location...";
	
	String COMMON_ERR_MSG = "Yell for Help for has encountered an error Pls try after some time";
	
	String CURR_ADDR = "Current add is not available";
	
	
	String ENB_PRO_SETTING = "Enable provider setting";
	
	String USR_SETTING_MSG = "GPS/Network is not enabled. Do you want to go to settings menu ?";
	String USR_SETTING = "Settings";
	
	String NET_GPS_NOT_ENABLED =  "netOrGpsEnable";
	
	String PROVIDER_GPS = "gps";
	String PROVIDER_NETWORK = "network";
	
	int SEARCH_PLC_DIST = 5000;
	int INCR_PLC_DIST = 5000;
	
	String ALL_POL_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
	String NEAR_POL_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
	String CURR_ADDR_URL = "http://maps.google.com/maps/api/geocode/json?latlng=";
	String GOOGLE_MAPS_URL = "http://maps.google.co.in/maps?q=";
	String URL_LOC = "&location=";
	String URL_RAD = "&radius=";
	String URL_RANK = "&rankBy=distance";
	String URL_TYPES = "&types=";
	String URL_SENSOR = "&sensor=true";
	String URl_KEY = "&key=";
	String URL_REF = "reference=";
	
	String JSON_RESULTS = "results";
	String JSON_RESULT = "result";
	
	String ALL_POL_ATTR_ICON = "icon";
	String ALL_POL_ATTR_NM = "name";
	String ALL_POL_ATTR_VICINITY = "vicinity";
	String ALL_POL_ATTR_ID = "id";
	String ALL_POL_ATTR_REF = "reference";
	
	String NEAR_POL_ATTR_ID = "id";
	String NEAR_POL_ATTR_NM = "name";
	String NEAR_POL_ATTR_FRMADD = "formatted_address";
	String NEAR_POL_ATTR_INPHNO =	"international_phone_number";
	String NEAR_POL_ATTR_PHNO	= "formatted_phone_number";
	String NEAR_POL_ATTR_VICINITY = "vicinity";
	
	String CURR_ADDR_FORMT= "formatted_address";
	
	
	String CONCT_DEL_MSG = "Please Select a Contact To Delete";
	String NO_CONCT_ADDED ="No Contact Added Yet!";
	String CONCT_ADD_ERR = "Cannot Add Contact!";
	String CONT_CANT_ADD = "Cannot add more Contact";
	
	String POL_CALL_ERR_MSG = "Extremely Sorry! the information is not available yet. Please try after a few seconds";
	String POL_CALL_TEL = "tel:";
	
	String SND_MSG = "Send SMS";
	String CALL_POL = "Call Police";
	String SMS_DLV_Y = "SMS Delivered";
	String SMS_DLV_N = "SMS not Delivered";
	String SMS_SEND_Y = "SMS Sending";
	String SMS_SEND_GEN_ERR = "Sms failed due to generic failure";
	String SMS_SERV_NOT_AVAIL =  "could not send the message due to unavailability of service.";
	String SMS_NO_PDU = "Null PDU";
	String SMS_NOT_SENT = "Message could not be sent";
	String SERVICE_EXP= "Location is not available yet, try after some time";
	
	String ALERT_SETTING = "Settings";
	String ALERT_CANCEL ="Cancel";
	
	String MSG_CONT1 = "Please Help! I am in Danger" ;
	String MSG_CONT2 = ", My current Location is ";
	
	String GET_URL_UTL_CLS = "com.example.idost.util.GetUrlUtilityClass";
	String GET_URL_UTL_MTH = "getUrlConnContents";
	String GET_URL_MTH = "getUrl";
	
	
	String GET_LOC_CLS = "com.example.idost.GetLocationClass";
	String GET_LOC_MTH = "getLocation";

	String NEAR_POL_CLS = "com.example.idost.NearestPoliceStationInfoClass";
	String NEAR_POL_MTH = "findNearPoliceStationInfo";
	
	
	String ALL_POL_CLS = "com.example.idost.GetAllPoliceStationInfoClass";
	String ALL_POL_MTH = "findAllPoliceStationInfo";
	
	String CURR_ADDR__CLS = "com.example.idost.GetCurrentAddrLocClass";
	String CURR_ADDR_MTH =  "getGeoLocation";
	
	String CURR_ADD_SERVICE = "com.example.idost.service.CurrentAddressService";
	String POL_ADD_SERVICE = "com.example.idost.service.PoliceAddService";
	String MSG_SERVICE = "com.example.idost.service.MessagingService";
	
	String CONTACT_VAL = "CONTACT";
	String PACKAGE_NAME_1 = "com.sample.framework";
	String PACKAGE_KEY_1 = "37:00:67:57:77:56:8C:0D:10:6B:89:FC:FC:E2:65:34";
	
}
