import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class DayFourAssignments {
	static private RequestSpecification postsRequestSpec;
	static private RequestSpecification commentsRequestSpec;
	static private RequestSpecification friendsRequestSpec;
	static private ResponseSpecification responseSpec;

	@BeforeClass
	public static void createRequestSpecification() {
		postsRequestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3000/posts").build();
		commentsRequestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3000/comments").build();
		friendsRequestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3000/friends").setContentType(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().build().statusCode(200).contentType(ContentType.JSON);
	}

	@DataProvider
	public static Object[][] postsInfo() {
		return new Object[][] { 
			{ "1", "API-Automation Day 1", "Sachin" }, 
			{ "2", "API-Automation Day 2", "Sachin F" },
			{ "3", "API-Automation Day 3", "Mahesh" }, 
			{ "4", "API-Automation Day 4", "Anirudha" } };
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
			spec(postsRequestSpec).
		when().
			get("/{id}").
		then().
			spec(responseSpec).
		and().
			assertThat().
			statusCode(200).
			body("title", equalTo(expectedTitle)).
	        body("author", equalTo(expectedAuthor));
	}
	
	@Test(dataProvider = "commentsInfo")
	public void verifyAssignment1_2(String id, String expectedComment) {
	    given().
	    	pathParam("id", id).
	    	spec(commentsRequestSpec).
		when().
			get("/{id}").
		then().
			spec(responseSpec).
		and().
			assertThat().
			body("body", equalTo(expectedComment)).
			body("postId", equalTo(Integer.parseInt(id)));
	  }
	
	@Test(priority=1)
	public void verifyAssignment2_SimpleJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("firstname", "Arif");
		jsonObject.put("lastname", "Mulla");
		jsonObject.put("id", 77);
		jsonObject.put("age", 25);
  
		given().
			spec(friendsRequestSpec).
			body(jsonObject.toString()).
		when().
			post().
		then().
			assertThat().
			statusCode(201);

		given().
			spec(friendsRequestSpec).
		when().
			get().
		then().
			spec(responseSpec).
		and().
			assertThat()
			.body("firstname", hasItem(jsonObject.get("firstname")))
			.body("lastname", hasItem(jsonObject.get("lastname")))
			.body("id", hasItem(jsonObject.get("id")))
			.body("age", hasItem(jsonObject.get("age")));
	}
	  
	@Test(priority=2)
	public void verifyAssignment2_MediumJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("firstname", "Rahul");
		jsonObject.put("lastname", "Lanjevar");
		jsonObject.put("address", new JSONObject().put("streetName", "Blue Ridge").put("houseNo", "7"));
		jsonObject.put("id", 2000);
		jsonObject.put("age", 25);

		given().
			spec(friendsRequestSpec).
			body(jsonObject.toString()).
		when().
			post().
		then().
			assertThat().
			statusCode(201);
	
		given().
			spec(friendsRequestSpec).
		when().
			get().
		then().
			spec(responseSpec).
		and().
			assertThat()
			.body("firstname", hasItem(jsonObject.get("firstname")))
			.body("lastname", hasItem(jsonObject.get("lastname")))
			.body("address.streetName", hasItem(jsonObject.getJSONObject("address").get("streetName")))
			.body("address.houseNo", hasItem(jsonObject.getJSONObject("address").get("houseNo"))).assertThat()
			.body("id", hasItem(jsonObject.get("id")))
			.body("age", hasItem(jsonObject.get("age")));
	}
	  
	@Test(priority=3)
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

		given().
			spec(friendsRequestSpec).
			body(jsonObject.toString()).
		when().
			post().
		then().
			assertThat().
			statusCode(201);
	
		given().
			spec(friendsRequestSpec).
		when().
			get().
		then().
			spec(responseSpec).
		and().
			assertThat()
			.body("firstname", hasItem(jsonObject.get("firstname")))
			.body("lastname", hasItem(jsonObject.get("lastname")))
			.body("id", hasItem(jsonObject.get("id")))
			.body("age", hasItem(jsonObject.get("age")));
	
		List streetNames = given().
								spec(friendsRequestSpec).
							when().
								get().
							then().
								extract().
								path("address.streetName");
		
		ArrayList<String> expectedList = new ArrayList<String>();
		expectedList.add("satara road");
		expectedList.add("paudh road");
		Assert.assertTrue(streetNames.get(1).equals(expectedList));		
	}

	@Test(priority=4)
	public void verifyAssignment3_SimpleJsonFile() {
		File file = new File("src/test/resources/simple_new_friends.json");

		given().
			spec(friendsRequestSpec).
			body(file).
		when().
			post().
		then().
			assertThat().
			statusCode(201);

		given().
			spec(friendsRequestSpec).
		when().
			get().
		then().
			spec(responseSpec).
		and().
			assertThat()
			.body("firstname", hasItem("Harish"))
			.body("lastname", hasItem("Palo"));
	}

	@Test(priority=5)
	public void verifyAssignment3_MediumJsonFile() {
		File file = new File("src/test/resources/medium_new_friends.json");

		given().
			spec(friendsRequestSpec).
			body(file).
		when().
			post().
		then().
			assertThat().
			statusCode(201);
	
		given().
			spec(friendsRequestSpec).
		when().
			get().
		then().
			spec(responseSpec).
		and().
			assertThat()
			.body("firstname", hasItem("Harish"))
			.body("age", hasItem(35));
	}

	@Test(priority=6)
	public void verifyAssignment3_ComplexJsonFile() {
		File file = new File("src/test/resources/complex_new_friends.json");

		given().
			spec(friendsRequestSpec).
			body(file).
		when().
			post().
		then().
			assertThat().
			statusCode(201);
	
		given().
			spec(friendsRequestSpec).
		when().
			get().
		then().
			spec(responseSpec).
		and().
			assertThat()
			.body("firstname", hasItem("Kale"))
			.body("id", hasItem(1119));
	}
}
