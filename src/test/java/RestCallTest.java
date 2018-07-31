import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RestCallTest {

	public static void main(String[] args) {
		
		//testRestCall();
		//testReach();
		testFileCall();

	}
	
	private static void testFileCall() {
		//File file = new File("C:\\Users\\Harshit Gupta\\workspace\\PrePoorClient\\testfiles\\Hello.txt");
		File file = new File("C:\\Users\\z003x6ke\\eclipse-workspace\\PrePoorClient\\testfiles\\Helloed.pdf");
		RestCall rc = new RestCall();
		String result= rc.sendFile(file);
		System.out.println(result);
	}
	
	
	private static void testRestCall() {
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setContributor("test author");
		fileMetadata.setCreated("test date");
		
		List<FileMetadata> files = new ArrayList<FileMetadata>();
		files.add(fileMetadata);
		
		RestCall restCall = new RestCall();
		restCall.sendFileMetadata(files);
	}

	public static void testReach() {

		// Just testing if KRM is reachable
		String krURL = "http://141.13.162.157:8080/krm/";
		try {
			URL url = new URL(krURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			System.out.println("1");
			con.setRequestMethod("GET");
			// con.setDoOutput(true);
			// DataOutputStream wr = new
			// DataOutputStream(con.getOutputStream());
			// wr.writeBytes(query);
			// wr.flush();
			// wr.close();
			System.out.println("2");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer sendResult = new StringBuffer();
			System.out.println("3");
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println("xx " + line);
				sendResult.append(line);
			}
			System.out.println("6");
			System.out.println("Result from KRM: " + sendResult.toString());
			// result = Boolean.getBoolean(sendResult);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
