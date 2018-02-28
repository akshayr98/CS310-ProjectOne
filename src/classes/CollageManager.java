package classes;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class CollageManager {

	private Vector<String> collageTitles = new Vector<String>();
	private Vector<BufferedImage> collages = new Vector<BufferedImage>();
	private int indexOfCollageToDisplay;
	
	public void insertCollage(String title, BufferedImage collage) {
		collageTitles.add(title);
		collages.add(collage);
	}
	
	public Vector<String> getCollageTitles() {
		return collageTitles;
	}
	
	public Vector<BufferedImage> getCollages() {
		return collages;
	}
	
	public int getIndex() {
		return indexOfCollageToDisplay;
	}
	
}
