import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * This class contains the implementation of each fish, including its speed, 
 * location, and direction
 */
public class Fish implements Serializable {
	// Location of the fish
	int xloc, yloc;
	
	// Size of the fish
	int imageWidth, imageHeight;
	
	// Velocity in X and Y direction
	int xSpeed;
	int ySpeed;
	
	// Direction of a fish
	Direction direction;
	
	// Denotes if the fish is currently in the net or not
	boolean isCaught;
	
	// Type of fish
	FishType species;
	
	// Range that the fish can move in top to bottom
	int topYBound;
	int bottomYBound;
	
	// Dimensions of frame
	private int frameHeight;
	private int frameBarSize = 0;
	private int frameWidth;
	private int defaultViewWidth = 1500;
	
	// is the tutorial screen up?
	private boolean isTutorial = false;
	
	/**
	 * Testing version of the constructor, allows setting of speeds and postitions
	 * @param species	The species of the fish
	 * @param direction	The direction that the fish is moving
	 * @param fW		The frameWidth
	 * @param fH		The frameHeight
	 * @param xS		The xSpeed of the fish
	 * @param yS		The ySpeed of the fish
	 * @param xL		The xLocation of the fish
	 * @param yL		The yLocation of the fish
	 */
	public Fish(FishType species, Direction direction, int fW, int fH, int xS, int yS, int xL, int yL) {
		this.species = species;
		this.direction = direction;
		this.isCaught = false;
		frameWidth = fW;
		frameHeight = fH;
		
		xSpeed = xS;
		ySpeed = yS;
		xloc = xL;
		yloc = yL;
		
		topYBound = 0;
		bottomYBound = frameHeight;
	}
	

	/**
	 * Constructor for fish.
	 * @param species	The species of the fish
	 * @param direction	The direction of travel of the fish
	 * @param fW		The frameWidth
	 * @param fH		The frameHeight
	 */
	public Fish(FishType species, Direction direction, int fW, int fH) {
		this.species = species;
		this.direction = direction;
		this.isCaught = false;
		frameWidth = fW;
		frameHeight = fH;
		
		// limits fish movement to respective species' living areas
		updateBounds();
		
		// randomly generates spawning location of Fish
		Random r = new Random();
		this.xloc = r.nextInt(Math.abs(frameWidth - imageWidth));
		this.yloc = Math.abs(r.nextInt() % (bottomYBound - topYBound)) + topYBound;
	}
	
	/**
	 * Makes a random fish
	 * @param type			The species of the fish to make
	 * @param frameWidth	The width of the frame
	 * @param frameHeight	The height of the frame
	 * @return				A new fish made with the parameters
	 */
	public static Fish makeRandomFish(FishType type, int frameWidth, int frameHeight){
		// creates a random direction
		Random r = new Random();
		Direction dir = Direction.values()[2 + r.nextInt(Direction.values().length -2)]; 
		
		// creates and returns new fish based off of random direction
		return new Fish(type, dir, frameWidth, frameHeight);	
	}
	

	/**
	 * Determines the increment (multiplier) for the ySpeed
	 * @return The multiplier for the speed.
	 */
	public int yIncriment(){
		switch (direction){
			case NORTH: 
				return -1;
			case NORTHEAST:
				return -1;
			case EAST:
				return 0;
			case SOUTHEAST:
				return 1;
			case SOUTH:
				return 1;
			case SOUTHWEST:
				return 1;
			case WEST:
				return 0;
			default: //NORTHWEST
				return -1;
		}
	}
	/**
	 * Determines the increment (multiplier) for the xSpeed
	 * @return The multiplier for the speed
	 */
    public int xIncriment(){
		switch (direction){
			case NORTH: 
				return 0;
			case NORTHEAST:
				return 1;
			case EAST:
				return 1;
			case SOUTHEAST:
				return 1;
			case SOUTH:
				return 0;
			case SOUTHWEST:
				return -1;
			case WEST:
				return -1;
			default: //NORTHWEST
				return -1;
		}
	}
	
	// Getters
	public int getXLoc() {
		return xloc;
	}
	public int getYLoc() {
		return yloc;
	}
	public Direction getDirection() {
		return direction;
	}
	public FishType getSpecies() {
		return species;
	}

