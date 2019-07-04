package simutool.aku;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UUIDGenerator {
	
	private static final Logger LOGGER = Logger.getLogger( UUIDGenerator.class.getName() );
	
	public static String getUUID() {
		try {
			return UUID.randomUUID().toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		    LOGGER.log( Level.SEVERE, e.toString(), e );
		    return null;
		}
	}
}
