import java.util.Iterator;
import java.util.List;

import javafx.application.Application;

public class PopUpTester {
	
	public static void main(String[] args) {
		//PopUp popup = new PopUp("Whats Your name");
		//popup.start(arg0);
		String arguments[] = new String[5];
		arguments[0] = "What's your name?";
		Application.launch(PopUp.class, arguments);
		// List<String> arr = PopUp.getArguments();
		// Iterator<String> i = arr.iterator();
		// System.out.println(i.next());
	}

}
