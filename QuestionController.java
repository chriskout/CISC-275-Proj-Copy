import java.util.ArrayList;

public class QuestionController {
	private QuestionModel qModel;

	// creates instances of question model and view
	public QuestionController(ArrayList<Fish> al) {
		qModel = new QuestionModel(al);
		new QuestionView(qModel.getQuestionType(), qModel.getQuestion(), qModel.getChoiceOne(), qModel.getChoiceTwo(), qModel.getChoiceThree(), qModel.getChoiceFour());
	}
}