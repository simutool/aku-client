import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class DatabaseInteractor {

	File database;
	FileInputStream fis;
	FileOutputStream fos;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	List<String> files;

	public DatabaseInteractor() {
		try {
			database = new File("database.dat");
			fis = new FileInputStream(database);
			ois = new ObjectInputStream(fis);
			fos = new FileOutputStream(database);
			oos = new ObjectOutputStream(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<String> readFiles() {
		try {
			files = (List<String>) ois.readObject();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return files;		
	}
	
	public void updateFiles(List<String> files) {
		try {
			database.createNewFile();
			oos.writeObject(files);
			oos.flush();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
