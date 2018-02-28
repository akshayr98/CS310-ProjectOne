import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CollageBuilder {
	
	public static void main(String args[]) {
		CollageBuilder cb = new CollageBuilder(600,800);
		cb.multiImageTest(cb);
	}
	
	private void multiImageTest(CollageBuilder cb) {
		ImageSourcer is = new ImageSourcer();
		Vector<String> imageSource = is.getImages("elon musk");
		BufferedImage collage = cb.buildCollage(imageSource);
		
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(collage)));
		frame.pack();
		frame.setVisible(true);
	}
	
	private int browserHeight;
	private int browserWidth;
	
	private int collageWidth;
	private int collageHeight;
	
	// Given constant values from requirements
	private static int imagePadding = 3; // Each image has 3px white padding
	private static double imageRatio = 1/20.0; // Each image is 1/20 of the image size
	private static int numImages = 30;

	public CollageBuilder(int inBrowserWidth, int inBrowserHeight)
	{
		browserWidth = inBrowserWidth;
		browserHeight = inBrowserHeight;
	
		/* Collage width must be between 40 and 70% of the browser viewport width
		 * Collage height must be between 35 and 50% of the browser viewport height
		 * Set collage dimensions to the maximum possible percentage given in requirements
		 */
		this.collageWidth = (int) (0.7*browserWidth);
		this.collageHeight = (int) (0.5*browserHeight);
		
		/* Collage width must be at least 800px and height must be at least 600px
		 * Ensure that collage meets minimum size requirements
		 */
		this.collageWidth = Math.max(this.collageWidth, 800);
		this.collageHeight = Math.max(this.collageHeight, 600); 	
	}
	
	/*
	 * Generates a collage from given vector of image URLs as a BufferedImage
	 */
	public BufferedImage buildCollage(Vector<String> imageSource){
		// Create a BufferedImage "canvas" to composite all other images on
		BufferedImage collage = new BufferedImage(collageWidth, collageHeight, BufferedImage.TYPE_INT_ARGB);
		
		// Create a Graphics2D object to composite images
		Graphics2D g2d = collage.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, collageWidth, collageHeight);
		
		try {
			// Convert image URLs to BufferedImages
			Vector<BufferedImage> bufferedImageVec = urlsToBufferedImages(imageSource);
			Vector<Integer> randDegrees = null;
			System.out.println("Number of images:" + bufferedImageVec.size());
			
			// Generate random values until there is at least one zero present
			int minDegree = 1;
			while (minDegree > 0) {
				randDegrees = generateDegrees();
				int indexOfZero = randDegrees.indexOf(0);
				if (indexOfZero > -1) {
					Collections.swap(randDegrees, 0, indexOfZero);
					minDegree = 0;
				}
			}
			
			double avgImgArea = collageWidth*collageHeight*imageRatio;
			double scaledWidth = 0, scaledHeight = 0;
			int placeWidth = 0, placeHeight = 0;
			double avgImgAreaDifference = 0;
			
			// Iterate through each image to scale, rotate, apply padding, and place onto canvas
			for (int i = 0; i < bufferedImageVec.size(); i++) {
				BufferedImage currImage = bufferedImageVec.get(i);
				int currW = currImage.getWidth(); 
				int currH = currImage.getHeight();
				
				BufferedImage finalImage;
				int numNullImages = numImages-bufferedImageVec.size();
				if (i == 0) { // Scale the image to fill the entire collage space
					avgImgAreaDifference = (collageWidth*collageHeight)-(avgImgArea*(numNullImages+1));
					scaledHeight = collageHeight;
					scaledWidth = collageWidth;
				} else { // Scale the rest of the images to fulfill 1/20 avg requirement
					scaledHeight = Math.sqrt((avgImgArea-(avgImgAreaDifference/((bufferedImageVec.size()-1))))*(double)currW/currH);
					scaledWidth = (double)currW/currH*scaledHeight;
				}
				// Scale, add padding, and rotate the image
				finalImage = getScaledImage(currImage, (int) scaledWidth, (int) scaledHeight);
				finalImage = addPadding(finalImage);
				finalImage = rotateImage(finalImage, randDegrees.get(i));
				
				// Place the image onto the canvas
				g2d.drawImage(finalImage, placeWidth, placeHeight, null);
				
				// Move the placement location for the next photo
				if (i != 0) {
					placeWidth += scaledWidth;
					if (placeWidth > collageWidth-scaledWidth) {
						placeWidth = 0;
						placeHeight += scaledHeight;
					}
					if (placeHeight > collageHeight-scaledHeight) {
						placeHeight = (int) (scaledWidth/2);
						placeWidth = 0;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return collage;
	}
	
	// Creates a vector of BufferedImages from given image URLs
	public Vector<BufferedImage> urlsToBufferedImages(Vector<String> imageSource) throws IOException {
		Vector<BufferedImage> bufferedImageVec = new Vector<BufferedImage>();
		
		// Parallelize getting images from URL string
		imageSource.parallelStream().forEach((urlString) ->
		{
			URL url;
			HttpURLConnection conn = null;
			BufferedImage image = null;
			try {
				url = new URL(urlString);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
				image = ImageIO.read(conn.getInputStream());
				
				if(image != null)
				{
					bufferedImageVec.add(image);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return bufferedImageVec;
	}
	
	// Rotates the given image by the given number of degrees and returns BufferedImage
	public BufferedImage rotateImage(BufferedImage src,int inDegrees) {
		// Calculate the width and height of the rotated image
		double rad = Math.toRadians(inDegrees);
		double sin = Math.abs(Math.sin(rad)), cos = Math.abs(Math.cos(rad));
	    int srcWidth = src.getWidth(), srcHeight = src.getHeight();
	    int rotWidth = (int)Math.floor(srcWidth*cos+srcHeight*sin), rotHeight = (int) Math.floor(srcHeight*cos + srcWidth*sin);
		
	    // Rotate the image using Graphics2D
	    BufferedImage rotatedImage = new BufferedImage(rotWidth, rotHeight, 
	    		BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = rotatedImage.createGraphics();
	    g2d.rotate(Math.toRadians(inDegrees), rotWidth/2, rotHeight/2);
	    g2d.drawImage(src, (rotWidth-srcWidth)/2, (rotHeight-srcHeight)/2, null);
        g2d.dispose();
        return rotatedImage;
	}
	
	// Generate a vector of random integers between -45 and 45 inclusive
	public Vector<Integer> generateDegrees(){
		Vector<Integer> degrees = new Vector<Integer>();
		Random rand = new Random();
		for(int i = 0; i < 30; i++) {
			int num = rand.nextInt(91);
			num -= 45;
			degrees.add(num);
		}
		return degrees;
	}
	
	// Add white padding of imagePadding pixels around given image
	private BufferedImage addPadding(BufferedImage src) {
		int srcWidth = src.getWidth(), srcHeight = src.getHeight();
		int paddedWidth = srcWidth+imagePadding*2, paddedHeight = srcHeight+imagePadding*2;
		
		// Create a white rectangle based on calculated width and height and draw image on top
		BufferedImage paddedImage = new BufferedImage(paddedWidth, paddedHeight, src.getType());
		Graphics2D g2d = paddedImage.createGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, paddedWidth, paddedHeight);
		g2d.drawImage(src, imagePadding, imagePadding, null);
		g2d.dispose();
		return paddedImage;
	}
	
	// scales the image based on desired width and height ratio of original image
	private BufferedImage getScaledImage(BufferedImage src, int width, int height){
		int srcWidth = src.getWidth();
		int srcHeight = src.getHeight();
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		
		// Scale image according to the ratio of the original image to new scaled image
		AffineTransform scaleOp = new AffineTransform();
		scaleOp.scale(width/(double)srcWidth, height/(double)srcHeight);
	
		// Use Graphics2D to complete the scaling
		Graphics2D g2d = scaledImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setTransform(scaleOp);
        g2d.drawImage(src, 0, 0, null);
        g2d.dispose();
		
        return scaledImage;
	}
	
	
}
