package gofish_assn;

import java.util.*;

public class Player {
	
	ArrayList<Card> hand = new ArrayList<Card>();
	ArrayList<CardPair> books = new ArrayList<CardPair>();
	String name;
	
	public Player() {
		this.name = null;
	}
	
	public Player(String name) {
		this.name = name;
	}
	
	public void addCardToHand(Card c) {
		hand.add(c);
	}
	
	public Card removeCardFromHand(Card c) {
		//find index of card
		int index = hand.indexOf(c);
		
		//find the Card at that index
		Card retCard = hand.get(index);
		
		//remove the card from the hand
		hand.remove(index);
		
		return retCard;
	}
	
	public String getName() {
		return name;
	}
	
	public String handToString() {
		String s = new String();
		s = "";
		
		for(Card card : hand) {
			s = s + card.toString() + ", "; //ask for format on piazza?
		}
	
		return s;
	}
	
	public String bookToString() {
		String s = new String();
		s = "";
		
		for(CardPair pair : books) {
			for (Card card : pair.getPair()) {
				s = s + card.toString() + ", "; //ask for format on piazza?
			}
		}
	
		return s;
	}
	
	public int getHandSize() {
		return hand.size();
	}
	
	
	public int getBookSize() {
		return books.size();
	}
	
	/*
    public int checkHandForBook() {
    	int bookSize = 2;
    	List<Integer> rank_frequency = new ArrayList<Integer>(Collections.nCopies(13, 0));
    	for (Card card : hand) {
    		rank_frequency.set(card.getRank() - 1, rank_frequency.get(card.getRank() - 1));
    		// Does this frequency equal 4, if so then we have a book, return the rank
    		if (rank_frequency.get(card.getRank() - 1) == bookSize) {
    			return card.getRank();
    		}
    	}
    	// If we reach here, there is no book.
    	return -1;  		
    	
    }
    */
   
    public void removeBookFromHand(int rank) {   
    	for (Card card : hand) {
    		if (card.getRank() == rank) {
    			hand.remove(card);
    		}
    	}	
    }
    
    public void addPairToBook(CardPair pair) {
    	books.add(pair);
    }
    
    //OPTIONAL
    // comment out if you decide to not use it    
    //Does the player have a card with the same rank as c in her hand?
    public boolean rankInHand(Card c) {
    	return false; //stubbed
    }
    
    //uses some strategy to choose one card from the player's
    //hand so they can say "Do you have a 4?"
    public Card chooseCardFromHand() {
    	Card c = new Card();
    	
    	return c;
    }
    
    //Does the player have the card c in her hand?
    /*public boolean cardInHand(Card c) {
    	if (hand.contains(c)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    */

    //OPTIONAL
    // comment out if you decide to not use it    
    //Does the player have a card with the same rank as c in her hand?
    //e.g. will return true if the player has a 7d and the parameter is 7c
    
    
    // Pass it the card you are requesting. It looks for a card with same rank
    // in the players hand. If the card is not there, it returns null.
    public Card sameRankInHand(Card c) {
    	int rank = c.getRank();
    	for (Card card : hand) {
    		if (card.getRank() == rank) {
    			return card;
    		}
    	}
    	return null;
    }

}
