import java.util.ArrayList;
import java.util.Random;

public class QuestionModel {
	
	// the FishType that corresponds to the question, and the question string
	private FishType questionType;
	private String question;
	
	// strings representing each of the four choices
	private String choiceOne;
	private String choiceTwo;
	private String choiceThree;
	private String choiceFour; 
	
	// string representing the choice ultimately selected by the player
	private String choice;
	
	// ArrayList for answers that have already been used as choices
	private ArrayList<FishType> availableAnswers;

	// random variables
	private Random rand;
	private int randomAnswer;
	private int score;
	
	public QuestionModel(ArrayList<Fish> al) {
		availableAnswers = new ArrayList<FishType>();
		for (FishType ft:FishType.values()) {
			if (ft != FishType.GOLD) {
				availableAnswers.add(ft);
			}
		}
		rand = new Random();
		
		score = 0;
		setQuestionType(al);
		
		setQuestion(questionType);
		setAnswers(questionType);
	}
	
	// randomly chooses a FishType to base the question off of given previously captured Fish
	private void setQuestionType(ArrayList<Fish> capturedFish) {
		int index = rand.nextInt(capturedFish.size());
        questionType = capturedFish.get(index).getSpecies();
		availableAnswers.remove(questionType);
	}
	
	// assign the string corresponding to questionType
	private void setQuestion(FishType qt) {
		question = FactsAndQuestions.getQuestion(questionType);
	}
	
	// randomly choose which choice is the correct one, and leftover choices are assigned random answers
	private void setAnswers(FishType ft) {
		randomAnswer = rand.nextInt(4);
		switch(randomAnswer) { 
			case 0: choiceOne = ft.getDisplayName();

					choiceTwo = assignRandomAnswer(ft);
					choiceThree = assignRandomAnswer(ft);
					choiceFour = assignRandomAnswer(ft);
					break;
			case 1: choiceTwo = ft.getDisplayName();
					
					choiceOne = assignRandomAnswer(ft);
					choiceThree = assignRandomAnswer(ft);
					choiceFour = assignRandomAnswer(ft);
					break;
			case 2: choiceThree = ft.getDisplayName();
					
					choiceOne = assignRandomAnswer(ft);
					choiceTwo = assignRandomAnswer(ft);
					choiceFour = assignRandomAnswer(ft);
					break;
			case 3: choiceFour = ft.getDisplayName();
	
					choiceOne = assignRandomAnswer(ft);
					choiceTwo = assignRandomAnswer(ft);
					choiceThree = assignRandomAnswer(ft);
					break;
			default:
				break;
		}
	}
	
	// assign a random string answer (name of a fish species)
	private String assignRandomAnswer(FishType correctAnswer) {
		FishType randomizedType = FishType.values()[rand.nextInt(FishType.values().length)];
		if (availableAnswers.contains(randomizedType)) {
			choice = randomizedType.getDisplayName();
		}	
		else {
			assignRandomAnswer(correctAnswer);
			
		}
		availableAnswers.remove(randomizedType);
		return choice;
	}
	
	// getters
	public FishType getQuestionType() {
		return questionType;
	}
	public String getQuestion() {
		return question;
	}
	public String getChoiceOne() {
		return choiceOne;
	}
	public String getChoiceTwo() {
		return choiceTwo;
	}
	public String getChoiceThree() {
		return choiceThree;
	}
	public String getChoiceFour() {
		return choiceFour;
	}
	public int getScore() {
		return score;
	}
	
}