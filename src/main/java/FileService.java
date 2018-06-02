import java.util.Iterator;
import java.util.List;

public class FileService extends Thread {

	DatabaseInteractor di;

	public FileService() {
		di = new DatabaseInteractor();
	}

	public void run() {
		while(true) {
			
			List<String> files_in_database = di.readFiles();
			//Code to get new files
			List<String> new_scanned_files = null;			
			
			Iterator<String> i = new_scanned_files.iterator();
			
			while(i.hasNext()) {
				String file_name = i.next();
				int k = files_in_database.indexOf(file_name);
				if(k==-1) {
					files_in_database.add(file_name);
				}
				else {
					new_scanned_files.remove(file_name);
				}
			}
			
			di.updateFiles(files_in_database);
			
			
			
			try {
				this.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
