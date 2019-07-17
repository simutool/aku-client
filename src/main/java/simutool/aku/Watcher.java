package simutool.aku;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class Watcher {

	public Watcher() {
    	try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
        	Path source = Paths.get(Config.getConfig().getObserveDirectory());

			source.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
			while (true) {
				WatchKey key;
				try {
					//return signaled key, meaning events occurred on the object
					key = watcher.take();
				} catch (InterruptedException ex) {
					return;
				}

				//retrieve all the accumulated events
				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();               
					
					System.out.println("kind "+ kind.name());
					Path path = (Path)event.context();
					System.out.println("path: " + path.toString());
					Config.updateConfig();

					if(!path.getFileName().toString().contains("temp")) {
						ConfirmSync dialog = new ConfirmSync(path);
						dialog.setVisible(true);						
					}
		
				}             
				//resetting the key goes back ready state
				key.reset();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
