import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseManager extends MouseAdapter {
    
    public static int mouseX, mouseY;
    public static boolean drawReady = false;

    private final int offsetY = 30;

    public MouseManager() {
        mouseX = 0;
        mouseY = 0;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY() - offsetY;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY() - offsetY;
    }
    
}
