package gofish_assn;

/**
 * This class simulates a card from a standard deck
 * of 52 cards, with no jokers.
 * @author John Sigmon, Daniel Diamont
 *
 */
public class Card {
	
	public enum Suits {club, diamond, heart, spade};
	
	static int TOP_RANK = 13; //King
	static int LOW_RANK = 1; //Ace
	
	int rank;  //1 is an Ace
	Suits suit;
	
	/**
	 * This constructor is the default, and makes 
	 * an ace of spades.
	 */
	public Card() {
		rank = 1;
		suit = Suits.spade;
	}
	
	/**
	 * Constructor takes a rank and makes a "rank" of spades.
	 * @param r
	 */
	public Card(int r) {
		this.rank = r;
		this.suit = Suits.spade;
	}
	
	/**
	 * This constructor creates a card of a specific
	 * rank and suit.
	 * @param r This parameter is an int, it defines the rank of the 
	 * card being created. Rank 1 corresponds to an ace,
	 * rank 11 -> Jack, rank 12 -> Queen, rank 13 -> King.
	 * 
	 * @param s This parameter is a single char, used as
	 * shorthand to denote the desired suit. Either upper
	 * or lower case can be used. s -> spades, d -> diamonds, 
	 * c -> clubs, h -> hearts.
	 */
	public Card(int r, char s) {
		this.rank = r;
		this.suit = this.toSuit(s);
	}
	
	///////////////////////Check this one.
	/**
	 * This constructor creates a card of a specific
	 * rank and suit.
	 * @param r This parameter is an int, it defines the rank of the 
	 * card being created. Rank 1 corresponds to an ace,
	 * rank 11 -> Jack, rank 12 -> Queen, rank 13 -> King.
	 * 
	 * @param s This parameter is an enum type, and it
	 * assigns the proper enum to the card suit.
	 */
	public Card(int r, Suits s) {
		this.rank = r;
		this.suit = s;
	}
	/**
	 * This 
	 * @param c
	 * @return
	 */
	private Suits toSuit(char c) {
		c = Character.toLowerCase(c);
		switch(c) {
			case 'c': return Suits.club;
			case 'd': return Suits.diamond;
			case 'h': return Suits.heart;
			case 's': return Suits.spade;
			default: assert false ;
		}
		return null; //dummy return
	}
	
	private String suitToString(Suits s)
	{
		switch(s) {
			case club: return "c";
			case diamond: return "d";
			case heart: return "h";
			case spade: return "s";
			default: assert false ;
	}
		return null; //dummy return
	}
	
	private String rankToString(int r)
	{
		switch(r) {
			case 1: return "A";
			case 2: return "2";
			case 3: return "3";
			case 4: return "4";
			case 5: return "5";
			case 6: return "6";
			case 7: return "7";
			case 8: return "8";
			case 9: return "9";
			case 10: return "10";
			case 11: return "J";
			case 12: return "Q";
			case 13: return "K";
			default: assert false;
		}
		return null; //dummy return
	}
		
	
	public int getRank() {
		return rank;
	}
	
	public Suits getSuit() {
		return suit;
	}
	
	public String toString() {
		String s = "";
		
		s = s + rankToString(getRank()) + suitToString(getSuit()); //format on piazza?
		
		return s;
	}
}
