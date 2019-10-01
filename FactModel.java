public class FactModel {
	
	// the target Fish that the fact will be about
	private Fish target;
	
	// fact corresponding to the given Fish
	private String fact;
	
	public FactModel(Fish f) {
		target = f;
		setFact(target);
	}
	
	// sets fact
	private void setFact(Fish f) {
			fact = FactsAndQuestions.getFact(f);
	}
	
	// getter for fact
	public String getFact() {
		return fact;
	}
}