import java.io.File;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class MainClient extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		//System.out.println("1");
		String path = new File("").getAbsoluteFile().getPath();
		System.out.println("path = " + path);
		//System.out.println("2");
		ConfigFile configFile = null;
		try {
			configFile = new ConfigFile(path);
			configFile.parse();
			System.out.println("[MC] Config File Parsed.");
		} catch (Exception e) {
			System.out.println("[MC] Config File Error.");
		}

		List<FileMetadata> new_files = null;
		FileService fileService = new FileService(configFile.getObserveDirectory());
		fileService.run();

		new_files = fileService.getNewFiles();

		FileMetadataExtractor extractor = new FileMetadataExtractor();

		// System.out.println("new files size: " + new_files.isEmpty());

		TextInputDialog dialog = new TextInputDialog("");

		for (int c = 0; c < new_files.size(); c++) {
			// System.out.println("File Name is: " + new_files.get(c));
			extractor.extractMetadata(new_files.get(c));
			// System.out.println("Author is: " +
			// new_files.get(c).getContributor());

			// PopUp to collect missing data

		}

		// Used be the old pop code here, not we file the missing data from
		// config file
		for (int c = 0; c < new_files.size(); c++) {
			FileMetadata file = new_files.get(c);
			String created = LocalDateTime.now().toString();
			String contributor = configFile.getContributor();
			String dc_description = file.getDc_description();
			String dc_subject = configFile.getDc_subject();
			String dc_references = configFile.getDc_references();
			String attachment = configFile.getAttachment();

			file.setCreated(created);
			file.setContributor(contributor);
			file.setDc_description(dc_description);
			file.setDc_subject(dc_subject);
			file.setDc_references(dc_references);
			file.setAttachment(attachment);
		}

		// Print metadata before calling rest
		for (int c = 0; c < new_files.size(); c++) {
			FileMetadata file = new_files.get(c);
			System.out.println("created: " + file.getCreated());
			System.out.println("Contributor: " + file.getContributor());
			System.out.println("DC_Description: " + file.getDc_description());
		}

		// RestCall restCall = new RestCall();
		//
		// for (int c = 0; c < new_files.size(); c++) {
		// String json = restCall.sendFile(new_files.get(c).getFile());
		// JsonReader reader = Json.createReader(new StringReader(json));
		// JsonObject jo = reader.readObject();
		// reader.close();
		// JsonValue value = jo.get("dc_identifier");
		// String uri = value.toString();
		// //System.out.println("\n" + new_files.get(c).getFile().getName() + ":
		// " + uri + "\n");
		// new_files.get(c).setDc_identifier(uri);
		//
		// }
		//
		// restCall.sendFileMetadata(new_files);

		/*
		 * while (true) {
		 * 
		 * // Get New Files new_files = fileService.getNewFiles();
		 * 
		 * for(int c = 0; c < new_files.size(); c++) { System.out.println(
		 * "File Name is: " + new_files.get(c)); }
		 */

		// Get The Missing Metadata of new Files
		/*
		 * if(new_files != null) {
		 * 
		 * Iterator i = new_files.iterator();
		 * 
		 * while(i.hasNext()) { FileMetadata file = (FileMetadata) i.next();
		 * 
		 * TextInputDialog dialog = new TextInputDialog(); dialog.setTitle(
		 * "Please Enter The Missing Information"); dialog.setHeaderText(
		 * "Please Enter The Missing Information");
		 * 
		 * if(file.getContributor() == null) { dialog.setContentText(
		 * "Please Enter Contributor"); Optional<String> result =
		 * dialog.showAndWait();
		 * 
		 * if(result.isPresent()) { file.setContributor(result.get()); }
		 * 
		 * }
		 * 
		 * if(file.getCreated() == null) { dialog.setContentText(
		 * "Please Enter The Creation Date"); Optional<String> result =
		 * dialog.showAndWait();
		 * 
		 * if(result.isPresent()) { file.setCreated(result.get()); }
		 * 
		 * }
		 * 
		 * if(file.getDc_description() == null) { dialog.setContentText(
		 * "Please Enter dc_description"); Optional<String> result =
		 * dialog.showAndWait();
		 * 
		 * if(result.isPresent()) { file.setDc_description(result.get()); }
		 * 
		 * }
		 * 
		 * 
		 * }
		 * 
		 * }
		 */

		// Send Meta of new files

		// Send new files
		// }
	}

	public static void main(String[] args) {
		launch(args);
	}

}
