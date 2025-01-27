import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

public class MyCursor {
    public Image image;
    public final Point p = new Point();

    public static final int SIZE = 128;
    
    public void draw(Graphics2D g) {
        g.drawImage(image, p.x, p.y, SIZE, SIZE, null);
    }
}
