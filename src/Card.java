import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {
    public static final int DIAMONDS = 0;
    public static final int SPADES = 1;
    public static final int HEARTS = 2;
    public static final int CLUBS = 3;

    public static final int WIDTH = 126;
    public static final int HEIGHT = 176;

    public static Image closedImage;
    public static Image openedImage;
    public static Image selectedClosedImage;
    public static Image selectedOpenedImage;
    public static final Image[] blackNumberImages = new Image[13];
    public static final Image[] redNumberImages = new Image[13];
    public static Image clubsImage;
    public static Image spadesImage;
    public static Image heartsImage;
    public static Image diamondsImage;
    public static Image redJokerImage;
    public static Image blackJokerImage;

    public static final Font font = new Font("Cascadia", Font.PLAIN, 100);

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
        
        g.drawImage(revealed ? (selected ? selectedOpenedImage : openedImage) 
                             : (selected ? selectedClosedImage : closedImage), x, y, WIDTH, HEIGHT, null); 

        if (revealed) {
            if (type == DIAMONDS || type == HEARTS)
                g.drawImage(redNumberImages[number-1], x+WIDTH/8, y+WIDTH/8, WIDTH/2, WIDTH/2, null);
            else
                g.drawImage(blackNumberImages[number-1], x+WIDTH/8, y+WIDTH/8, WIDTH/2, WIDTH/2, null);
            switch (type) {
                case DIAMONDS:
                    g.drawImage(diamondsImage, x+WIDTH/6, y+HEIGHT/2, WIDTH/2, WIDTH/2, null);
                    break;
                case HEARTS:
                    g.drawImage(heartsImage, x+WIDTH/6, y+HEIGHT/2, WIDTH/2, WIDTH/2, null);
                    break;
                case CLUBS:
                    g.drawImage(clubsImage, x+WIDTH/6, y+HEIGHT/2, WIDTH/2, WIDTH/2, null);
                    break;
                case SPADES:
                    g.drawImage(spadesImage, x+WIDTH/6, y+HEIGHT/2, WIDTH/2, WIDTH/2, null);
                    break;
            }
        }
        if (numberOfHeldCards > 0) {
            g.setColor(Color.yellow);
            g.setFont(font);
            g.drawString(numberOfHeldCards + "", x, (int)(y + HEIGHT + 0.7*font.getSize()));
        }

        g.rotate(-angle, x+WIDTH/2, y+HEIGHT/2);
    }

    public boolean inShape(float x, float y) {
        return x + MyCursor.SIZE > this.x && this.x + WIDTH  > x 
            && y + MyCursor.SIZE > this.y && this.y + HEIGHT > y;
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

    public static void loadImages() throws IOException {
        closedImage = ImageIO.read(new File("sprites/Base/Closed Card.png"));
        openedImage = ImageIO.read(new File("sprites/Base/Open Card.png"));
        selectedClosedImage = ImageIO.read(new File("sprites/Base/Selected Closed Card.png"));
        selectedOpenedImage = ImageIO.read(new File("sprites/Base/Selected Open Card.png"));
        for (int i = 1; i < blackNumberImages.length+1; i++) {
            blackNumberImages[i-1] = ImageIO.read(new File("sprites/Numbers/b"+i+".png"));
        }
        for (int i = 1; i < redNumberImages.length+1; i++) {
            redNumberImages[i-1] = ImageIO.read(new File("sprites/Numbers/r"+i+".png"));
        }
        clubsImage = ImageIO.read(new File("sprites/Suits/Club.png"));
        spadesImage = ImageIO.read(new File("sprites/Suits/Spade.png"));
        heartsImage = ImageIO.read(new File("sprites/Suits/Heart.png"));
        diamondsImage = ImageIO.read(new File("sprites/Suits/Diamond.png"));
        redJokerImage = ImageIO.read(new File("sprites/Jokers/red.png"));
        blackJokerImage = ImageIO.read(new File("sprites/Jokers/black.png"));
    }
}
