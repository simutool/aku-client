package simutool.aku;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class MetadataSender {

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
		payObj.addProperty("title", "test title");
		payObj.addProperty("description", "dummy description");

		payObj.addProperty("uploader", Config.getConfig().getContributor());
		payObj.addProperty("created", new Date().toString());
		payObj.addProperty("url", FileService.getGeneratedURL());

		payload.add(payObj);
		jj.add("payload", payload);
		metaJson = jj;
	}

	private static String sendMetadata() {
		try {
			URL url = new URL(Config.getConfig().getKgHost() + Config.getConfig().getKgPort() + Config.getConfig().getKgURL());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			System.out.println("url: " + url);

			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json" );
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
			return null;
		}
	}

	private static void fetchRelations() {
		try {
			URL url = new URL(Config.getConfig().getKgHost() + Config.getConfig().getKgPort() + Config.getConfig().getInheritanceQuery());
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

			System.out.println("fetchRelations: " + jsonTree);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		fetchRelations();
	}

}
