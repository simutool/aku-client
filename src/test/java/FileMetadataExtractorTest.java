import java.io.File;

public class FileMetadataExtractorTest {
	
	public static void main(String[] args) {
		FileMetadataExtractor extractor = new FileMetadataExtractor();
		
		File testFile = new File("C:\\Users\\Harshit Gupta\\workspace\\PrePoorClient\\testfiles\\testFile.pdf");
		
		FileMetadata fileMetadata = extractor.getMetadata(testFile);
		
		System.out.println("Contributor is: " + fileMetadata.getContributor());
		
		System.out.println("Date created is: " + fileMetadata.getCreated());
	}

}
