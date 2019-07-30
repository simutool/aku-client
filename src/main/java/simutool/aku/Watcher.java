package simutool.aku;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Level;

public class Watcher {

	private static volatile Watcher instance;
	public static WatchKey started;

	public static Watcher getWatcher() {
		if(instance==null) {
			instance = new Watcher();	
		}
		return instance;
	}

	public static void killWatcher() {
		started.cancel();
	}

	private Watcher() {

		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path source = Paths.get(Config.getConfig().getObserveDirectory());

			started = source.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
		    Config.LOGGER.log( Level.INFO, "new watcher created", started );

			// System.out.println("new watcher created: " + started);
			while (true) {
				WatchKey key;
				try {
					//return signaled key, meaning events occurred on the object
					key = watcher.take();
				} catch (InterruptedException e) {
				    Config.LOGGER.log( Level.SEVERE, e.toString(), e );

					return;
				}

				//retrieve all the accumulated events
				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind(); 
					
				    Config.LOGGER.log( Level.INFO, "event", kind.name() );

					// System.out.println("kind "+ kind.name());
					Path path = (Path)event.context();
					// System.out.println("path: " + path.toString());


					if(!path.getFileName().toString().contains("temp")) {
						App.setup(false);
						
					    Config.LOGGER.log( Level.INFO, "watcher 1" );

						File srcFile = path.toFile();
						String type = srcFile.isFile()? "file" : "folder";
						ConfirmSync dialog = new ConfirmSync(path, type);
					}

				}             
				//resetting the key goes back ready state
				key.reset();
			}
		} catch (Exception e) {
		    Config.LOGGER.log( Level.SEVERE, e.toString(), e );

			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

}
