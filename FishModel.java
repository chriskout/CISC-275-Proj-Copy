import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * The model for our tagging game. This class contains all of the logic required 
 * for the fish and the net to move and interact.
 */
@SuppressWarnings("serial")
public class FishModel implements Serializable {
	// ArrayList of current fish to be displayed
	ArrayList<Fish> fishes = new ArrayList<>();
	
	// ArrayList containing past-used target fish
	ArrayList<Fish> capturedFish = new ArrayList<>(); 

	// Fish to be captured by player
	Fish targetFish;
	
	// Frame Size
	int frameWidth;
	int frameHeight;
	
	// Location and size of the net
	int netX = 0;
	int netWidth;
	int netY = 0;
	int netHeight;
	
	// Bounds of the screen
	int topBound = 0;
	int leftBound = 0;
	
	// gold question mark
	Fish goldenFish;
	// should a question mark be added?
	boolean addGold;
	// is a question mark already on screen?
	boolean goldDisplayed;
	// is the question frame currently up?
	boolean questionDisplayed;
	
	// Velocity of the net
	private int velocity = 0;
	
	// Changes the ability of the player to enter keystrokes 
	boolean playerInputAllowed;
	// Is the net currently dragging a fish to the surface?
	boolean grabbingFish;
	
	// Booleans denoting stages of the game
	boolean inTutorial;
	// is the game over>?
	boolean gameOver;
	// Current Player Score
	private int score;

    public static FishModel readFishModel(){
        FishModel model = (FishModel) SerializeHelper.read(SerializeHelper.FISH_FILE);
        return model;
    }

	/**
	 * This constructor creates a random set of fish and uses them
	 * @param width		The width of the JFrame
	 * @param height	The height of the JFrame
	 * @param nWidth	The width of the net
	 * @param nHeight	The height of the net
	 */

	public FishModel(int width, int height, int nWidth, int nHeight) {
		frameWidth = width;
		frameHeight = height;
		
		ArrayList<Fish> f = new ArrayList<Fish>();
		
		inTutorial = true;
		// tutorial fish
		Fish fs = new Fish(FishType.HOGCHOKER, Direction.EAST, frameWidth, frameHeight);
		fs.setTutorialFish();
		f.add(fs);
		
		// Setup the game using random fish
		setupGame(width, height, nWidth, nHeight, f);

        //store the current model in txt file, for later testing
        SerializeHelper.write(SerializeHelper.FISH_FILE, this);
    }
	// Wrapper for testing purposes, allows users to predefine a set of fish for the game
	
	/**
	 * This constructor allows users to predefine a set of fish to use. Used for testing purposes only.
	 * @param width		The width of the JFrame
	 * @param height	The height of the JFrame
	 * @param nWidth	The width of the net
	 * @param nHeight	The height of the net
	 * @param f			An arrayList of fish to be used
	 */
	public FishModel(int width, int height, int nWidth, int nHeight, ArrayList<Fish> f){
		// Setup the game with the included of fish
		setupGame(width, height, nWidth, nHeight, f);
	}
	
	/**
	 * Sets up the game, using the inputed variables.
	 * @param width		The width of the JFrame
	 * @param height	The height of the JFrame
	 * @param nWidth	The width of the net
	 * @param nHeight	The height of the net
	 * @param f			An arrayList of fish to be used
	 */
	private void setupGame(int width, int height, int nWidth, int nHeight, ArrayList<Fish> f) {
		// Set size of frame and net
		this.frameWidth = width;
		this.frameHeight = height;
		this.netWidth = nWidth;
		this.netHeight = nHeight;
		
		// net velocity
		velocity = (int) (frameWidth / 120);
		
		// Set fishes to the input list of fishes
		fishes = f;
		
		// Set the netX to be centered in the screen
		netX = (int) (frameWidth / 2 - netWidth / 2);

		//Assigns a random Fish to target fish
		targetFish();

		// sets up game with input allowed, not currently grabbing fish, game isn't
		// over, do not add gold question mark, do not display gold question mark, 
		// do not display question frame, and score is set to 0
		playerInputAllowed = true;
		grabbingFish = false;
		gameOver = false;
		addGold = false;
		goldDisplayed = false;
		questionDisplayed = false;
		score = 0;
	}
	
