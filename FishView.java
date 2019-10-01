import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The view for the fishGame. Contains all of the visuals for the fishgame. *
 */
@SuppressWarnings("serial")
public class FishView extends JPanel implements Serializable {
	// The current frame
	JFrame frame;
	
	// Constants for View Size
	private int viewWidth;
	private int viewHeight;
	private int defaultWidth = 1500;
	
	// The height of the content pane
	private int contentHeight;
	
	// Fishes
	private ArrayList<Fish> fishList = new ArrayList<Fish>();
	// target fish (displayed in upper right corner)
	private Fish targetFish;
	// String of the target fish type
	private String targetSpecies;
	
	// Player Variables
	private int playerX;
	private int playerY;
	
	// Images
	private BufferedImage net;
	private BufferedImage spacebar;
	private BufferedImage arrowkeys;
	private BufferedImage pointer;
	private BufferedImage questionmark;
	private BufferedImage background;
	private HashMap<String, ArrayList<BufferedImage>> fishImages = new HashMap<>();
	private BufferedImage target;

	// Transparent Color
	private Color transparent = new Color(0,0,0,0);
	
	// Current Score
	private int score = 0;
	
	// Game - Specific options
	private boolean inTutorial;
	private boolean gameOver;
	
	/**
	 * Constructor for the fishView - sets up everything for the fishGame
	 * @param f	The JFrame containing the panel
	 */
	public FishView(JFrame f) {
		// Set the frame
		frame = f;
		// Set the height and width from the frame
		viewWidth = frame.getWidth();
		viewHeight = frame.getHeight();
		
		// Image locations
		String playerImageLoc = "src/images/animals/net.png";
		String backgroundLoc = "src/images/animals/background.png";
		String arrowKeyLoc = "src/images/arrowkeys.png";
		String spaceBarLoc = "src/images/spacebar.png";
		String arrowLoc = "src/images/pointerarrow.png";
		String questionLoc = "src/images/animals/question.png";
		
		// Scale the net
		net = ImageHelper.createImage(playerImageLoc);
		double scale = (double) viewWidth / defaultWidth;
		int nWidth = (int)(net.getWidth() * scale);
		int nHeight = (int)(net.getHeight() * scale);
		net = ImageHelper.scaleImage(net, nWidth, nHeight);

		// Create and scale the background
		background = ImageHelper.createImage(backgroundLoc);
	 	background = ImageHelper.scaleImage(background, viewWidth, viewHeight);
	 	
	 	// Create and scale images for the arrow keys
	 	arrowkeys = ImageHelper.createImage(arrowKeyLoc);
	 	int aWidth = (int) (arrowkeys.getWidth() * scale);
	 	double vertScale = ((double) aWidth / arrowkeys.getWidth());
	 	int aHeight = (int) (arrowkeys.getHeight() * vertScale);
	 	arrowkeys = ImageHelper.scaleImage(arrowkeys, aWidth, aHeight);
	 	
	 	// Create and scale images for the space bar
	 	spacebar = ImageHelper.createImage(spaceBarLoc);
	 	int sWidth = (int) (spacebar.getWidth() * scale);
	 	vertScale = ((double) sWidth / spacebar.getWidth());
	 	int sHeight = (int) (spacebar.getHeight() * vertScale);
	 	spacebar = ImageHelper.scaleImage(spacebar, sWidth, sHeight);

	 	// Create and scale the arrow
	 	pointer = ImageHelper.createImage(arrowLoc);
	 	int pWidth = (int) (pointer.getWidth() * scale);
	 	vertScale = ((double) pWidth / pointer.getWidth());
	 	int pHeight = (int) (pointer.getHeight() * vertScale);
	 	pointer = ImageHelper.scaleImage(pointer, pWidth, pHeight);
	 	
	 	// Create and scale the question mark
	 	questionmark = ImageHelper.createImage(questionLoc);
	 	int qWidth = (int) (viewWidth / 12);
	 	vertScale = ((double) qWidth / questionmark.getWidth());
	 	int qHeight = (int) (questionmark.getHeight() * vertScale);
	 	questionmark = ImageHelper.scaleImage(questionmark, qWidth, qHeight);
	 	
	 	// game states
		inTutorial = true;
		gameOver = false;

		// Remove all previous panels from the frame
		frame.getContentPane().removeAll();
		// Add this game to the jFrame and focus on it
		frame.setBackground(Color.white);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(viewWidth, viewHeight);
		frame.setVisible(true);
		frame.setFocusable(true);
		
		contentHeight = frame.getContentPane().getHeight();
		// Set focus on the panel
		this.requestFocus();		
	}
	
