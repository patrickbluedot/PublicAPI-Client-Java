package com.bluedotinnovation.beacon;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bluedotinnovation.common.BDCommon;

/**
 * @author Bluedot Innovation
 * Copyright (c) 2016 Bluedot Innovation. All rights reserved.
 * Add Beacon to Zone client demonstrates adding a beacon to an existing zone of your Bluedot backend using Apache HTTP client and JSON Simple libraries.
 */

public class AddBeaconToZone extends BDCommon {
	
	private static String bdCustomerApiKey    = "7cd1ea80-d40e-11e4-84cb-b8ca3a6b869d"; //This key is generated by Bluedot Point Access UI when your account is created.
	private static String bdApplicationApiKey = "afc346a0-de5e-11e4-af33-b8ca3a7b879d"; //This apiKey is generated when you create an application
	private static String bdZoneId            = "bbef1027-9d47-4e5b-95f2-a9208715348a"; //This is the id of the zone being updated. This can be fetched by calling GET Zones API
	private static String bdRestUrl           = "https://api.bluedotinnovation.com/1/zones/beacons";
		
	/**
	 * @param args
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * @throws ParseException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static void main(String[] args) throws IllegalStateException, IOException, ParseException, KeyManagementException, NoSuchAlgorithmException {
		
		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(getSSLContextFactory()).build();
		
		JSONParser parser    = new JSONParser();
		JSONObject bdAddBeaconToZoneJSONObject = (JSONObject) parser.parse(getJsonBeaconToZone()); //add a Beacon to a Zone
		
		HttpPost postRequest = new HttpPost(bdRestUrl);
		postRequest.addHeader("content-type", "application/json");
		postRequest.setEntity(new StringEntity(bdAddBeaconToZoneJSONObject.toJSONString()));
		
		HttpResponse response = httpRestClient.execute(postRequest);
		
		if (response.getStatusLine().getStatusCode() == 200) {
        	System.out.println("Beacon was successfully created");
        	InputStream inputStream = response.getEntity().getContent();
        	byte[] bytes            = readStream(inputStream);
        	String resultString     = new String(bytes); //json result
        	JSONObject jsonResult   = (JSONObject)  parser.parse(resultString);
        	System.out.println(jsonResult);
        } else {
        	InputStream inputStream = response.getEntity().getContent();
        	byte[] bytes            = readStream(inputStream);
        	String resultString     = new String(bytes); //json error result
        	System.out.println(resultString);
        }
	}
	
	private static String getJsonBeaconToZone() {
		
		String addBeaconToZone =
				"{" +
			       "\"security\": {" +
			        	"\"apiKey\":"+ "\"" + bdApplicationApiKey +"\","+
			        	"\"customerApiKey\":" + "\"" + bdCustomerApiKey + "\" "+
			        "}," + 
				    "\"content\": {" +
				        "\"zone\": {"+
				            "\"zoneId\":" + "\"" + bdZoneId + "\","+
				            "\"beacons\": [" +
				            	"{" +
					               "\"beaconId\":  \"b22ea927-f757-4a8f-8ba7-2960a4b688b2\"," +
					    		   "\"proximity\": 1," +
					    		   "\"order\": 1" +
					    		"}"+
					    	"]"+
				        "}"+
				    "}"+
			 "}";
		return addBeaconToZone;
	}
}
