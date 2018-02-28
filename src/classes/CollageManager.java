package classes;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class CollageManager {
	// Vector holding all titles of Collages in the session
	private Vector<String> collageTitles = new Vector<String>();
	// Vector holding all Collages in the session
	private Vector<BufferedImage> collages = new Vector<BufferedImage>();
	
	// Insert a collage and title into both vectors
	public void insertCollage(String title, BufferedImage collage) {
		collageTitles.add(title);
		collages.add(collage);
	}
	// Getter
	public Vector<String> getCollageTitles() {
		return collageTitles;
	}
	// Getter
	public Vector<BufferedImage> getCollages() {
		return collages;
	}	
}
