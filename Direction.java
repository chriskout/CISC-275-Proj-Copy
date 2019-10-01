/**
 * Direction enumeration for the fish tagging game
 * Every Direction has a string for name
 * @author Elena
 */
public enum Direction {
	NORTH("north"),
	SOUTH("south"),
	EAST("east"),
	WEST("west"),
	
	NORTHEAST("northeast"),
	NORTHWEST("northwest"),
	SOUTHEAST("southeast"),
	SOUTHWEST("southwest");
	
	private final String name;
	
	private Direction(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
