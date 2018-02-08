package gofish_assn;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest {

	@Test
	public void ConstructorTest() {
		
		Card test =  new Card(4, 's');
		
		Card test2 = new Card(11, 'h');
		
		Card test3 = new Card(1, 's');
		
//		assertEquals("spade", test.suitToString(test.getSuit()));
//		assertEquals(4, test.getRank());
		
		assertEquals("4s",test.toString());
		assertEquals("Jh", test2.toString());
		assertEquals("As", test3.toString());	
		
	
	}
	
	/*
	 * this test doesn't work because getFirstCard()
	 * pulls from index 0 of the arrayList and dealCard() pulls
	 * from element size()-1
	 */
//	@Test
//	public void DeckTest() {
//		Deck deck_1 = new Deck();
//		deck_1.shuffle();
//		int num_test_cards = 52;
//		for (int i = 0; i < num_test_cards; i ++) {
//			Card expected = deck_1.getFirstCard();
//			Card actual = deck_1.dealCard();
//			assertEquals(expected, actual);
//		}
//		assertEquals(0, deck_1.getSize());
		
//	}

}
