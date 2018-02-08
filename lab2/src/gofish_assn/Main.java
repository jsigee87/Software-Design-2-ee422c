package gofish_assn;

import java.util.ArrayList;

public class Main {
	
	public static void main(String args[]) {
		
		/*
		 * Initialize the game with as many players as we want
		 */
		ArrayList<String> names = new ArrayList<String>();
		names.add("Jane");
		names.add("Joe");
		
		GoFishGame game = new GoFishGame(names);
		
		try {
			
			/*
			 * Check if any of the players have one or more books..
			 */
			game.updateAllBooks();
			
			int turn;
			turn = game.getTurn(); //get whose turn it is for the first round
			
			/*
			 * game loop
			 */
			while(!game.isGameOver()) {
				
				
				/*
				 * Check if any of the players have one or more books..
				 */
				game.updateAllBooks();
				
				/*
				 * the player requests a card from the other (next) player.
				 * 
				 * Note that a player can only request a card if he/she has at least
				 * one card in their hand.
				 * 
				 * If the current player does not have at least one card, he/she draws
				 * a card and the turn goes to the other player.
				 */
				if(game.playerList.get(turn).getHandSize() >= 1) {
					
					Card requestedCard = game.playerList.get(turn).chooseCardFromHand();
					
					System.out.println(game.playerList.get(turn).getName()
							+ " asks - Do you have a " + requestedCard.getRank() + "?");
					
					boolean nextPlayerHadCard = game.requestCard(turn, requestedCard);
					
					System.out.print(game.playerList.get((turn+1)%game.getNumPlayers()) + " says - ");
					
					if(nextPlayerHadCard) {
						
						System.out.println("Yes. I have a " + requestedCard.getRank() + ".");
						
						System.out.println(game.playerList.get(turn).getName() + " books the " + requestedCard.getRank() + ".");
					}
					else { //if the next player did NOT have the requested Card
						
						System.out.println("Go Fish");
						
						/*
						 * current player goes fishing
						 */
						try {
							
							Card newCard = game.gameDeck.dealCard();
							
							System.out.println(game.playerList.get(turn).getName() + " draws " + newCard.toString());
							
							game.playerList.get(turn).addCardToHand(newCard); //add card from gameDeck to hand
						}
						catch (IllegalStateException ex) { //drew from an empty deck
							throw ex;
						}
					}
					
				}
				else {
					
					try {
						
						game.playerList.get(turn).addCardToHand(game.gameDeck.dealCard()); //add card from gameDeck to hand						
					}
					catch (IllegalStateException ex) {
						throw ex;
					}
					
				}
				

				/*
				 * check again if any of the players have one or more books
				 */
				game.updateAllBooks();
				
				/*
				 * update whose turn it is before beginning the next round or quitting the game
				 */
				turn = game.changeTurn();
				
				
			}
			
			/*
			 * Post Game code
			 * 
			 * Who won...
			 * With how many pairs
			 */
		}
		catch (IllegalStateException ex){
			
			System.out.println(ex);
		}
		
	}

}
