import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;

public class RestCall {

	// This will make REST Calls, and return the result

	public boolean sendFileMetadata(List<FileMetadata> new_files) {
		boolean result = false;
		for (int c = 0; c < new_files.size(); c++) {
			String krURL = "http://localhost:8085/krm/ReceiveMetadata";
			String dc_identifier = UUIDGenerator.getUUID();
			String uri = "http://uni-bamberg.de/mobi/kbms/simutool/Data/" + dc_identifier;

			// Construct Json Query for Neo4J
			String createDataQuery = "CREATE (d:Data { " + "created: \"" + new_files.get(c).getCreated() + "\", "
					+ "uri: \"" + uri + "\", " + "dc_identifier: \"" + dc_identifier + "\" }) ";

			String matchQuery = "MATCH (d:Data), (u:User) " + "WHERE d.uri = \"" + uri + "\" " + "and u.uri = \""
					+ new_files.get(c).getContributor() + "\" " + "CREATE (d)-[:dc_contributor]->(u)";

			String query = "json=" + createDataQuery + matchQuery;

			//System.out.println("json is: " + query);

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
				while(line != null) {
					sendResult = sendResult + line;
					line = in.readLine();
				}
				System.out.println("Result from KRM: " + sendResult);
				//result = Boolean.getBoolean(sendResult);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

}
