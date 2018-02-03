package gofish_assn;

public class GoFishGame {
	
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
		
	}
	
	public void requestCard(int turn) {
		if (turn == 0) {
			Card requestedCard = player1.chooseCardFromHand();
			// If player 2 has the requested card
			Card myCard = player2.sameRankInHand(requestedCard);
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
			// If player 1 has the requested card
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
		
	}
	
	public void isGameOver() {
		
	}
	
	public void changeTurn() {
	
	}

}
