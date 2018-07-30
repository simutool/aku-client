import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ConfigFile {

	String base = "C:\\Users\\Harshit Gupta\\workspace\\";
	// String base = "C:\\Users\\z003x6ke\\eclipse-workspace\\"

	private File configFile;
	private BufferedReader br;

	private String username;
	private String password;
	private String observeDirectory;
	private List<String> fileTypes = new ArrayList<String>();
	private String activity;
	private String contributor;
	private String dc_description;
	private String dc_subject;
	private String dc_references;
	private String attachment;

	public ConfigFile(String path) {

		configFile = new File(path + "\\config.txt");
		//configFile = new File(base + "PrePoorClient\\appfiles\\config.txt");
		// configFile = new File(base + "PrePoorClient\\appfiles\\config2.txt");
		try {
			br = new BufferedReader(new FileReader(configFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("[CF] Error Loading Config File.");
			//System.out.println(new File(".").getAbsolutePath());
			//System.out.println(new File("").getCanonicalPath());
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDc_subject() {
		return dc_subject;
	}

	public void setDc_subject(String dc_subject) {
		this.dc_subject = dc_subject;
	}

	public String getDc_references() {
		return dc_references;
	}

	public void setDc_references(String dc_references) {
		this.dc_references = dc_references;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
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
		String line = null;
		try {
			line = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while (line != null) {
				
				//Parse username
				int indexOfUsername = line.indexOf("username");
				if (indexOfUsername >= 0) {
					username = line.substring(11, line.length());
				}
				
				//Parse password
				int indexOfPassword = line.indexOf("password");
				if (indexOfPassword >= 0) {
					password = line.substring(11, line.length());
				}

				// Parse Observation Directory
				int indexOfObserve = line.indexOf("observe");
				if (indexOfObserve >= 0) {
					observeDirectory = line.substring(11, line.length() - 1);
					observeDirectory = observeDirectory.replaceAll("\\\\", "\\\\\\\\");
					// System.out.println("Observe Directory is: " +
					// observeDirectory);
				}

				// Parse File Types
				int indexOfFiles = line.indexOf("files");
				if (indexOfFiles >= 0) {
					int noOfCommas = StringUtils.countMatches(line, ",");
					System.out.println("NoOfCommas: " + noOfCommas);
					String localFileTypes = line.substring(9, line.length() - 1);
					if (noOfCommas == 0) {
						// Means only one file type
						fileTypes.add(localFileTypes);
					} else if (noOfCommas == 1) {
						// Means 2 file types
						String firstFileType = localFileTypes.substring(0, localFileTypes.indexOf(","));
						String secondFileType = localFileTypes.substring(localFileTypes.indexOf(",") + 1,
								localFileTypes.length());
						fileTypes.add(firstFileType);
						fileTypes.add(secondFileType);

					} else if (noOfCommas > 1) {
						// Means more than 2 file types
						System.out.println("NoOfCommas2IF");
						for (int c = 0; c <= noOfCommas; c++) {
							if (c == noOfCommas) {
								fileTypes.add(localFileTypes);
								break;
							}
							String fileType = localFileTypes.substring(0, localFileTypes.indexOf(","));
							localFileTypes = localFileTypes.substring(localFileTypes.indexOf(",") + 1,
									localFileTypes.length());
							fileTypes.add(fileType);
							if (localFileTypes.indexOf(",") <= 0) {
								if (localFileTypes.length() > 0) {

								}
							}
						}

					}
				}

				// Parse Activity
				int indexOfActivity = line.indexOf("activity");
				if (indexOfActivity >= 0) {
					activity = line.substring(11, line.length());
				}

				// Parse Contributor
				int indexOfContributor = line.indexOf("contributor");
				if (indexOfContributor >= 0) {
					contributor = line.substring(13, line.length());
				}

				// Parse dc_description
				int indexOfDescription = line.indexOf("dc_description");
				if (indexOfDescription >= 0) {
					dc_description = line.substring(16, line.length());
				}

				// Parse dc_subject
				int indexOfSubject = line.indexOf("dc_subject");
				if (indexOfSubject >= 0) {
					dc_subject = line.substring(13, line.length());
				}

				// Parse dc_references
				int indexOfReferences = line.indexOf("dc_references");
				if (indexOfReferences >= 0) {
					dc_references = line.substring(16, line.length());
				}

				// Parse attachment
				int indexOfAttachment = line.indexOf("attachment");
				if (indexOfAttachment >= 0) {
					attachment = line.substring(13, line.length());
				}

				line = br.readLine();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
