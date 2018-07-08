import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class MainClient extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		ConfigFile configFile = new ConfigFile();
		configFile.parse();

		List<FileMetadata> new_files = null;
		FileService fileService = new FileService(configFile.getObserveDirectory());
		fileService.run();


		// Task<Void> sleeper = new Task<Void>() {
		// @Override
		// protected Void call() throws Exception {
		// try {
		// Thread.sleep(10000);
		// } catch (InterruptedException e) {
		// }
		// return null;
		// }
		// };
		
		new_files = fileService.getNewFiles();
		
		FileMetadataExtractor extractor = new FileMetadataExtractor();
		
		//System.out.println("new files size: " + new_files.isEmpty());
		
		for(int c = 0; c < new_files.size(); c++) {
			//System.out.println("File Name is: " + new_files.get(c));
			extractor.extractMetadata(new_files.get(c));
			//System.out.println("Author is: " + new_files.get(c).getContributor());
			
		}
		
		RestCall restCall = new RestCall();
		
		restCall.sendFileMetadata(new_files);
		
		/*while (true) {

			// Get New Files
			new_files = fileService.getNewFiles();
			
			for(int c = 0; c < new_files.size(); c++) {
				System.out.println("File Name is: " + new_files.get(c));
			}*/

			// Get The Missing Metadata of new Files
			/*if(new_files != null) {
				
				Iterator i = new_files.iterator();
				
				while(i.hasNext()) {
					FileMetadata file = (FileMetadata) i.next();
					
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("Please Enter The Missing Information");
					dialog.setHeaderText("Please Enter The Missing Information");
					
					if(file.getContributor() == null) {
						dialog.setContentText("Please Enter Contributor");
						Optional<String> result = dialog.showAndWait();
						
						if(result.isPresent()) {
							file.setContributor(result.get());
						}
						
					}
					
					if(file.getCreated() == null) {
						dialog.setContentText("Please Enter The Creation Date");
						Optional<String> result = dialog.showAndWait();
						
						if(result.isPresent()) {
							file.setCreated(result.get());
						}
						
					}
					
					if(file.getDc_description() == null) {
						dialog.setContentText("Please Enter dc_description");
						Optional<String> result = dialog.showAndWait();
						
						if(result.isPresent()) {
							file.setDc_description(result.get());
						}
						
					}
					
					
				}
				
			}*/

			// Send Meta of new files

			// Send new files
		//}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
