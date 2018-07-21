import java.io.File;

public class FileReceiveEndpointTest {
	
	public static void main(String[] args) {
		File file = new File("C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 8.0\\bin\\files");
		System.out.println(file.isDirectory());
	}

}
