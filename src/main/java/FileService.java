import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileService extends Thread {

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
		while(true) {
			
			List<String> files_in_database = di.readFiles();
			new_files.clear();

			//Code to get new files
			List<String> new_scanned_files = null;
			
			File[] files = new File(observeDirectory).listFiles();
			
			for (File file : files) {
			    if (file.isFile()) {
			        new_scanned_files.add(file.getName());
			    }
			}
			
			Iterator<String> i = new_scanned_files.iterator();
			
			while(i.hasNext()) {
				String file_name = i.next();
				int k = files_in_database.indexOf(file_name);
				
				if(k==-1) {
					//Means it is a new file
					files_in_database.add(file_name);
					
					//Add the new file into a variable that the MainClient can also access
					for(int c = 0; c < files.length; c++) {
						if(file_name.equals(files[c].getName())) {
							FileMetadata fileMetadata = new FileMetadata();
							fileMetadata.setFile(files[c]);
							new_files.add(fileMetadata);
						}
					}
				}
				else {
					new_scanned_files.remove(file_name);
				}
			}
			
			di.updateFiles(files_in_database);
			
			
			
			try {
				this.sleep(50000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
