package simutool.aku;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

import javafx.scene.control.Alert.AlertType;


public class Config {
	
	// values received after registration
	private String user_identifier;
	private String object_storage_username;
	private String object_storage_password;
	private String object_storage_host;
	
	private String documentEndpoint;
	private String idGenEndpoint;
	private String inheritanceQueryEndpoint;
	private String createActivityEndpoint;
	
	private String firstLoginEndpoint;
	
	// path to password file
	private String passwordFile;
	

	// properties from registration popup
	private String kmsEmail;
	private String kmsPassword;
	private String kmsHost;
	private String observeDirectory;

	
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
		Config conf = null;
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.findAndRegisterModules();
		try {
			conf = mapper.readValue(initialFile, Config.class);
		} catch (Exception e) {
			InfoPopUp err = new InfoPopUp("Configuration error", "File config.yaml is malformed or missing.", AlertType.ERROR);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return conf;
    }
    
	
	private Config() {
		super();
	}
	


	public String getObserveDirectory() {
		return observeDirectory;
	}


	public void setObserveDirectory(String observeDirectory) {
		this.observeDirectory = observeDirectory;
	}


	

	public String getUser_identifier() {
		return user_identifier;
	}


	public void setUser_identifier(String user_identifier) {
		this.user_identifier = user_identifier;
	}


	public String getObject_storage_username() {
		return object_storage_username;
	}


	public void setObject_storage_username(String object_storage_username) {
		this.object_storage_username = object_storage_username;
	}


	public String getObject_storage_password() {
		return object_storage_password;
	}


	public void setObject_storage_password(String object_storage_password) {
		this.object_storage_password = object_storage_password;
	}


	public String getObject_storage_host() {
		return object_storage_host;
	}


	public void setObject_storage_host(String object_storage_host) {
		this.object_storage_host = object_storage_host;
	}


	public String getDocumentEndpoint() {
		return documentEndpoint;
	}


	public void setDocumentEndpoint(String documentEndpoint) {
		this.documentEndpoint = documentEndpoint;
	}


	public String getIdGenEndpoint() {
		return idGenEndpoint;
	}


	public void setIdGenEndpoint(String idGenEndpoint) {
		this.idGenEndpoint = idGenEndpoint;
	}


	public String getInheritanceQueryEndpoint() {
		return inheritanceQueryEndpoint;
	}


	public void setInheritanceQueryEndpoint(String inheritanceQueryEndpoint) {
		this.inheritanceQueryEndpoint = inheritanceQueryEndpoint;
	}


	public String getCreateActivityEndpoint() {
		return createActivityEndpoint;
	}


	public void setCreateActivityEndpoint(String createActivityEndpoint) {
		this.createActivityEndpoint = createActivityEndpoint;
	}


	public String getFirstLoginEndpoint() {
		return firstLoginEndpoint;
	}


	public void setFirstLoginEndpoint(String firstLoginEndpoint) {
		this.firstLoginEndpoint = firstLoginEndpoint;
	}


	public String getPasswordFile() {
		return passwordFile;
	}


	public void setPasswordFile(String passwordFile) {
		this.passwordFile = passwordFile;
	}


	public String getKmsEmail() {
		return kmsEmail;
	}


	public void setKmsEmail(String kmsEmail) {
		this.kmsEmail = kmsEmail;
	}


	public String getKmsPassword() {
		return kmsPassword;
	}


	public void setKmsPassword(String kmsPassword) {
		this.kmsPassword = kmsPassword;
	}


	public String getKmsHost() {
		return kmsHost;
	}


	public void setKmsHost(String kmsHost) {
		this.kmsHost = kmsHost;
	}
	
	public boolean isValid(String value) {
		return value != null && value.length()>0;
	}


	public void generateYaml() {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));
		mapper.findAndRegisterModules();

		// Write object as YAML file
		try {
			mapper.writeValue(new File("config.yaml"), this);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	}

	public static void main(String[] args) {
		Config c = Config.getConfig();
		c.generateYaml();
	}
}
