import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MGView extends JPanel implements Serializable {
	private int viewWidth;
	private int viewHeight;
	private JFrame frame;
	private JPanel apanel; 
	private JPanel epanel;
	private JPanel bigpanel;
	ImageIcon aCover;
	ImageIcon eCover;
	ArrayList<Card> animals;
	ArrayList<Card> environments;
	ArrayList<ImageIcon> ecards = new ArrayList<ImageIcon>(); //Environment cards shown to player
	ArrayList<ImageIcon> acards = new ArrayList<ImageIcon>(); //Animal cards shown to player
	ArrayList<ImageIcon> aBaseCards = new ArrayList<ImageIcon>(); //Actual animal cards w/ green border (no covers)
	ArrayList<ImageIcon> eBaseCards = new ArrayList<ImageIcon>(); //Actual environment cards w/ green border (no covers)
	ArrayList<ImageIcon> aHiCards = new ArrayList<ImageIcon>(); //Highlighted animal base card (yellow border)
	ArrayList<ImageIcon> eHiCards = new ArrayList<ImageIcon>(); //Highlighted environment base card (yellow border)
	int score = 0;
	private JButton startButton = new JButton("Start");
	int screen = 0;

    /**
     * Creates new View
     * @param f inputted JFrame
     * @param ani Animal cards
     * @param envi Environment cards
     * @param newScore Starting score
     */
	public MGView(JFrame f, ArrayList<Card> ani, ArrayList<Card> envi, int newScore) {
	        score = newScore;
		   frame = f;
		   animals = ani;
		   environments = envi;
		   
		   viewWidth = frame.getWidth();
		   viewHeight = frame.getHeight();
		   
		   try {
		      addIcons(animals,environments);
		   } catch (IOException e) {
		      e.printStackTrace();
		   }
		   
		   frame.getContentPane().removeAll();
		   
		   
		   apanel = new JPanel(new GridLayout(3,2));
		   epanel = new JPanel(new GridLayout(3,2));
		   
		   frame.setBackground(Color.gray);
		   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		      frame.setSize(viewWidth, viewHeight);
		   frame.setFocusable(true);

		   //frame.setLayout(new GridLayout(1,2));
		   this.drawCards();
		   frame.getContentPane();
		   //frame.getContentPane().add(apanel, BorderLayout.CENTER);

            JPanel cards = new JPanel();
            LayoutManager cardLayout = new GridLayout(1,2);
            cards.setLayout(cardLayout);

            cards.add(apanel);
            cards.add(epanel);


		   JPanel overlayPanel = new JPanel();
		   LayoutManager overlayL = new OverlayLayout(overlayPanel);
		   overlayPanel.setLayout(overlayL);

		   JPanel glassFrame = new JPanel();
		   glassFrame.setLayout(null);
		   glassFrame.setSize(viewWidth, viewHeight);
		   Color transparent = new Color(0,0,0,0);

		   glassFrame.setBackground(transparent);

		   JLabel scoreLabel = new JLabel("Score: " + score);
		   scoreLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		   int offset = 10;
		   scoreLabel.setBounds(offset,offset,scoreLabel.getPreferredSize().width,scoreLabel.getPreferredSize().height);

		   scoreLabel.setOpaque(true);

		   JLabel tutorial1 = new JLabel("Click a card with a crab to reveal an animal");
		   JLabel tutorial2 = new JLabel("Click a card with a tree to reveal an environment");
		   tutorial1.setFont(new Font("Arial", Font.PLAIN, 30));
		   tutorial2.setFont(new Font("Arial", Font.PLAIN, 30));
		   tutorial1.setBounds(offset,offset * 6,tutorial1.getPreferredSize().width,tutorial1.getPreferredSize().height);
		   tutorial2.setBounds(glassFrame.getWidth()/2 + offset,offset * 6,tutorial2.getPreferredSize().width,tutorial2.getPreferredSize().height);
		   tutorial1.setOpaque(true);
		   tutorial2.setOpaque(true);
		   glassFrame.add(tutorial1);
		   glassFrame.add(tutorial2);
		   glassFrame.add(scoreLabel);

		   overlayPanel.add(glassFrame);

		   overlayPanel.add(cards);


		   frame.add(overlayPanel);

		   apanel.setVisible(true);
		   epanel.setVisible(true);
		   
		   frame.setVisible(true);
		   this.requestFocus();
		}

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Draws all cards onto the screen
     */
	private void drawCards() {
		for (ImageIcon img: acards) {
			JLabel label = new JLabel(img);
			apanel.add(label);
		}
		for (ImageIcon img: ecards) {
			JLabel label = new JLabel(img);
			epanel.add(label);
		}
		
	}

    /**
     * adds the card icons to the frame
     * @param animals animal card list
     * @param environments environment card list
     * @throws IOException
     */
	private void addIcons(ArrayList<Card> animals, ArrayList<Card> environments) throws IOException {
		ArrayList<String> aNames = new ArrayList<>();
		ArrayList<String> eNames = new ArrayList<>();
		
	    String aCoverLoc = "src/images/animals/animal_cover.png";
	    String eCoverLoc = "src/images/environments/environment_cover.png";
	    
		//aCover = new ImageIcon(ImageHelper.scaleImage(ImageHelper.createImage(aCoverLoc), viewWidth / 4, viewHeight / 3));
		aCover = new ImageIcon(ImageHelper.createImage(aCoverLoc).getScaledInstance(viewWidth / 4 - 50, viewHeight / 3 - 50, Image.SCALE_SMOOTH));
		eCover = new ImageIcon(ImageHelper.createImage(eCoverLoc).getScaledInstance(viewWidth / 4 - 50, viewHeight / 3 - 50, Image.SCALE_SMOOTH));

		for (int i = 0; i < 6; i++) {
			//Sets cover side for cards
			acards.add(aCover);
			ecards.add(eCover);
			//Sets the 'flipped' side of cards, which is unique to each card
			Card ca = animals.get(i);
			Card ce = environments.get(i);
			String[] aStringArray = ca.toString().split("");
			String[] eStringArray = ce.toString().split("");
			switch (aStringArray[1]) {
				case "0":
					aNames.add("bog_turtle"); //e0
					break;
				case "1":
					aNames.add("osprey"); //e1
					break;
				case "2":
					aNames.add("red_knot"); //e2
					break;
				case "3":
					aNames.add("hogchoker"); //e3 lower fish
					break;
				case "4":
					aNames.add("weakfish"); //e4 mid fish
					break;
				case "5":
					aNames.add("atlantic_menhaden"); //e5 upper fish
					break;
				default:
					aNames.add("darkpurple");
					break;
			}
			switch (eStringArray[1]) {
				case "0":
					eNames.add("marsh_mud"); //e0
					break;
				case "1":
					eNames.add("shore_trees"); //e1
					break;
				case "2":
					eNames.add("shore"); //e2
					break;
				case "3":
					eNames.add("floor"); //e3
					break;
				case "4":
					eNames.add("intermediate_zone"); //e4
					break;
				case "5":
					eNames.add("surface"); //e5
					break;
				default:
					System.out.println("eStringArray[1] is: " + aStringArray[1]);
					eNames.add("shore");
					break;
			}
			ImageIcon animal = new ImageIcon(ImageHelper.createImage("src/images/animals/" + aNames.get(i) + "_card.png").getScaledInstance(viewWidth / 4 - 50, viewHeight / 3 - 50, Image.SCALE_SMOOTH));
			ImageIcon animalHi = new ImageIcon(ImageHelper.createImage("src/images/animals/" + aNames.get(i) + "_card_hi.png").getScaledInstance(viewWidth / 4 - 50, viewHeight / 3 - 50, Image.SCALE_SMOOTH));
			aBaseCards.add(animal);
			aHiCards.add(animalHi);
			ImageIcon environment = new ImageIcon(ImageHelper.createImage("src/images/environments/" + eNames.get(i) + "_card.png").getScaledInstance(viewWidth / 4 - 50, viewHeight / 3 - 50, Image.SCALE_SMOOTH));
			ImageIcon environmentHi = new ImageIcon(ImageHelper.createImage("src/images/environments/" + eNames.get(i) + "_card_hi.png").getScaledInstance(viewWidth / 4 - 50, viewHeight / 3 - 50, Image.SCALE_SMOOTH));
			eBaseCards.add(environment);
			eHiCards.add(environmentHi);
		}
	}

	public void addClickListener(MouseListener flip) {
		frame.addMouseListener(flip);
	}

    /**
     * Flips the inputted card on the frame
     * @param card the string identifier for the card to be flipped
     */
	public void flipCard(String card) {
		//Checks which card is flipped based on its index value (i.e. a1 or e2) and flips it in view
		char cType = card.charAt(0);
		int cLoc = Character.getNumericValue(card.charAt(2)); //this needs to be changed
		System.out.println(cLoc);
		if (cType == 'a') {
			acards.set(cLoc,aBaseCards.get(cLoc));
		} else {
			ecards.set(cLoc,eBaseCards.get(cLoc));
		}
	}

    /**
     * updates the JFrame
     * @param ani list of animal cards
     * @param envi list of environment cards
     * @param newScore current score
     */
	public void redraw (ArrayList<Card> ani, ArrayList<Card> envi, int newScore) {
		if (screen == 1) {
			displayScore();
			return;
		}
	    score = newScore;
		animals = ani;
		environments = envi;
		for (int i = 0; i < 6; i++) {
			if (animals.get(i).face == 'c') {
				acards.set(i,aCover);
			} else if (animals.get(i).isMatched()){
				acards.set(i,aBaseCards.get(i));
			} else {
				acards.set(i,aHiCards.get(i));
			}
		}
		for (int i = 0; i < 6; i++) {
			if (environments.get(i).face == 'c') {
				ecards.set(i,eCover);
			} else if (environments.get(i).isMatched()){
				ecards.set(i,eBaseCards.get(i));
			} else {
				ecards.set(i,eHiCards.get(i));
			}
		}
		frame.getContentPane().removeAll();
		   
		   
		   apanel = new JPanel(new GridLayout(3,2));
		   epanel = new JPanel(new GridLayout(3,2));
		   
		   frame.setBackground(Color.gray);
		   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		      frame.setSize(viewWidth, viewHeight);
		   frame.setFocusable(true);

		   frame.setLayout(new GridLayout(1,2));
		   this.drawCards();
		   frame.getContentPane();
		   //frame.getContentPane().add(apanel, BorderLayout.CENTER);

		   JPanel left = new JPanel();
		   LayoutManager overlayL = new OverlayLayout(left);
		   left.setLayout(overlayL);

		   JPanel glassFrame = new JPanel();
		   glassFrame.setLayout(null);
		   glassFrame.setSize(viewWidth, viewHeight);
		   Color transparent = new Color(0,0,0,0);

		   glassFrame.setBackground(transparent);

		   JLabel scoreLabel = new JLabel("Score: " + score);
		   scoreLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		   scoreLabel.setBounds(10,10,scoreLabel.getPreferredSize().width,scoreLabel.getPreferredSize().height);
		   scoreLabel.setOpaque(true);


        glassFrame.add(scoreLabel);

		   left.add(glassFrame);
		   left.add(apanel);
		       

		   frame.add(left);
		   frame.add(epanel);

		   apanel.setVisible(true);
		   epanel.setVisible(true);
		   
		   frame.setVisible(true);
		   this.requestFocus();	
	}

	// Adds the key listener to our jpanel.
	public void addKeyInput(KeyListener kL) {
		frame.addKeyListener(kL);
	}
	
	public void displayScore() {
		String scoringText = Scoring.scoreString(Scoring.MATCHING_FILE);
		System.out.println("Is Clearing Frame");
		frame.getContentPane().removeAll();
		
		JPanel scoreScreen = new JPanel();
		JLabel slabel = new JLabel("Number of matches " + score);
		slabel.setHorizontalAlignment(JLabel.CENTER);
		slabel.setVerticalAlignment(JLabel.CENTER);
		slabel.setFont(new Font("Serif", Font.PLAIN, 50));
		slabel.setBounds(90, 90, slabel.getPreferredSize().width, slabel.getPreferredSize().height);
		
		JLabel elabel = new JLabel("Press 'Enter'");
		elabel.setHorizontalAlignment(JLabel.CENTER);
		elabel.setVerticalAlignment(JLabel.CENTER);
		elabel.setFont(new Font("Serif", Font.PLAIN, 50));
		elabel.setBounds(90, 90, slabel.getPreferredSize().width, slabel.getPreferredSize().height);
		
		JLabel hlabel = new JLabel("HighScores: "+ Scoring.getScores(Scoring.MATCHING_FILE));
		hlabel.setHorizontalAlignment(JLabel.CENTER);
		hlabel.setVerticalAlignment(JLabel.CENTER);
		hlabel.setFont(new Font("Serif", Font.PLAIN, 50));
		hlabel.setBounds(90, 90, slabel.getPreferredSize().width, slabel.getPreferredSize().height);
		
		scoreScreen.setLayout(new GridLayout(3,1));
		Color transparent = new Color(0,0,0,0);

		scoreScreen.setBackground(transparent);
		
		scoreScreen.add(slabel);
		scoreScreen.add(hlabel);
		scoreScreen.add(elabel);
		
		frame.add(scoreScreen);
		frame.setBackground(Color.CYAN);
		
		frame.revalidate();
		frame.repaint();
		screen = 1;
		
	}
	
}