	/**
	 * Adds randomly generated fish to an arraylist.
	 * @return	An arrayList of randomly generated fish
	 */
	private ArrayList<Fish> populateFish(){
		ArrayList<Fish> f = new ArrayList<Fish>();
		 for (FishType ft: FishType.values()){
			 if (ft != FishType.GOLD) {
				 f.add(Fish.makeRandomFish(ft, frameWidth, frameHeight));
			 }
			 
		 }
		 // Resets captured fish if entries are in it from the tutorial
		 capturedFish = new ArrayList<Fish>();
		 return f;
	}
	
	/**
	 * Assigns a random FishType to target fish.
	 * Adds chosen target to ArrayList takenTargets to ensure
	 * that the same FishType will not be chosen again
	 */
	private void targetFish() {
		Random r = new Random();
		if (fishes.size() > 0){
            targetFish = fishes.get(r.nextInt(fishes.size()));
            if((checkCapturedFish(capturedFish, targetFish) || targetFish.getSpecies().equals(FishType.GOLD)) && capturedFish.size() < FishType.values().length -1) {
                targetFish();
            }
        }

		/*else if (targetFish.getSpecies() != FishType.GOLD){
			capturedFish.add(targetFish);
		}*/
	}
	
	/**
	 * Returns true if the fish has already been chosen
	 * @param al	The list of already captured fish
	 * @param chosenTarget	The fish to check if it has been captured or not
	 * @return	True if the fish is in al, false otherwise.
	 */
	private boolean checkCapturedFish(ArrayList<Fish> al, Fish chosenTarget) {
		Boolean alreadyCaptured = false;
		for(Fish f: al) {
			if (f.equals(chosenTarget)) {
				alreadyCaptured = true;
			}
		}
		return alreadyCaptured;
	}
	
	private int rightBound(Fish fish){
		return frameWidth - fish.imageWidth;
	}
	
	/**
	 * Updates the fish and the net positions on the screen.
	 * @param currentKey	A string representation of the keys the user is pressing
	 */
	public void update(String currentKey){
		// Moves the net depending on what key is pressed down
		switch(currentKey) {
		case "up": // The up arrow key is pressed
			moveUp();
			break;
		case "right": // Right arrow key
			moveRight();
			break;
		case "left": // Left arrow key
			moveLeft();
			break;
		case "down": // Down arrow key
			moveDown();
			break;
		case "up/right": // Both up and right
			moveRight();
			moveUp();
			break;
		case "up/left": // Both up and left
			moveUp();
			moveLeft();
			break;
		case "down/right": // Both down and right
			moveDown();
			moveRight();
			break;
		case "down/left": // Both down and left
			moveDown();
			moveLeft();
			break;
		default:
			if(currentKey.length() != 0) {
				System.out.println(currentKey);
			}
				
		}

		// If the player is currently grabbing a fish
		if(grabbingFish) {
			if(netY > 0)
				netY -= velocity; // Move the net towards the top of the screen
			if(netY <= 0) { // If the net reaches the top
				netY = 0;
				playerInputAllowed = true; // Allow player input
				grabbingFish = false; // Stop grabbing the fish
			}
		}
		// if addGold is true, add a gold question mark to the ArrayList of fishes
		else if (addGold) {
			goldenFish = new Fish(FishType.GOLD, Direction.WEST, frameWidth , frameHeight);
			fishes.add(goldenFish);
			goldDisplayed=true;
			addGold = false; // set to false so that only one mark is added
		}
		// Iterate through the fishes
		Iterator<Fish> i = fishes.iterator();
		boolean changeTarget = false; // Shows whether or not a change of target is needed
		while(i.hasNext()) {
			Fish f = i.next();
			// conditions ensure player can only capture target fish or gold question mark
			if(f.isCaught) {
				f.xloc = netX + (netWidth - f.imageWidth) / 2;
				f.yloc = netY + (netHeight - f.imageHeight) / 2;
				if(netY == 0) {
					if (f.getSpecies().equals(FishType.GOLD)) {
						i.remove();
						goldDisplayed = false;
						// pass in capturedFish so that questions are based off of facts that have been displayed
						new QuestionController(capturedFish);
						questionDisplayed = true;
					}
					else if (!inTutorial){
						capturedFish.add(targetFish);
						questionDisplayed = false;
						i.remove();
						new FactController(targetFish);
						changeTarget = true;
					}
					else {
						i.remove();
						changeTarget = true;
					}
				}
			} // If the fish isn't caught, move it normally
			else {
				moveFish(f);
			}
		}
		if(changeTarget && fishes.size() != 0) {
			targetFish(); // once removed, generate a new target fish
		}
		if(fishes.size() == 0) {
			if(inTutorial) { // If the tutorial ends, run populate fish
				fishes = populateFish();
				targetFish(); // Choose a new targetFish
				inTutorial = false; 
			}
			else { 
				gameOver = true;
			}
		}
	}

