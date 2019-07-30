package simutool.aku;

import java.io.File;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.Alert.AlertType;

public class RegisterPopup{

	public SimpleStringProperty username = new SimpleStringProperty(Config.getConfig().getKmsEmail());
	public SimpleStringProperty password = new SimpleStringProperty(Config.getConfig().getKmsPassword());
	public SimpleStringProperty host = new SimpleStringProperty(Config.getConfig().getKmsHost());
	public Stage stage;

	public RegisterPopup() {

		stage = new Stage();

		GridPane grid = new GridPane();

		Text scenetitle = new Text("Please enter your data");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);


		Label userName = new Label("Email:");
		grid.add(userName, 0, 2);
		TextField userTextField = new TextField();
		userTextField.setMinWidth(400);
		grid.add(userTextField, 1, 2);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 3);
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 3);

		Label kmsHostLabel = new Label("Host:");
		grid.add(kmsHostLabel, 0, 4);

		TextField kmsHostBox = new TextField();
		kmsHostBox.setPromptText("e.g. http://141.13.162.157:9000");
		grid.add(kmsHostBox, 1, 4);

		Label dirLabel = new Label("Observe\r\ndirectory:");
		grid.add(dirLabel, 0, 5);

		String buttonLabel = (Config.getConfig().getObserveDirectory()!=null&&Config.getConfig().getObserveDirectory().length()>0) ? Config.getConfig().getObserveDirectory() : "Choose";
		Button dirButton = new Button(buttonLabel);
		dirButton.setMinWidth(400);
		grid.add(dirButton, 1, 5);

		final DirectoryChooser directoryChooser = new DirectoryChooser();

		dirButton.setOnAction(new EventHandler<ActionEvent>() { 
			@Override
			public void handle(ActionEvent event) {
				File dir = directoryChooser.showDialog(stage);
				if (dir != null) {
					String result = dir.getAbsolutePath();
					dirButton.setText(result);
					Config.getConfig().setObserveDirectory(result);
				} else {
					dirButton.setText("Choose");
				}
			}
		});

		Button okBtn = new Button("OK");
		grid.setHalignment(okBtn, HPos.CENTER);
		okBtn.setMinWidth(100);
		okBtn.setTranslateX(-50);
		grid.add(okBtn, 1, 10);

		Bindings.bindBidirectional(userTextField.textProperty(), username);
		Bindings.bindBidirectional(pwBox.textProperty(), password);
		Bindings.bindBidirectional(kmsHostBox.textProperty(), host);

		okBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				System.out.println("new username: " + username.get());
				System.out.println("new password: " + password.get());
			
				System.out.println("dir: " + Config.getConfig().getObserveDirectory());


				try {
					File dir = new File(Config.getConfig().getObserveDirectory());
					if(!dir.exists()) {
						InfoPopUp err = new InfoPopUp("No directory selected","Please choose a directory you want to observe", AlertType.ERROR);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					// e1.printStackTrace();
					InfoPopUp err = new InfoPopUp("No directory selected","Please choose a directory you want to observe", AlertType.ERROR);
				}

				Config.getConfig().setKmsEmail(username.get());
				Config.getConfig().setKmsPassword(password.get());
				Config.getConfig().setKmsHost(host.get());
				boolean registrationSuccess = RestCalls.registerUser(username.get(), password.get(), host.get());
				if (registrationSuccess) {
					//System.out.println("Watcher launched");
					stage.hide();
					stage.close();
					//System.out.println("Watcher.started: " + Watcher.started);
					if(Watcher.started != null){
						Watcher.killWatcher();
					}
					Watcher w = Watcher.getWatcher();
				} else {
					stage.hide();
					stage.close();
				} 

			}
		});

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(15, 5, 5, 15));
		Scene scene = new Scene(grid, 500, 350);
		stage.setScene(scene);		
		stage.setScene(scene);
		stage.showAndWait();
	}

}
