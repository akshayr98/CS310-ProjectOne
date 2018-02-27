

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class tttServlet
 */
@WebServlet("/collageBuilderServlet")
public class CollageBuilderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public CollageBuilderServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Got into servlet");
		
		// ensuring that HttpSession exists
		HttpSession session = request.getSession(false);
		if(session == null) // if HttpSession does not exist, create one
		{
			System.out.println("NO SESSION");
			session = request.getSession(true);
			System.out.println("MADE A NEW SESSION");
		}
		
		// ensuring that a CollageManager exists
		if(session.getAttribute("collageManager")==null) // if CollageManager does not exist, create one
		{
			CollageManager collageManager = new CollageManager();
			session.setAttribute("collageManager", collageManager); // set session attribute to created CollageManager
		}
		CollageManager collageManager = (CollageManager) session.getAttribute("collageManager"); // access CollageManager from HttpSession
		
		// parsing user input
		String searchText = request.getParameter("searchtext"); // get search query from ajax call
		
		// obtaining images from google
		ImageSourcer imageSourcer = new ImageSourcer(); // instantiate ImageSourcer object
		Vector<String> imageSource = imageSourcer.getImages(searchText); // ImageSourcer.getImages(searchText) searches Google Images with searchText as the query
																		 // and returns a vector of URLs to top 30 image results.
																		 // If < 30 results found, returns null instead.
		
		boolean collageBuildingFailed = false; // flag to indicate whether the collage building failed
		
		// if the imageSourcer returned 30 image URLs
		if(imageSource != null && searchText != null && searchText.length() > 0)
		{
			//TODO: change the width and height
			CollageBuilder collageBuilder = new CollageBuilder(1920,1080); // instantiate CollageBuilder object

			
			BufferedImage collage = collageBuilder.buildCollage(imageSource);			// CollageBuilder.buildCollage(imageSource) builds a collage out of the 30
																						// images returned from the ImageSourcer and returns it as a BufferedImage.
			collageManager.insertCollage(searchText, collage); // insert searchText (which will serve as title of collage) and created collage into the CollageManager.
		}
		else // if imageSourcer returned null, trigger flag to indicate that collage building failed
		{
			collageBuildingFailed = true;
		}
		
		
		// construct HTML code to pass back to client as response text
		String responseText; // string to hold raw text representation of response text
		
		HTMLGenerator htmlGenerator = new HTMLGenerator(collageManager); // instantiate HTMLGenerator object. Takes CollageManager pointer in constructor.
		if(collageBuildingFailed)
		{
			responseText = htmlGenerator.generateHTML(searchText); // if collage building was unsuccesful, call the generateHTML(String) function, which will generate the HTML to display
													// all previous collages and indicate that the most recent collage building failed.
		}
		else // if collage building did not fail
		{
			responseText = htmlGenerator.generateHTML(); // if collage building was succesful, call the generateHTML() function, which will generate the HTML to display 
											// all previous collages with the most recent one in the main collage display area
		}
		
		// set response text settings
    	response.setContentType("text/plain");
    	response.setCharacterEncoding("UTF-8");
    	
    	// set response text to responseText string
    	response.getWriter().write(responseText);
    	
	} // end service

}
