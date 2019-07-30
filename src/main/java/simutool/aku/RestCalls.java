package simutool.aku;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Date;

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


	public static void createJSON() {

		JsonObject jj = new JsonObject();

		JsonArray payload = new JsonArray();
		JsonObject payObj = new JsonObject();
		payObj.addProperty("title", MetadataInput.title.get());
		payObj.addProperty("description", MetadataInput.desc.get());

		payObj.addProperty("uploader", Config.getConfig().getUser_identifier());

		Date date = new Date();
		String result = (date.getYear()+1900)+"-"+String.format("%02d", (date.getMonth()+1))+"-"+String.format("%02d", date.getDate())+
				" " + String.format("%02d", date.getHours()) + ":" + String.format("%02d", date.getMinutes()) + 
				":" + String.format("%02d", date.getSeconds());
		// StdDateFormat is ISO8601 since jackson 2.9
		
		payObj.addProperty("created", result);
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

	public static String sendMetadata() {

		createJSON();

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
			// System.out.println("Result from KRM: " + sendResult);
			return sendResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InfoPopUp err = new InfoPopUp("Metadata error", "Metadata could not be sent", AlertType.ERROR);
			return null;
		}
	}

	public static JsonArray makeInheritanceQuery(String param) {
		try {
			// System.out.println(Config.getConfig().getInheritanceQueryEndpoint() + param);
			URL url = new URL(Config.getConfig().getInheritanceQueryEndpoint() + param);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// System.out.println("url: " + url);

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

			return payload;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			InfoPopUp err = new InfoPopUp("Connection error", "An error occured while connecting to the host.", AlertType.ERROR);
			return null;
		}
	}

	public static boolean registerUser(String username, String password, String host) {

		try {
			username = username.toLowerCase();
			JsonObject regRequestBody = new JsonObject();
			regRequestBody.addProperty("email", username);
			regRequestBody.addProperty("password", password);
			// System.out.println("regRequestBody: " + regRequestBody);

			URL url = new URL(Config.getConfig().getKmsHost() + Config.getConfig().getFirstLoginEndpoint());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// System.out.println("url: " + url);

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

			// System.out.println(jsonTree);

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
			// e.printStackTrace();
			InfoPopUp err = new InfoPopUp("Server error", "A problem with registration occured, please check credentials.", AlertType.ERROR);
			return false;
		}


	}

	public static void makePasswordFile(String password) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(Config.getConfig().getPasswordFile()))){
			// System.out.println(Config.getConfig().getPasswordFile());
			// System.out.println(password.replaceAll("\"", ""));
			writer.write(password);
		}catch(Exception e) {
			// e.printStackTrace();
			InfoPopUp err = new InfoPopUp("Password error", "Please check the passwordFile value in your configuration.", AlertType.ERROR);
			throw new NullPointerException();
		}
	}

	public static JsonElement getUniqueFilenameUrl(String fileName) {
		JsonElement jsonTree = null;
		try {
			URL obj = new URL(Config.getConfig().getIdGenEndpoint() + URLEncoder.encode(fileName, "UTF-8"));

			// System.out.println("obj: " + obj);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

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

			// System.out.println("jsonTree: " + jsonTree);

			String generatedId = jsonTree.getAsJsonObject().get("unique_name").toString();
			String newURL = jsonTree.getAsJsonObject().get("url").toString();
			// System.out.println("generatedId: " + generatedId);
			// System.out.println("newURL: " + newURL);

			return jsonTree;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			InfoPopUp err = new InfoPopUp("Wrong id", "Id generation failed.", AlertType.ERROR);
		}
		return jsonTree;

	}

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			Date date = new Date();
			String result = mapper.writeValueAsString(date);
			System.out.println("date: " + date);
			System.out.println("result: " + (date.getYear()+1900)+"-"+String.format("%02d", (date.getMonth()+1))+"-"+String.format("%02d", date.getDate())+
					" " + String.format("%02d", date.getHours()) + ":" + String.format("%02d", date.getMinutes()) + 
					":" + String.format("%02d", date.getSeconds()) );
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
