import java.util.List;

public class DatabaseInteractorTest {
	
	public static void main(String[] args) {
		
		DatabaseInteractor database = new DatabaseInteractor(); 
		
		List<String> files = database.readFiles();
		
		for(int c = 0; c < files.size(); c++) {
			System.out.println("File in Database: " + files.get(c));
		}
	}

}