	// setters
	public void setImageWidth(int iW) {
		imageWidth = iW;
		if(!isTutorial) {
			xSpeed = (int) (species.getSpeed() * imageWidth);
		}
	}
	public void setImageHeight(int iH) { 
		imageHeight = iH;
		if(!isTutorial) {
			ySpeed = (int) (species.getSpeed() * imageHeight);
		}
		updateBounds();
	}
	public void setTopBound(int tB) {
		topYBound = tB;
	}
	public void setBottomBound(int bB) {
		bottomYBound = bB;
	}
	public void accountForTitleBars(int titleHeight) {
		frameBarSize = titleHeight;
	}
	
	// updates the boundaries of the fish types
	public void updateBounds() {
		switch (this.species){
		case HOGCHOKER:
		case SILVERSIDE:
		case FLOUNDER:
			this.topYBound = frameHeight / 3 * 2;
			this.bottomYBound = frameHeight - imageHeight - frameBarSize;
			break;
		case PERCH: 
		case MINNOW: 
		case WEAKFISH:
			this.topYBound = frameHeight / 3;
			this.bottomYBound = frameHeight / 3 * 2 - imageHeight;
			break;

		case MENHADEN:
		case MUMMICHOG:
			this.topYBound = 0;
			this.bottomYBound = frameHeight / 3 - imageHeight;
			break;
		case GOLD:
		default:
			topYBound = 0;
			bottomYBound = frameHeight;
		}
	}
	// randomly generates int from 0-7 which determines direction
	public void randomizeDirection() {
		Random r = new Random();
		int randomDir = r.nextInt(8);
		direction = Direction.values()[randomDir];
	} 

	// flips the current direction of a Fish to the opposite direction
	public void flipDirection(Direction flip){
    	switch (direction){
			case NORTH: 
				direction = Direction.SOUTH;
				break;
			case NORTHEAST:
				if (flip == Direction.EAST) direction = Direction.NORTHWEST;
				else direction = Direction.SOUTHEAST;
				break;
			case EAST:
				direction = Direction.WEST;
				break;
			case SOUTHEAST:
				if (flip == Direction.EAST) direction = Direction.SOUTHWEST;
				else direction = Direction.NORTHEAST;
				break;
			case SOUTH:
				direction = Direction.NORTH;
				break;
			case SOUTHWEST:
				if (flip == Direction.WEST) direction = Direction.SOUTHEAST;
				else direction = Direction.NORTHWEST;
				break;
			case WEST:
				direction = Direction.EAST;
				break;
			default: //NORTHWEST
				if (flip == Direction.WEST) direction = Direction.NORTHEAST;
				else direction = Direction.SOUTHWEST;
				break;
    		}
    }
	
	// sets parameters of the fish used in the tutorial
	public void setTutorialFish() {
		xSpeed = 0;
		ySpeed = 0;
		xloc = frameWidth / 2;
		yloc = frameHeight / 2;
		isTutorial = true;
	}
	
	// increments and decrements xloc and yloc to move in specified direction
	public void move() { 
		System.out.println("MOVING");
		switch(direction) {
			case NORTH: {
				yloc -= ySpeed;
			}
			case SOUTH: {
				yloc+= xSpeed;
			}
			case EAST: {
				xloc+= xSpeed;
			}
			case WEST: {
				xloc-= xSpeed;
			}
			case NORTHEAST: {
				xloc+=xSpeed;
				yloc-=ySpeed;
			}
			case NORTHWEST: {
				xloc-=xSpeed;
				yloc-=ySpeed;
			}
			case SOUTHEAST: {
				xloc+=xSpeed;
				yloc+=ySpeed;
			}
			case SOUTHWEST: {
				xloc-=xSpeed;
				yloc+= ySpeed;
			}
		}
	}
	// Tests equality between fish
	@Override
	public boolean equals(Object other) { 
		if (other instanceof Fish) {
			Fish otherFish =((Fish) other);
			return this.xloc == otherFish.getXLoc() && this.yloc == otherFish.getYLoc()
								&& this.isCaught == otherFish.isCaught && this.direction
								== otherFish.direction && this.species == otherFish.species;
		}
		return false;
	}
	
}