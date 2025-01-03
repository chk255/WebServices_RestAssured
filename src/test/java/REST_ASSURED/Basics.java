package REST_ASSURED;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import resources.payload;
import resources.reusablecomponents;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class Basics {
	
	public JsonPath js;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// given-->All input header,body,authorization,query parameter
		// when-->resources
		// then-->validation
		//Test Case;
		// Add Place API-->Update Place with new Address-->Get Place API to validate if
				// new address is present in response.

		RestAssured.baseURI = "https://rahulshettyacademy.com/";

		// Add Place API
		String AddPlaceresponse = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(payload.addplace()).when()
				.post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Server", equalTo("Apache/2.4.52 (Ubuntu)")).extract().response().asString();

		System.out.println(AddPlaceresponse);
		

		JsonPath js = reusablecomponents.rawtoJson(AddPlaceresponse);;// for parsing Json
		String placeID = js.getString("place_id");
		System.out.println(placeID);

		// Update Place API
		String NewAddress = "Panchsheel Hynish Gaya, India";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeID + "\",\r\n" + "\"address\":\"" + NewAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));
		// get Place API
		String GetPlaceresponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
				.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();

		JsonPath js1=reusablecomponents.rawtoJson(GetPlaceresponse);
		String ActualAddress = js1.getString("address");
		System.out.println(ActualAddress);
		Assert.assertEquals(NewAddress, ActualAddress);

	}

}
