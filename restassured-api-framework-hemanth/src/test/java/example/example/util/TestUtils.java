package example.example.util;

import org.json.JSONObject;

public class TestUtils {

	public static boolean jsonHasKey(String json, String key)// id
	{
		JSONObject jsonObject= new JSONObject(json);
		boolean flag=jsonObject.has(key);
		return flag;

		//return json.contains(key);


	}

}
