package simutool.aku;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class MetadataSender {
	
	private static JsonObject metaJson = null;
	
	public static void sendJSON() {
		createJSON();
		System.out.println(metaJson.toString());
		makeRestCall();
	}
	
	
	public static void createJSON() {
		
		JsonObject jj = new JsonObject();
		JsonArray payload = new JsonArray();
			JsonObject payObj = new JsonObject();
			payObj.addProperty("title", "test title");
			payObj.addProperty("uploader", Config.getConfig().getContributor());

			payObj.addProperty("description", "dummy description");
			payObj.addProperty("created", new Date().toString());
			payObj.addProperty("url", "http://141.13.162.157:9000/simutool_kms/default/download/" + FileService.getGeneratedId());

			payload.add(payObj);
			jj.add("payload", payload);
			metaJson = jj;
	}
	
	private static String makeRestCall() {
		try {
			URL url = new URL(Config.getConfig().getKgURL());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

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

}
