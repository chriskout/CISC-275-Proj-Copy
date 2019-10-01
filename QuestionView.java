import javax.swing.*;
import java.util.Random;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class QuestionView extends JFrame implements ActionListener {
	
	// size of question frame
	private final int viewWidth = 1500;
	private final int viewHeight = 900;
	private JFrame questionFrame;
	
	// label that contains question string
	private JLabel questionLabel;
	private JPanel buttonPanel;
	private JLabel text;
	
	// buttons displaying each possible choice
	private JButton one;
	private JButton two;
	private JButton three;
	private JButton four;
	
	// question type that was selected
	private FishType qt;
	// string on the button that the user selects
	private String selected;
	// is the selected button the correct one?
	static Boolean isCorrect;
	
	// delay before remove question screen
	private int threeSeconds = 3000;
	
	public QuestionView(FishType ft, String q,String choiceOne, String choiceTwo, String choiceThree, String choiceFour ) {
		qt = ft;
		// for mouse clicks
		MouseAdapter adapter = new MouseAdapter() {
		      @Override
		      public void mousePressed(MouseEvent e) {}
		      public void mouseEntered(MouseEvent e) {}
		    };
		
		// create frame and set its size
		questionFrame = new JFrame();
		questionFrame.setSize(viewWidth, viewHeight);
		
		// create label containing the question
		questionLabel = new JLabel(q, JLabel.CENTER);
		questionLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		
		// create button panel and buttons for choices
		JPanel buttonPanel = new JPanel(new GridLayout(4,0));
		one = new JButton(choiceOne);
		two = new JButton(choiceTwo);
		three = new JButton(choiceThree);
		four = new JButton(choiceFour);
		
		// set font size for buttons
		one.setFont(new Font("Arial", Font.PLAIN, 40));
		two.setFont(new Font("Arial", Font.PLAIN, 40));
		three.setFont(new Font("Arial", Font.PLAIN, 40));
		four.setFont(new Font("Arial", Font.PLAIN, 40));
		
		// add action listener for buttons
		one.addActionListener(this);
		two.addActionListener(this);
		three.addActionListener(this);
		four.addActionListener(this);
		
		// add mouse listener to each button
		one.addMouseListener(adapter);
		two.addMouseListener(adapter);
		three.addMouseListener(adapter);
		four.addMouseListener(adapter);
		
		// add buttons to panel
		buttonPanel.add(one);
		buttonPanel.add(two);
		buttonPanel.add(three);
		buttonPanel.add(four);
		
		// add components to frame
		questionFrame.getContentPane().setBackground(Color.white);
		questionFrame.add(questionLabel);
		questionFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		questionFrame.setVisible(true);
	}
	
	// occurs once an answer is chosen and the correct/incorrect screen shows
	ActionListener remove = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			removeQuestionScreen();
		}
	};
	
	//
	@Override
	public void actionPerformed(ActionEvent e) {
		selected = getSelected(e);
		checkIfCorrect(selected,qt);
		questionFrame.getContentPane().removeAll();
		questionFrame.setBackground(Color.blue);
		if(isCorrect) { // set text to correct
			text = new JLabel("Correct!", JLabel.CENTER);
		}
		else { // set text to incorrect + display correct answer
			text = new JLabel("Incorrect! Answer was: " + qt.getDisplayName(), JLabel.CENTER);
		}
		text.setFont(new Font("Arial", Font.PLAIN, 50));

		// add the JLabel containing correct or incorrect
		questionFrame.add(text, JLabel.CENTER);
		questionFrame.setVisible(true);
		
		// new timer that calls remove after three seconds
		Timer t = new Timer(threeSeconds, remove);
		t.start();
	}
	 
	// gets which button was clicked and assigns the text content of the button to selectedAnswer
	public String getSelected(ActionEvent ae) {
		String selectedAnswer = "";
		if(ae.getSource() == one) {
			 selectedAnswer = one.getText();
		}
		else if (ae.getSource() == two) {
			 selectedAnswer = two.getText();
		}
		else if (ae.getSource() == three) {
			 selectedAnswer = three.getText();
		}
		else if (ae.getSource() == four) {
			 selectedAnswer = four.getText();
		}
		return selectedAnswer;
	}
	
	// checks if the chosen answer corresponds to the FishType of the question
	public Boolean checkIfCorrect(String input, FishType questionType) {
		if(input == qt.getDisplayName()) {
			isCorrect = true;
		}
		else {
			isCorrect = false;
		}
		return isCorrect;
	}
	
	// sets the questionFrame to not visible, revealing the fish game again
	public void removeQuestionScreen() {
		isCorrect = false;
		questionFrame.setVisible(false);
		
	}
	
	// getter for if the answer is correct
	public static Boolean getIsCorrect() {
		return isCorrect;
	}
}