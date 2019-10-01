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
import javax.swing.*;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class TitleView extends JPanel{
	
	// Constants for the size of the view
	private int viewWidth;
	private int viewHeight;
	
	
	private BufferedImage background;
	
	private JFrame frame;
	private JButton startButton = new JButton("Start");
	
	public TitleView(JFrame f) {
		frame = f;
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		viewWidth = frame.getWidth();
		viewHeight = frame.getHeight();
		
		frame.getContentPane().removeAll();
		frame.setLayout(new BorderLayout());
		// Set up the button and add it to a panel
		startButton.setFont(new Font("Arial", Font.PLAIN, 40));
		
		JPanel buttonPanel = new JPanel(new GridLayout(1,2,4,4));
	    buttonPanel.add(startButton);	
	    
	    // Add the background image to a panel
		String backgroundLoc = "src/images/titlescreen.png";
		background = ImageHelper.createImage(backgroundLoc);
	    JPanel imagePanel = new JPanel (new GridLayout());
	    JLabel picLabel = new JLabel(new ImageIcon(background.getScaledInstance(viewWidth, viewHeight, Image.SCALE_SMOOTH)));
	    imagePanel.add(picLabel);
		
	    // Add the panels to the JFrame
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		frame.getContentPane().add(imagePanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		frame.setSize(viewWidth, viewHeight);
		frame.setVisible(true);
		frame.setFocusable(true);
	}
    // Action Listener for Start/Stop Button
    public void addStartListener(ActionListener start) {
    	startButton.addActionListener(start);
    }
}