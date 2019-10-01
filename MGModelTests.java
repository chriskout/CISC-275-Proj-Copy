import java.awt.Point;
import java.util.ArrayList;
import junit.framework.TestCase;

public class MGModelTests extends TestCase {
	static MGModel m1 = new MGModel(500, 1500);
	static MGModel m2 = new MGModel(500, 1500);
	static MGModel m3 = new MGModel(500, 1500);
	
	public static void test_makeCards() {
		m1.makeCards();
		m1.animals.get(0).match();	
	}
	public static void test_isMatch() {
		m1.flipped.add(new Card("a0"));
		m1.flipped.add(new Card("e0"));
		assertEquals(true,m1.isMatch());
		m2.flipped.add(new Card("a1"));
		m2.flipped.add(new Card("a2"));
		assertEquals(false,m2.isMatch());
	}
	public static void test_flip() {
		Card c1 = new Card("a0");
		assertEquals('c',c1.face);
		m1.modelFlip(c1);
		assertEquals('b',c1.face);
	}
	public static void test_flipClicked() {
		m1.frameWidth = 1500;
		m1.frameHeight = 500;
		Point p1 = new Point(100,100);
		Point p2 = new Point(300,300);
		Point p3 = new Point(500,500);
		Point p4 = new Point(100,500);
		Point p5 = new Point(1300,100);
		Point p6 = new Point(1100,200);
		Point p7 = new Point(1300,300);
		m1.getFlippedClicked(p1);
		m1.getFlippedClicked(p2);
		m1.getFlippedClicked(p3);
		m1.getFlippedClicked(p4);
		m1.getFlippedClicked(p5);
		m1.getFlippedClicked(p6);
		m1.getFlippedClicked(p7);
	}
	
	public static void test_clearFlipped() {
		Card a0 = new Card("a0");
		m1.flipped.add(a0);
		m1.clearFlipped();
		assertEquals(m1.flipped.isEmpty(), true);	
	} 
	
	public static void test_getScore() {
		assertEquals(m1.getScore(), 0);
	}
	
	
}
