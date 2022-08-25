package RestAssuredAutomation.RestAssuredAutomation;

import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;

public class BaseClass {
	
	@BeforeTest
	void setBaseURI() {
		RestAssured.baseURI = "https://reqres.in/";
	}

}
