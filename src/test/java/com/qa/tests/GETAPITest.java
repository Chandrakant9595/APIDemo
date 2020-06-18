package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GETAPITest extends TestBase{

	TestBase testBase;
	RestClient restClient;
	
	String serviceURL;
	String appURL;
	String url;
	
	CloseableHttpResponse httpsResponse;
	
	public GETAPITest() throws IOException {
		super();
	}

	@BeforeMethod
	public void setUp() throws IOException {
		testBase = new TestBase();
		serviceURL =  prop.getProperty("url");
		appURL = prop.getProperty("serviceURL");
		
		url = serviceURL+appURL;
	}
	
	@Test(priority=1, description="API test without header passing")
	public void getWithoutHeaderAPITest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		httpsResponse = restClient.get(url);
		
		//getting status code
		int statusCode = httpsResponse.getStatusLine().getStatusCode();
		System.out.println("Ststus Code: " +statusCode);
		Assert.assertEquals(responseStatusCode_200, statusCode, "Status not matches");
		
		
		
		//getting JSON string
		String responseString = EntityUtils.toString(httpsResponse.getEntity(), "UTF-8");
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("Response of JSON from API: " +responseJSON);
		
		//getting data from json object
		String perPageValue = TestUtil.getValueByPath(responseJSON, "/per_page");
		System.out.println("Per page value in JSON: " +perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6, "Per page value is not matching");
		
		//getting data from json object
		String totalValue = TestUtil.getValueByPath(responseJSON, "/total");
		System.out.println("Per page value in JSON: " +totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12, "Total value is not matching");
		
		//getting data from json array
		String id0 = TestUtil.getValueByPath(responseJSON, "/data[0]/id");
		System.out.println("Data[0] ID is: " +id0);
		Assert.assertEquals(Integer.parseInt(id0), 1, "ID value is not matching");
		String firstName0 = TestUtil.getValueByPath(responseJSON, "/data[0]/first_name");
		System.out.println("Data[0] First name is: " +firstName0);
		Assert.assertEquals(firstName0, "George", "First Name value is not matching");
		String lastName0 = TestUtil.getValueByPath(responseJSON, "/data[0]/last_name");
		System.out.println("Data[0] Last name is: " +lastName0);
		Assert.assertEquals(lastName0, "Bluth", "Last Name value is not matching");
		String email0 = TestUtil.getValueByPath(responseJSON, "/data[0]/email");
		System.out.println("Data[0] Email is: " +email0);
		Assert.assertEquals(email0, "george.bluth@reqres.in", "Email value is not matching");
		String avatar0 = TestUtil.getValueByPath(responseJSON, "/data[0]/avatar");
		System.out.println("Data[0] Avatare is: " +avatar0);
		Assert.assertEquals(avatar0, "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg", "Avatar value is not matching");
		
		
		
		
		//getting all Headers
		Header[] headerArray = httpsResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
			for(Header header : headerArray) {
				allHeaders.put(header.getName(), header.getValue());
			}
			System.out.println("Hearder array: " +allHeaders);
			
	}


	@Test(priority=2, description="API test without header passing")
	public void getWithHeaderAPITest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		
		//add headers
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		//headerMap.put("username", "admin");
		//headerMap.put("pssword", "test@123");
		
		httpsResponse = restClient.get(url);
		
		//getting status code
		int statusCode = httpsResponse.getStatusLine().getStatusCode();
		System.out.println("Ststus Code: " +statusCode);
		Assert.assertEquals(responseStatusCode_200, statusCode, "Status not matches");
		
		
		
		//getting JSON string
		String responseString = EntityUtils.toString(httpsResponse.getEntity(), "UTF-8");
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("Response of JSON from API: " +responseJSON);
		
		//getting data from json object
		String perPageValue = TestUtil.getValueByPath(responseJSON, "/per_page");
		System.out.println("Per page value in JSON: " +perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6, "Per page value is not matching");
		
		//getting data from json object
		String totalValue = TestUtil.getValueByPath(responseJSON, "/total");
		System.out.println("Per page value in JSON: " +totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12, "Total value is not matching");
		
		//getting data from json array
		String id1 = TestUtil.getValueByPath(responseJSON, "/data[1]/id");
		System.out.println("Data[1] ID is: " +id1);
		Assert.assertEquals(Integer.parseInt(id1), 2, "ID value is not matching");
		String firstName1 = TestUtil.getValueByPath(responseJSON, "/data[1]/first_name");
		System.out.println("Data[1] First name is: " +firstName1);
		Assert.assertEquals(firstName1, "Janet", "First Name value is not matching");
		String lastName1 = TestUtil.getValueByPath(responseJSON, "/data[1]/last_name");
		System.out.println("Data[1] Last name is: " +lastName1);
		Assert.assertEquals(lastName1, "Weaver", "Last Name value is not matching");
		String email1 = TestUtil.getValueByPath(responseJSON, "/data[1]/email");
		System.out.println("Data[1] Email is: " +email1);
		Assert.assertEquals(email1, "janet.weaver@reqres.in", "Email value is not matching");
		String avatar1 = TestUtil.getValueByPath(responseJSON, "/data[1]/avatar");
		System.out.println("Data[1] Avatare is: " +avatar1);
		Assert.assertEquals(avatar1, "https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg", "Avatar value is not matching");
		
		
		
		
		//getting all Headers
		Header[] headerArray = httpsResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
			for(Header header : headerArray) {
				allHeaders.put(header.getName(), header.getValue());
			}
			System.out.println("Hearder array: " +allHeaders);
			
	}
}
