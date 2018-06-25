import java.util.Optional;

import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class PopUp extends Application {
	
	private String header;
	private String userInput;

	public PopUp(String header) {
		this.header = header;
	}
	
	public String getResult() {
		userInput = null;
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Please Enter The Missing Information");
		dialog.setHeaderText("Please Enter The Missing Information");
		dialog.setContentText(header);
		
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			userInput = result.get();
		}
		
		return userInput;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
