import java.io.File;

public class FileMetadata {

	private String filename;
	private String file_path;
	private File file;

	private String contributor;
	private String created;
	private String dc_description;
	private String dc_identifier;
	private String dc_subject;
	private String dc_references;
	private String attachment;

	public FileMetadata() {
		filename = "default";
		file_path = "default";
		file = null;
		contributor = "default";
		created = "default";
		dc_description = "default";
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



	public String getDc_identifier() {
		return dc_identifier;
	}

	public void setDc_identifier(String dc_identifier) {
		this.dc_identifier = dc_identifier;
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
