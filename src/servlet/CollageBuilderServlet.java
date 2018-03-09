package servlet;

import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.imageio.ImageIO;

import java.util.Base64;

import classes.*;

/**
 * Collage Builder Servlet Implementation
 */
@WebServlet("/collageBuilderServlet")
public class CollageBuilderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CollageBuilderServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Ensuring that HttpSession exists
		HttpSession session = request.getSession(false);
		// If HttpSession does not exist, create one
		if(session == null) {
			session = request.getSession(true);
		}
		// Ensuring that a CollageManager exists
		if(session.getAttribute("collageManager") == null) {
			// If CollageManager does not exist, create one
			CollageManager collageManager = new CollageManager();
			session.setAttribute("collageManager", collageManager); // Set session attribute to created CollageManager
		}
		// Access CollageManager from HttpSession
		CollageManager collageManager = (CollageManager) session.getAttribute("collageManager");
		
		/**** ORIGINAL CODE ****/
		// // Parsing user input
		// String searchText = request.getParameter("searchText"); // Get search query from AJAX call

		// // Obtaining images from google
		// ImageSourcer imageSourcer = new ImageSourcer(); // Instantiate ImageSourcer object
		// Vector<String> imageSource = imageSourcer.getImages(searchText); // ImageSourcer.getImages(searchText) searches Google Images with searchText as the query
		// 																 // and returns a vector of URLs to top 30 image results.
		// 																 // If < 30 results found, returns null instead.
		
		// boolean collageBuildingFailed = false; // Flag to indicate whether the collage building failed
		
		// // If the ImageSourcer returned 30 image URLs
		// if(imageSource != null && searchText != null && searchText.length() > 0) {
		// 	int browserWidth = Integer.valueOf(request.getParameter("browserWidth").trim());
		// 	int browserHeight = Integer.valueOf(request.getParameter("browserHeight").trim());
		// 	CollageBuilder collageBuilder = new CollageBuilder(browserWidth, browserHeight); // Instantiate CollageBuilder object
			
		// 	BufferedImage collage = collageBuilder.buildCollage(imageSource);			// CollageBuilder.buildCollage(imageSource) builds a collage out of the 30
		// 																				// Images returned from the ImageSourcer and returns it as a BufferedImage.
		// 	collageManager.insertCollage(searchText, collage); // Insert searchText (which will serve as title of collage) and created collage into the CollageManager.
		// }
		// else { // If imageSourcer returned null, trigger flag to indicate that collage building failed
		
		// 	collageBuildingFailed = true;
		// }
		// // Result string to hold response text
		// String result = "";
		// if (collageBuildingFailed) {
		// 	result = "fail " + searchText;
		// } else {
		// 	result = "success";
		// }
		// response.setContentType("text/plain");
		// response.setCharacterEncoding("UTF-8");
		// response.getWriter().write(result);
		// session.setAttribute("collageManager", collageManager); // Set session attribute to created CollageManager
		// session.setAttribute("currentSearchText", searchText);
		/**** ORIGINAL CODE ****/



		/**** TESTING CODE ****/
		BufferedImage bImage = ImageIO.read(new File("/home/student/test.png")); //Get image from local directory
		String result = "success";

		collageManager.insertCollage("test", bImage);
		
		session.setAttribute("collageManager", collageManager);
		session.setAttribute("currentSearchText", searchText);

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result);
		/**** TESTING CODE ****/
	} // End service
	
	
}
