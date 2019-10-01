import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Controller for the Matching Game
 * contains JFrame and variables for model, view of Matching Game
 * @author Elena
 *
 */
public class MGController {

    private MGModel model;
    private MGView view;
    JFrame frame;

    boolean scored = false;
    private Action drawAction;

    /**
     * Constructor for MGController that creates new instances of Model and View
     * @param f a given JFrame
     */
    public MGController(JFrame f){
        frame = f;
        model = new MGModel(frame.getWidth(), frame.getHeight()); //view.getImageWidth(), view.getImageHeight());
        view = new MGView(frame, model.animals, model.environments, model.getScore());

        view.addClickListener(new ClickListener());
        //view.addKeyListener(new KeyListener());
        view.addKeyInput(new KeyInput());
    }
    /**
     * Listener for mouse implementation
     * @author Elena
     *
     */
    class ClickListener implements MouseListener {

        /**
         * Takes in a mouseClick, calculates location of click onscreen,
         * determines corresponding Card, and flips that Card
         * @param e Takes in a MouseEvent that has occurred
         */
        @Override
    public void mouseClicked(MouseEvent e) {
    	
    		
    	


            Point clickLocation = e.getLocationOnScreen();
            model.getFlippedClicked(clickLocation);
            //view.flipCard(card); //Checks which card is flipped based on its index value (i.e. a1 or e2) and flips it in view
            //System.out.println("before matching logic");
            //model.printCards();
            if (model.flipped.size() >=3)
                model.clearFlipped();
            view.redraw(model.animals, model.environments, model.getScore());
            try {
                Thread.sleep(5);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if (model.flipped.size() == 2) {
                if (!model.isMatch()) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else { //checks to see if user is done with game
                	ArrayList<Card> ani = model.animals;
                	if (ani.get(0).isMatched && ani.get(1).isMatched && ani.get(2).isMatched && ani.get(3).isMatched && ani.get(4).isMatched && ani.get(5).isMatched) {
                		//EndController endCon = new EndController(frame);
                        //view.displayScore();


                        if (!scored){
                            Scoring.storeScore(model.score, Scoring.MATCHING_FILE);
                            Scoring.sortScores(Scoring.MATCHING_FILE);
                            view.setScore(model.score);
                            scored = true;

                        }
                        model.setAtScoreScreen(true);
                        view.displayScore();



                    }
                }
                model.clearFlipped();
            }
    	
    }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

    }
    //Listens for keyInput
    class KeyInput implements KeyListener{
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int code = keyEvent.getKeyCode();
            switch (code) {
                case 81:     //81 is the key "q"
                    //EndController endCon = new EndController(frame);
                	view.displayScore();
                	if (!scored){
                        Scoring.storeScore(model.score, Scoring.MATCHING_FILE);
                        Scoring.sortScores(Scoring.MATCHING_FILE);
                        view.setScore(model.score);
                        scored = true; 
                	}
                	model.setAtScoreScreen(true);
                	break;
                case 10: // enter
                    if (scored){
                        EndController endCon = new EndController(frame);
                    }

                	break;
                default:
                    break;

            }

        }
        @Override
        public void keyReleased(KeyEvent keyEvent) {
            //Blank
        }
        @Override
        public void keyTyped(KeyEvent keyEvent) {
            //Blank
        }
    }


	
	/**
	 * Constructor for MGController that creates new instances of Model and View
	 * @param f a given JFrame
	 */

 
	

	/**
	 * Listener for mouse implementation
	 * @author Elena
	 *
	 */
 

}


