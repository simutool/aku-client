import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class DatabaseInteractor {

	public List<String> readFiles() {
		List<String> files = null;
		try {
			File database = new File("database.dat");
			FileInputStream fis = new FileInputStream(database);
			ObjectInputStream ois = new ObjectInputStream(fis);
			files = (List<String>) ois.readObject();
			fis.close();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return files;
	}

	public void updateFiles(List<String> files) {
		try {
			File database = new File("database.dat");
			database.createNewFile();
			FileOutputStream fos = new FileOutputStream(database);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(files);
			oos.flush();
			fos.close();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
