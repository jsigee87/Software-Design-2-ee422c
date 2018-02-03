package gofish_assn;

import java.util.*;

public class CardPair {
	Card Card1 = new Card();
	Card Card2 = new Card();
	
	public CardPair(Card card1, Card card2) {
		this.Card1 = card1;
		this.Card2 = card2;
	}
	
	public CardPair(int r, char suit1, char suit2) {
		this.Card1 = new Card(r, suit1);
		this.Card2 = new Card(r, suit2);
	}
	
	public List<Card> getPair() {
		List<Card> pair = new ArrayList<Card>();
		pair.add(this.Card1);
		pair.add(this.Card2);
		return pair;
	}
}
