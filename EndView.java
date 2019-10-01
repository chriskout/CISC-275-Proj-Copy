import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class EndView extends JPanel{
	private int viewWidth;
	private int viewHeight;

	private BufferedImage background;
	
	private JFrame frame;
	private JButton startButton = new JButton("Back to Home Screen");
	
	public EndView(JFrame f) {
		// Set defaults, set up frame
		frame = f;
		
		viewWidth = frame.getWidth();
		viewHeight = frame.getHeight();
		
		frame.getContentPane().removeAll();
		frame.setLayout(new BorderLayout());
		// Set up the button and add it to a panel
		startButton.setFont(new Font("Arial", Font.PLAIN, 40));
		
		JPanel buttonPanel = new JPanel(new GridLayout(1,2,4,4));
	    buttonPanel.add(startButton);	
	    
	    // Add the background image to a panel
		String backgroundLoc = "src/images/endscreen.png";
		background = createImage(backgroundLoc);
	    JPanel imagePanel = new JPanel (new GridLayout());
	    JLabel picLabel = new JLabel(new ImageIcon(background.getScaledInstance(viewWidth, viewHeight, Image.SCALE_SMOOTH)));
	    imagePanel.add(picLabel);
		
	    // Add the panels to the JFrame
		frame.getContentPane().setBackground(Color.CYAN);
		frame.getContentPane().add(imagePanel);
		frame.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		frame.setSize(viewWidth, viewHeight);
		frame.setVisible(true);
		frame.setFocusable(true);
	}
    // Action Listener for Start/Stop Button
    public void addStartListener(ActionListener start) {
    	startButton.addActionListener(start);
    }
 // Reads in images
 	private BufferedImage createImage(String loc) {
 		BufferedImage bufferedImage;
 		try {
 			bufferedImage = ImageIO.read(new File(loc));
 			return bufferedImage;
 		} catch(IOException e) {
 			e.printStackTrace();
 		}
 		return null;
 	}
}