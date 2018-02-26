import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;

public class CollageBuilder {
	
	private int browserHeight;
	private int browserWidth;
//	private Vector<String> imageSource;
	
	private int collageWidth;
	private int collageHeight;

	public CollageBuilder(int inBrowserWidth, int inBrowserHeight)
	{
		
		//set for now will change in the future
		browserWidth = 1920;
		browserHeight = 1080;
		
		// set collage dimensions as per stakeholder requirements
		this.collageWidth = (int) 0.7 * browserWidth;
		this.collageHeight = (int) 0.5 * browserHeight;
		
		// ensure that collage meets minimum size requirements
		this.collageWidth = Math.max(this.collageWidth, 800);
		this.collageHeight = Math.max(this.collageHeight, 600); 

	}
	
//	public CollageBuilder(int inBrowserHeight, int inBrowserWidth, Vector<String> inImageSource) {
//		browserHeight = inBrowserHeight;
//		browserWidth = inBrowserWidth;
//		imageSource = inImageSource;
//	}
//	//default constructor
//	public CollageBuilder() {
//		browserHeight = 0;
//		browserWidth = 0;
//		imageSource = null;
//	}
	
	public BufferedImage buildCollage(Vector<String> imageSource){
	//public BufferedImage buildCollage() {
		// dummy return
		BufferedImage dummy = new BufferedImage(0, 0, 0);
		return dummy;
	}
	
	
	//iterates through the vector and checks if all strings are null 
	//if one is null return false
	public boolean checkValid(Vector<String> imageSource) {
	//public boolean checkValid() {
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
//	public Vector<BufferedImage> grabbingImages() throws IOException{
		Vector<BufferedImage> bufferedImageVec = new Vector<BufferedImage>();
		for(int i=0;i<imageSource.size();i++) {
			bufferedImageVec.add(ImageIO.read(new URL(imageSource.get(i))));
		}
		
		
		
		//example of how to do it
		//BufferedImage image = ImageIO.read(new URL(imageUrl));
		
		
		return bufferedImageVec;
	}
	
	
	
	
	//takes in an image and rotates it randomly -45 to 45 degrees
	public BufferedImage rotateImage(BufferedImage inImage,int inDegrees) {
		
	    AffineTransform transform = new AffineTransform();
	    transform.rotate(Math.toRadians(inDegrees), inImage.getWidth()/2, inImage.getHeight()/2);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    inImage = op.filter(inImage, null);
		
		return inImage;
	}
	
	public Vector<Integer> generateDegrees(){
		Vector<Integer> degrees = new Vector<Integer>();
		Random rand = new Random();
		
		for(int i=0;i<30;i++) {
			int num = rand.nextInt(90)+1;
			num -= 45;
			degrees.add(num);
			
		}
		return degrees;
	}
	
	
}
