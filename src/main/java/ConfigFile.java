import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ConfigFile {

	private File configFile;
	private BufferedReader br;

	private String observeDirectory;
	private List<String> fileTypes = new ArrayList<String>();
	private String activity;
	private String contributor;
	private String dc_description;

	public ConfigFile() {
		try {
			br = new BufferedReader(new FileReader(configFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getObserveDirectory() {
		return observeDirectory;
	}

	public void setObserveDirectory(String observeDirectory) {
		this.observeDirectory = observeDirectory;
	}

	public List<String> getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(List<String> fileTypes) {
		this.fileTypes = fileTypes;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	public String getDc_description() {
		return dc_description;
	}

	public void setDc_description(String dc_description) {
		this.dc_description = dc_description;
	}

	
	
	public void parse() {
		String line;
		try {
			while ((line = br.readLine()) != null) {

				// Parse Observation Directory
				int indexOfObserve = line.indexOf("observe");
				if (indexOfObserve > 0) {
					observeDirectory = line.substring(11, line.length() - 1);
				}

				// Parse File Types
				int indexOfFiles = line.indexOf("files");
				if (indexOfFiles > 0) {
					int noOfCommas = StringUtils.countMatches(line, ",");
					String localFileTypes = line.substring(9, line.length() - 1);
					if (noOfCommas == 0) {
						// Means only one file type
						fileTypes.add(localFileTypes);
					} else if (noOfCommas == 1) {
						// Means 2 file types
						String firstFileType = localFileTypes.substring(0, localFileTypes.indexOf(","));
						String secondFileType = localFileTypes.substring(localFileTypes.indexOf(","),
								localFileTypes.length());
						fileTypes.add(firstFileType);
						fileTypes.add(secondFileType);

					} else if (noOfCommas > 1) {
						// Means more than 2 file types
						while (localFileTypes.indexOf(",") > 0) {
							String fileType = localFileTypes.substring(0, localFileTypes.indexOf(","));
							localFileTypes = localFileTypes.substring(localFileTypes.indexOf(","),
									localFileTypes.length());
							fileTypes.add(fileType);
						}

					}
				}

				// Parse Activity
				int indexOfActivity = line.indexOf("activity");
				if (indexOfActivity > 0) {
					activity = line.substring(11, line.length());
				}
				else activity = "empty";

				// Parse Contributor
				int indexOfContributor = line.indexOf("contributor");
				if (indexOfContributor > 0) {
					contributor = line.substring(13, line.length());
				}
				else contributor = "empty";

				// Parse dc_description
				int indexOfDescription = line.indexOf("dc_description");
				if (indexOfDescription > 0) {
					dc_description = line.substring(16, line.length());
				}
				else dc_description = "empty";
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
