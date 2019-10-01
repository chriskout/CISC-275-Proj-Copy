import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MGModel implements Serializable {
	int frameWidth;
	int frameHeight;
	ArrayList<Card> flipped; 
	ArrayList<Card> animals;   	
	ArrayList<Card> environments;
	int score;
	boolean atScoreScreen = false;
	
	

	public static MGModel readMGModel(){
	    MGModel model = (MGModel) SerializeHelper.read(SerializeHelper.MATCHING_FILE);
	    return model;
    }


    /**
     * Constructs a Matching Model
     * @param width screen width
     * @param height screen height
     */
	public MGModel(int width, int height) {
		animals = new ArrayList<Card>();
		environments = new ArrayList<Card>();
		flipped = new ArrayList<Card>(); 
		frameWidth = width; 
		frameHeight = height;
		makeCards();
		score = 0;

        //store the current model in txt file, for later testing
        SerializeHelper.write(SerializeHelper.MATCHING_FILE, this);
	}
	public void setAtScoreScreen(boolean flag) { 
		atScoreScreen = flag;
	}

    /**
     * creates all of the new charges
     */
	public void makeCards() {
		for (int i = 0; i < 6; i++){
			Card a = new Card ("a" + i);
			Card e = new Card ("e" + i);
			animals.add(a);
			environments.add(e);
		}
		Collections.shuffle(animals);
		Collections.shuffle(environments);
	}

    /**
     * Checks to see if the contents of flipped contain a match
     * @return a boolean true if the two flipped cards are a match
     */
	public boolean isMatch() {
		Card c1 = flipped.get(0);
		Card c2 = flipped.get(1);
		String[] sArray1 = c1.name.split("");
		String[] sArray2 = c2.name.split("");
		if (sArray1[1].equals(sArray2[1])) {
			c1.match();
			c2.match();
            addScore();
			return true;
		} else {
			c1.cardFlip();
			c2.cardFlip();
			return false;
		}		 
	}

    /**
     * adds card to flipped, unless doing so would create a match
     * @param c the card to be flipped
     */
	public void modelFlip(Card c) {
		if (!c.isMatched) {
			if (flipped.size() == 0) {
				flipped.add(c);
				c.cardFlip();
			} else if (!flipped.get(0).name.split("")[0].equals(c.name.split("")[0])) {
				flipped.add(c);
				c.cardFlip();
			} else {
				return;
			}
		}
	}



    /**
     * gets the card at the inputted location
     * @param clickLocation
     * @return the corresponding card
     */
	public String getFlippedClicked(Point clickLocation) {
		double xloc = clickLocation.getX();
		double yloc = clickLocation.getY();

		Card c;
		String id;
		int colNum;
		ArrayList<Card> list;
			if (xloc <= frameWidth / 4) { //flips column 1
				colNum = 1;
				list = animals;
			} else if (xloc <= frameWidth / 2) { //flips column 2
				colNum = 2;
				list = animals;
			} else if (xloc >= frameWidth * 0.75) { //flips column 4
				colNum = 4;
				list = environments;
			} else { //flips column 3
				colNum = 3;
				list = environments;
			}
			//USE OF MOD HERE
			if (colNum % 2 == 1) {
				if (yloc <= frameHeight / 3) {
					c = list.get(0);
					modelFlip(c);
					id = c.name + 0;
				}
				else if(yloc <= frameHeight * 0.6667) {
					c = list.get(2);
					modelFlip(c);
					id = c.name + 2;
				} else {
					c = list.get(4);
					modelFlip(c);
					id = c.name + 4;
				}
			} else {
				if (yloc <= frameHeight / 3) {
					c = list.get(1);
					modelFlip(c);
					id = c.name + 1;
					
				} else if(yloc <= frameHeight * 0.6667) {
					c = list.get(3);
					modelFlip(c);
					id = c.name + 3;
				} else {
					c = list.get(5);
					modelFlip(c);
					id = c.name + 5;
				}
			}
			return id;
		}

    /**
     * removes all from flipped
      */
	//Made the following methods public for testing
	public void clearFlipped() {
	    for (Card f: flipped){
	        f.isFlipped = false;
        }
		flipped = new ArrayList<>();
	}


	int getScore(){
		return score;
	}

    /**
     * increments score by 10
     */
    public void addScore() {
    	score = score + 1;
    }


	
    
}
