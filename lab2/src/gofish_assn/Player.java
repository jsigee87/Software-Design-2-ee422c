package gofish_assn;

import java.util.*;

public class Player {
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<CardPair> book = new ArrayList<CardPair>();
	
	private String name;
	
	/**
	 * Default Constructor sets player name to null.
	 */
	public Player() {
		this.name = null;
	}
	
	/**
	 * Constructor takes a string and creates a player with that name.
	 * @param name for players name
	 */
	public Player(String name) {
		this.name = name;
	}
	
	/**
	 * This method takes a Card and adds it to the player's hand.
	 * @param c Card
	 */
	public void addCardToHand(Card c) {
		hand.add(c);
	}
	
	/**
	 * This method takes a Card and removes it from a player's hand.
	 * @param c Card to be removed.
	 * @return Card that got removed.
	 */
	public Card removeCardFromHand(Card c) {
		//find index of card
		int index = hand.indexOf(c);
		
		//find the Card at that index
		Card retCard = hand.get(index);
		
		//remove the card from the hand
		hand.remove(index);
		
		return retCard;
	}
	
	/**
	 * This method returns a string of the player's name.
	 * @return String of player name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method prints the players hand out as a string
	 * @return String of cards in player's hand.
	 */
	public String handToString() {
		String s = new String();
		s = "";
		
		for(Card card : hand) {
			s = s + card.toString() + ", "; //ask for format on piazza?
		}
	
		return s;
	}
	
	/**
	 * This method prints out the players book as a string
	 * @return String of cards in player's book.
	 */
	public String bookToString() {
		String s = new String();
		s = "";
		
		for(CardPair pair : book) {
			for (Card card : pair.getPair()) {
				s = s + card.toString() + ", "; //ask for format on piazza?
			}
			s = s + "\r\n";
		}
	
		return s;
	}
	
	/**
	 * Method to get number of cards in hand.
	 * @return integer for number of cards in hand.
	 */
	public int getHandSize() {
		return hand.size();
	}
	
	/**
	 * Method to get number of books the player has.
	 * @return integer for number of books.
	 */
	public int getBookSize() {
		return book.size();
	}
	
	/**
	 * This method takes a Card and checks to see if a card of the same rank
	 * is in the player's hand.
	 * @param c Card requested
	 * @return Card if a card of same rank is present, Null otherwise.
	 */
	// Pass it the card you are requesting. It looks for a card with same rank
    // in the players hand. If the card is not there, it returns null.
    public Card sameRankInHand(Card c) {
    	int rank = c.getRank();
    	for (Card card : hand) {
    		if (card.getRank() == rank && !card.equals(c)) {
    			return card;
    		}
    	}
    	return null;
    }
    
    /**
	 * This function checks your hand for a book.
	 * @return Rank of book, if there is one. -1 otherwise.
	 */
	public int checkForBook() {
		for (Card card: this.hand){
			// Not null means there is that same rank in the hand
			if (sameRankInHand(card) != null) {
				return card.rank;
			}
		}
		return -1;
	}

	/**
	 * This function searches an entire hand and removes only one book
	 * of the given rank. This function assumes that you already checked
	 * if there is a book.
	 * @param rank Rank input tells the function what book to look for
	 * @return CardPair my pair is a card pair 
	 */
	
    public CardPair removeBookFromHand(int rank) {   
    	int counter = 0;
    	List<Card> pair = new ArrayList<Card>();
    	
    	for (int i = 0; i < hand.size(); i++) { //used counter instead of for-each to avoid concurrentModificationExceptiton
    		if (hand.get(i).getRank() == rank) {
    			pair.add(hand.get(i));
    			hand.remove(hand.get(i));
    			i--;
    			
    			/////////////////////////need to make sure both cards get added to pair
    			counter += 1;
    			
    			// If we found two, then return.
    			if (counter == 2) {
    				CardPair myPair = new CardPair(pair.get(0), pair.get(1));
//    				System.out.println("Removed " + pair.get(0).toString() + " and " + pair.get(1).toString() + ".");
    				return myPair;
    			}
    		}
    	}
		return null;	
    }
	
    //uses some strategy to choose one card from the player's
    //hand so they can say "Do you have a 4?"
    
    /**
     * this method selects ranks between 1 and 13 at random and checks
     * if a card of that rank exists in the current player's hand. If this is the case,
     * the function returns that card.
     * @return a random card in a player's hand
     */
    public Card chooseCardFromHand() {
    	//we can probably select this card randomly
    	Random rand = new Random();
    	int rank;
    	
    	while(true) {
    		rank = rand.nextInt(14) + 1; //return random Int from 1 to 13
    		Card c = new Card(rank);
    		Card newCard = this.sameRankInHand(c);
    		if(newCard != null) { //check if card exists in current player's hand
    			return newCard;
    		}
    	}
    }

    /**
     * Adds a card pair to a player's book
     * @param pair CardPair to be added.
     */
    public void addPairToBook(CardPair pair) {
    	book.add(pair);
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
   
	
	
    
    
    //OPTIONAL
    // comment out if you decide to not use it    
    //Does the player have a card with the same rank as c in her hand?
    //public boolean rankInHand(Card c) {
    //	return false; //stubbed
    //}
    
    
    
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
    
    
    

}
