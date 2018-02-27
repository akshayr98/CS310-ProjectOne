import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Vector;

import javax.imageio.ImageIO;

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
	
	// Generates HTML page when a new collage is created
	public String generateHTML() {
		// Instantiate file object from template HTML file
		File collageTemplateFile = new File("WebContent/collagetemplate.html");
		
		// Vector of collages created in session
		Vector<BufferedImage> collages = collageManager.getCollages();
		try {
			int collageIndex = collages.size() - 1;
			// Converts File to String
			String htmlString = FileUtils.readFileToString(collageTemplateFile, StandardCharsets.UTF_8);
			// Grabs the currently displayed collage's title
			String currentCollageTitle = collageManager.getCollageTitles().get(collageIndex);
			// Replaces title placeholder with current collage's title
			htmlString = htmlString.replace("$topicString", currentCollageTitle);
			
			ByteArrayOutputStream byteArrayOS; // Output stream to convert BufferedImage to ByteArray
			byte[] imageBytes; // ByteArray containing the BufferedImage in bytes
			String base64String; // Contains the string of the ByteArray in base64
			Vector<String> imageStringVector = new Vector<String>(); // Vector holding the collages as base64 image strings
			
			// Loops through all collages
			for (int i = 0; i < collages.size(); i++) {
				byteArrayOS = new ByteArrayOutputStream();
				// Writes the BufferedImage to the output stream in PNG format
				ImageIO.write(collages.get(i), "png", byteArrayOS);
				// Converts the output stream to a ByteArray
				imageBytes = byteArrayOS.toByteArray();
				// Encoding of ByteArray
				imageBytes = Base64.getEncoder().encode(imageBytes);
				// Converts ByteArray to base64 string
				base64String = new String(imageBytes, "UTF-8");
				// Adds base64 string image to string vector
				imageStringVector.add(base64String);
			}
			
			/*for (int i = 0; i < testVector.size(); i++) {
				byteArrayOS = new ByteArrayOutputStream();
				// Writes the BufferedImage to the output stream in PNG format
				ImageIO.write(testVector.get(i), "png", byteArrayOS);
				// Converts the output stream to a ByteArray
				imageBytes = byteArrayOS.toByteArray();
				// Encoding of ByteArray
				imageBytes = Base64.getEncoder().encode(imageBytes);
				// Converts ByteArray to base64 string
				base64String = new String(imageBytes, "UTF-8");
				// Adds base64 string image to string vector
				imageStringVector.add(base64String);
			}*/
			
			// Creates HTML string to display img
			String displayImgString = "<img id=\"collage\" src" + "\"data:image/png;base64, " + imageStringVector.get(collageIndex) + "\"" + ">";
			// Replaces image URL placeholder
			htmlString = htmlString.replace("$collageSpaceContents", displayImgString);
			
			String previousCollageURLs = "<table><tbody><tr>";
			// Iterates through image string vector // SKIPS LAST INDEX BC LAST INDEX IS CURRENT COLLAGE
			for (int i = 0; i < imageStringVector.size() - 1; i++) {
				
//				if (i == collageIndex) {
//					continue;
//				}
//				else {
					// Replaces placeholder with previous collage's URL
					previousCollageURLs = previousCollageURLs + "<td><img id=\"previousCollage\" src=\"" + "data:image/png;base64, "
							+ imageStringVector.get(i) + "\"></td>";
//				}
			}
			previousCollageURLs = previousCollageURLs + "</tr></tbody></table>";
			htmlString = htmlString.replace("$tableContents", previousCollageURLs);
			// Writes new htmlString to file
			//FileUtils.writeStringToFile(new File("WebContent/collageresult.html"), htmlString, StandardCharsets.UTF_8);
			// Return HTML String
			return htmlString;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}

	// Generates HTML page when insufficient images found
	public String generateHTML(String searchText) {
		// Instantiate file object from template HTML file
		File collageTemplateFile = new File("WebContent/collagetemplate.html");
		try {
			// Converts File to String
			String htmlString = FileUtils.readFileToString(collageTemplateFile, StandardCharsets.UTF_8);
			// Replaces title placeholder with search text
			htmlString = htmlString.replace("$topicString", searchText); 
			
			Vector<BufferedImage> collages = collageManager.getCollages(); // Vector of collages created in session
			ByteArrayOutputStream byteArrayOS; // Output stream to convert BufferedImage to ByteArray
			byte[] imageBytes; // ByteArray containing the BufferedImage in bytes
			String base64String; // Contains the string of the ByteArray in base64
			Vector<String> imageStringVector = new Vector<String>(); // Vector holding the collage images as strings
			
			// Loops through all collages
			for (int i = 0; i < collages.size(); i++) {
				byteArrayOS = new ByteArrayOutputStream();
				// Writes the BufferedImage to the output stream in PNG format
				ImageIO.write(collages.get(i), "png", byteArrayOS);
				// Converts the output stream to a ByteArray
				imageBytes = byteArrayOS.toByteArray();
				// Encoding of ByteArray
				imageBytes = Base64.getEncoder().encode(imageBytes);
				// Converts ByteArray to base64 string
				base64String = new String(imageBytes, "UTF-8");
				// Adds base64 string image to string vector
				imageStringVector.add(base64String);
			}
			// Creates HTML string to display error message
			String displayedErrorMessage = "<p>Insufficient number of images found</p>";
			// Replace image URL placeholder
			htmlString = htmlString.replace("$collageSpaceContents", displayedErrorMessage);
			
			String previousCollageURLs = "<table><tbody><tr>";
			// Iterates through image string vector
			for (int i = 0; i < imageStringVector.size(); i++) {
				// Replaces placeholder with previous collage's URL
				previousCollageURLs = previousCollageURLs + "<td><img id=\"previousCollage\" src=\"" + "data:image/png;base64, "
						+ imageStringVector.get(i) + "\"></td>";
			}
			previousCollageURLs = previousCollageURLs + "</tr></tbody></table>";
			htmlString = htmlString.replace("$tableContents", previousCollageURLs);
			
			// Writes new htmlString to file
			//FileUtils.writeStringToFile(new File("WebContent/collageresult.html"), htmlString, StandardCharsets.UTF_8);
			// Return HTML String
			return htmlString;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
	
	// Generates HTML page when previous collage is clicked
	public String generateHTMLForPreviousCollage() {
		// Instantiate file object from template HTML file
		File collageTemplateFile = new File("WebContent/collagetemplate.html");
		try {
			int collageIndex = collageManager.getIndex();
			Vector<BufferedImage> collages = collageManager.getCollages(); // Vector of collages created in session
			Vector<String> collageTitles = collageManager.getCollageTitles(); // Vector of collages created in session
			
			// Converts File to String
			String htmlString = FileUtils.readFileToString(collageTemplateFile, StandardCharsets.UTF_8);
			// Replaces title placeholder with title for that collage
			htmlString = htmlString.replace("$topicString", collageTitles.get(collageIndex));
			
			ByteArrayOutputStream byteArrayOS; // Output stream to convert BufferedImage to ByteArray
			byte[] imageBytes; // ByteArray containing the BufferedImage in bytes
			String base64String; // Contains the string of the ByteArray in base64
			Vector<String> imageStringVector = new Vector<String>(); // Vector holding the collage images as strings
			
			// Loops through all collages
			for (int i = 0; i < collages.size(); i++) {
				byteArrayOS = new ByteArrayOutputStream();
				// Writes the BufferedImage to the output stream in PNG format
				ImageIO.write(collages.get(i), "png", byteArrayOS);
				// Converts the output stream to a ByteArray
				imageBytes = byteArrayOS.toByteArray();
				// Encoding of ByteArray
				imageBytes = Base64.getEncoder().encode(imageBytes);
				// Converts ByteArray to base64 string
				base64String = new String(imageBytes, "UTF-8");
				// Adds base64 string image to string vector
				imageStringVector.add(base64String);
			}
			// Creates HTML string to display img
			String displayImgString = "<img id=\"collage\" src" + "\"data:image/png;base64, " + imageStringVector.get(collageIndex) + "\"" + ">";
			// Replace image URL placeholder
			htmlString = htmlString.replace("$tableContents", displayImgString);
			
			/*  PREVIOUS COLLAGE SELECTOR  */
			String previousCollageURLs = "<table><tbody><tr>";
			// Iterates through image string vector
			for (int i = 0; i < imageStringVector.size(); i++) {
				// Does not add currently displayed collage to previous collage selector
				if (i == collageIndex) {
					continue;
				}
				else {
					// Replaces placeholder with previous collage's URL
					previousCollageURLs = previousCollageURLs + "<td><img id=\"previousCollage\" src=\"" + "data:image/png;base64, "
							+ imageStringVector.get(i) + "\"></td>";
				}
			}
			previousCollageURLs = previousCollageURLs + "</tr></tbody></table>";
			htmlString = htmlString.replace("$tableContents", previousCollageURLs);
			// Writes new htmlString to file
			//FileUtils.writeStringToFile(new File("WebContent/collageresult.html"), htmlString, StandardCharsets.UTF_8);
			// Return HTML String
			return htmlString;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
}
