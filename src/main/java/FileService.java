import java.io.EOFException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileService {

	private DatabaseInteractor di;
	private List<FileMetadata> new_files;
	String observeDirectory;

	public FileService(String observeDirectoy) {
		di = new DatabaseInteractor();
		new_files = new ArrayList<FileMetadata>();
		this.observeDirectory = observeDirectoy;
	}

	public List getNewFiles() {
		return new_files;
	}

	public void run() {

		List<String> files_in_database = null;

		// Check if database is any good
		try {
			files_in_database = di.readFiles();
		} catch (Exception e) {
			files_in_database = new ArrayList<String>();
			System.out.println("Problem with database");
		}
		
		System.out.println("Before clearing new files");
		new_files.clear();
		System.out.println("After clearing new files");

		// Code to get new files
		List<String> new_scanned_files = new ArrayList<String>();

		File[] files = new File(observeDirectory).listFiles();

		for (File file : files) {
			if (file.isFile()) {
				new_scanned_files.add(file.getName());
				// System.out.println("FileName is: " + file.getName());
			}
		}

		// If the database is empty then all files are new
		if (files_in_database == null || files_in_database.size() == 0) {
			//System.out.println("in if");
			try {
				for (int x = 0; x < files.length; x++) {
					FileMetadata fileMetadata = new FileMetadata();
					fileMetadata.setFile(files[x]);
					new_files.add(fileMetadata);
					//System.out.println("added to new_files 1");
					files_in_database.add(files[x].getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else {
			//System.out.println("in else");
			for (int c = 0; c < new_scanned_files.size(); c++) {
				// Means file already exists in database
				if (files_in_database.contains(new_scanned_files.get(c))) {
					new_scanned_files.remove(c);
				}
				// Means its a new file
				else {
					System.out.println("New File Detected: " + new_scanned_files.get(c));
					files_in_database.add(new_scanned_files.get(c));
					// Create object of FileMetadata
					// Find the file in Files by linear search
					String file_name = new_scanned_files.get(c);
					for (int x = 0; x < files.length; x++) {
						if (file_name.equals(files[x])) {
							FileMetadata fileMetadata = new FileMetadata();
							fileMetadata.setFile(files[x]);
							new_files.add(fileMetadata);
							System.out.println("added to new_files 2");
						}
					}
				}
			}
		}

		// Current files in database (new or old)
		di.updateFiles(files_in_database);
	}
}
