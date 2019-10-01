import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Controller for the fish tagging game
 * Has variables model, view, score, drawDelay, drawAction,
 * 	timer, questionTimer, and frame
 * @author Elena
 */
@SuppressWarnings("serial")
public class FishController {
	// Instances of Model and View for Fish
	private FishModel model;
	private FishView view;
	
	// frame
	private JFrame frame;
	
	// General Timer
	private Timer timer;
	// Delay in the drawing
	private int drawDelay = 30;
	
	// Timer for optional question
	private Timer questionTimer;
	// delay between questions
	private int tenSeconds = 10000;
	
	// Action to be completed in the timer
	private Action drawAction;
	
	// are we in the tutorial?
	private boolean inTutorial;
	private boolean scored = false;
	private int score;
	
	// String representing current key being used
	private String currentKey = "";

	/**
	 * Constructor for FishController
	 * @param f takes in a starting JFrame
	 */
	public FishController(JFrame f) {
		// Grab the frame from the title screen
		frame = f;
		
		// Initialize model and view, passing in our frame
		view = new FishView(frame);
		model = new FishModel(view.getWidth(), view.getHeight(), view.getNetHeight(), view.getNetWidth());
		
		view.updateImages(model.getFishList());
		inTutorial = model.getInTutorial();
		
		// Add the keyListener to View
		view.addKeyInput(new KeyInput());

		// Create the action that occurs every cycle
		drawAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				// update the model with the current keypress
				model.update(currentKey);
				// update the view with info from the model
				view.update(model.getFishList(), model.getTargetFish(), model.getNetX(), model.getNetY(), model.getScore(), model.getGameOver());
				// if we are no longer in the tutorial
				if(inTutorial != model.getInTutorial()) {
					// update the images needed from the model's fishlist
					view.updateImages(model.getFishList());
					// end the tutorial
					inTutorial = model.getInTutorial();
					view.endTutorial();
				}
				
				// if all 8 fish species have been captured, stop timer 
				if(model.getGameOver()) {
					if (!scored){
						scored = true;
						Scoring.storeScore(model.getScore(), Scoring.FISH_FILE);
						Scoring.sortScores(Scoring.FISH_FILE);
					}
					timer.stop();
				}
			}
		};
		start();
	}
	
	// action that occurs every ten seconds
	ActionListener addQuestion = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			// display question mark if not already displayed, not in tutorial, no question
			// is currently displayed, and fish have been captured to base question off of
			if(!model.goldDisplayed && !model.questionDisplayed && !view.getInTutorial() && !model.getCapturedFish().isEmpty()) {
				model.addGold = true;
			}
		}
	};
	
	/**
	 * starts the timer and therefore the animation
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				timer = new Timer(drawDelay, drawAction);
				timer.start();
				
				// calls addQuestion action every ten seconds
				questionTimer = new Timer(tenSeconds, addQuestion);
				questionTimer.start();
			}
		});
	}
	
	/**
	 * KeyInput class that allows for user input
	 * The arrow keys move the player's net
	 * SPACE attempts the capture of a Fish
	 * ENTER stops the timer and moves to the next game
	 * @author Elena
	 */
	class KeyInput implements KeyListener{
		@Override
		public void keyPressed(KeyEvent keyEvent) {
			
			int code = keyEvent.getKeyCode();
			
			/*
			 * Allows for diagonal movements 
			 * in short, if one key is pressed, you can add another perpendicular one
			 */
			
			switch (code) {
				case 38: // Up arrow key
					if(currentKey.equals("left")) {
						currentKey = "up/left";
					}
					else if(currentKey.equals("right")) {
						currentKey= "up/right";
					}
					else {
						currentKey = "up";
					}
					break;
				case 37: // Left arrow key
					if(currentKey.equals("up")) {
						currentKey = "up/left";
					}
					else if(currentKey.equals("down")) {
						currentKey = "down/left";
					}
					else {
						currentKey = "left";
					}
					break;
				case 39: // Right arrow key
					if(currentKey.equals("up")) {
						currentKey = "up/right";
					}
					else if(currentKey.equals("down")) {
						currentKey = "down/right";
					}
					else {
						currentKey = "right";
					}
					break;
				case 40: // Down arrow key
					if(currentKey.equals("left")) {
						currentKey = "down/left";
					}
					else if (currentKey.equals("right")) {
						currentKey = "down/right";
					}
					else {
						currentKey = "down";
					}
					break;
				case 10: // Enter key
					if(model.getGameOver()) {
						MGController mgCon = new MGController(frame);
					}
					break;
				case 32: // SpaceBar
					model.checkCollisions();
					break;
				case 81: // q - ENDS THE GAME (FOR TESTING ONLY)
					timer.stop();
					model.gameOver = true;
					break;
				default:
					break;
			}		
		}
		@Override
		public void keyReleased(KeyEvent keyEvent) {
			int code = keyEvent.getKeyCode();
			
			/*
			 * Whenever a key is released it removes it from the 
			 * current key string
			 */
			switch(currentKey) {
			case "":
				break;
			case "up":
			case "right":
			case "left":
			case "down":
				currentKey = "";
				break;
			case "up/right":
				if(code == 38) { // UP
					currentKey = "right";
				}
				else if(code == 39) { // RIGHT
					currentKey = "up";
				}
				break;
			case "up/left":
				if(code == 38) { // UP
					currentKey = "left";
				}
				else if(code == 37) { // LEFT
					currentKey = "up";
				}
				break;
			case "down/right":
				if(code == 40) { // DOWN
					currentKey = "right";
				}
				else if (code == 39) { // RIGHT
					currentKey = "down";
				}
				break;
			case "down/left":
				if(code == 40) { // DOWN
					currentKey = "left";
				}
				else if (code == 37) { // LEFT
					currentKey = "down";
				}
				break;
			default:
				currentKey = "";
			}
		}
		@Override
		public void keyTyped(KeyEvent keyEvent) {}
	}
}
