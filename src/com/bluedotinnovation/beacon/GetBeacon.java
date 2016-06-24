package com.bluedotinnovation.beacon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.bluedotinnovation.common.BDCommon;

/**
 * @author Bluedot Innovation
 * Copyright (c) 2016 Bluedot Innovation. All rights reserved.
 * Get Beacon client demonstrates listing a beacon for a given beaconId for a customer's account from Bluedot backend 
 * using Apache HTTP client and JSON Simple libraries.
 */

public class GetBeacon extends BDCommon {

	public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		
		String bdCustomerApiKey = "7cd1ea80-d40e-11e4-84cb-b8ca3a6b879d"; //This key is generated by Bluedot Access UI when you register.
		String bdBeaconId     	= "11146bf3-b894-4491-9ef1-4f151f782730"; //This is the ID of the beacon which is created when the beacon is configured on Bluedot Backend
		String bdRestUrl        = "https://api.bluedotinnovation.com/1/beacons?customerApiKey=" +bdCustomerApiKey + "&beaconId=" + bdBeaconId;

		CloseableHttpClient httpRestClient = HttpClients.custom().setSSLSocketFactory(getSSLContextFactory()).build();
		HttpGet request       = new HttpGet(bdRestUrl);
		HttpResponse response = httpRestClient.execute(request);
		BufferedReader rd     = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		String line           = "";
		while ( (line = rd.readLine()) != null ) {
		     System.out.println(line);
		}
	}
}
