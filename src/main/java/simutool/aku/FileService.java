package simutool.aku;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

import com.github.fracpete.processoutput4j.output.ConsoleOutputProcessOutput;
import com.github.fracpete.rsync4j.RSync;
import com.google.gson.JsonElement;

public class FileService {
	
	private static File tempDir = new File(Config.getConfig().getObserveDirectory() + "/.temp/");
	private static String generatedId;
	private static String generatedURL;
	
	public static void syncFile(Path path) {
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
		
		File srcFile = path.toFile();
    	if(srcFile.isDirectory()) {
    		srcFile = zipDirectory(srcFile);
    	}
		renameAndCopy(srcFile);
		launchRsync();
		MetadataSender.sendJSON();
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
		}
		
	}
	
	private static File zipDirectory(File fileToZip) {
		try(
				FileOutputStream fos = new FileOutputStream(tempDir + "/current.zip");
				ZipOutputStream zipOut = new ZipOutputStream(fos);
				FileInputStream fis = new FileInputStream(fileToZip + "/")){
		
			ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
			zipOut.putNextEntry(zipEntry);
			byte[] bytes = new byte[1024];
			int length;
			while((length = fis.read(bytes)) >= 0) {
			    zipOut.write(bytes, 0, length);
			}
			return new File(tempDir + "current.zip");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	private static void launchRsync() {
		
	  	try {
	  		Config conf = Config.getConfig();
	  		System.out.println("source: " + tempDir.getAbsolutePath());
	  		System.out.println("destination: " + "rsync://" + conf.getUsername() + "@141.13.162.157"+ conf.getRsyncPort() +"/files/");
			final RSync rsync = new RSync()
				  .source(tempDir.getAbsolutePath() + "/")
				  .destination("rsync://" + conf.getUsername() + "@141.13.162.157"+ conf.getRsyncPort() + "/files/")
				  .additional("++size-only")
				  .recursive(true)
				  .times(true)
				  .dirs(true)
				  .verbose(true)
				  .passwordFile(conf.getPasswordFile());

			
			ConsoleOutputProcessOutput output = new ConsoleOutputProcessOutput();
			output.monitor(rsync.builder());

			
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

}
