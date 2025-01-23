import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class TouchHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        App.mouseClicked(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        App.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        App.mouseReleased();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePressed(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        App.mouseWheelMoved(e);
    }

}
