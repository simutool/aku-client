package simutool.aku;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class RestCall {
	
	public String getRenamedFiles(String user, String id) {
		String result = null;
		try {
			URL url = new URL("http://141.13.162.157:12001/" + user + "/" );

		//	URL url = new URL("http://141.13.162.157:12001/" + user + "/" );
		//	System.out.println("restcall url: " + url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "text/plain; UTF-8");
			con.setRequestProperty("Accept", "application/json");
			if (id != null) {
				con.setRequestProperty("Content-Length", Integer.toString(id.length()));
				OutputStream wr = new BufferedOutputStream(con.getOutputStream());
				wr.write(id.getBytes("UTF-8"));
				wr.flush();
				wr.close();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String sendResult = "";
				String line = in.readLine();
				while (line != null) {
				//	System.out.println("line: " + line);
					sendResult = sendResult + line;
					line = in.readLine();
				}
				result = sendResult;
			}
			
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
		return result;
		
	}

}
