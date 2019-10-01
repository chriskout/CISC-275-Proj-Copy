
public class FactsAndQuestions {

	/**
	 * Method that returns a fish fact
	 * @param f takes in the target Fish
	 * @return Fact corresponding to the target fish
	 */
	public static String getFact(Fish f) {
		switch(f.getSpecies()) {
			case HOGCHOKER:
				return "As hogchokers mature, their left eye moves to the same side of their head as their right eye. "
						+ "They are called right-handed flatfish.";
			case SILVERSIDE:
				return "Warm temperatures result in more male larvae, whereas cold temperatures "
						+ "produce more female Atlantic silverside minnow larvae.";
			case FLOUNDER:
				return "The oldest recorded summer flounder was 20 years old!";
			case PERCH:
				return "The white perch is considered an invasive species in some regions of the US "
						+ "because it is a prolific breeder that eats other fish eggs.";
			case MINNOW:
				return "The sheepshead minnow is very adaptive to extreme variablility in its environment.";
			case WEAKFISH:
				return "The weakfish is the state fish of Delaware. It is also a drum fish, "
						+ "meaning that it produces a purr-like sound.";
			case MENHADEN:
				return "The Atlantic menhaden was likely used by Native Americans as fertilizer for their corn.";
			case MUMMICHOG:
				return "A single mummichog fish may consume up to 2000 mosquito larvae in a day.";
			default:
				return "Please capture the question mark.";
		}
	}
	
	/**
	 * method that returns a question about a fish
	 * @param ft FishType of a previously captured Fish
	 * @return a question corresponding to the given FishType
	 */
	public static String getQuestion(FishType ft) {
		switch(ft) {
			case HOGCHOKER:
				return "Which fish species is a right-handed flatfish?";
			case SILVERSIDE:
				return "Which fish species has larvae that is heavily influenced by water temperature?";
		case FLOUNDER:
			return "Which of these fish species has lived to twenty years old?";
		case PERCH:
			return "Which fish is considered a prohibited, invasive species in some regions of the US?";
		case MINNOW:
			return "Which of these fish species is the most environmentally adaptable?";
		case WEAKFISH:
			return "Which fish species belongs to the drum fish category?";
		case MENHADEN:
			return "Which fish species was most likely used by Native Americans as crop fertilizer?";
		case MUMMICHOG:
			return "Which fish species is considered a natural controllant of the mosquito population?";
		default:
			return "This is a default question.";
		}
	}
}
