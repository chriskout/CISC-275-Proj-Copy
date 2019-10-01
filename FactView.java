import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FactView extends JFrame {
 	
	// size of fact frame
	private final int viewWidth = 1500;
	private final int viewHeight = 900;
	private JFrame factFrame;
	
	// label that contains fact string
	private JLabel factLabel;
	
	// delay before remove fact screen
	private int fiveSeconds = 5000;
	
	public FactView(String f) {
	
		// create frame and set its size
		factFrame = new JFrame();
		factFrame.setSize(viewWidth, viewHeight);
	 	
		// create label containing the fact
		factLabel = new JLabel(f, JLabel.CENTER);
		factLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		
		// add components to frame
		factFrame.getContentPane().setBackground(Color.WHITE);
		factFrame.add(factLabel);
		factFrame.setVisible(true);
		
		Timer t = new Timer(fiveSeconds, remove);
		t.start();
	}
	
	// occurs after five seconds of showing the fact
	ActionListener remove = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			removefactScreen();
		}
	};
	
	// sets the factFrame to not visible, revealing the fish game again
	public void removefactScreen() {
		factFrame.setVisible(false);
	}
}