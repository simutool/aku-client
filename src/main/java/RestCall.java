import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;

public class RestCall {

	// This will make REST Calls, and return the result

	public boolean sendFileMetadata(String created, String userURI) {
		boolean result = false;
		String krURL = "";
		String dc_identifier = UUIDGenerator.getUUID();
		String uri = "http://uni-bamberg.de/mobi/kbms/simutool/Data/" + dc_identifier;

		// Construct Json Query for Neo4J
		String createDataQuery = "CREATE (d:Data { " + "created: \"" + created + "\", " + "uri: \"" + uri + "\", "
				+ "dc_identifier: \"" + dc_identifier + "\" }) ";

		String matchQuery = "MATCH (d:Data), (u:User) " + "WHERE d.uri = \"" + uri + "\" " + "and u.uri = \"" + userURI
				+ "\" " + "CREATE (d)-[:dc_contributor]->(u)";

		String query = createDataQuery + matchQuery;

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
			String sendResult = in.readLine();
			result = Boolean.getBoolean(sendResult);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		return result;
	}

}
