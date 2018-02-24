import java.awt.image.BufferedImage;
import java.util.Vector;

public class CollageManager {

	Vector<String> collageTitles;
	Vector<BufferedImage> collages;
	
	public void insertCollage(String title, BufferedImage collage)
	{
		collageTitles.addElement(title);
		collages.addElement(collage);
	}
	
	public Vector<String> getCollageTitles()
	{
		return collageTitles;
	}
	
	public Vector<BufferedImage> getCollages()
	{
		return collages;
	}
	
}
