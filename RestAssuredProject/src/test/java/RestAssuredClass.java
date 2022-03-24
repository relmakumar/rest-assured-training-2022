import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;


public class RestAssuredClass {
	Response response;
	JsonPath jsonPath;
	String uri = "";
	
  @Test
  public void verifyTest1() {
	  uri = "https://restful-booker.herokuapp.com/booking";
	  given().when().log().all()
	  	.get(uri)
	  	.then()
		.assertThat().statusCode(200); // verify status code
//		.log().body(); // print response body
	  
	  response = given().get(uri);
	  jsonPath = response.jsonPath();
	  List<Integer> responseValues = jsonPath.get("bookingid");
	  for (Integer s : responseValues)
		  System.out.println(s.toString());
	  
	  // hamcrest matchers
	  given().when()
	  	.get(uri)
	  	.then()
		.assertThat().body("bookingid",hasItem(3));
	}
  
  @Test
  public void verifyTest2() {
//	  response = given().get("https://restful-booker.herokuapp.com/booking");
//	  jsonPath = response.jsonPath();
	  String id = "/"+jsonPath.getString("[1].bookingid");
	  
	  given().when().log().all()
	  	.get(uri+id)
	  	.then()
	  	.assertThat().statusCode(200)
	  	.assertThat().log().all();
	  	
	  	response = given().get(uri+id);
		jsonPath = response.jsonPath();
	
	// print the item values from the response
	  System.out.println(jsonPath.getString("firstname"));
	  System.out.println(jsonPath.getString("lastname"));
	  System.out.println(jsonPath.getInt("totalprice"));	
	  
	// hamcrest matchers
	  given().when()
		  	.get(uri+id)
		  	.then()
			.assertThat().body("firstname",equalTo(jsonPath.getString("firstname")))
			.assertThat().body("lastname",equalTo(jsonPath.getString("lastname")))
			.assertThat().body("totalprice",equalTo(jsonPath.getInt("totalprice")))
			.assertThat().body("depositpaid",equalTo(jsonPath.getBoolean("depositpaid")))
			.assertThat().body("additionalneeds",equalTo(jsonPath.getString("additionalneeds")));
  }
}
