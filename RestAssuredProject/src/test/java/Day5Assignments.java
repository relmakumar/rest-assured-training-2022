import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import modules.Friends;
import modules.FriendWithLombok;

import static io.restassured.RestAssured.given;


import java.util.Arrays;
import java.util.List;

public class Day5Assignments {
	private static RequestSpecification requestSpec;

	@BeforeClass
	public static void setRequestSpecification() {
		requestSpec = new RequestSpecBuilder().setBaseUri("http://localhost:3000").setContentType(ContentType.JSON)
				.build();
	}
	
	@Test
	public void verifyAddFriend() {
		Friends	newFriend = new Friends();
		newFriend.setFirstname("Sherry");
		newFriend.setAge(25);
		newFriend.setId(1199);
		newFriend.setLastname("Cobath");
		
		Response response = given().spec(requestSpec).body(newFriend).post("friends");
		Assert.assertEquals(response.statusCode(),201);
		Assert.assertEquals(response.as(Friends.class).getId(), 1199);	
		
		List<Friends> friendList = Arrays.asList(given().spec(requestSpec).get("friends").as(Friends[].class));
		for (Friends f : friendList) {
			if (f.getId() == 1199) {
				Assert.assertEquals(f.getFirstname(), "Sherry");
				Assert.assertEquals(f.getLastname(), "Cobath");
				Assert.assertEquals(f.getAge(), 25);
				break;
			}
		}
	}
	
	@Test
	public void verifyAddSameFriend() {
		FriendWithLombok newFriend = FriendWithLombok.builder().firstname("Kiara").age(33).id(1199).lastname("Beth").build();
		
		Response response = given().spec(requestSpec).body(newFriend).post("friends");
		Assert.assertEquals(response.statusCode(),500);
	}
}
