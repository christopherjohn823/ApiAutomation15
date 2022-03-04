package api.com.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class ReadTestData {
	
	//private final static String filePath = "Testdata//testdata.json" ;
	
	public static JSONObject getJsonData() throws IOException, org.json.simple.parser.ParseException  {
		
		//pass the path of the testdata.json file
		File filename = new File ("Resources/TestData/TestData.json");
		//convert json file into string
		String json = FileUtils.readFileToString(filename, "UTF-8");
		//parse the string into object
		Object obj = new JSONParser().parse(json);
		//jsonobject  that I can return it to the function everytime it gets called
		JSONObject jsonobject = (JSONObject) obj;
		return jsonobject;
		
	}
	
	public static String getTestData(String keyname) throws IOException, org.json.simple.parser.ParseException {
		String testdata;
		return testdata = (String) getJsonData().get(keyname); 
	}
	

}
