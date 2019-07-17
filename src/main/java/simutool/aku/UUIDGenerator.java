package simutool.aku;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class UUIDGenerator {
	
	private static final Logger LOGGER = Logger.getLogger( UUIDGenerator.class.getName() );
	

	public static JsonElement getUUID(String fileName) {
		JsonElement jsonTree = null;
		try {
				URL obj = new URL(Config.getConfig().getKgHost() + Config.getConfig().getKgPort() + Config.getConfig().getIdGenURL() + URLEncoder.encode(fileName, "UTF-8"));
	
				System.out.println("obj: " + obj);
				
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
				// optional default is GET
				con.setRequestMethod("GET");
		
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				JsonParser parser = new JsonParser();
				jsonTree = parser.parse(response.toString());
				
				System.out.println("jsonTree: " + jsonTree);

				String generatedId = jsonTree.getAsJsonObject().get("unique_name").toString();
				String newURL = jsonTree.getAsJsonObject().get("url").toString();
				System.out.println("generatedId: " + generatedId);
				System.out.println("newURL: " + newURL);
				
				return jsonTree;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonTree;
		
	}
	
}
