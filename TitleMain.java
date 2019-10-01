import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * creates the initial JFrame for the title screen
 * creates a new TitleController with the frame
 * @author Elena
 *
 */
@SuppressWarnings("serial")

public class TitleMain {
	private static JFrame frame;
	// Constants for the size of the view
	private static int viewWidth = 1500;
	private static int viewHeight = 900;

	public static void main(String[] args) {
		// Create and start a new Controller
        frame = new JFrame();
   		frame.setSize(viewWidth, viewHeight);
		TitleController con = new TitleController(frame);
		
	}
	
}