	/**
	 * Updates images after changes to fish. Called after the tutorial is finished
	 * @param fish	The list of fish in the game
	 */
	public void updateImages(ArrayList<Fish> fish) {
		// Creates the fish images
		 importFishImages();
		 
		 for(Fish f: fish) {
			 // Updates the Fish based on the sizes of their images
			 f.accountForTitleBars(viewHeight - contentHeight); // account for the title bars to keep fish onscreen
			 f.setImageWidth(fishImages.get(f.getSpecies().name()).get(0).getWidth());
			 f.setImageHeight(fishImages.get(f.getSpecies().name()).get(0).getHeight());
		 }
	}
	
	/**
	 * Update is called on every timer tick and updates the display
	 * @param fish		The updated list of fish
	 * @param target	The target fish
	 * @param pX		The player's x location
	 * @param pY		The player's y location
	 * @param sc		The current score
	 * @param gO		A boolean denoting if the game is over yet or not 
	 */
	public void update(ArrayList<Fish> fish, Fish target, int pX, int pY, int sc, boolean gO) {
		// update the player's position
		playerX = pX;
		playerY = pY;
		// Set the score
		score = sc;
		// Receive any changes made to the fish
		fishList = fish;
		// update target fish
		targetFish = target;
		// Repaint the frame with the new information
		gameOver = gO;
		repaint();
	}
	
	protected void paintComponent(Graphics g) {
		// Draw the background image
		g.drawImage(background, 0, 0, Color.black, this);

		// If the game is over
		if(gameOver) {
			double fontScale = (double)1 / 15;
			int fontSize = (int) (viewWidth * fontScale);
			g.setFont(new Font("ARIAL", Font.PLAIN, fontSize));
			g.setColor(Color.white);
			double offsetScale = (double) fontSize / 100;
			
			// Draw Strings 
			g.drawString("Thanks for helping us tag!", viewWidth / 2 - (int)(625 * offsetScale), viewHeight / 3);
			g.setFont(new Font("ARIAL", Font.PLAIN, fontSize / 2));
			g.drawString("Final Score: " + score, viewWidth / 2 - (int)(300 * offsetScale), (int)(viewHeight * 0.45));
			g.setFont(new Font("ARIAL", Font.PLAIN, fontSize / 3));
			g.drawString("High Scores:",viewWidth / 2 - (int)(300 * offsetScale), (int)(viewHeight * 0.5));

			// Read in previous high scores from the fish file and display them
			String scoringText = Scoring.scoreString(Scoring.FISH_FILE);
			int textY = (int)(viewHeight * (0.5));
			for (String line : scoringText.split("\n"))
				g.drawString(line, viewWidth / 2 - (int)(300 * offsetScale), textY += g.getFontMetrics().getHeight());
			
			g.setFont(new Font("ARIAL", Font.PLAIN, fontSize / 2));
			g.drawString("Press ENTER to continue.", viewWidth / 2 - (int) (300 * offsetScale), viewHeight * 2 / 3 + 2 * fontSize);
		}
		// If we are in the game or in the tutorial
		else {			
			double fontScale = (double)1 / 50;
			int fontSize = (int) (viewWidth * fontScale);

			g.setFont(new Font("ARIAL", Font.PLAIN, fontSize));
			g.setColor(Color.white);

			// Used for placing images
			int fontHeight = g.getFontMetrics().getHeight();

			// if we are in the tutorial, render the helper images and test 
			if(inTutorial) {
				// Arrow key image and explainer
				g.drawImage(arrowkeys, viewWidth / 10, 0, transparent, this);
				g.drawString("Use the arrow keys to move the net!", fontHeight, arrowkeys.getHeight() + fontHeight);
				// Space bar image and explainer
				g.drawImage(spacebar, viewWidth / 13, viewHeight / 3, transparent, this);
				g.drawString("Catch fish using the spacebar.", fontHeight, viewHeight / 3 + spacebar.getHeight() + g.getFontMetrics().getHeight());
				// Question mark image and explainer
				g.drawImage(questionmark, viewWidth / 8 , viewHeight * 2 / 3, transparent, this);
				g.drawString("Catch the question marks to test", fontHeight, viewHeight * 2 / 3  + questionmark.getHeight() + fontHeight);
				g.drawString("your knowledge.", fontHeight, viewHeight * 2 / 3 + fontHeight * 2 + questionmark.getHeight());
				// Pointer image and explainer
				g.drawImage(pointer, viewWidth - pointer.getWidth(), fontSize * 6, transparent, this);
				g.setFont(new Font("ARIAL", Font.PLAIN, (int)  (fontSize * 1.5)));
				g.setColor(Color.yellow);
				String findFish = "Go find this fish!";
				g.drawString(findFish, viewWidth -g.getFontMetrics().stringWidth(findFish)- fontSize, fontSize * 14);
 
				// Reset font before displaying the rest
				g.setFont(new Font("ARIAL", Font.PLAIN, fontSize));
				g.setColor(Color.white);
			}

			// displays target fish image and name in upper right hand corner
			if (targetFish != null && targetFish.getSpecies() != FishType.GOLD) {
				g.setFont(new Font("Arial", Font.PLAIN, 4 * fontSize / 5));
				fontHeight = g.getFontMetrics().getHeight();
				String dispName = "Target Fish: " + targetFish.getSpecies().getDisplayName();
				g.drawString(dispName, viewWidth - g.getFontMetrics().stringWidth(dispName) - fontHeight, fontHeight);
				
				// If we have a new target fish, load in its image
				if(!targetFish.getSpecies().getName().equals(targetSpecies)) {					
					String imgLoc = "src/images/animals/" + targetFish.getSpecies().getName() + "_large.png";
					target = ImageHelper.createImage(imgLoc);
					
					// scaling
					int newWidth = viewWidth / 8;
					double scale = (double) newWidth / target.getWidth();
					int newHeight = (int) (target.getHeight() * scale);
					target = ImageHelper.scaleImage(target, newWidth, newHeight);
					targetSpecies = targetFish.getSpecies().getName();
				}
				g.drawImage(target, viewWidth - target.getWidth() - fontSize, fontSize * 2, transparent, this);
			}
			
			// Display the score if we aren't in the tutorial
			if(!inTutorial) {
				String scoreStr = "Score: " + score; 
				g.drawString(scoreStr , 10, 10 + fontHeight / 2);
			}				

			// Draw each fish in the list
			for(Fish f: fishList) {
				if(f.species.equals(FishType.GOLD)) {
					f.setImageWidth(fishImages.get(FishType.GOLD.name()).get(0).getWidth());
					f.setImageHeight(fishImages.get(FishType.GOLD.name()).get(0).getHeight());
				}
				BufferedImage img;
				switch(f.getDirection()) {
				case NORTHEAST:
				case EAST:
				case SOUTHEAST:
				    if (f.species != FishType.GOLD)
					    img = fishImages.get(f.getSpecies().name()).get(1);
				    else img = fishImages.get(f.getSpecies().name()).get(0);
					break;
				case NORTHWEST:
				case WEST:
				case SOUTHWEST:
					img = fishImages.get(f.getSpecies().name()).get(0);
					break;
				default:
					img = null;
				}
				g.drawImage(img, f.getXLoc(), f.getYLoc(), transparent, this);
			}
			// Draw the net
			g.drawImage(net, playerX, playerY, transparent, this);
		}
	}

