import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import modules.BooksHelper;
//import modules.CountryHelper;
import utilities.ExcelUtility;

public class Day4FrameworkTests {
	BooksHelper bookHelper = new BooksHelper();
	SoftAssert softAssert = new SoftAssert();
	
	@Test(dataProvider = "getApiEndPointData", dataProviderClass = ExcelUtility.class,testName="verifyExcelDataProvider_BookTest")
	public void verifyExcelDataProvider_BookTest(String methodName, String serviceEndpoint, Map<String,String> headerMap, Map<String,String> queryParamMap,Map<String,Object> pathParamMap,int statusCode,String responseMessage) {
		ResponseOptions<Response> response = bookHelper.getBooksDetails(methodName, serviceEndpoint, headerMap, queryParamMap, pathParamMap);
		softAssert.assertTrue(response.statusCode() == 200, "Status code failed");
		softAssert.assertEquals(response.body().jsonPath().get("publisher").toString().trim(), responseMessage.trim(),"Publisher not matched");
		softAssert.assertAll();
	}
}
