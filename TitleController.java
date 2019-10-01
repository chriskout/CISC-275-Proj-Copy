import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TitleController {
	// Create instances of the Model and View classes
	private TitleView tView;
	private boolean isStarted = false;
	
	private JFrame frame;
	
	public TitleController(JFrame f){
		frame = f;
		// Create new instances of Model and View
		tView = new TitleView(frame);
		
		tView.addStartListener(new StartListener());
	}
	class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			isStarted = !isStarted;
			FishController fgCon = new FishController(frame);
			fgCon.start();
		}
	}
}