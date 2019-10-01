import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/** 
 * The Card class contains all the features of a Card in the MatchingGame
 * @author Elena
 */
public class Card implements Serializable {
	
	public String name;
	public boolean isFlipped;
	public boolean isMatched;
	public Image img;
	char face; //image currently shown to viewer
	char cover; //cover image, same for all animals and all environments
	char back; //the actual animal or environment image, which is unique to each card 
	

	public Card(String n) {
		name = n;
		// Set default constants
		cover = 'c';
		back = 'b';
		face = cover;
		isFlipped = false;
		isMatched = false;
	}
	
	/**
	 * asks if the current card is turned over
	 * @return the boolean isFlipped
	 */
	public boolean isFlipped() {
		return isFlipped;
	}
	/**
	 * asks if the current card is currently matched
	 * @return the boolean isMatched
	 */
	public boolean isMatched() {
		return isMatched;
	}
	
	/**
	 * sets isMatched to true for a Card if it is correctly matched
	 */
	public void match() {
		isMatched = true;
	}
	public Card() {}
	
	/**
	 * flips Card over if it is facing down
	 * else returns Card to facing down
	 */
	public void cardFlip() {
		if (!isMatched) {
			if (face == cover) {
				face = back;
				isFlipped = true;
			} else {
				face = cover;
				isFlipped = false;
			}
		}
	}
	@Override
	public String toString() { 
		return name;
	}
}