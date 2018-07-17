import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;

public class PopUp extends Application {
	
	private static List<String> arguments;
	
	public static List<String> getArguments() {
		return arguments;
	}

	
	public String getResult(Stage stage) {
		arguments = this.getParameters().getRaw();
		String header = "";
		Iterator<String> i = arguments.iterator();
		while(i.hasNext()) {
			header = i.next();
		}
		//arguments.clear();
		
		String userInput = null;
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Please Enter The Missing Information");
		dialog.setHeaderText("Please Enter The Missing Information");
		dialog.setContentText(header);
		
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			userInput = result.get();
		}
		arguments.add(userInput);
		stage.hide();
		stage.close();
		return userInput;
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		VBox root = new VBox();
		Scene scene = new Scene(root, 450, 250);
		stage.setScene(scene);
		stage.show();
		getResult(stage);
			
		
	}
	
	public static void main(String[] args) {
		String aa[] = new String[3];
		aa[0] = "Hello";
		launch(aa);
	}
	
}
