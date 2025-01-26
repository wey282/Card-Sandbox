import java.awt.Graphics2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

public class Joker extends Card {
    private static final int RED = 0;
    private static final int BLACK = 1;

    public static final Font font = new Font("Cascadia", Font.BOLD, 25);

    public Joker(int x, int y, boolean revealed, int type) {
        super(0, type, x, y, revealed);
    }

    @Override
    public void draw(Graphics2D g) {
        int x = getX();
        int y = getY();
        g.rotate(getAngle(), x+WIDTH/2, y+HEIGHT/2);
        
        g.setColor(isRevealed() ? Color.white : Color.red);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setStroke(new BasicStroke(5));
        if (selected)
            g.setColor(Color.yellow);
        else
            g.setColor(Color.black);
        g.drawRect(x, y, WIDTH, HEIGHT);
        if (isRevealed()) {
            if (getType() == RED)
                g.setColor(Color.red);
            else
                g.setColor(Color.black);
            g.setFont(font);
            g.drawString("J", x + 5, y + (int)(font.getSize()*0.7));
            g.drawString("o", x + 5, y + (int)(font.getSize()*0.7)*2);
            g.drawString("k", x + 5, y + (int)(font.getSize()*0.7)*3);
            g.drawString("e", x + 5, y + (int)(font.getSize()*0.7)*4);
            g.drawString("r", x + 5, y + (int)(font.getSize()*0.7)*5);
            g.setFont(Card.font);
            g.drawString("🃏", x+20, y+HEIGHT/2 + (int)(font.getSize()*0.7));
        }
        if (getNumberOfHeldCards() > 0) {
            g.setColor(Color.yellow);
            g.setFont(Card.font);
            g.drawString(getNumberOfHeldCards() + "", x, (int)(y + HEIGHT + 0.7*Card.font.getSize()));
        }

        g.rotate(-getAngle(), x+WIDTH/2, y+HEIGHT/2);
    }
}
