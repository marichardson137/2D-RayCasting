import java.awt.Dimension;
import javax.swing.JFrame;

public class MyFrame{
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 700;
	
	public MyFrame() {
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setTitle("Please Work");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		MyPanel v = new MyPanel();
		MouseManager m = new MouseManager();
		
		frame.add(v);
		frame.addMouseListener(m);
		frame.addMouseMotionListener(m);
		
		frame.setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		new MyFrame();
	}

}
