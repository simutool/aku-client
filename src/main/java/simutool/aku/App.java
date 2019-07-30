package simutool.aku;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App  extends Application {
	

	public static Stage primaryStage;

	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;

		stage.setAlwaysOnTop(true);
		setup(true);
	}


	public static void setup(boolean newWatcher) {
		try {
			if(newWatcher) {
			Handler fh = new FileHandler("aku.log");
			Config.LOGGER.addHandler(fh);
			}
			
			Config.updateConfig();
			Config c = Config.getConfig();
			if(!c.isValid(c.getFirstLoginEndpoint())) {
			    Config.LOGGER.log( Level.SEVERE, "malformed config" );
				InfoPopUp e = new InfoPopUp("Malformed configuration","Value firstLoginEndpoint is missing or malformed. Please fix it manually in file config.yaml.", AlertType.ERROR);
			}else if(!c.isValid(c.getUser_identifier()) || !c.isValid(c.getObject_storage_host()) || !c.isValid(c.getPasswordFile()) || 
					!c.isValid(c.getObject_storage_username()) || !c.isValid(c.getDocumentEndpoint()) || !c.isValid(c.getIdGenEndpoint()) ||
					!c.isValid(c.getInheritanceQueryEndpoint()) || !c.isValid(c.getObserveDirectory()) || !c.isValid(c.getCreateActivityEndpoint())) {
				RegisterPopup reg = new RegisterPopup();
			}else if(newWatcher){
				Watcher w = Watcher.getWatcher();
			}
		} catch (Exception e) {
		    Config.LOGGER.log( Level.SEVERE, e.toString(), e );

		}
	}

	public static void main(String[] args) {
		launch(args);
			
	}
}


