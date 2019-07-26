package simutool.aku;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.zeroturnaround.zip.ZipUtil;

import com.github.fracpete.processoutput4j.output.ConsoleOutputProcessOutput;
import com.github.fracpete.rsync4j.RSync;
import com.google.gson.JsonElement;

import javafx.scene.control.Alert.AlertType;

public class FileService {
	
	private static File tempDir = new File(Config.getConfig().getObserveDirectory() + "/.temp/");
	private static String generatedId;
	private static String generatedURL;
	
	public static void syncFile(Path path) {
		Config.updateConfig();
		if (!tempDir.exists()) {
			tempDir.mkdir();
		} else {
			try {
				FileUtils.deleteDirectory(tempDir);
				tempDir.mkdir();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		File srcFile = new File(Config.getConfig().getObserveDirectory() + "/" +path.getFileName());
    	if(srcFile.isDirectory()) {
    		srcFile = zipDirectory(srcFile, path);
    	}
		renameAndCopy(srcFile);
		launchRsync(tempDir.getAbsolutePath() + "/");
	}
	
	private static void renameAndCopy(File srcFile) {
		System.out.println("renaming...");

    	String fileName = srcFile.getName().substring(srcFile.getName().lastIndexOf('/')+1);
    	JsonElement idGenResponse = UUIDGenerator.getUUID(fileName);
		generatedId = idGenResponse.getAsJsonObject().get("unique_name").toString().replaceAll("\"", "");
		generatedURL = idGenResponse.getAsJsonObject().get("url").toString();

		
		File destFile = new File(tempDir  + "/" + generatedId);
		try {
			FileUtils.copyFile(srcFile, destFile);
			System.out.println("destFile: " + destFile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InfoPopUp err = new InfoPopUp("Wrong id", "Id generation failed.", AlertType.ERROR);
		}
		
	}
	
	private static File zipDirectory(File fileToZip, Path path) {
 
		String fileName = fileToZip.getName().substring(fileToZip.getName().lastIndexOf('/')+1);
		File zipped = new File(fileToZip + ".zip");
		
		ZipUtil.pack(fileToZip, zipped);
		return zipped;

	}
	
	private static void launchRsync(String source) {
		
	  	try {
	  		Config conf = Config.getConfig();
	  		String destination = "rsync://" + conf.getObject_storage_username() + "@" + conf.getObject_storage_host().substring(conf.getObject_storage_host().indexOf("://")+3);
	  		System.out.println("source: " + source);
	  		System.out.println("destination: " + destination);
			final RSync rsync = new RSync()
				  .source(source)
				  .destination(destination)
				  .additional("++size-only")
				  .recursive(true)
				  .times(true)
				  .dirs(true)
				  .verbose(true)
				  .passwordFile(conf.getPasswordFile());

			
			ConsoleOutputProcessOutput output = new ConsoleOutputProcessOutput();
			output.monitor(rsync.builder());
			if(output.getExitCode() != 0) {
				InfoPopUp err = new InfoPopUp("File transfer failed", "File could not be synchronized,  please check your credentials in configuration file.", AlertType.ERROR);
			}else {
				RestCalls.sendJSON();
				InfoPopUp inf = new InfoPopUp("File sent", "File was successfully synchronized.", AlertType.INFORMATION);
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static String getGeneratedId() {
		return generatedId;
	}

	public static void setGeneratedId(String generatedId) {
		FileService.generatedId = generatedId;
	}

	public static String getGeneratedURL() {
		return generatedURL;
	}

	public static void setGeneratedURL(String generatedURL) {
		FileService.generatedURL = generatedURL;
	}
	
	public static void main(String[] args) {

		launchRsync(Config.getConfig().getObserveDirectory());
	}

}
