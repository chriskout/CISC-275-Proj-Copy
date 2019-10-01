/**
 * Every FishType has a name, speed and size.
 * 	Name: denotes the species of a Fish
 * 	Speed: each fish is slow, medium, or fast
 * 	Size: researched size scaled and altered by a factor of .045
 * @author Elena
 */
public enum FishType {
	
	// bottom feeders
	HOGCHOKER("hogchoker", 0.085, 1*.045, "Hogchoker"),
	SILVERSIDE("atlantic_silverside", 0.1, 1*.045, "Atlantic Silverside"),
	FLOUNDER("summer_flounder", 0.02, 2.333*.045, "Summer Flounder"),
	
	// mid-range fish
	PERCH("white_perch", 0.06, 1.3333*.045, "White Perch"),
	MINNOW("sheepshead_minnow", 0.085, .7777*.045, "Sheepshead Minnow"),
	WEAKFISH("weakfish", 0.06, 1.6666*.045, "Weakfish"),
	
	// surface dwellers
	MENHADEN("atlantic_menhaden", 0.04, 1.57333333*.045, "Atlantic Menhaden"),
	MUMMICHOG("mummichog", 0.09, .666666*.045, "Mummichog"),
	
	// gold question mark with Fish behaviors because it gets added to the ArrayList
	// of current fishes; DOES NOT MOVE
	GOLD("question", 0.0, .25, "Question");
	
	private final String name;
	private final double speed;
	private final double size;
	private final String displayName;
	
	private FishType(final String name, double speed, double size, String displayName) {
		this.name = name;
		this.speed = speed;
		this.size = size;
		this.displayName = displayName;
	}
	
	public double getSpeed(){
		return speed;
	}
	public String getName() {
		return name;
	}
	public double getSize() {
		return size;
	}
	public String getDisplayName() {
		return displayName;
	}
}
