import java.io.File;

public class FileMetadata {

	private String filename;
	private String file_path;
	private File file;

	private String contributor;
	private String created;
	private String dc_description;

	public FileMetadata() {
		filename = null;
		file_path = null;
		file = null;
		contributor = null;
		created = null;
		dc_description = null;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getDc_description() {
		return dc_description;
	}

	public void setDc_description(String dc_description) {
		this.dc_description = dc_description;
	}

}
