import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;


public class RestAssuredClass {
  @Test
  public void verifyMethod() {
	  given().when().log().all()
	  	.get("https://restful-booker.herokuapp.com/booking")
	  	.then()
		.assertThat().statusCode(200); // verify status code
//		.log().body(); // print response body
	  
	  Response response = given().get("https://restful-booker.herokuapp.com/booking");
	  JsonPath jsonPath = response.jsonPath();
	  List<Integer> responseValues = jsonPath.get("bookingid");
	  for (Integer s : responseValues)
		  System.out.println(s.toString());
	  
	  // hamcrest matchers
	  given().when()
	  	.get("https://restful-booker.herokuapp.com/booking")
	  	.then()
		.assertThat().body("[1].bookingid",equalTo(3));
	}
}
