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
		CollageBuilder cb = new CollageBuilder();
		//cb.singleImageTest(cb);
		cb.multiImageTest(cb);
	}
	
	private void multiImageTest(CollageBuilder cb) {
		ImageSourcer is = new ImageSourcer();
		Vector<String> imageSource = is.getImages("dummy");
		BufferedImage collage = cb.buildCollage(imageSource);
		
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(collage)));
		frame.pack();
		frame.setVisible(true);
	}
	
	private void singleImageTest(CollageBuilder cb) {
		Vector<String> urls = new Vector<String>();
		for(int i=0;i<30;i++) {
			urls.add("http://cdn.audubon.org/cdn/farfuture/RLIlWxqbInfEuN23V2H3hgR6R8M6O6BY47H_6m1ESE8/mtime:1497969000/sites/default/files/styles/hero_image/public/web_gbbc_sandhill_crane_3_bob-howdeshell_tn_2012_kk.jpg?itok=FfVIDhGx");
		}
		//urls.add(null);
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		try {
			BufferedImage bi = ImageIO.read(new URL(urls.get(0)));
			bi = cb.getScaledImage(bi, 200, 200);
		    
			frame.getContentPane().add(new JLabel(new ImageIcon(cb.rotateImage(bi,-45))));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.pack();
		frame.setVisible(true);

		//cb.buildCollage(urls);
	}
	
	private int browserHeight;
	private int browserWidth;
//	private Vector<String> imageSource;
	
	private int collageWidth;
	private int collageHeight;

	//public CollageBuilder(int inBrowserWidth, int inBrowserHeight)
	public CollageBuilder()
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
		// TYPE_INT_ARGB means 4 bytes per pixel with alpha channel
		BufferedImage canvas = new BufferedImage(collageWidth, collageHeight, BufferedImage.TYPE_INT_ARGB);
		
		// Create a Graphics2D object to composite images
		Graphics2D graphic = canvas.createGraphics();
		graphic.setColor(Color.white);
		graphic.fillRect(0, 0, collageWidth, collageHeight);
		
		if(checkValid(imageSource)) {
			try {
				Vector<BufferedImage> bufferedImageVec = grabbingImages(imageSource);
				int minDegree = 7;
				Vector<Integer> randDegrees = null;
				
				//generating random numbers until the mindegrees are less than 6
				while(minDegree>6) {
					randDegrees = generateDegrees();
					int indexOfSmallestDegree = getSmallestDegree(randDegrees);
					Collections.swap(randDegrees, 0, indexOfSmallestDegree);
//					for(int i=0;i<randDegrees.size();i++) {
//						System.out.println(randDegrees.get(i));
//					}
					minDegree = randDegrees.get(0);
				}
				double avgImgArea = collageWidth*collageHeight/20;
				double scaledWidth=0, scaledHeight=0;
				
				// TODO: change these placements
				int placeWidth=-50, placeHeight=-50;
				
				for (int i=0;i<bufferedImageVec.size();i++) {
					BufferedImage currImage = bufferedImageVec.get(i);
					int currW = currImage.getWidth(), currH = currImage.getHeight();
					BufferedImage finalImage;
					if(i==0) {										
						// calculate scaled area of the image
						scaledHeight = Math.sqrt(avgImgArea*20*currW/currH);
						scaledWidth = currW/currH*scaledHeight;
						
					}else if(i==1){
						// calculate scaled area of the image
						scaledHeight = Math.sqrt((avgImgArea/20)*currW/currH);
						scaledWidth = currW/currH*scaledHeight;
//						System.out.println("scaledHeight is " +scaledHeight);
//						System.out.println("scaledWidth is" + scaledWidth);
					}else {
						// calculate scaled area of the image
						scaledHeight = Math.sqrt(avgImgArea*currW/currH);
						scaledWidth = currW/currH*scaledHeight;
					}
					// scale and rotate the image accordingly
					finalImage = getScaledImage(currImage, (int)scaledWidth, (int)scaledHeight);
					finalImage = rotateImage(finalImage, randDegrees.get(i));	
					// place the image onto the canvas
					if(i==0) {
						
						graphic.drawImage(finalImage, -100,-100, null);
					}else {
						graphic.drawImage(finalImage, placeWidth, placeHeight, null);
					}
					
					// TODO: fix naive placement and hard coded constants
					if(i!=1||i!=0) {
						placeWidth += scaledWidth*3/4;
						if (placeWidth > collageWidth) {
							placeWidth = -50;
							placeHeight += scaledHeight*3/4;
						}
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return canvas;
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
		return bufferedImageVec;
	}
	
	//takes in an image and rotates it randomly -45 to 45 degrees
	public BufferedImage rotateImage(BufferedImage inImage,int inDegrees) {
	    /*AffineTransform transform = new AffineTransform();
	    transform.rotate(Math.toRadians(inDegrees), inImage.getWidth()/2, inImage.getHeight()/2);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    inImage = op.filter(inImage, null);
	    return inImage;*/
	    
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
		
		/*for (int i=0;i<30;i++) {
			System.out.println(degrees.get(i));
		}*/
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
