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

public class FileService {
	
	private static File tempDir = new File(Config.getConfig().getObserveDirectory() + "/.temp/");
	private static String generatedId;
	
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
    	String extension = srcFile.getName().substring(srcFile.getName().lastIndexOf('.'));
		generatedId = UUIDGenerator.getUUID() + extension;
		File destFile = new File(tempDir  + "/" + generatedId);
		try {
			FileUtils.copyFile(srcFile, destFile);
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
	  		System.out.println("destination: " + "rsync://" + conf.getUsername() + "@141.13.162.157:12000/files/");
			final RSync rsync = new RSync()
				  .source(tempDir.getAbsolutePath() + "/")
				  .destination("rsync://" + conf.getUsername() + "@141.13.162.157:12000/files/")
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

}
