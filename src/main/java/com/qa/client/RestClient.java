package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {

	//Get Method without header
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpsClient = HttpClients.createDefault(); //create the client
		HttpGet httpGet = new HttpGet(url); //http get request create
		CloseableHttpResponse httpsResponse = httpsClient.execute(httpGet); //hit the URL and return response
		
		return httpsResponse;
	}
	
	//Get Method with header
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpsClient = HttpClients.createDefault(); //create the client
		HttpGet httpGet = new HttpGet(url); //http get request create
		
		//pass the header
		for(Map.Entry<String, String> entry :headerMap.entrySet()) {
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}
		
		CloseableHttpResponse httpsResponse = httpsClient.execute(httpGet); //hit the URL and return response
		
		return httpsResponse;
	}
	
	//Post method
	public CloseableHttpResponse post(String url, HashMap<String, String> headerMap, String entityString) throws ClientProtocolException, IOException {
		CloseableHttpClient httpsClient = HttpClients.createDefault(); //create the client
		HttpPost httpPost = new HttpPost(url); //http post request create
		httpPost.setEntity(new StringEntity(entityString)); //payload
		
		//pass the header
		for(Map.Entry<String, String> entry :headerMap.entrySet()) {
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}
		
		CloseableHttpResponse httpsResponse = httpsClient.execute(httpPost);
		
		return httpsResponse;
	}
}