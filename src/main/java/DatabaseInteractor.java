import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInteractor {
	
	String base = "C:\\Users\\Harshit Gupta\\workspace\\";
	//String base = "C:\\Users\\z003x6ke\\eclipse-workspace\\"

	public List<String> readFiles() {
		List<String> files = null;
		try {
			String path = new File("").getAbsoluteFile().getPath();
			File database = new File(path + "\\database.dat");
			//File database = new File(base + "PrePoorClient\\appfiles\\database.dat");
			//File database = new File(base + "PrePoorClient\\appfiles\\database.dat");
			if (!database.exists()) {
				database.createNewFile();
				files = new ArrayList<String>();
			} 
			else {
				FileInputStream fis = new FileInputStream(database);
				ObjectInputStream ois = new ObjectInputStream(fis);
				files = (List<String>) ois.readObject();
				fis.close();
				ois.close();
			}
		} catch (Exception e) {
			System.out.println("Database is Empty");
			files = new ArrayList<String>();
		}

		return files;
	}

	public void updateFiles(List<String> files) {
		try {
			File database = new File(base + "PrePoorClient\\appfiles\\database.dat");
			//File database = new File(base + "PrePoorClient\\appfiles\\database.dat");
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
