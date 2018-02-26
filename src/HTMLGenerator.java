import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

public class HTMLGenerator {

	private CollageManager collageManager;
	
	// FOR TESTING PURPOSES
	public HTMLGenerator()
	{
		this.collageManager = new CollageManager();
	}
	
	// Constructor
	public HTMLGenerator(CollageManager collageManager)
	{
		this.collageManager = collageManager;
	}

	// Main generator function
	public String generateHTML(String searchText) {
		
		// Instantiate file object from template html file
		File collageTemplateFile = new File("WebContent/collagetemplate.html");
		// String to hold encoding of file
		String encoding = "";
		try {
			String htmlString = FileUtils.readFileToString(collageTemplateFile, StandardCharsets.UTF_8);
			String imgURL = "\"" + "images/exCollage.png" + "\"";
			htmlString = htmlString.replace("$topicString", searchText);
			htmlString = htmlString.replace("$imgURL", imgURL);
			FileUtils.writeStringToFile(new File("WebContent/collageresult.html"), htmlString, StandardCharsets.UTF_8);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
		
		// dummy return
		String dummy = "dummy";
		return dummy;
	}
	
	public String generateHTML() {
		
		// dummy return
		String dummy = "dummy";
		return dummy;
	}
	
}
