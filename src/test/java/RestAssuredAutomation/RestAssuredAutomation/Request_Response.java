package RestAssuredAutomation.RestAssuredAutomation;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Request_Response extends BaseClass{

	@Test
	public void getUsersDetails() {

		RequestSpecification req = RestAssured.given();

		Response res = req.get("/api/users/2");

		System.out.println(res.getBody().asString());

		System.out.println(res.getStatusCode());
		Assert.assertEquals(res.getStatusCode(), 200);
		System.out.println(res.getStatusLine());
		Assert.assertEquals(res.getStatusLine(), "HTTP/1.1 200 OK");
	}

	@Test
	public void createNewUserPostMethod() {
		RequestSpecification req = RestAssured.given();
		
		// create the request body for POST request using JSOn object
		JSONObject obj = new JSONObject();
		
		obj.put("name","Manikandan");
		obj.put("job","Software Engineer");
		
		// add the header parameters
		req.header("Content-Type","application/json");
		
		// add the request body
		req.body(obj.toJSONString());
		
		// call the post request
		Response res = req.post("/api/users");
		
		System.out.println(res.getBody().asString());

		System.out.println(res.getStatusCode());
		Assert.assertEquals(res.getStatusCode(), 201);
		System.out.println(res.getStatusLine());
		Assert.assertEquals(res.getStatusLine(), "HTTP/1.1 201 Created");
		//res.getTime(); //returns the response time in milli seconds
		
		// getting the response value
		System.out.println(res.jsonPath().get("name").toString());
		// validating the response value
		Assert.assertTrue(res.jsonPath().get("job").equals("Software Engineer"),"Job name is not matching!");
		
		// getting header values
		System.out.println(res.header("Content-Type"));
		String resContentType = res.header("Content-Type");
		// validating the header values
		Assert.assertTrue(resContentType.equals("application/json; charset=utf-8"));
	}
	
	@Test
	public void getAllHeaderValuesPostMethod() {
		RequestSpecification req = RestAssured.given();
		
		Map<String,String> mapObj = new HashMap<>();
		
		mapObj.put("name", "Manikandan Srinivasan");
		mapObj.put("job","Automation Engineer");
		
		
		req.header("Content-Type","application/json");
		req.body(mapObj.toString());
		
		Response res = req.post("/api/users");
		
		Headers headerDetails = res.headers();
		
		for(Header h: headerDetails) {
			System.out.println(h.getName() + "-->" + h.getValue());
		}
		
		/*
		 * PreemptiveBasicAuthScheme basicAuth = new PreemptiveBasicAuthScheme();
		 * 
		 * basicAuth.setUserName("Admin"); basicAuth.setPassword("Admin");
		 * 
		 * RestAssured.authentication = basicAuth;
		 */
		
		//req.auth().preemptive().basic("xx","ccc").header(null).contentType(ContentType.JSON).body(req).post().then().extract().response();
		
	}
	
	@Test (dataProvider = "DDMethod")
	public void createNewEmployeesDataDrivenPostMethod(String empName, String empSalary, String empAge) {
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
		
		RequestSpecification req = RestAssured.given();
		
		Map<String,String> mapObj = new HashMap<>();
		
		mapObj.put("name", empName);
		mapObj.put("salary",empSalary);
		mapObj.put("age",empAge);
		
		
		req.header("Content-Type","application/json");
		req.body(mapObj.toString());
		
		Response res = req.post("/create");
		
		System.out.println(res.asPrettyString());
	}
	
	@DataProvider(name="DDMethod")
	public String[][] dataDrivenMethod() {
		String[][] strObj = { {"Manikandan","10000","28"},{"Srinivasan","10000","30"},{"Gopal","12220","28"} };
		
		return strObj;
	}

}
