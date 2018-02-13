package gofish_assn;

import java.util.*;

/**
 * 
 * @author John Sigmon, Daniel Diamont
 * 
 */
public class CardPair {
	Card Card1 = new Card();
	Card Card2 = new Card();
	
	/**
	 * Constructor takes two Card types and makes them a pair
	 * @param card1
	 * @param card2
	 */
	public CardPair(Card card1, Card card2) {
		this.Card1 = card1;
		this.Card2 = card2;
	}
	
	/**
	 * Constructor takes a rank, and two suits and makes a pair
	 * @param r rank of both cards
	 * @param suit1 suit of card 1
	 * @param suit2 suit of card 2
	 */
	public CardPair(int r, char suit1, char suit2) {
		this.Card1 = new Card(r, suit1);
		this.Card2 = new Card(r, suit2);
	}
	
	/**
	 * This method returns a list of the two cards in the pair.
	 * Can be used to access the pair items.
	 * @return List of 2 Cards
	 */
	public List<Card> getPair() {
		List<Card> pair = new ArrayList<Card>();
		pair.add(this.Card1);
		pair.add(this.Card2);
		return pair;
	}
}
