package simutool.aku;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


public class Config {
	

	private String username;
	private String passwordFile;
	private String observeDirectory;
	private String activity;
	private String contributor;
	private String dc_description;
	private String dc_subject;
	private String dc_references;
	private String attachment;
	private String kgURL;
	private String kgHost;
	private String kgPort;
	private String idGenURL;
	private String rsyncPort;
	private String inheritanceQuery;
	
    private static volatile Config instance;
	
    /**
     * @return instance of Config class
     */
    public static Config getConfig() {
    	if(instance==null) {
    		instance = readConfigFile();	
    	}
		return instance;
    }
    
    
    public static void updateConfig() {
		instance = readConfigFile();	
    }    
    
    
    /**
     * Parses .yaml configuration file to a Config object
     * @return Config object with configuration
     */
    private static Config readConfigFile() {
		File initialFile = new File("config.yaml");
		try(FileInputStream inputStream = new FileInputStream(initialFile)){
			Yaml yaml = new Yaml(new Constructor(Config.class));
			Config conf =  yaml.load(inputStream);
			return conf;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
	
	private Config() {
		super();
	}
	


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getObserveDirectory() {
		return observeDirectory;
	}


	public void setObserveDirectory(String observeDirectory) {
		this.observeDirectory = observeDirectory;
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


	public String getPasswordFile() {
		return passwordFile;
	}


	public void setPasswordFile(String passwordFile) {
		this.passwordFile = passwordFile;
	}


	public String getKgURL() {
		return kgURL;
	}


	public void setKgURL(String kgURL) {
		this.kgURL = kgURL;
	}


	public String getKgHost() {
		return kgHost;
	}


	public void setKgHost(String kgHost) {
		this.kgHost = kgHost;
	}


	public String getKgPort() {
		return kgPort;
	}


	public void setKgPort(String kgPort) {
		this.kgPort = kgPort;
	}


	public String getIdGenURL() {
		return idGenURL;
	}


	public void setIdGenURL(String idGenURL) {
		this.idGenURL = idGenURL;
	}


	public String getRsyncPort() {
		return rsyncPort;
	}


	public void setRsyncPort(String rsyncPort) {
		this.rsyncPort = rsyncPort;
	}


	public String getInheritanceQuery() {
		return inheritanceQuery;
	}


	public void setInheritanceQuery(String inheritanceQuery) {
		this.inheritanceQuery = inheritanceQuery;
	}


	@Override
	public String toString() {
		return "Config [username=" + username + ", observeDirectory=" + observeDirectory + ", activity=" + activity
				+ ", contributor=" + contributor + ", dc_description=" + dc_description + ", dc_subject=" + dc_subject
				+ ", dc_references=" + dc_references + ", attachment=" + attachment + "]";
	}


	public static void main(String[] args) {
		Config c = Config.getConfig();
	}
}
