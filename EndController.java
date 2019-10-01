import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class EndController {
	// Create instances of the Model and View classes
	private EndView eView;
	
	private JFrame frame;
	
	public EndController(JFrame f){
		frame = f;
		// Create new instances of Model and View
		eView = new EndView(frame);
		// Picks up on the button being clicked
		eView.addStartListener(new StartListener());
	}
	class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TitleController titleCon = new TitleController(frame);
		}
	}
}