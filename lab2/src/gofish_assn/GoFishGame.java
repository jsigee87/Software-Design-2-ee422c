package gofish_assn;

public class GoFishGame {
	
	final static int NUMBOOKSTOWIN = 26;
	final static int SIZEFIRSTHAND = 7;
	// If turn == 0, player 1 goes
	// If turn == 1, player 2 goes
	int turn;
	
	Player player1;
	
	Player player2;
	
	Deck gameDeck;
	
	public GoFishGame(String player1, String player2) {
		turn = 0;
		this.player1 = new Player(player1);
		this.player2 = new Player(player2);
		this.gameDeck = new Deck();
		gameDeck.shuffle();
		dealHand(this.player1);
		dealHand(this.player2);	
	}
	
	public void dealHand(Player p) {
		for (int i = 0; i < SIZEFIRSTHAND; i ++){
			p.addCardToHand(gameDeck.dealCard());
		}
	}
	
	public void requestCard(int turn) {
		if (turn == 0) {
			Card requestedCard = player1.chooseCardFromHand();
			Card myCard = player2.sameRankInHand(requestedCard);
			// If player 2 has the requested card, remove it from
			// both hands, update the books.
			if (myCard != null) {
				player2.removeCardFromHand(myCard);
				player1.removeCardFromHand(requestedCard);
				CardPair bookPair = new CardPair(requestedCard, myCard);
				player1.addPairToBook(bookPair);
				return;
			}
			else {
				return ;
			}
		}
		else {
			Card requestedCard = player2.chooseCardFromHand();
			Card myCard = player1.sameRankInHand(requestedCard);
			if (myCard != null) {
				player1.removeCardFromHand(myCard);
				player2.removeCardFromHand(requestedCard);
				CardPair bookPair = new CardPair(requestedCard, myCard);
				player2.addPairToBook(bookPair);
				return;
			}
			else {
				return ;
			}
		}
	}
	
	public void updateBooks() {
		// Loop until we have removed all books
		while (true) {
			int bookRank = player1.checkForBook();
			if (bookRank > 0 && bookRank <= 13) {
				player1.removeBookFromHand(bookRank);
			}
			else {
				break;
			}
		}
		// Loop until we have removed all books
		while (true) {
			int bookRank = player2.checkForBook();
			if (bookRank > 0 && bookRank <= 13) {
				player2.removeBookFromHand(bookRank);
			}
			else {
				break;
			}
		}
	}
	
	public boolean isGameOver() throws IllegalStateException {
		int pl1Books = player1.books.size();
		int pl2Books = player2.books.size();
		int totalBooks = pl1Books + pl2Books;
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
	
	public void changeTurn() {
		// XOR to alternate turns
		turn ^= 1;
	}

}
