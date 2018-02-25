import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;

public class CollageBuilder {

	public BufferedImage buildCollage(Vector<String> imageSource){
		// dummy return
		BufferedImage dummy = new BufferedImage(0, 0, 0);
		return dummy;
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
		Vector<BufferedImage> bi = new Vector<BufferedImage>();
		for(int i=0;i<imageSource.size();i++) {
			bi.add(ImageIO.read(new URL(imageSource.get(i))));
		}
		
		
		
		//example of how to do it
		//BufferedImage image = ImageIO.read(new URL(imageUrl));
		
		
		return bi;
	}
	
	
	
	
	//takes in an image and rotates it randomly -45 to 45 degrees
	public BufferedImage rotateImage(BufferedImage inImage) {
		
		
		
		return inImage;
	}
	
	
}
