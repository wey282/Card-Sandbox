import java.awt.*;

public class Card {
    public static final int DIAMONDS = 0;
    public static final int SPADES = 1;
    public static final int HEARTS = 2;
    public static final int CLUBS = 3;

    public static final int WIDTH = 63;
    public static final int HEIGHT = 88;

    public static final Font font = new Font("Cascadia", Font.PLAIN, 50);

    public boolean selected = false;

    private boolean revealed;
    private int x, y;
    private int number;
    private int type;
    private double angle;
    private boolean held;

    private int numberOfHeldCards = 0;


    public Card(int number, int type, int x, int y, boolean revealed) {
        this.revealed = revealed;
        this.x = x;
        this.y = y;
        this.type = type;
        this.number = number;
        this.angle = 0;
    }



    public void draw(Graphics2D g) {
        g.rotate(angle, x+WIDTH/2, y+HEIGHT/2);
        
        g.setColor(revealed ? Color.white : Color.red);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setStroke(new BasicStroke(5));
        if (selected)
            g.setColor(Color.yellow);
        else
            g.setColor(Color.black);
        g.drawRect(x, y, WIDTH, HEIGHT);
        if (revealed) {
            if (type == DIAMONDS || type == HEARTS)
                g.setColor(Color.red);
            else
                g.setColor(Color.black);
            g.setFont(font);
            String s = ""+number;
            if (number == 1) 
                s = "A";
            else if (number == 11)
                s = "J";
            else if (number == 12) 
                s = "Q";
            else if (number == 13)
                s = "K";
            g.drawString(s, x, y + (int)(font.getSize()*0.7));
            switch (type) {
                case DIAMONDS:
                    s = "♦";
                    break;
                case HEARTS:
                    s = "♥";
                    break;
                case CLUBS:
                    s = "♣";
                    break;
                case SPADES:
                    s = "♠";
                    break;
            }
            g.drawString(s, x+10, y + (int)(font.getSize()*1.5));
        }
        if (numberOfHeldCards > 0) {
            g.setColor(Color.yellow);
            g.setFont(font);
            g.drawString(numberOfHeldCards + "", x, (int)(y + HEIGHT + 0.7*font.getSize()));
        }

        g.rotate(-angle, x+WIDTH/2, y+HEIGHT/2);
    }

    public boolean inShape(float x, float y) {
        return x > this.x && this.x + WIDTH  > x 
            && y > this.y && this.y + HEIGHT > y;
    }

    public void touchEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumber() {
        return number;
    }

    public int getType() {
        return type;
    }

    public void flip() {
        revealed = !revealed;
    }

    public void setAngle(int wheelRotation) {
        angle += Math.PI*0.125*wheelRotation;
    }

    public boolean isHeld() {
        return held;
    }

    public void setHeld(boolean b) {
        held = b;
    }

    public void setNumberOfHeldCards(int numberOfHeldCards) {
        this.numberOfHeldCards = numberOfHeldCards;
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

    public boolean isSelected() {
        return selected;
    }

    public double getAngle() {
        return angle;
    }

    public int getNumberOfHeldCards() {
        return numberOfHeldCards;
    }
}
