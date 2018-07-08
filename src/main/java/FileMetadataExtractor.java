import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class FileMetadataExtractor {

	FileMetadata fileMetadata = null;

	public FileMetadata getMetadata(File file) {

		FileMetadata fileMetadata = extractMetadata(file);

		return fileMetadata;
	}

	public List<FileMetadata> getMetadata(List<File> files) {
		Iterator<File> i = files.iterator();
		ArrayList<FileMetadata> filesMetadata = new ArrayList<FileMetadata>();

		while (i.hasNext()) {
			File file = i.next();
			filesMetadata.add(extractMetadata(file));
		}

		return null;
	}
	
	
	public void extractMetadata(FileMetadata file) {

		// parameters of parse() method
		Parser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(file.getFile());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ParseContext context = new ParseContext();

		// Parsing the given file
		try {
			parser.parse(inputstream, handler, metadata, context);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] metadataNames = metadata.names();

		for (String name : metadataNames) {
			if(name.equals("created")) {
				file.setCreated(metadata.get(name));
			}
			else if(name.equals("Author")) {
				file.setContributor(metadata.get(name));
			}
			//System.out.println(name + ": " + metadata.get(name));
		}

		// // printing all the meta data elements with new elements
		// System.out.println("List of all the metadata elements after adding
		// new elements ");
		// String[] metadataNamesafter = metadata.names();
	}
	

	private FileMetadata extractMetadata(File file) {

		// parameters of parse() method
		Parser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ParseContext context = new ParseContext();

		// Parsing the given file
		try {
			parser.parse(inputstream, handler, metadata, context);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] metadataNames = metadata.names();
		
		fileMetadata = new FileMetadata(); 

		for (String name : metadataNames) {
			if(name.equals("created")) {
				fileMetadata.setCreated(metadata.get(name));
			}
			else if(name.equals("Author")) {
				fileMetadata.setContributor(metadata.get(name));
			}
			//System.out.println(name + ": " + metadata.get(name));
		}

		// // printing all the meta data elements with new elements
		// System.out.println("List of all the metadata elements after adding
		// new elements ");
		// String[] metadataNamesafter = metadata.names();

		return fileMetadata;
	}

}