	/**
	 * Imports images of all of the fish
	 */
	public void importFishImages() {
		int defaultViewWidth = 1500;
		for(FishType species : FishType.values()) {
			String imgLoc = "src/images/animals/" + species.getName() + "_large.png";
			BufferedImage img = ImageHelper.createImage(imgLoc);
			
			// Scale the image
			double fishScale = species.getSize();
			double viewScale = (double)viewWidth / defaultViewWidth;
			int fishWidth = (int) (img.getWidth() * fishScale * viewScale);
			int fishHeight = (int) (img.getHeight() * fishScale * viewScale);
	
			//int imageWidth = (int)(fishWidth * fishScale * viewScale);
			//int imageHeight = (int) (fishHeight * fishScale * viewScale);
			
			// add the image and its reverse into an arraylist
			ArrayList<BufferedImage> lr = new ArrayList<>();
			lr.add(ImageHelper.scaleImage(img, fishWidth, fishHeight));
			lr.add(ImageHelper.flipImage(lr.get(0)));
			fishImages.put(species.name(), lr);
		}
	}
	
	/**
	 * Adds a KeyListener to our frame
	 * @param kL	The keyListener to add
	 */
	public void addKeyInput(KeyListener kL) {
		this.addKeyListener(kL);
	}
	/**
	 * Finishes the tutorial
	 */
	public void endTutorial() {
		inTutorial = false;
	}
	
	// Getters
	public int getWidth() {
		return viewWidth;
	}
	public int getHeight() {
		return viewHeight;
	}
	public int getNetWidth() {
		return net.getWidth();
	}
	public int getNetHeight() {
		return net.getHeight();
	}
	public Boolean getInTutorial() {
		return inTutorial;
	}
}
