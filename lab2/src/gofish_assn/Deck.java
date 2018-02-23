package gofish_assn;

import java.util.ArrayList;

//import gofish_assn.Card.Suits;
import java.util.Random;

public class Deck {
	ArrayList<Card> deck = new ArrayList<Card> ();
	final int NUM_CARDS = 52;  //for this kind of deck
	
	char[] suits = new char[] {'s', 'd', 'c', 'h'};
	
	/**
	 * Default Constructor, makes deck sorted by suit, then rank.
	 */
	public Deck() {
		
		for(char suit : suits) {
			for (int i = 1; i <= 13; i ++) {
				Card curr_card = new Card(i, suit);
				deck.add(curr_card);
			}
		}
		
	}
	
	/**
	 * Shuffle method fully shuffles a deck each time it is called.
	 */
	public void shuffle() {
		Random rand = new Random();
		int num_shuffles = 1000;
		for(int i = 0; i < num_shuffles; i ++) {
			int first_index = rand.nextInt(52);
			int second_index = rand.nextInt(52);
			Card temp = deck.get(first_index);
			deck.set(first_index, deck.get(second_index));
			deck.set(second_index, temp);
		}
	}
	
	/**
	 * Print Deck method will print the entire deck to standard I/O.
	 */
	
	public void printDeck() {
		for (Card card: deck) {
			System.out.println(card.toString());
		}
	}
	
	/**
	 * This function deals a card from the deck. Due to the choice of
	 * ArrayList, the function draws the the end of the deck to be a
	 * little more efficient. The dealt card is then removed from the 
	 * deck.
	 * @return Card object, the card being dealt.
	 */
	public Card dealCard() {
		if (deck.isEmpty() == true) {
			throw new IllegalStateException("You can't draw from an empty deck");
		}
		Card c = deck.get(deck.size() - 1);
		deck.remove(deck.size() - 1);
		return c;
		
	}
	
	/**
	 * This method returns the top card in the deck.
	 * @return Card that is the top of the deck.
	 */
	public Card getFirstCard() {
		if (deck.isEmpty() == true) {
			assert false;
		}
		Card c = deck.get(0);
		return c;
	}
	
	/**
	 * This method returns the number of cards left in the deck
	 * @return integer that is the number of cards left in the deck.
	 */
	public int getSize() {
		return deck.size();
	}

}
