package gofish_assn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class GoFishGame {
	
	final static int NUMBOOKSTOWIN = 26;
	final static int SIZEFIRSTHAND = 7;
	// If turn == 0, player 1 goes
	// If turn == 1, player 2 goes
	private int turn;
	private int numPlayers;
	
	private Player player1;
	
	private Player player2;
	
	ArrayList<Player> playerList = new ArrayList<Player>();
	
//	ArrayList<Integer> numBooks = new ArrayList<Integer>();
	
	Deck gameDeck;
	
	/**
	 * This is the primary game constructor, it takes two strings, 
	 * makes them into players, creates a deck, shuffles it, and 
	 * deals an initial hand.
	 * @param player1 first player
	 * @param player2 second player
	 */
	public GoFishGame(String player1, String player2) {
		
		this.player1 = new Player(player1);
		this.player2 = new Player(player2);
		
		this.playerList.add(this.player1);
		this.playerList.add(this.player2);
		
		numPlayers = playerList.size();
		
		this.gameDeck = new Deck();
		gameDeck.shuffle();
		
		dealHand(this.player1);
		dealHand(this.player2);	
		
	}
	
	/**
	 * This game constructor takes a whole list of strings,
	 * converts them into players, makes a deck and shuffles it, 
	 * then deals all the players hands. Note- size of first hand
	 * is static and so remains 7 in this case. This should be 
	 * fixed.
	 * @param list of strings that are player names.
	 */
	public GoFishGame(ArrayList<String> list) {
		
		for(String s : list) {
			Player p = new Player(s);
			this.playerList.add(p);
		}
		
		numPlayers = playerList.size();
		
		this.gameDeck = new Deck();
		gameDeck.shuffle();
		
		for(Player player : playerList) {
			dealHand(player);
		}
	}
	
	/**
	 * This method deals a hand.
	 * @param p is a Player type that is passed in, and given a hand.
	 */
	public void dealHand(Player p) {
		for (int i = 0; i < SIZEFIRSTHAND; i ++){
			p.addCardToHand(gameDeck.dealCard());
		}
	}
	
	/**
	 * This method takes an  integer identifying the current player, and a Card
	 * type and checks the other players hand for that card. If it finds it,
	 * it removes it from the other player's hand and adds it to the current
	 * players hand, then makes a book immediately.
	 * @param currentPlayer is the current player
	 * @param requestedCard the card being requested
	 * @return true or false depending on if the request was successful.
	 */
	public boolean requestCard(int currentPlayer, Card requestedCard) {
		
		int nextPlayer = (currentPlayer + 1) % numPlayers;
		
		Card myCard = playerList.get(nextPlayer).sameRankInHand(requestedCard);
		
		// If next player has the requested card, remove it from
		// both hands, update the books.
		if (myCard != null) {
			playerList.get(nextPlayer).removeCardFromHand(myCard);
			playerList.get(currentPlayer).removeCardFromHand(requestedCard);
			CardPair bookPair = new CardPair(requestedCard, myCard);
			playerList.get(turn).addPairToBook(bookPair);
			return true;
		}
		else {
			return false;
		}
//		if (turn == 0) {
//			Card requestedCard = player1.chooseCardFromHand();
//			Card myCard = player2.sameRankInHand(requestedCard);
//			// If player 2 has the requested card, remove it from
//			// both hands, update the books.
//			if (myCard != null) {
//				player2.removeCardFromHand(myCard);
//				player1.removeCardFromHand(requestedCard);
//				CardPair bookPair = new CardPair(requestedCard, myCard);
//				player1.addPairToBook(bookPair);
//				return;
//			}
//			else {
//				return ;
//			}
//		}
//		else {
//			Card requestedCard = player2.chooseCardFromHand();
//			Card myCard = player1.sameRankInHand(requestedCard);
//			if (myCard != null) {
//				player1.removeCardFromHand(myCard);
//				player2.removeCardFromHand(requestedCard);
//				CardPair bookPair = new CardPair(requestedCard, myCard);
//				player2.addPairToBook(bookPair);
//				return;
//			}
//			else {
//				return ;
//			}
//		}
	}
	
	/**
	 * This method updates the books for all players at the same time.
	 */
	public void updateAllBooks() {
		
		for(Player player : playerList) {
			
			int bookRank = player.checkForBook();
//			System.out.println("bookRank = " + bookRank);
			
			while(bookRank != -1) { //keep removing books until there are no more books left
				
				CardPair pair = player.removeBookFromHand(bookRank);
				player.addPairToBook(pair);
				bookRank = player.checkForBook();
//				System.out.println("bookRank = " + bookRank);

			}
		}
	}
	
	/**
	 * This method checks if a game is over. If the number of books in existence is less than
	 * required, it returns false, if it is the number of books to end the game, it returns true.
	 * If there are somehow more books than necessary to end the game, and illegal state exception
	 * is thrown
	 * @return true if the game is over, false if the game should continue.
	 * @throws IllegalStateException when you have too many books somehow
	 */
	public boolean isGameOver() throws IllegalStateException {
		
		int totalBooks = 0;
		
		for(int i = 0; i < playerList.size(); i++) {
			
			totalBooks += playerList.get(i).getBookSize();
		}
		/*
		 * commented the lines below because now we can have a set of players of size greater than 2,
		 * so we must account for all of them
		 */
//		int pl1Books = player1.books.size();
//		int pl2Books = player2.books.size();
//		int totalBooks = pl1Books + pl2Books;
		if (totalBooks == NUMBOOKSTOWIN) {
			return true;
		}
		if (totalBooks < NUMBOOKSTOWIN) {
			return false;
		}
		if (totalBooks > NUMBOOKSTOWIN) {
			throw new IllegalStateException("You somehow have too many books.");
		}
		return false;
	}
	
	/**
	 * This method changes the turn
	 * @return integer for which player's turn it is.
	 */
	public int changeTurn() {
		
		turn = (turn + 1) % playerList.size();
		
		return turn;
	}
	
	/**
	 * This method returns the current turn.
	 * @return integer turn for current player's turn.
	 */
	public int getTurn() {
		return turn;
	}
	
	/**
	 * This method gets the number of players
	 * @return integer number of players.
	 */
	public int getNumPlayers() {
		return numPlayers;
	}
	
	/**
	 *  This method randomly chooses a player to go first.
	 */
	public void chooseFirstPlayer() {
		Random rand = new Random();
		
		turn =  rand.nextInt(2);
	}
	
	/**
	 * This method figures out which player is the winner, given that 
	 * the game is over.
	 * @return integer for which player wins.
	 */
	public int determineWinner() {
//		int winnerIndex = playerList.get(0).getBookSize();
//		int i;
//		for(i = 1; i < playerList.size(); i++) {
//			if(playerList.get(i).getBookSize() > playerList.get(i-1).getBookSize()) {	
//				winnerIndex = i;
//			}
//		}
		
		//find the max in the arrayList
		int max = playerList.get(0).getBookSize();
		
//		System.out.println("p1 size: " + max);
//		System.out.println("p2 size: " + playerList.get(1).getBookSize());
		
		int indexMax = 0;
		
		int size;
		
		for(int i = 0; i < playerList.size(); i++) {
			size = playerList.get(i).getBookSize();
			if(size > max) {
				max = size;
				indexMax = i;
			}
		}
		
//		System.out.println("max: " + max);
		//find number of other occurrences of max
		int occurrences = 0;
		
		for(int i = 0; i < playerList.size(); i++) {
			if(i != indexMax && playerList.get(i).getBookSize() == max)
				occurrences++;			
		}
		
//		System.out.println("Other occurrences: " + occurrences);
		if(occurrences == 0) {//we only have one winner
//			System.out.println("indexMax = " + indexMax);
			return indexMax;
		}
		//we have a tie
		else {
			indexMax = -1;
//			System.out.println("indexMax = " + indexMax);
			return indexMax;
		}
	}
	
	/**
	 *  This method prints the winning player.
	 * @return the player that wins
	 * @throws FileNotFoundException if the file needed does not exist
	 */
	public Player printWinner() throws FileNotFoundException {		
		PrintWriter print = new PrintWriter("GoFish_results.txt");
		int winIndex;
		winIndex = determineWinner();
		
	//	System.out.println("WinIndex: " + winIndex);
			
		if(winIndex >= 0) {
			
			Player winner = playerList.get(winIndex);
			System.out.println("\n\n" + winner.getName() 
				+ " wins with " + winner.getBookSize() + " booked pairs.\nPairs:");
			print.println("\n\n" + winner.getName() 
				+ " wins with " + winner.getBookSize() + " booked pairs.\nPairs:");
				
			System.out.println(winner.bookToString());
			print.println(winner.bookToString());
				
			print.close();
			return winner;
		}		
		else {
			System.out.println("\n\nThe game resulted in a tie!");
			print.println("\n\nThe game resulted in a tie!");
			
			print.close();
			return null;
		}
		
	}

	
//	public void updateBook(Player p) {
//		
//		int bookRank = p.checkForBook();
//		
//		while(bookRank != -1) {
//			
//			CardPair pair = p.removeBookFromHand(bookRank);
//			p.addPairToBook(pair);
//			bookRank = p.checkForBook();	
//		}
//		
//	}
		
		
//		// Loop until we have removed all books
//		while (true) {
//			int bookRank = player1.checkForBook();
//			if (bookRank > 0 && bookRank <= 13) {
//				player1.removeBookFromHand(bookRank);
//			}
//			else {
//				break;
//			}
//		}
//		// Loop until we have removed all books
//		while (true) {
//			int bookRank = player2.checkForBook();
//			if (bookRank > 0 && bookRank <= 13) {
//				player2.removeBookFromHand(bookRank);
//			}
//			else {
//				break;
//			}
//		}
//	}
//	public void printLosers(Player winner) throws FileNotFoundException {
//		
//		PrintWriter print = new PrintWriter("GoFish_results.txt");
//		
//		for(Player player : playerList) {
//			
//			if(!player.equals(winner) || winner.equals(null) == true) {
//				
//				System.out.println("\n\n" + player.getName() 
//					+ " has " + player.getBookSize() + " booked pairs.\nPairs:");
//				print.println("\n\n" + player.getName() 
//				+ " has " + player.getBookSize() + " booked pairs.\nPairs:");
//				
//				System.out.println(player.bookToString());
//				print.println(player.bookToString());
//			}
//		}
//		print.close();
//	}

}
