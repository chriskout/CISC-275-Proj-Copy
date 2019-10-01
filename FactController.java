public class FactController {
	private FactModel fModel;

	// creates instances of question model and view
	public FactController(Fish f) {
		fModel = new FactModel(f);
		new FactView(fModel.getFact());
	}
}