package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.jsonData.UsersDataForGetMethod;

public class POSTAPITest extends TestBase{

	TestBase testBase;
	RestClient restClient;
	CloseableHttpResponse httpsResponse;
	UsersDataForGetMethod usersDataForGetMethod;
	
	String serviceURL;
	String appURL;
	String url;
	
	public POSTAPITest() throws IOException {
		super();
	}
	
	@BeforeMethod
	public void setUp() throws IOException {
		testBase = new TestBase();
		serviceURL =  prop.getProperty("url");
		appURL = prop.getProperty("serviceURL");
		
		url = serviceURL+appURL;
	}

	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		
		restClient = new RestClient();
		
		//add headers
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//jackson API used to create json data
		ObjectMapper mapper = new ObjectMapper();
		usersDataForGetMethod = new UsersDataForGetMethod("Chandrakant", "Automation Eng"); //expected user data
		
		//Convert object to json  
		mapper.writeValue(new File("D:/Projects/APIDemo/src/main/java/com/qa/jsonData/"
				+ "UserDataForGetMethod.json"), usersDataForGetMethod);
		
		//Convert object to json in string - - marshling
		String usersJSONString = mapper.writeValueAsString(usersDataForGetMethod);
		System.out.println("Created JON data is: " +usersJSONString);
		
		//call the POST method 
		httpsResponse = restClient.post(url, headerMap, usersJSONString);
		
		//get status now
		int statusCode = httpsResponse.getStatusLine().getStatusCode();
		System.out.println("Status code is: " +statusCode);
		Assert.assertEquals(statusCode, responseStatusCode_201, "Status code not matching");
		
		//getting data from json object
		String responseString = EntityUtils.toString(httpsResponse.getEntity(),"UTF-8");
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("Response of JSON from API: " +responseJSON);
		
		//validate the all values in json (Convert json to java) - unmarshling 
		UsersDataForGetMethod dataForGetMethod = mapper.readValue(responseString, UsersDataForGetMethod.class); //actual user data 
		System.out.println("Validated data is: " +dataForGetMethod);
		
		//check name is correct or not
		Assert.assertTrue(usersDataForGetMethod.getName().equals(dataForGetMethod.getName()));
		//check the job is correct or not
		Assert.assertTrue(usersDataForGetMethod.getJob().equals(dataForGetMethod.getJob()));
		
		//print all values
		System.out.println("ID is: " +dataForGetMethod.getId());
		System.out.println("Name is: " +dataForGetMethod.getName());
		System.out.println("Job is: " +dataForGetMethod.getJob());
		System.out.println("Created date and time is " +dataForGetMethod.getCreatedAt());
	}	
}