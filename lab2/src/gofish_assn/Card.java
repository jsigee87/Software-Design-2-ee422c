package gofish_assn;

/**
 * This class simulates a card from a standard deck
 * of 52 cards, with no jokers.
 * @author John Sigmon, Daniel Diamont
 *
 */
public class Card {
	
	/**
	 * This enumerates all the possible suits that a
	 * card in our deck can be.
	 */
	public enum Suits {club, diamond, heart, spade};
	
	static int TOP_RANK = 13; //King
	static int LOW_RANK = 1; //Ace
	
	int rank;  //1 is an Ace
	Suits suit;
	
	/**
	 * This is the default constructor, and makes 
	 * an ace of spades.
	 */
	public Card() {
		rank = 1;
		suit = Suits.spade;
	}
	
	/**
	 * Constructor takes a rank and makes a "rank" of spades.
	 * @param r integer rank
	 */
	public Card(int r) {
		this.rank = r;
		this.suit = Suits.spade;
	}
	
	/**
	 * This constructor creates a card of a specific
	 * rank and suit.
	 * @param r This parameter is an integer, it defines the rank of the 
	 * card being created. Rank 1 corresponds to an ace,
	 * 
	 * @param s This parameter is a single char, used as
	 * shorthand to denote the desired suit. Either upper
	 * or lower case can be used. 
	 */
	public Card(int r, char s) {
		this.rank = r;
		this.suit = this.toSuit(s);
	}
	
	/**
	 * This constructor creates a card of a specific
	 * rank and suit.
	 * @param r This parameter is an integer, it defines the rank of the 
	 * card being created. Rank 1 corresponds to an ace,
	 * 
	 * @param s This parameter is an enumerated type, and it
	 * assigns the proper enumerated to the card suit.
	 */
	public Card(int r, Suits s) {
		this.rank = r;
		this.suit = s;
	}
	
	/**
	 * This method converts a single char (upper or lower case) to
	 * the appropriate enumerated suit. An exception is thrown if a char that
	 * is not covered is passed in.
	 * @param c char
	 * @return Suit 
	 */
	private Suits toSuit(char c) {
		c = Character.toLowerCase(c);
		switch(c) {
			case 'c': return Suits.club;
			case 'd': return Suits.diamond;
			case 'h': return Suits.heart;
			case 's': return Suits.spade;
			default: throw new IllegalArgumentException();
		}
	}
	
	/**
	 * This method prints the object's suit as a char
	 * @param s suit enumerated type
	 * @return String
	 */
	private String suitToString(Suits s)
	{
		switch(s) {
			case club: return "c";
			case diamond: return "d";
			case heart: return "h";
			case spade: return "s";
			default: throw new IllegalArgumentException();
		}
	}
	
	/**
	 * This method prints the object's rank as a String.
	 * @param r is the rank as an integer
	 * @return String is the actual rank
	 */
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
		
	/**
	 * This method gets the rank of the card and returns it
	 * @return integer rank
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * This method gets the suit of the card and returns it
	 * @return Suits type
	 */
	public Suits getSuit() {
		return suit;
	}
	
	/**
	 * This toString method prints up the object by printing the
	 * rank and the suit together.
	 */
	public String toString() {
		String s = "";
		
		s = s + rankToString(getRank()) + suitToString(getSuit()); //format on piazza?
		
		return s;
	}
}
