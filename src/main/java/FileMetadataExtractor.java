import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

		Metadata metadata = extractMetadata(file);
		
		return null;
	}

	public List<FileMetadata> getMetadata(List<File> files) {
		Iterator<File> i = files.iterator();
		
		while(i.hasNext()) {
			File file = i.next();
			Metadata metadata = extractMetadata(file);
		}
		
		
		return null;
	}
	

	private Metadata extractMetadata(File file) {

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

		// // list of meta data elements elements
		// System.out.println(" metadata elements and values of the given file
		// :");
		// String[] metadataNamesb4 = metadata.names();
		//
		// for (String name : metadataNamesb4) {
		// System.out.println(name + ": " + metadata.get(name));
		// }

		// // printing all the meta data elements with new elements
		// System.out.println("List of all the metadata elements after adding
		// new elements ");
		// String[] metadataNamesafter = metadata.names();
		
		return metadata;
	}

}
