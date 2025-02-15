import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;
import java.util.ArrayList;

public class App {
    private static JFrame frame;
    private static JPanel panel;

    private static List<Card> cards;
    private static List<Die> dice;

    private static final int x = 500, y = 500;

    private static Card mainCard;
    private static List<Card> selectedCards = new ArrayList<>();
    private static Die selectedDie;
    private static Point offset = new Point();

    private static boolean selectingMultipleCards = false;
    private static boolean selectingExtraCard = false;

    private static MyCursor cursor;
    private static Image openHand;
    private static Image closedHand;
    private static Image defaultCursor;
    private static Cursor emptyCursor;

    private static Image background;

    private static int WIDTH;
    private static int HEIGHT;
    
    public static void main(String[] args) throws Exception {
        Card.loadImages();
        Die.loadImages();

        initCards();
        initDice();
        
        panel = createPanel();
        frame = createFrame(panel);

        // shuffle(cards);
        initCursors();
        
        generateBackground();
        frame.setCursor(emptyCursor);

        panel.repaint();
    }

    private static void initCards() {
        cards = new ArrayList<>();
        cards.add(new Joker(x, y, false, 0));
        cards.add(new Joker(x, y, false, 1));
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

    private static void initCursors() {
        cursor = new MyCursor();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        openHand = toolkit.getImage("icons/Open Hand.png");
        closedHand = toolkit.getImage("icons/Closed Hand.png");
        defaultCursor = toolkit.getImage("icons/Default Hand.png");
        Image image = toolkit.getImage("icons/empty.png");
        emptyCursor = toolkit.createCustomCursor(image , new Point(frame.getX(), 
                        frame.getY()), "img");
        cursor.image = defaultCursor;
    }

    private static <T> void shuffle(List<T> l) {
        for (int i = 0; i < l.size(); i++) {
            int o = (int)(Math.random()*l.size());
            T temp = l.get(o);
            l.set(o, l.get(i));
            l.set(i, temp);
        }
    }

    private static <T> void shuffle(List<T> l, int count) {
        for (int i = 0; i < l.size() && i < count; i++) {
            int o = (int)(Math.random()*count);
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
                if (background != null) {
                    g2.drawImage(background, 0, 0, null);
                }
                for (int i = cards.size()-1; i >= 0; i--) {
                    cards.get(i).draw(g2);
                }
                for (Die die : dice) {
                    die.draw(g2);
                }
                cursor.draw(g2);
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
        EventHandler handler = new EventHandler();
        frame.addMouseListener(handler);
        frame.addMouseMotionListener(handler);
        frame.addMouseWheelListener(handler);
        frame.addKeyListener(handler);
        return frame;
    }

    public static void mousePressed(MouseEvent e) {
        final int x = e.getX(), y = e.getY();
        if (selectedDie == null && selectedCards.isEmpty()) {
            for (int i = 0; i < dice.size(); i++) {
                Die cur = dice.get(i);
                if (cur.inShape(x, y)) {
                    cursor.image = closedHand;
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
        if (selectedDie == null && (selectedCards.isEmpty() || selectingMultipleCards || selectingExtraCard)) {
            for (int i = 0; i < cards.size(); i++) {
                Card cur = cards.get(i);
                if (cur.inShape(x, y)) {
                    if (selectedCards.isEmpty()) {
                        offset.x = x - cur.getX();
                        offset.y = y - cur.getY();
                        cards.forEach(c -> c.selected = false);
                        cur.selected = true;
                        mainCard = cur;
                        cursor.image = closedHand;
                    }
                    if (!cur.isHeld()) {
                        cards.remove(i);
                        cards.add(selectedCards.size(), cur);
                        selectedCards.add(cur);
                        selectingExtraCard = false;
                    }
                    cur.setHeld(true);
                    mainCard.setNumberOfHeldCards(selectedCards.size());
                    if (!selectingMultipleCards && !selectingExtraCard)
                        break;
                }
            }
        }
        for (Card card : selectedCards) {
            card.touchEvent(x-offset.x, y-offset.y);
        }

        
        cursor.p.x = e.getX();
        cursor.p.y = e.getY();
            
        panel.repaint();
    }

    public static void mouseReleased(MouseEvent e) {
        selectedCards.forEach(c -> c.setHeld(false));
        selectedCards.clear();
        if (mainCard != null)
            mainCard.setNumberOfHeldCards(0);
        mainCard = null;
        mouseMoved(e);
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
                if (!selectingMultipleCards)
                    break;
            }
        }
    }

    public static void mouseWheelMoved(MouseWheelEvent e) {
        for (Card card : selectedCards) {
            card.setAngle(e.getWheelRotation());
            panel.repaint();
        }
    }

    public static void keyPressed(String keyText) {
        if (keyText.equals("Shift")) 
            selectingMultipleCards = true;
    }

    public static void keyReleased(String keyText) {
        if (keyText.equals("Shift")) 
            selectingMultipleCards = false;
        else if (keyText.equals("Ctrl"))
            selectingExtraCard = true;
        else if (keyText.equals("S")) {
            shuffle(cards, selectedCards.size());
            panel.repaint();
        }
    }

    public static void mouseMoved(MouseEvent e) {
        boolean b = false;
        for (Card card : cards) 
            if (card.inShape(e.getX(), e.getY()))
                b = true;
        for (Die die : dice) 
            if (die.inShape(e.getX(), e.getY()))
                b = true;
        if (b) 
            cursor.image = openHand;
        else
            cursor.image = defaultCursor;
        cursor.p.x = e.getX();
        cursor.p.y = e.getY();
        panel.repaint();
    }

    private static void generateBackground() {
        Dimension size = Toolkit.getDefaultToolkit(). getScreenSize();
        WIDTH = (int)size.getWidth();
        HEIGHT = (int)size.getHeight();

        background = BackgroundGenerator.generate(WIDTH, HEIGHT);
    }
}
