
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
public class FishTests { 
	@Test
	public void testMovement(){
		Fish f1 = new Fish(FishType.HOGCHOKER, Direction.EAST, 500, 500, 5, 5, 1, 1);
		Fish f2 = new Fish(FishType.FLOUNDER, Direction.SOUTH, 500, 500, 5, 5, 1, 1);
		
		f2.setTopBound(0);
		f2.setBottomBound(500);
		ArrayList<Fish> fish = new ArrayList<Fish>();
		fish.add(f1);
		fish.add(f2);
		FishModel tester = new FishModel(500, 500, 100, 100, fish);
		
		tester.update("");
		
		int newX = f1.getXLoc();
		assertEquals(newX, 6);
		
		int newY = f2.getYLoc();
		assertEquals(newY, 6);
	}
	
	@Test
	public void testFlips() {
		Fish f1 = new Fish(FishType.HOGCHOKER, Direction.NORTH, 500, 500, 5, 5, 3, 3);
		Fish f2 = new Fish(FishType.FLOUNDER, Direction.EAST, 500, 500, 5, 5, 497, 5);
		Fish f3 = new Fish(FishType.MENHADEN, Direction.SOUTH, 500, 500, 5, 5, 5, 497);
		Fish f4 = new Fish(FishType.MINNOW, Direction.WEST, 500, 500, 5, 5, 3, 3);
		
		
		ArrayList<Fish> fishes = new ArrayList<Fish>();
		fishes.add(f1);
		fishes.add(f2);
		fishes.add(f3);
		fishes.add(f4);
		
		FishModel tester = new FishModel(500, 500, 100, 100, fishes);
		
		tester.update("");
		
		assertEquals(0, f1.getYLoc());
		assertEquals(Direction.SOUTH, f1.getDirection());
	
		assertEquals(500, f2.getXLoc());
		assertEquals(Direction.WEST, f2.getDirection());
		
		assertEquals(500, f3.getYLoc());
		assertEquals(Direction.NORTH, f3.getDirection());
		
		assertEquals(0, f4.getXLoc());
		assertEquals(Direction.EAST, f4.getDirection());
	}
	
	@Test
	public void testNetMovement() {
		FishModel tester = new FishModel(500, 500, 0, 0, new ArrayList<Fish>());
		
		assertEquals(tester.getNetX(), 250);
		
		tester.update("down");
		tester.update("down");
		
		assertEquals(tester.getNetY(), 2 * tester.getVelocity());
		
		tester.update("left");
		assertEquals(tester.getNetX(), 250 - tester.getVelocity());

		tester.update("right");
		assertEquals(tester.getNetX(), 250);
		
		tester.update("up");
		assertEquals(tester.getNetY(), tester.getVelocity());
		
		tester.setNetY(499);
		tester.moveDown();
		assertEquals(tester.getNetY(), 500);
		
		tester.setNetY(1);
		tester.moveUp();
		assertEquals(tester.getNetY(), 0);
		
		tester.setNetX(1);
		tester.moveLeft();
		assertEquals(tester.getNetX(), 0);
		
		tester.setNetX(499);
		tester.moveRight();
		assertEquals(tester.getNetX(), 500);
	}
	
	@Test
	public void testCollisions() {
		Fish f1 = new Fish(FishType.FLOUNDER, Direction.EAST, 500, 500, 0, 0, 250, 0);
		ArrayList<Fish> fish1 = new ArrayList<Fish>();
		fish1.add(f1);

		
		FishModel tester1 = new FishModel(500, 500, 100, 100, fish1);
		tester1.checkCollisions();
		assertEquals(true, f1.isCaught);
		

		Fish f2 = new Fish(FishType.HOGCHOKER, Direction.EAST, 500, 500, 0, 0, 250, 250);
		ArrayList<Fish> fish2 = new ArrayList<Fish>();
		fish2.add(f2);
		
		FishModel tester2 = new FishModel(500, 500, 100, 100, fish2);
		tester2.checkCollisions();
		assertEquals(false, f2.isCaught);
		
		
		f1.isCaught = false;
		f2.isCaught = false;
		ArrayList<Fish> fish3 = new ArrayList<Fish>();
		fish3.add(f1);
		fish3.add(f2);
		
		FishModel tester3 = new FishModel(500, 500, 100, 100, fish3);
		tester3.setTargetFish(f2);
		tester3.checkCollisions();
		assertEquals(false, f1.isCaught);
	}
	
	@Test
	public void testCaptured() {
		Fish f1 = new Fish(FishType.FLOUNDER, Direction.EAST, 500, 500, 0, 0, 250, 250);
		f1.isCaught = true;
		ArrayList<Fish> fish = new ArrayList<Fish>();
		fish.add(f1);
		
		FishModel tester = new FishModel(500, 500, 0, 0, fish);
		tester.setNetY(250);
		tester.grabbingFish = true;
		
		tester.update("");
		assertEquals(250 - tester.getVelocity(), tester.getNetY());
		
		assertEquals(250 - tester.getVelocity(), f1.getYLoc());
		assertEquals(250, f1.getXLoc());
		
		tester.setNetY(1);
		tester.update("");
		assertEquals(tester.getNetY(), 0);
		assertEquals(tester.getFishList().size(), 0);
	}
	
	@Test
	public void testTutorial() {
		FishModel tester = new FishModel(500, 500, 0, 0, new ArrayList<Fish>());
		tester.inTutorial = true;
		
		tester.update("");
		
		assertTrue(tester.getFishList().size() > 0);
	}
	
	@Test
	public void testCreationOfRandomFish() {
		FishModel tester = new FishModel(500, 500, 0, 0);
		assertEquals(tester.getFishList().size(), 1);
	}
}