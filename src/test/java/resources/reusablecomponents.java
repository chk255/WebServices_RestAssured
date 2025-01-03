package resources;

import io.restassured.path.json.JsonPath;

public class reusablecomponents {
	
	public static JsonPath rawtoJson(String response) {
		
		JsonPath js=new JsonPath(response);
		return js;
	}

}
