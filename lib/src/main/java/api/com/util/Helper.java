package api.com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.json.simple.parser.ParseException;

public class Helper {

	public static final String commonFilePath= System.getProperty("user.dir")+ "/common.properties";           
	public static String propertyReader(String key) {
		
		String value = null;
		//InputStream is required while loading into properties
		try (InputStream input = new FileInputStream(commonFilePath)) {
			
			Properties prop = new Properties();
			// load a properties file
			prop.load(input);
			
			//getProperty will fetch the value according to the key
			value=prop.getProperty(key);
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
		return value;
		
	}
	// function to get the the status code for GET users by getting end-point from testdata.json file and server address from properties reader file
	public static String getUri() throws IOException, ParseException {
		String uri = Helper.propertyReader("qaBaseUrl")+ ReadTestData.getTestData("endpointGetUsers");
        return uri;	
	}
	/*
	 * create folder
	 */
	public static void CreateFolder(String path) {
		//File is a class inside java.io package
		File file = new File(path);
		if(!file.exists()) {
			file.mkdir(); // mkdir is used to create a folder			
		} else
			System.out.println("Folder already created");
	}
	
	/*
	 * Return current timestamp
	 */
	public static String Timestamp() {
		
		Date now = new Date();
		String Timestamp = now.toString().replace(":", "-");
		return Timestamp;
	}
	
}
