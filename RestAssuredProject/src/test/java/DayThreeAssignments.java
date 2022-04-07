import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;


public class DayThreeAssignments {
   
  @DataProvider
  public static Object[][] postsInfo() {
      return new Object[][] {
          { "1","API-Automation Day 1", "Sachin"},
          { "2","API-Automation Day 2", "Sachin F"},
          { "3","API-Automation Day 3", "Mahesh"},
          { "4","API-Automation Day 4", "Anirudha"}
      };
  }
  
  @DataProvider
  public static Object[][] commentsInfo() {
      return new Object[][] {
          { "1","some comment globant 1"},
          { "2","some comment globant 2"},
          { "3","some comment globant 3"},
          { "4","some comment globant 4"}
      };
  }

  @Test(dataProvider = "postsInfo")
  public void verifyAssignment1_1(String id, String expectedTitle, String expectedAuthor) {

      given().
          pathParam("id", id).
      when().
          get("http://localhost:3000/posts/{id}").
      then().
          assertThat().
          body("title", equalTo(expectedTitle)).
          body("author", equalTo(expectedAuthor));
  }
  
  @Test(dataProvider = "commentsInfo")
  public void verifyAssignment1_2(String id, String expectedComment) {

      given().
          pathParam("id", id).
      when().
          get("http://localhost:3000/comments/{id}").
      then().
          assertThat().
          body("body", equalTo(expectedComment)).
          body("postId", equalTo(Integer.parseInt(id)));
  }
  
  @Test
  public void verifyAssignment2_SimpleJson() {
	  JSONObject jsonObject = new JSONObject();
	  jsonObject.put("firstname", "Arif");
	  jsonObject.put("lastname", "Mulla");
	  jsonObject.put("id", 77);
	  jsonObject.put("age", 25);
	  
	  given()
	  	.contentType(ContentType.JSON)
	  	.body(jsonObject.toString())
	  .when()
	  	.post("http://localhost:3000/friends")
	  .then()
	  .assertThat()
	  .statusCode(201);
	  
	  given().when()
	  	.get("http://localhost:3000/friends")
	  	.then()
		.assertThat().body("firstname",hasItem(jsonObject.get("firstname")))
		.assertThat().body("lastname",hasItem(jsonObject.get("lastname")))
		.assertThat().body("id",hasItem(jsonObject.get("id")))
		.assertThat().body("age",hasItem(jsonObject.get("age")));
  }
  
  @Test
  public void verifyAssignment2_MediumJson() {
	  JSONObject jsonObject = new JSONObject();
	  jsonObject.put("firstname", "Rahul");
	  jsonObject.put("lastname", "Lanjevar");
	  jsonObject.put("address", new JSONObject().put("streetName", "Blue Ridge").put("houseNo", "7"));
	  jsonObject.put("id", 2000);
	  jsonObject.put("age", 25);
	  
	  given()
	  	.contentType(ContentType.JSON)
	  	.body(jsonObject.toString())
	  .when()
	  	.post("http://localhost:3000/friends")
	  .then()
	  .assertThat()
	  .statusCode(201);
	  	  
	  given().when()
	  	.get("http://localhost:3000/friends")
	  	.then()
		.assertThat().body("firstname",hasItem(jsonObject.get("firstname")))
		.assertThat().body("lastname",hasItem(jsonObject.get("lastname")))
		.assertThat().body("address.streetName",hasItem(jsonObject.getJSONObject("address").get("streetName")))
		.assertThat().body("address.houseNo",hasItem(jsonObject.getJSONObject("address").get("houseNo")))
		.assertThat().body("id",hasItem(jsonObject.get("id")))
		.assertThat().body("age",hasItem(jsonObject.get("age")));
  }
  
  @Test
  public void verifyAssignment2_ComplexJson() {
	  JSONObject jsonObject = new JSONObject();
	  jsonObject.put("firstname", "Smita");
	  jsonObject.put("lastname", "Shewale");
	  JSONArray jsonArray = new JSONArray();
	  jsonArray.put(new JSONObject().put("streetName", "satara road").put("houseNo", 2111));
	  jsonArray.put(new JSONObject().put("streetName", "paudh road").put("houseNo", 4121));
	  jsonObject.put("address", jsonArray);
	  jsonObject.put("id", 99);
	  jsonObject.put("age", 25);
	  
	  given()
	  	.contentType(ContentType.JSON)
	  	.body(jsonObject.toString())
	  .when()
	  	.post("http://localhost:3000/friends")
	  .then()
	  .assertThat()
	  .statusCode(201);
	  
//	  JsonPath jsonPath = given().get("http://localhost:3000/friends").jsonPath();

//	  System.out.println(jsonPath.get);
//	  assertEquals(jsonPath.get("address"),jsonObject);
	  given().when()
	  	.get("http://localhost:3000/friends")
	  	.then()
		.assertThat().body("firstname",hasItem(jsonObject.get("firstname")))
		.assertThat().body("lastname",hasItem(jsonObject.get("lastname")))
//		.assertThat().body("address.streetName", hasItem(arr))
//		.assertThat().body("address",equalTo(arrayWithSize(2)))
		//		.assertThat().body("address[0].streetName",hasItem(jsonObject.getJSONArray("address").getJSONObject(0).get("streetName")))
//		.assertThat().body("address[1].houseNo",hasItem(jsonObject.getJSONArray("address").getJSONObject(0).get("houseNo")))
		.assertThat().body("id",hasItem(jsonObject.get("id")))
		.assertThat().body("age",hasItem(jsonObject.get("age")));
  }
  
  @Test
  public void verifyAssignment3_SimpleJsonFile() {
	  File file = new File("src/test/resources/simple_new_friends.json");

  	given().
  		contentType(ContentType.JSON).
  		body(file).
  	when().
  		post("http://localhost:3000/friends").
  	then().
  		assertThat().
  		statusCode(201);
  	
  	given().when()
  	.get("http://localhost:3000/friends")
  	.then()
	.assertThat().body("firstname",hasItem("Harish"))
	.assertThat().body("lastname",hasItem("Palo"));
  }
  
  @Test
  public void verifyAssignment3_MediumJsonFile() {
	  File file = new File("src/test/resources/medium_new_friends.json");

  	given().
  		contentType(ContentType.JSON).
  		body(file).
  	when().
  		post("http://localhost:3000/friends").
  	then().
  		assertThat().
  		statusCode(201);
  	
  	given().when()
  	.get("http://localhost:3000/friends")
  	.then()
	.assertThat().body("firstname",hasItem("Heather"))
	.assertThat().body("age",hasItem(35));
  }
  
  @Test
  public void verifyAssignment3_ComplexJsonFile() {
	  File file = new File("src/test/resources/complex_new_friends.json");

  	given().
  		contentType(ContentType.JSON).
  		body(file).
  	when().
  		post("http://localhost:3000/friends").
  	then().
  		assertThat().
  		statusCode(201);
  	
  	given().when()
  	.get("http://localhost:3000/friends")
  	.then()
	.assertThat().body("firstname",hasItem("Kale"))
	.assertThat().body("id",hasItem(1119));
  }
}
