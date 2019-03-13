package com.test;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class API_RestAssured {

	static String geturl = "http://dummy.restapiexample.com/api/v1/";
	
	public static void main(String[] args) {
		get();
		testPOST();
		testRestAssured();

	/*	System.out.println(Pattern.matches(".s", "as"));//true (2nd char is s)  
		System.out.println(Pattern.matches(".s", "mk"));//false (2nd char is not s)  
		System.out.println(Pattern.matches(".s", "mst"));//false (has more than 2 char)  
		System.out.println(Pattern.matches(".s", "amms"));//false (has more than 2 char)  
		System.out.println(Pattern.matches("..s", "mas"));//true (3rd char is s)  
		
		
		System.out.println(Pattern.matches("[amn]", "abcd"));//false (not a or m or n)  
		System.out.println(Pattern.matches("[amn]", "a"));//true (among a or m or n)  
		System.out.println(Pattern.matches("[am]", "m"));//false (m and a comes more than once)  
*/	}
	public static  void get(){
		HttpUriRequest request = new HttpGet(geturl+"employees");
		try {
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(),HttpStatus.SC_OK); // Status code
		System.out.println(ContentType.getOrDefault(httpResponse.getEntity()).getMimeType()); //Content type
		String result = EntityUtils.toString(httpResponse.getEntity()); //Response from Json
		JSONObject jo = new JSONObject(result);
		System.out.println(jo.getString("Temperature"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static String data =  "{/'name'/':/'test/',/'salary/':/'6623/',/'age/':/'23/'}";
	
	public static void testPOST(){
		 CloseableHttpClient client = HttpClients.createDefault();
		HttpPost  httpPost = new HttpPost(geturl+"create");
		StringEntity entity;
		try {
			entity = new StringEntity(data);
		
			
			httpPost.setEntity(entity);

		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json");
		    CloseableHttpResponse response = client.execute(httpPost);
		    Assert.assertEquals(response.getStatusLine().getStatusCode(), Integer.parseInt("200"));
		    client.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testRestAssured(){
		 RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		 RequestSpecification httpRequest = RestAssured.given();		 
		 Response response = httpRequest.request(Method.GET, "/Hyderabad");		 
		 String responseBody = response.getBody().asString();
		 System.out.println("Response Body is =>  " + responseBody);
	}

}
