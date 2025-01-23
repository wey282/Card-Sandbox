import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;
import java.util.ArrayList;

public class App {
    private static JFrame frame;
    private static JPanel panel;

    private static List<Card> cards;
    private static List<Die> dice;

    private static final int x = 100, y = 100;

    private static Card selectedCard;
    private static Die selectedDie;
    private static Point offset = new Point();
    
    public static void main(String[] args) throws Exception {
        panel = createPanel();
        frame = createFrame(panel);
        initCards();
        initDice();
        shuffle(cards);
        panel.repaint();
    }

    private static void initCards() {
        cards = new ArrayList<>();
        for (int type = 0; type < 4; type++) {
            for (int i = 1; i <= 13; i++) {
                cards.add(new Card(i, type, x, y, false));
            }
        }
        
    }
    private static void initDice() {
        dice = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            dice.add(new Die(x * (2+i), y));
        }
        
    }

    private static <T> void shuffle(List<T> l) {
        for (int i = 0; i < l.size(); i++) {
            int o = (int)(Math.random()*l.size());
            T temp = l.get(o);
            l.set(o, l.get(i));
            l.set(i, temp);
        }
    }
            
    private static JPanel createPanel() {
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(Color.black);
                g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());
                for (int i = cards.size()-1; i >= 0; i--) {
                    cards.get(i).draw(g2);
                }
                for (Die die : dice) {
                    die.draw(g2);
                }
            };
        };
        return panel;
    }
        
    private static JFrame createFrame(JPanel panel) {
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
        TouchHandler handler = new TouchHandler();
        frame.addMouseListener(handler);
        frame.addMouseMotionListener(handler);
        frame.addMouseWheelListener(handler);
        return frame;
    }

    public static void mousePressed(MouseEvent e) {
        final int x = e.getX(), y = e.getY();
        if (selectedDie == null && selectedCard == null) {
            for (int i = 0; i < dice.size(); i++) {
                Die cur = dice.get(i);
                if (cur.inShape(x, y)) {
                    offset.x = x - (int)cur.getX();
                    offset.y = y - (int)cur.getY();
                    selectedDie = cur;
                    selectedDie.set(0);
                    dice.remove(i);
                    dice.add(0, cur);
                    break;
                }
            }
        }
        else if (selectedDie != null) {
            selectedDie.touchEvent(x-offset.x, y-offset.y);
            panel.repaint();
        }
        if (selectedDie == null && selectedCard == null) {
            for (int i = 0; i < cards.size(); i++) {
                Card cur = cards.get(i);
                if (cur.inShape(x, y)) {
                    selectedCard = cur;
                    cards.forEach(c -> c.selected = false);
                    cur.selected = true;
                    offset.x = x - cur.getX();
                    offset.y = y - cur.getY();
                    cards.remove(i);
                    cards.add(0, cur);
                    break;
                }
            }
        }
        else if (selectedCard != null) {
            selectedCard.touchEvent(x-offset.x, y-offset.y);
            panel.repaint();
        }
    }

    public static void mouseReleased() {
        selectedCard = null;
        if (selectedDie != null) {
            selectedDie.roll();
            panel.repaint();
            selectedDie = null;
        }
    }

    public static void mouseClicked(MouseEvent e) {
        final int x = e.getX(), y = e.getY();
        for (int i = 0; i < cards.size(); i++) {
            Card cur = cards.get(i);
            if (cur.inShape(x, y)) {
                cur.flip();
                panel.repaint();
                break;
            }
        }
    }

    public static void mouseWheelMoved(MouseWheelEvent e) {
        if (selectedCard != null) {
            selectedCard.setAngle(e.getWheelRotation());
            panel.repaint();
        }
    }
}
