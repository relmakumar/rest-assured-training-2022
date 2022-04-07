package modules;

import java.util.Map;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import utilities.RestAssuredEngine;

public class BooksHelper {
	
	public ResponseOptions<Response> getBooksDetails(String methodName, String serviceEndpoint, Map<String,String> headerMap, Map<String,String> queryParamMap, Map<String, Object> pathParamMap){
		RestAssuredEngine restAssuredEngine = new RestAssuredEngine("bWFoZXNoLmJoYW5kaWdhcmU6bWFoZXNoMTIz");
//		serviceEndpoint = "https://bookstore.toolsqa.com/BookStore/v1/Book";
		return restAssuredEngine.executeMethod(methodName, serviceEndpoint, headerMap, queryParamMap, pathParamMap);
	}
	
}
