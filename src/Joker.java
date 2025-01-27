import java.awt.Graphics2D;

import java.awt.Color;
import java.awt.Font;

public class Joker extends Card {
    private static final int RED = 0;
    private static final int BLACK = 1;

    public static final Font font = new Font("Cascadia", Font.BOLD, 50);

    public Joker(int x, int y, boolean revealed, int type) {
        super(0, type, x, y, revealed);
    }

    @Override
    public void draw(Graphics2D g) {
        int x = getX();
        int y = getY();
        g.rotate(getAngle(), x+WIDTH/2, y+HEIGHT/2);
        
        g.drawImage(isRevealed() ? (selected ? selectedOpenedImage : openedImage) 
                             : (selected ? selectedClosedImage : closedImage), x, y, WIDTH, HEIGHT, null); 

        if (isRevealed()) {
            if (getType() == RED)
                g.drawImage(redJokerImage, x, y, WIDTH, HEIGHT, null);
            if (getType() == BLACK)
                g.drawImage(blackJokerImage, x, y, WIDTH, HEIGHT, null);
        }
        if (getNumberOfHeldCards() > 0) {
            g.setColor(Color.yellow);
            g.setFont(Card.font);
            g.drawString(getNumberOfHeldCards() + "", x, (int)(y + HEIGHT + 0.7*Card.font.getSize()));
        }

        g.rotate(-getAngle(), x+WIDTH/2, y+HEIGHT/2);
    }
}
