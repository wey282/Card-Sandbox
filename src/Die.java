import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Die {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;

    public static final Font font = new Font("Cascadia", Font.PLAIN, 100);

    private float x, y;
    private int number = 6;

    private static final Image[] images = new Image[7];

    public Die(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g) {
        g.drawImage(images[number], (int)x, (int)y, WIDTH, HEIGHT, null);
    }

    public boolean inShape(float x, float y) {
        return x + MyCursor.SIZE > this.x && this.x + WIDTH  > x 
            && y + MyCursor.SIZE > this.y && this.y + HEIGHT > y;
    }

    public void touchEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void roll() {
        number = (int)(Math.random()*6)+1;
    }

    public void set(int n) {
        number = n;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static Font getFont() {
        return font;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getNumber() {
        return number;
    }

    public static void loadImages() throws IOException {
        for (int i = 0; i <= 6; i++) {
            images[i] = ImageIO.read(new File("sprites/Dice/"+i+".png"));
        }
    }
}
