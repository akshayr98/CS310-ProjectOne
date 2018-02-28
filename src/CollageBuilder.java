import java.awt.Color;
import java.util.Collections;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CollageBuilder {
	
	//TEST TO SEE IF THIS WORKS
	public static void main(String args[]) {
		//CollageBuilder cb = new CollageBuilder(1920,1080);
		CollageBuilder cb = new CollageBuilder(600,1000);
		cb.multiImageTest(cb);
	}
	
	private void multiImageTest(CollageBuilder cb) {
		ImageSourcer is = new ImageSourcer();
		Vector<String> imageSource = is.getImages("yellow");
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
	private static int imagePadding = 3;

	public CollageBuilder(int inBrowserWidth, int inBrowserHeight)
	{
		browserWidth = inBrowserWidth;
		browserHeight = inBrowserHeight;
	
		/* Collage width must be between 40 and 70% of the browser viewport width
		 * Collage height must be between 35 and 50% of the browser viewport height
		 * Set collage dimensions to the maximum possible percentage given in requirements
		 */
		this.collageWidth = (int) (0.7 * browserWidth);
		this.collageHeight = (int) (0.5 * browserHeight);
		
		/* Collage width must be at least 800px and height must be at least 600px
		 * Ensure that collage meets minimum size requirements
		 */
		this.collageWidth = Math.max(this.collageWidth, 800);
		this.collageHeight = Math.max(this.collageHeight, 600); 	
	}
	
	public BufferedImage buildCollage(Vector<String> imageSource){
		// Create TYPE_INT_ARGB means 4 bytes per pixel with alpha channel
		BufferedImage collage = new BufferedImage(collageWidth, collageHeight, BufferedImage.TYPE_INT_ARGB);
		
		// Create a Graphics2D object to composite images
		Graphics2D graphic = collage.createGraphics();
		graphic.setColor(Color.orange); //TODO: change to white
		graphic.fillRect(0, 0, collageWidth, collageHeight);
		
		if(checkValid(imageSource)) {
			try {
				Vector<BufferedImage> bufferedImageVec = grabbingImages(imageSource);
				int minDegree = 7;
				Vector<Integer> randDegrees = null;
				
				//generating random numbers until the mindegrees are less than 6
				while(minDegree>=1) {
					randDegrees = generateDegrees();
					int indexOfSmallestDegree = getSmallestDegree(randDegrees);
					Collections.swap(randDegrees, 0, indexOfSmallestDegree);
					minDegree = randDegrees.get(0);
				}
				double avgImgArea = collageWidth*collageHeight/20;
				double scaledWidth=0, scaledHeight=0;
				
				// TODO: change these placements
				int placeWidth=-(collageWidth/16), placeHeight=0;
				double avgImgAreaDifference = 0;
				System.out.println("Number of images:" + bufferedImageVec.size());
				for (int i=0;i<bufferedImageVec.size();i++) {
					BufferedImage currImage = bufferedImageVec.get(i);
					int currW = currImage.getWidth(); 
					int currH = currImage.getHeight();
					BufferedImage finalImage;
					if(i==0) {										
						// calculate scaled area of the image
						avgImgAreaDifference = (collageWidth*collageHeight) - avgImgArea;
						scaledHeight= collageHeight;
						scaledWidth= collageWidth;
					}else {
						// calculate scaled area of the image
						scaledHeight = Math.sqrt((avgImgArea-(avgImgAreaDifference/(bufferedImageVec.size()-1)))*(double)currW/currH);
						scaledWidth = (double)currW/currH*scaledHeight;
						
					}
					if(scaledWidth<=0) {
						System.out.println("scaled width");
						scaledWidth=1;
					}
					if(scaledHeight<=0) {
						System.out.println("scaled height");
						scaledHeight=1;
					}
					// scale and rotate the image accordingly
					finalImage = getScaledImage(currImage, (int)scaledWidth, (int)scaledHeight);
					finalImage = addPadding(finalImage);
					finalImage = rotateImage(finalImage, randDegrees.get(i));
					
					// place the image onto the canvas
					if(i==0) {
						graphic.drawImage(finalImage,0,0, null);
					} else {
						// TODO: fix naive placement and hard coded constants
						graphic.drawImage(finalImage, placeWidth, placeHeight, null);
					}
					if(i!=0) {
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
		}
		
		return collage;
	}
	
	//iterates through the vector and checks if all strings are null 
	//if one is null return false
	public boolean checkValid(Vector<String> imageSource) {
		boolean exists = true;
		for(int i=0;i<imageSource.size();i++){
			if(imageSource.get(i)==null) {
				exists = false;
			}
		}
		return exists;
	}
	
	//iterates through the the vector of urls, creating BufferedImage vector
	public Vector<BufferedImage> grabbingImages(Vector<String> imageSource) throws IOException{
		Vector<BufferedImage> bufferedImageVec = new Vector<BufferedImage>();
		//for(int i=0;i<imageSource.size();i++) 
		imageSource.parallelStream().forEach((urlString) ->
		{
			try {
				//URL url = new URL(imageSource.get(i));
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
				BufferedImage image = ImageIO.read(conn.getInputStream());
				if(image != null)
				{
					bufferedImageVec.add(image);
				}
			} catch(IOException e)
			{
				e.printStackTrace();
			}
		});
		return bufferedImageVec;
	}
	
	//takes in an image and rotates it randomly -45 to 45 degrees
	public BufferedImage rotateImage(BufferedImage inImage,int inDegrees) {
		double rad = Math.toRadians(inDegrees);
		double sin = Math.abs(Math.sin(rad)), cos = Math.abs(Math.cos(rad));
	    int w = inImage.getWidth(), h = inImage.getHeight();
	    
	    int rotWidth = (int)Math.floor(w*cos+h*sin), rotHeight = (int) Math.floor(h*cos + w*sin);
		
	    BufferedImage rotatedImage = new BufferedImage(rotWidth, rotHeight, 
	    		BufferedImage.TRANSLUCENT);
	    Graphics2D graphic = rotatedImage.createGraphics();
	    graphic.rotate(Math.toRadians(inDegrees), rotWidth/2, rotHeight/2);
	    graphic.drawImage(inImage, (rotWidth-w)/2, (rotHeight-h)/2, null);
        graphic.dispose();
        return rotatedImage;
	}
	
	public Vector<Integer> generateDegrees(){
		Vector<Integer> degrees = new Vector<Integer>();
		Random rand = new Random();
		
		for(int i=0;i<30;i++) {
			int num = rand.nextInt(91);
			num -= 45;
			degrees.add(num);
		}
		return degrees;
	}
	
	//getting smallest positive degree
	public int getSmallestDegree(Vector<Integer> inDegreeVec) {
		int min = 45;
		int index = -1;
		for(int i=0;i<inDegreeVec.size();i++) {
			if(min>inDegreeVec.get(i)&&inDegreeVec.get(i)>=0) {
				index = i;
				min = inDegreeVec.get(i);
			}
		}
		return index;
	}
	
	private BufferedImage addPadding(BufferedImage src) {
		int srcWidth = src.getWidth();
		int srcHeight = src.getHeight();
		int paddedWidth = srcWidth+imagePadding*2, paddedHeight = srcHeight+imagePadding*2;
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
		Graphics2D graphic = scaledImage.createGraphics();
		graphic.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphic.setTransform(scaleOp);
        graphic.drawImage(src, 0, 0, null);
        graphic.dispose();
		
        return scaledImage;
	}
	
	
}
