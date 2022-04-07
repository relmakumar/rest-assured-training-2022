package utilities;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class AuthenticationToken {

	public String getAuthenticationToken() {
		File file = new File("./src/test/resources/auth.json");
		RequestSpecBuilder builder = new RequestSpecBuilder(); 
		String validRequest = "{\n" +
	            "  \"userName\": \"smita.shewale\",\n" +
	            "  \"password\": \"Smita@123\" \n}";
//		Map<String, String> payload = new HashMap<String, String>();
//		payload.put("username", "mahesh");
//		payload.put("password", "mahesh");
		//builder.setBody(validRequest);
		
		//builder.setBody(file);
		RequestSpecification requestSpecification = builder.build();
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.spec(requestSpecification);
        request.body(file);
        String token = request.post("https://bookstore.toolsqa.com/Account/v1/Authorized").getBody().asString();
        System.out.println("token"+token);
        return token;
	}
}
