import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
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
		fileService.start();
		
		while (true) {

			// Get New Files
			new_files = fileService.getNewFiles();

			// Get The Missing Metadata of new Files
			if(new_files != null) {
				
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
				
			}

			// Send Meta of new files

			// Send new files
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
