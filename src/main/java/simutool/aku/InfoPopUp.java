package simutool.aku;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class InfoPopUp{

	public InfoPopUp(String shortDesc, String longDesc, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle("Error");

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);

		alert.setHeaderText(shortDesc);
		alert.setContentText(longDesc);

		alert.showAndWait();
	}
}