	/**
	 * Movement function for the fish
	 * @param fish	The fish to move
	 */
	private void moveFish(Fish fish){
		int newX = fish.xloc + fish.xIncriment() * fish.xSpeed; // update x location
		int newY = fish.yloc + fish.yIncriment() * fish.ySpeed; // update y location
		if (newX < leftBound) { // if the fish goes too far left
			newX = leftBound;
			fish.flipDirection(Direction.WEST);

		}
		if (newX > rightBound(fish)){ // if the fish goes too far right
			newX = rightBound(fish);
			fish.flipDirection(Direction.EAST);

		}
		if (newY < fish.topYBound){ // if the fish goes too far north
			newY = fish.topYBound;
			fish.flipDirection(Direction.NORTH);

		}
		if (newY > fish.bottomYBound){ // if the fish goes too far south
			newY = fish.bottomYBound;
			fish.flipDirection(Direction.SOUTH);

		}
		fish.xloc = newX; // change the fishes location
		fish.yloc = newY;
	}

	// moves the net upwards
	public void moveUp() {
		if(playerInputAllowed) {
			netY -= velocity;
		}
		if(netY < 0) {
			netY = 0;
		}
	}
	// moves the net to the left
	public void moveLeft() {
		if(playerInputAllowed) {
			netX -= velocity;
		}
		if(netX < 0) {
			netX = 0;
		}
	}
	// moves the net to the right
	public void moveRight() {
		if(playerInputAllowed) {
			netX += velocity;
		}
		if(netX + netWidth > frameWidth) {
			netX = frameWidth - netWidth;
		}
	}
	// moves the net downwards
	public void moveDown() {
		if(playerInputAllowed) {
			netY += velocity;
		}
		if(netY + netHeight > frameHeight) {
			netY = frameHeight - netHeight;
		}
	}

	// collisions for Net and Fish in the environment
	public void checkCollisions(){
		if(playerInputAllowed) {
			// The net corner locations
			int netX1 = netX; //P1.x
			int netY1 = netY; //P1.y
			int netX2 = netX + netWidth;//P2.x
			int netY2 = netY + netHeight;//P2.y
	
			for(Fish f: fishes){
				// The fish corner locations
				int fishX1 = f.getXLoc();
				int fishY1 = f.getYLoc();
				int fishX2 = fishX1 + f.imageWidth;
				int fishY2 = fishY1 + f.imageHeight;
	
				// only set isCaught if targetFish or the gold question mark
				if((f.equals(targetFish) || f.getSpecies().equals(FishType.GOLD)) && netY2 >= fishY1 && netY1 <= fishY2 && netX2 >= fishX1 && netX1 <= fishX2 ) {
					if(f.getSpecies().equals(FishType.GOLD)) {
						playerInputAllowed = false;
						grabbingFish = true;
						f.isCaught = true;
					}
					else {
						playerInputAllowed = false;
						grabbingFish = true;
						f.isCaught = true;
						score += 10;
					}
				}
			}
		}
	}
	
	/* Setters for testing purposes only */
	
	// Sets a specific target fish
	public void setTargetFish(Fish f) {
		targetFish = f;
	}
	// Sets the y location of the net
	public void setNetY(int y) {
		netY = y;
	}
	// Sets the x location of the net
	public void setNetX(int x) {
		netX = x;
	}

	/* Getters */
	// Returns the list of fish present on the screen
	public ArrayList<Fish> getFishList(){
		return fishes;
	}
	// Returns the list of fish that have been caught already
	public ArrayList<Fish> getCapturedFish() {
		return capturedFish;
	}
	// Return the target fish.
	public Fish getTargetFish() {
		return targetFish;
	}
	// Returns the x location of the net
	public int getNetX() {
		return netX;
	}	
	// Returns the y location of the net
	public int getNetY() {
		return netY;
	}
	// Returns the score
	public int getScore() {
		return score;
	}
	// Returns true if the game is over
	public boolean getGameOver() {
		return gameOver;
	}
	// Returns true if we are in the tutorial
	public boolean getInTutorial() {
		return inTutorial;
	}
	// returns the net velocity
	public int getVelocity() {
		return velocity;
	}
	
}
