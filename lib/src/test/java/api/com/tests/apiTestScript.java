package api.com.tests;

import org.testng.annotations.Test;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertEquals;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

import api.com.pojo.cityModel;
import api.com.pojo.pojoComplex;
import api.com.pojo.postPojo;
import api.com.util.DataGeneration;
import api.com.util.Helper;
import api.com.util.ReadTestData;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

 
public class apiTestScript {
	
	private String accessToken;
	
	 //fetch base url
	String baseUrl ;
	// fetch endpoint
	String endpoint ;
	//create uri
	//String uri = Helper.propertyReader("qaBaseUrl")+ ReadTestData.getTestData("endpointGetUsers");
	DataGeneration dg = new DataGeneration();
	private String name = dg.generateString(6);
	private String job = dg.generateString(8);
	
	@Test (description= "Validate the status code for GET users endpoint")	
	public void validateStatusCodeGetUsers() {
		
		Response resp = given()
				       .when()
				       .get("https://reqres.in/api/users?page=2");
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 200);			       
	}
	
	@Test (description= "Validate the status code for GET users endpoint using queryParam")	
	public void validateStatusCodeGetUser() {
		
		Response resp = given()
				       .queryParam("page", 2) //queryParam("page", 2).queryParam("page", 3) for multiple params
				       .when()
				       .get("https://reqres.in/api/users");//RestAssured
		
		int actualStatusCode = resp.statusCode(); //RestAssured
		assertEquals(actualStatusCode, 200);	//TestNG		       
	}
	
	@Test (description= "Validate the status code for GET users endpoint and print the response body using JsonPath")	
	public void validateResponseBody() {
		
		Response resp = given()
					   .queryParam("page", 2)
				       .when()
				       .get("https://reqres.in/api/users");
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 200);	
		System.out.println(resp.body().asString());
		String actualValue= resp.path("data[1].email");
		assertEquals(actualValue, "lindsay.ferguson@reqres.in" );
	}
	
	@Test (description= "Validate the status code for GET users endpoint using header")	
	public void validateResponseBodyGetHeader() {
		
		Response resp = given()
				       .header("Content-type", "application/json")// for mutiple headers use'.header'. for eg.header("Content-type", "application/json")
				       .when()
				       .get("https://gorest.co.in/public-api/users");
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 200);	
		System.out.println(resp.body().asString());
	}
	
	@Test (description= "Validate the status code for GET users endpoint using pathParam")	
	public void validateResponseBodyGetPathParam() {
		
		//String raceSeasonValue = "2017";
		Response resp = given()
				       .pathParam("raceSeason", "2017")
				       .when()
				       .get("http://ergast.com/api/f1/{raceSeason}/circuits.json");
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 200);	
		System.out.println(resp.body().asString());
	}
	
	@Test (description= "Validate the status code for GET users endpoint using Basic Authentication")	
	public void validateResponseBodyGetBasicAuth() {
		
		Response resp = given()
				       .auth()
				       .basic("postman", "password")
				       .when()
				       .get("https://postman-echo.com/basic-auth");
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 200);	
		System.out.println(resp.body().asString());
		accessToken = resp.path("token"); // this variable will store the accessToken that is generated 
										  //when the basic authentication is done by passing the username and password
										  // refer to the JsonPath test case
	}
	
	@Test (description= "Validate the status code for GET users endpoint using Digest Authentication")	
	public void validateResponseBodyGetDigestAuth() {
		
		Response resp = given()
				       .auth()
				       .digest("postman", "password")
				       .when()
				       .get("https://postman-echo.com/basic-auth");
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 200);	
		System.out.println(resp.body().asString());
	}
	
	@Test (description= "Validate the status code for GET users endpoint using OAuth1-Verify Signature")	
	public void validateResponseBodyGetOauth1Auth() {
		
		Response resp = given()
				       .auth()
				       .oauth("consumerKey", "consumerSecret", "accessToken", "secretToken")
				       .when()
				       .get("https://reqres.in/api/users/2");
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 200);	
		System.out.println(resp.body().asString());
	}
	
	@Test (description= "Validate the status code for GET users endpoint using OAuth2")	
	public void validateResponseBodyGetOauth2Auth() {
		
		Response resp = given()
				       .auth()
				       .oauth2(accessToken)
				       .when()
				       .get("https://reqres.in/api/users/2");
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 200);	
		System.out.println(resp.body().asString());
	}
	
	@Test (description= "Validate the status code for GET users endpoint using OAuth2 using header")	
	public void validateResponseBodyGetOauth2WithHeader() {
		
		Response resp = given()
				       .header("Authorization", accessToken) //before running this test case the accessToken generating test case (basic auth) should run first
				       .when()
				       .get("https://reqres.in/api/users/2");
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 200);	
		System.out.println(resp.body().asString());
	}

	@Test (description= "Validate the status code for GET users endpoint by fetching test data from .json file")	
	public void validateStatusCodeGetUsersByFetchingEndpoint() throws IOException, ParseException {
		
		System.out.println("*******************"+ ReadTestData.getTestData("uriGetUsers"));
		Response resp = given()
				       .when()
				       .get(ReadTestData.getTestData("uriGetUsers"));
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 200);		
		
		// test case to fetch test data from .json files
		
	}
	
	@Test (description= "Validate the status code for GET users endpoint and print the response body using JsonPath while fetching the email from testdata.json file")	
	public void validateResponseBodyFetchingEmail() throws IOException, ParseException {
		
		System.out.println("*******************"+ ReadTestData.getTestData("expectedEmail"));
		Response resp = given()
					   .queryParam("page", 2)
				       .when()
				       .get("https://reqres.in/api/users");
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 200);	
		System.out.println(resp.body().asString());
		String actualValue= resp.path("data[1].email");
		assertEquals(actualValue, ReadTestData.getTestData("expectedEmail"));
	}
	
	@Test (description= "Validate the status code for GET users endpoint using properties reader file")	
	public void validateStatusCodeGetUserPropertiesFile() {
		
		System.out.println("***************"+ Helper.propertyReader("qaBaseUrl"));
		Response resp = given()
				       .queryParam("page", 2) //queryParam("page", 2).queryParam("page", 3) for multiple params
				       .when()
				       .get(Helper.propertyReader("qaBaseUrl"));
		
		int actualStatusCode = resp.statusCode(); 
		assertEquals(actualStatusCode, 200);			       
	}
	
	@Test (description= "Validate the status code for GET users endpoint in testdata.json file and server address in properties reader file")	
	public void validateStatusCodeGetUserPropertiesFileAndTestDatafile() throws IOException, ParseException {
		
		System.out.println("***************"+ Helper.propertyReader("qaBaseUrl"));
		
//		// fetch base url
//		String baseUrl = Helper.propertyReader("qaBaseUrl");
//		// fetch endpoint
//		String endpoint = ReadTestData.getTestData("endpointGetUsers");
		//create uri
		//String uri = baseUrl + endpoint;
		System.out.println("Uri is *****" + Helper.getUri());
		
		Response resp = given()
				       .queryParam("page", 2) //queryParam("page", 2).queryParam("page", 3) for multiple params
				       .when()
				       .get(Helper.getUri());
		
		int actualStatusCode = resp.statusCode(); 
		assertEquals(actualStatusCode, 200);	
		
//		private static String getUri() throws IOException, ParseException {
//		String uri = Helper.propertyReader("qaBaseUrl")+ ReadTestData.getTestData("endpointGetUsers");
//	    return uri;	
//}
		
	}
		
	@Test (description= "Validate the status code for GET users endpoint by using FileInsputStream ")	
	public void validateResponseBodyPojoPostHappypath() throws IOException {
		
		File file = new File(System.getProperty("user.dir")+ "/Resources/TestData/postRequestBodys.json" ); //fetch the request body that's hard coded in the JSON(postRequestBodys.json)
		FileInputStream fileStream = new FileInputStream(file);
		Response resp = given()
					   .body(IOUtils.toString(fileStream))
				       .header("Content-type", "application/json") 
				       .when()
				       .post("https://reqres.in/api/users/2");
		
		int actualStatusCode = resp.statusCode();
		assertEquals(actualStatusCode, 201);	
		System.out.println(resp.body().asString());
		
		//Test case for POST method by fetching of hardcoded values from JSON file and using the endpoint that has been hardcoded in the .post
		// refer to the Day 10 recording from 9 minutes onwards
			
	}
	
	@Test (description= "Validate the status code for GET users endpoint by using FileInsputStream ")	
	public void validateResponseBodyPojoPost() throws IOException {
		
		//File file = new File(System.getProperty("user.dir")+ "/Resources/TestData/postRequestBodys.json"); 
		//FileInputStream fileStream = new FileInputStream(new File(System.getProperty("user.dir")+ "/Resources/TestData/postRequestBodys.json"));
		Response resp = given()
					   .body(IOUtils.toString(new FileInputStream(new File(System.getProperty("user.dir")+ "/Resources/TestData/postRequestBodys.json"))))
				       .header("Content-type", "application/json") 
				       .when()
				       .post("https://reqres.in/api/users/2");
		
		//int actualStatusCode = resp.statusCode();
		assertEquals(resp.statusCode(), 201);	
		System.out.println(resp.body().asString()); // try an avoid sysout as when running a method in an actual project, you may 
													// use it for debugging purpose but as per coding standards one should avoid it
	
		//In this test case we are also learning how to follow best coding standards, the commented lines of code are examples of this
		//File, FileInputStream,int actualStatusCode, Sysout. If the object is being used only once, then there is no use of creating 
		//a separate object, but instead try initializing and calling the object of the class in a single line
		
	}
		
	@Test (description= "Validate the status code for GET users endpoint by using FileInsputStream ")	
	public void validateResponseBodyPojoPostUsingEncapsulation() throws IOException {
		
		postPojo pojo = new postPojo();
		pojo.setName("test");    // we never return any value in setter methods
		pojo.setJob("testing");
		Response resp = given()
					   .body(pojo)
				       .header("Content-type", "application/json") 
				       .when()
				       .post("https://reqres.in/api/users/2");
		assertEquals(resp.statusCode(), 201);	
		
		//In this Test case we will see the example of using getter and setter method to fetch and set values through these methods at runtime. 
		// To demonstrate this we will play around with line no 285-
		//body(IOUtils.toString(new FileInputStream(new File(System.getProperty("user.dir")+ "/Resources/TestData/postRequestBodys.json"))))	
	}
	
	@Test (description= "Validate the status code for GET users endpoint by using FileInsputStream ")	
	public void validateResponseBodyPojoPostUsingRandomUtils() throws IOException {
		
//		DataGeneration dg = new DataGeneration();------------> defined it at class level
//		String name = dg.generateString(6);------------> defined it at class level
//		String job = dg.generateString(8);------------> defined it at class level
		
//		postPojo pojo = new postPojo();											]
//		pojo.setName(name);    // we never return any value in setter methods	]-----------> created a method outside the test case(line no 333) 
//		pojo.setJob(job);														]-----------> and called it in line no 320
		Response resp = given()
					   .body(pojo())
				       .header("Content-type", "application/json") 
				       .when()
				       .post("https://reqres.in/api/users/2");
		assertEquals(resp.statusCode(), 201);	
		System.out.println(resp.body().asString());
		
		//In this Test case we will see the example of using RandomUtils method, which we modified by creating a method of our own(DataGeneration),
		// and then set values through this method at run time instead of hardcoding values in name and job fields 
		//like how we did in the previous TC. Every time a fresh set of values is set 
		Math.random();
//		java.util.Random rand = new java.util.Random();
//		System.out.println(rand.nextInt(10));
		
		
	}
	
	private postPojo pojo() {
		postPojo pojo = new postPojo();
		pojo.setName(name);
		pojo.setJob(job);
		return pojo;
		
		//Method to set the name and job 
	}
	
//skipping a test case
	
//	@Test (description= "Validate the status code for Get users endpoint")
//	public void validatePostBodyComplexPojo() throws IOException {
//		
//		pojoComplex pojo = new pojoComplex();
//		
//		//created a list of type String to have all the values defined as per the JSON
//		List<String> job = new ArrayList<>();
//		job.add("tester");
//		job.add("developer");
//		job.add("support");
//		
//		cityModel cities = new cityModel();
//		cities.setName("bangalore");
//		cities.setTemperature("30");
//		
//		cityModel cities2 = new cityModel();
//		cities2.setName("delhi");
//		cities2.setTemperature("40");
//		
//		List<cityModel> lst = new ArrayList<>();
//		lst.add(cities);
//		lst.add(cities2);
//		
//		pojo.setJobs(job);
//		pojo.setName("morpheus");
//		pojo.setCityModels(lst);
//		
//		
//		Response resp =given()
//				       .body(pojo())
//				       .header("Content-type", "application/json")
//				       .when()
//				       .post("https://reqres.in/api/users");
//		AssertJUnit.assertEquals(resp.statusCode(), 201);
//		
//	}	
}
