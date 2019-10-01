import junit.framework.TestCase;

public class FishClassTests extends TestCase {
	
	
	public static void test_makeRandomFish() {
		
		Fish f = Fish.makeRandomFish(FishType.HOGCHOKER, 1500, 900);
		assertEquals(f.species, FishType.HOGCHOKER);

	}
	
	public static void test_basics() {
		Fish f = new Fish(FishType.MINNOW, Direction.SOUTH, 1500, 900);
		assertEquals(f.getXLoc(), f.xloc );
		assertEquals(f.getYLoc(), f.yloc);
		
		f.setImageWidth(500);
		assertEquals(42, f.xSpeed);
		f.setImageHeight(500);
		assertEquals(42, f.ySpeed);
		
	} 
	
	public static void test_flipDirection() { //should test x and y increment functions aswell
		Fish f = new Fish(FishType.MINNOW, Direction.SOUTH, 1500, 900);
		f.flipDirection(f.getDirection());
		assertEquals(f.xIncriment(), 0);
		assertEquals(f.yIncriment(), -1);
		f.flipDirection(f.getDirection());
		assertEquals(f.xIncriment(), 0);
		assertEquals(f.yIncriment(), 1);
		
		f = new Fish(FishType.MENHADEN, Direction.EAST, 1500, 900);
		f.flipDirection(f.getDirection());
		assertEquals(f.xIncriment(), -1);
		assertEquals(f.yIncriment(), 0);
		f.flipDirection(f.getDirection());
		assertEquals(f.xIncriment(), 1);
		assertEquals(f.yIncriment(), 0);
		
		f = new Fish(FishType.MENHADEN, Direction.SOUTHEAST, 1500, 900);
		f.flipDirection(Direction.SOUTH);
		assertEquals(f.xIncriment(), 1);
		assertEquals(f.yIncriment(), -1);
		f.flipDirection(Direction.EAST);
		assertEquals(f.xIncriment(), -1);
		assertEquals(f.yIncriment(), -1);
		
		f = new Fish(FishType.MENHADEN, Direction.SOUTHWEST, 1500, 900);
		f.flipDirection(Direction.SOUTH);
		assertEquals(f.xIncriment(), -1);
		assertEquals(f.yIncriment(), -1);
		f.flipDirection(Direction.WEST);
		assertEquals(f.xIncriment(), 1);
		assertEquals(f.yIncriment(), -1);

        f = new Fish(FishType.MENHADEN, Direction.NORTHEAST, 1500, 900);
        f.flipDirection(Direction.NORTH);
        assertEquals(f.xIncriment(), 1);
        assertEquals(f.yIncriment(), 1);
        f.flipDirection(Direction.EAST);
        assertEquals(f.xIncriment(), -1);
        assertEquals(f.yIncriment(), 1);

        f = new Fish(FishType.MENHADEN, Direction.NORTHWEST, 1500, 900);
        f.flipDirection(Direction.NORTH);
        assertEquals(f.xIncriment(), -1);
        assertEquals(f.yIncriment(), 1);
        f.flipDirection(Direction.WEST);
        assertEquals(f.xIncriment(), 1);
        assertEquals(f.yIncriment(), 1);




		
	}
	public static void test_move() {
		Fish f = new Fish(FishType.PERCH, Direction.NORTHEAST, 1500, 900, 5, 5, 100, 100);
		f.move();
		assertEquals(f.getXLoc(), 100);
		assertEquals(f.getYLoc(), 100);
		
		f = new Fish(FishType.PERCH, Direction.NORTH, 1500, 900, 1, 1, 100, 100);
		f.move();
		assertEquals(f.getYLoc(), 100);
		
		f = new Fish(FishType.PERCH, Direction.EAST, 1500, 900, 1, 1, 100, 100);
		assertEquals(f.getXLoc(), 100);
		
		f = new Fish(FishType.PERCH, Direction.SOUTHEAST, 1500, 900, 1, 1, 100, 100);
		assertEquals(f.getXLoc(), 100);
		assertEquals(f.getYLoc(), 100);
		
		f = new Fish(FishType.PERCH, Direction.SOUTH, 1500, 900, 1, 1, 100, 100);
		assertEquals(f.getYLoc(), 100);
		
		f = new Fish(FishType.PERCH, Direction.SOUTHWEST, 1500, 900, 1, 1, 100, 100);
		assertEquals(f.getXLoc(), 100);
		assertEquals(f.getYLoc(), 100);
		
		f = new Fish(FishType.PERCH, Direction.WEST, 1500, 900, 1, 1, 100, 100);
		assertEquals(f.getXLoc(), 100);
		
		f = new Fish(FishType.PERCH, Direction.NORTHWEST, 1500, 900, 1, 1, 100, 100);
		assertEquals(f.getXLoc(), 100);
		assertEquals(f.getYLoc(), 100);
		
	}
	
	public static void test_setTutorialFish() { 
		Fish f = new Fish(FishType.MINNOW, Direction.NORTH, 1500, 900);
		f.setTutorialFish();
		assertEquals(f.xSpeed, 0);
		assertEquals(f.ySpeed, 0);
		assertEquals(f.xloc, 1500/2);
		assertEquals(f.yloc, 900/2);
		
		
		
	}
	public static void test_Equals() { 
		Fish f = new Fish(FishType.MINNOW, Direction.NORTH, 1500, 900);
		Fish g = new Fish(FishType.MINNOW, Direction.NORTH, 1500, 900);
		assertEquals(f.equals(g), false);
		assertEquals(f.equals(f), true);
	}
	 
	public static void test_rDirection() {
		Fish f = new Fish(FishType.MINNOW, Direction.NORTH, 1500, 900);
		f.direction = null;
		f.randomizeDirection();
		String s = " ";
		assertEquals(f.direction.toString().getClass(), s.getClass());
	}
	 
	

}
