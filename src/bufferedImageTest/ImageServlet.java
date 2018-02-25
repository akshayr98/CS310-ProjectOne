package bufferedImageTest;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.Base64;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    	
    		
		System.out.println("doGet");
		// create a BufferedImage object to test with
		// can use any image file you want
		BufferedImage bImage = ImageIO.read(new File("/home/student/test.png"));
		
		// these 3 variables are responsible for passing the actual image
		ByteArrayOutputStream baos; // used to convert BufferedImage to ByteArray
		byte[] imageBytes; // contains BufferedImage in bytes
		String base64String; // contains string of ByteArray in base 64
		
		List<BufferedImage> BIList = new ArrayList<BufferedImage>();
		List<String> stringList = new ArrayList<String>();
		
		// a vector of buffered images
		for (int i = 0; i < 10; i++)
			BIList.add(bImage);
		
		// 1. Convert BufferedImage object to ByteArray object
		// 2. Convert ByteArray to base 64 ByteArray
		// 3. Store base 64 ByteArray object as String to stringList
		for (int i = 0; i < BIList.size(); i++) {
			baos = new ByteArrayOutputStream();
			ImageIO.write(bImage, "png", baos);
			imageBytes = baos.toByteArray();
			imageBytes = Base64.getEncoder().encode(imageBytes);
			base64String = new String(imageBytes, "UTF-8");
			stringList.add(base64String);
		}
		
		// pass stringList to jsp as JSON
		String json = new Gson().toJson(stringList);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("bufferedImageTest/bufferedImage.html");
	}

}
