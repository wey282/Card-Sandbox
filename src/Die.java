import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Die {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    public static final Font font = new Font("Cascadia", Font.PLAIN, 50);

    private float x, y;
    private int number = 6;


    public Die(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.yellow);
        g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
        g.setColor(Color.red);
        g.setFont(font);
        g.drawString(number + "", x + font.getSize()*0.15f, y + font.getSize()*0.7f);
    }

    public boolean inShape(float x, float y) {
        return x > this.x && this.x + WIDTH  > x 
            && y > this.y && this.y + HEIGHT > y;
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
}
