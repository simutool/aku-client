import java.util.List;

public class ConfigFileTester {
	
	
	public static void main(String[] args) {
		ConfigFile configFile = new ConfigFile("somethign");
		configFile.parse();
		
		System.out.println("Observe Directory: " + configFile.getObserveDirectory() + "\n");
		
		List<String> fileTypes = configFile.getFileTypes();
		for(int c = 0; c < fileTypes.size(); c++ ) {
			System.out.println("File Type: " + fileTypes.get(c));
		}
		
		System.out.println();
		
		System.out.println("Username: " + configFile.getUsername());
		
		System.out.println("Password: " + configFile.getPassword());
		
		System.out.println("Activity: " + configFile.getActivity());
		
		System.out.println("Contributor: " + configFile.getContributor());
		
		System.out.println("DC_Description: " + configFile.getDc_description());
		
		System.out.println("DC_Subject: " + configFile.getDc_subject());
		
		System.out.println("DC_References: " + configFile.getDc_references());
		
		System.out.println("Attachment: " + configFile.getAttachment());
		
		
	}

}
