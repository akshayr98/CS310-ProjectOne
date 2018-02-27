import java.awt.image.BufferedImage;
import java.util.Vector;

public class CollageManager {

	private Vector<String> collageTitles;
	private Vector<BufferedImage> collages;
	private int indexOfCollageToDisplay;
	
	public void insertCollage(String title, BufferedImage collage) {
		collageTitles.addElement(title);
		collages.addElement(collage);
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
