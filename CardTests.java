
import junit.framework.TestCase;
public class CardTests extends TestCase {
	static Card c = new Card();
	public static void test_cardFlip() {
		Card c0 = new Card("c0");
		
		c0.cardFlip();
		assertEquals(c0.isFlipped(), true);
		c0.cardFlip();
		assertEquals(c0.isFlipped(), false);
		
	}
	
	public static void test_basics() {
		Card c0 = new Card("c0");
		
		assertEquals(c0.toString(), "c0");
		assertEquals(c0.isFlipped(), false);
		assertEquals(c0.isMatched(), false);
		c0.match();
		assertEquals(c0.isMatched(), true);
	}

}
