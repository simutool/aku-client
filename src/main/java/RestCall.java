import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class RestCall {

	// This will make REST Calls, and return the result

	public boolean sendFileMetadata(List<FileMetadata> new_files) {
		boolean result = false;
		for (int c = 0; c < new_files.size(); c++) {
			String krURL = "http://localhost:8085/krm/ReceiveMetadata";
			String dc_identifier = UUIDGenerator.getUUID();
			String uri = "http://uni-bamberg.de/mobi/kbms/simutool/Data/" + dc_identifier;

			// Construct Json Query for Neo4J
			String matchQuery = "MATCH (d:Data), (u:User) " + "WHERE d.uri = '" + uri + "' " + "and u.uri = '"
					+ new_files.get(c).getContributor() + "' " + " CREATE (d)-[:dc_contributor]->(u) ";

			// System.out.println("json is: " + query);

			JsonObjectBuilder json = Json.createObjectBuilder();

			JsonArrayBuilder statements = Json.createArrayBuilder();

			JsonObjectBuilder statement01 = Json.createObjectBuilder();
			JsonObjectBuilder statement02 = Json.createObjectBuilder();

			JsonObjectBuilder props_outer = Json.createObjectBuilder();
			JsonObjectBuilder props = Json.createObjectBuilder();

			props.add("created", new_files.get(c).getCreated());
			props.add("uri", uri);
			props.add("dc_identifier", dc_identifier);

			statement01.add("statement", "CREATE (d:Data {props} )");
			props_outer.add("props", props);
			statement01.add("parameters", props_outer);
			statement02.add("statement", matchQuery);

			statements.add(statement01);
			statements.add(statement02);

			json.add("statements", statements);

			String query = "json=" + json.build().toString();

			try {
				URL url = new URL(krURL);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();

				con.setRequestMethod("POST");
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(query);
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

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public void sendFile(File file) {

		HttpClient httpclient = HttpClientBuilder.create().build();

		HttpPost post = new HttpPost("http://localhost:8085/krm/ReceiveFile");
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addPart("fileName", new FileBody(file));
		post.setEntity(builder.build());

		HttpResponse response = null;
		try {
			response = httpclient.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String responseJSON = "";
		try {
			responseJSON = EntityUtils.toString(response.getEntity());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.print("Result of file: " + responseJSON);
	}

}
