package simutool.aku;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.UUID;

public class TestURIs {
	
	final String id = UUID.randomUUID().toString();

	public String metadataTest() {
		
	    File[] files = new File("C:\\Users\\valen\\eclipse-workspace\\aku\\source-sync").listFiles();
	    
	    iterateFiles(files);
	    readMetadata(files);
	    return id;
	}
	
	public void iterateFiles(File[] files) {
	    for (File file : files) {
	        if (file.isDirectory()) {
	        	iterateFiles(file.listFiles()); // Calls same method again.
	        } else {
	        	writeMetadata(file);
	        }
	    }
	}
	
	public void readMetadata(File[] files) {
	    for (File file : files) {
	        if (file.isDirectory()) {
	        	readMetadata(file.listFiles()); // Calls same method again.
	        } else {
	        	Path path = file.toPath();
	            try {
	            	 
					UserDefinedFileAttributeView view =
					        Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
					    String name = "user.id";
					    ByteBuffer buf = ByteBuffer.allocate(view.size(name));
					    view.read(name, buf);
					    buf.flip();
					    String value = Charset.defaultCharset().decode(buf).toString();
					    System.out.println("file: " + file.getName() + ", id: " + value);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       }
	    }
	}
	
	public void writeMetadata(File file) {
	    Path path = file.toPath();
	    UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
		try {
			view.write("user.id", Charset.defaultCharset().encode(id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] argv) {
		TestURIs t = new TestURIs();

		t.metadataTest();
	}
}
