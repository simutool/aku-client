package simutool.aku;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class ConfirmSync{

	public ConfirmSync(Path newFile, String type) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Syncronize now?");

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);


		alert.setContentText("Do you want to synchronize "+ type +" \"" + newFile.getFileName() + "\"?");;

		alert.showAndWait();
		System.out.println("alert.getResult(): " + alert.getResult());

		if (alert.getResult() == ButtonType.OK) {
			MetadataInput inp = new MetadataInput(newFile);
			//FileService.syncFile(Paths.get( Config.getConfig().getObserveDirectory() + newFile) );
		}
	}





}
