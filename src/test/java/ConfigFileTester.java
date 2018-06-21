import java.util.List;

public class ConfigFileTester {
	
	
	public static void main(String[] args) {
		ConfigFile configFile = new ConfigFile();
		configFile.parse();
		
		System.out.println("Observe Directory: " + configFile.getObserveDirectory() + "\n");
		
		List<String> fileTypes = configFile.getFileTypes();
		for(int c = 0; c < fileTypes.size(); c++ ) {
			System.out.println("File Type: " + fileTypes.get(c));
		}
		
		System.out.println();
		
		System.out.println("Activity: " + configFile.getActivity());
		
		System.out.println("Contributor: " + configFile.getContributor());
		
		System.out.println("DC_Description: " + configFile.getDc_description());
		
		
	}

}
