package com.assurityapi.AssurityApiTesting;
import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
public class myAssurityApiTestingClass
{
	
	static String url="https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false";
	
	@BeforeTest
	public void setUp()
	{
		RestAssured.useRelaxedHTTPSValidation();
	}
	
	
	@Test(priority = 0, alwaysRun = true)
	public static void getResponseBody(){
		System.out.println("=========================================================================================");
		System.out.println("                                 RESPONSE BODY                                           ");
		given().when().get(url).then().log().all();
		System.out.println("=========================================================================================");
		}
		
	@Test(priority = 1, alwaysRun = true)
	public static void getResponseStatus(){
		   int statusCode= given().when().get(url).getStatusCode();
		   System.out.println("=========================================================================================");
		   System.out.println("                                 STATUS CODE                                             ");
		   System.out.println("The response status is "+statusCode);
		   System.out.println("=========================================================================================");
		   
		   String statusLine=given().when().get(url).getStatusLine();
		   System.out.println("=========================================================================================");
		   System.out.println("                                 STATUS LINE                                             ");
		   System.out.println("The response status is "+statusLine);
		   System.out.println("=========================================================================================");
		   
		   
		   given().when().get(url).then().log().ifError().assertThat().statusCode(200).statusLine("HTTP/1.1 200 OK");
	
	}
	@Test(priority = 2, alwaysRun = true)
	public void whenLogResponseIfErrorOccurred_thenSuccess() {
	 
		 given().when().get(url)
	      .then().log().ifError();
		 given().when().get(url)
	      .then().log().ifStatusCodeIsEqualTo(500);
	}
	
	@Test(priority = 3, alwaysRun = true)
	public static void getResponseHeaders(){
		System.out.println("=========================================================================================");
		System.out.println("                                 RESPONSE HEADER                                         ");
		System.out.println("The headers in the response "+
		given().when().get(url).then().extract().headers());
		System.out.println("=========================================================================================");
		}
	
	@Test(priority = 4, alwaysRun = true)
	public static void getResponseContentType(){
		System.out.println("=========================================================================================");
		System.out.println("                                 CONTENT TYPE                                            ");
	    System.out.println("The content type of response "+given().when().get(url).then().extract().contentType());
		System.out.println("=========================================================================================");
		}
	
	
	@Test(priority = 5, alwaysRun = true)
	public static void verifyName()
	{
		Response response=given().when().get(url);
		JsonPath jsonPathEvaluator = response.jsonPath();
		String Name = jsonPathEvaluator.get("Name");
		System.out.println("=========================================================================================");
		System.out.println("                            ACCEPTANCE CRITERIA (1) NAME                                 ");
		System.out.println("Name received from Response " + Name);
		System.out.println("=========================================================================================");
		 
		Assert.assertEquals(Name, "Carbon credits", "Correct name received in the Response");
	}
	
	@Test(priority = 6, alwaysRun = true)
	public static void verifyCanRelist()
	{
		Response response=given().when().get(url);
		JsonPath jsonPathEvaluator = response.jsonPath();
		Boolean CanRelist = jsonPathEvaluator.get("CanRelist");
		System.out.println("=========================================================================================");
		System.out.println("                            ACCEPTANCE CRITERIA (2) CANRELIST                            ");
		System.out.println("CanRelist received from Response " + CanRelist);
		System.out.println("=========================================================================================");
		
		Assert.assertTrue(CanRelist, "Correct CanRelist received in the Response");
	}
	
	@Test(priority = 7, alwaysRun = true)
	public static void verifyPromotionsDescriptions()
	{
		Response response=given().when().get(url);
		JsonPath jsonPathEvaluator = new JsonPath(response.asString());
		int s=jsonPathEvaluator.getInt("Promotions.size()");
		for(int i = 0; i < s; i++) {
	         String Name = jsonPathEvaluator.getString("Promotions["+i+"].Name");
	         String Description = jsonPathEvaluator.getString("Promotions["+i+"].Description");
	         if (Name.equals("Gallery"))
	         {
	         System.out.println("=========================================================================================");
	         System.out.println("                  ACCEPTANCE CRITERIA (3) NAME & DESCRIPTION                             ");
	         System.out.println("Gallery found at index:" +i);
	         System.out.println("Name received from Response "+ Name);
	         System.out.println("Description received from Response "+ Description);
	         System.out.println("=========================================================================================");
	         Assert.assertEquals(Description, "Good position in category", "Correct Description received in the Response");
		
	         }
	}
}
	
	@AfterTest()
	public void terminateTesting(){
		System.out.println("=========================================================================================");
		System.out.println("                               END OF API TESTING                                        ");
		System.out.println("=========================================================================================");
	}
}