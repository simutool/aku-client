
public class RestCall {
	
	//This will make REST Calls, and return the result
	
	public boolean sendFileMetadata(String created, String userURI) {
		String dc_identifier = UUIDGenerator.getUUID();
		String uri = "http://uni-bamberg.de/mobi/kbms/simutool/Data/" + dc_identifier;
		
		//Construct Json Query for Neo4J
		String createDataQuery = "CREATE (d:Data { " + 
		"created: \"" + created + "\", " +
		"uri: \"" + uri + "\", " +
		"dc_identifier: \"" + dc_identifier + "\" })";
		
		String matchQuery = "MATCH (d:Data), (u:User) " + 
		"WHERE d.uri = \"" + uri + "\" " + 
		"and u.uri = \"" + userURI + "\" " +
		"CREATE (d)-[:dc_contributor]->(u)";
		
		String query = createDataQuery + matchQuery;
		
		
		return true;
	}

}
