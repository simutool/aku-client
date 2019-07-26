package simutool.aku;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import javafx.scene.control.Alert.AlertType;


public class RestCalls {

	private static JsonObject metaJson = null;

	public static void sendJSON() {
		createJSON();
		System.out.println(metaJson.toString());
		sendMetadata();
	}


	public static void createJSON() {

		JsonObject jj = new JsonObject();
		
		JsonArray payload = new JsonArray();
		JsonObject payObj = new JsonObject();
		payObj.addProperty("title", MetadataInput.title.get());
		payObj.addProperty("description", MetadataInput.desc.get());

		payObj.addProperty("uploader", Config.getConfig().getUser_identifier());
		
		String date = new Date().toString();
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    // StdDateFormat is ISO8601 since jackson 2.9
	    mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
		try {
			date = mapper.writeValueAsString(date);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		payObj.addProperty("created", date);
		payObj.addProperty("url", FileService.getGeneratedURL().replaceAll("\"", ""));
		
		if(MetadataInput.activity != null && MetadataInput.activity.length()>0) {
			payObj.addProperty("activity", MetadataInput.activity);			
		}
		
		if(MetadataInput.chosenRelations != null && MetadataInput.chosenRelations.size()>0) {
			JsonArray relations = new JsonArray();
			for(String s : MetadataInput.chosenRelations) {
				relations.add(new JsonPrimitive(s));
			}
			payObj.add("relation", relations);			
		}

		payload.add(payObj);
		jj.add("payload", payload);
		metaJson = jj;
	}

	private static String sendMetadata() {
		try {
			URL url = new URL(Config.getConfig().getDocumentEndpoint());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			System.out.println("url: " + url);
			String encoding = Base64.getEncoder().encodeToString((Config.getConfig().getKmsEmail() + ":" + Config.getConfig().getKmsPassword()).getBytes("UTF-8"));

			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json" );
			con.setRequestProperty  ("Authorization", "Basic " + encoding);

			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(metaJson.toString());
			wr.flush();
			wr.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String sendResult = "";
			String line = in.readLine();
			while (line != null) {
				sendResult = sendResult + line;
				line = in.readLine();
			}
			System.out.println("Result from KRM: " + sendResult);
			// result = Boolean.getBoolean(sendResult);
			return sendResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InfoPopUp err = new InfoPopUp("Metadata error", "Metadata could not be sent", AlertType.ERROR);
			return null;
		}
	}

	public static JsonArray fetchRelations(String param) {
		try {
			System.out.println(Config.getConfig().getInheritanceQueryEndpoint() + param);
			URL url = new URL(Config.getConfig().getInheritanceQueryEndpoint() + param);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			System.out.println("url: " + url);

			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			JsonElement jsonTree = null;
			JsonParser parser = new JsonParser();
			jsonTree = parser.parse(response.toString());

			JsonArray payload = jsonTree.getAsJsonObject().get("payload").getAsJsonArray();

			System.out.println("fetchRelations: " + jsonTree);
			System.out.println("payload: " + payload.getAsJsonArray());

			return payload;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InfoPopUp err = new InfoPopUp("Connection error", "An error occured while connecting to the host.", AlertType.ERROR);
			return null;
		}
	}

	public static boolean register(String username, String password, String host) {


		try {
			username = username.toLowerCase();
			JsonObject regRequestBody = new JsonObject();
			regRequestBody.addProperty("email", username);
			regRequestBody.addProperty("password", password);
			System.out.println("regRequestBody: " + regRequestBody);

			URL url = new URL(Config.getConfig().getKmsHost() + Config.getConfig().getFirstLoginEndpoint());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			System.out.println("url: " + url);

			String encoding = Base64.getEncoder().encodeToString((username + ":" + password).getBytes("UTF-8"));

			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json" );
			con.setRequestProperty("Accept","*/*");
			con.setRequestProperty  ("Authorization", "Basic " + encoding);

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(regRequestBody.toString());
			wr.flush();
			wr.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String sendResult = "";
			String line = in.readLine();
			while (line != null) {
				sendResult = sendResult + line;
				line = in.readLine();
			}
			JsonElement jsonTree = null;
			JsonParser parser = new JsonParser();
			jsonTree = parser.parse(sendResult.toString());
			//				jsonTree = parser.parse("{'object_storage_host': '141.13.162.157:12002/files/', " +
			//						"'object_storage_username': 'vvoronova', " +
			//						"'object_storage_password': 'somepassword', " +
			//						"'user_identifier': 'http://uni-bamberg.de/mobi/kbms/simutool/User/18779w', " +
			//						"'document_endpoint': 'http://141.13.162.157:9000/simutool_kms/kg/api?type=document', " +
			//						"'id_gen_endpoint': 'http://141.13.162.157:9000/simutool_kms/data/api/unique-name?fn=', " +
			//						"'inheritance_query_endpoint': 'http://141.13.162.157:9000/simutool_kms/kg/api?type=KBMSThing', " + 
			//						"'create_activity_endpoint': 'http://141.13.162.157:9000/simutool_kms/manage/factory?class=Activity&Action=new'}");

			System.out.println(jsonTree);

			// Refresh values
			Config.getConfig().setObject_storage_host(jsonTree.getAsJsonObject().get("object_storage_host").toString().replaceAll("\"", ""));
			Config.getConfig().setObject_storage_username(jsonTree.getAsJsonObject().get("object_storage_username").toString().replaceAll("\"", ""));
			Config.getConfig().setObject_storage_host(jsonTree.getAsJsonObject().get("object_storage_host").toString().replaceAll("\"", ""));

			makePasswordFile(jsonTree.getAsJsonObject().get("object_storage_password").toString().replaceAll("\"", ""));

			Config.getConfig().setUser_identifier(jsonTree.getAsJsonObject().get("user_identifier").toString().replaceAll("\"", ""));

			Config.getConfig().setDocumentEndpoint(jsonTree.getAsJsonObject().get("document_endpoint").toString().replaceAll("\"", ""));
			Config.getConfig().setIdGenEndpoint(jsonTree.getAsJsonObject().get("id_gen_endpoint").toString().replaceAll("\"", ""));
			Config.getConfig().setInheritanceQueryEndpoint(jsonTree.getAsJsonObject().get("inheritance_query_endpoint").toString().replaceAll("\"", ""));
			Config.getConfig().setCreateActivityEndpoint(jsonTree.getAsJsonObject().get("create_activity_endpoint").toString().replaceAll("\"", ""));

			Config.getConfig().generateYaml();
			Config.getConfig().updateConfig();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			InfoPopUp err = new InfoPopUp("Server error", "A problem with registration occured, please check credentials.", AlertType.ERROR);
			return false;
		}


	}

	public static void makePasswordFile(String password) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(Config.getConfig().getPasswordFile()))){
			System.out.println(Config.getConfig().getPasswordFile());
			System.out.println(password.replaceAll("\"", ""));
			writer.write(password);
		}catch(Exception e) {
			e.printStackTrace();
			InfoPopUp err = new InfoPopUp("Password error", "Please check the passwordFile value in your configuration.", AlertType.ERROR);
			throw new NullPointerException();
		}
	}

	public static void main(String[] args) {
		//RestCalls r = new RestCalls();
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    // StdDateFormat is ISO8601 since jackson 2.9
	    mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
	    try {
	    	Date date = new Date();
			String result = mapper.writeValueAsString(date);
			System.out.println("date: " + date);
			System.out.println("result: " + result);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
