package gofish_assn;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
	
	public static void main(String args[]) throws FileNotFoundException {
		
		/*
		 * Initialize File I/O
		 */
		File file = new File("GoFish_results.txt");
		@SuppressWarnings("resource")
		PrintWriter printWriter = new PrintWriter("GoFish_results.txt");
		
		/*
		 * Initialize the game with as many players as we want
		 */
		ArrayList<String> names = new ArrayList<String>();
		names.add("Jane");
		names.add("Joe");
		
//		System.out.println("Welcome to goFish!");
		
		GoFishGame game = new GoFishGame(names);
		
//		System.out.println("\nGame has been created");
		
//		for(Player p : game.playerList) {
//			System.out.println(p.getName() + "'s hand is of size: " + p.getHandSize());
//			System.out.println("Hand: " + p.handToString());
//			System.out.println("Book: " + p.bookToString());
//		}
		
		try {
			
			/*
			 * Check if any of the players have one or more books..
			 */			
			game.updateAllBooks();
			
//			System.out.println("\nBooks have been updated..\n");
			
//			for(Player p : game.playerList) {
//				System.out.println(p.getName() + "'s hand is of size: " + p.getHandSize());
//				System.out.println("Hand: " + p.handToString());
//				System.out.println("Book: " + p.bookToString());
//			}
			
			int turn;
			game.chooseFirstPlayer();
			turn = game.getTurn(); //get whose turn it is for the first round
			
//			System.out.println("It is " + game.playerList.get(turn).getName() + "'s turn.");
			
			/*
			 * game loop
			 */
//			int roundCounter = 1;
			
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
					
//					System.out.println(game.playerList.get(turn).getName() + " has cards in the hand.");
					
					Card requestedCard = game.playerList.get(turn).chooseCardFromHand();
					
					System.out.println(game.playerList.get(turn).getName()
							+ " asks - Do you have a " + requestedCard.getRank() + "?");
					printWriter.println(game.playerList.get(turn).getName()
							+ " asks - Do you have a " + requestedCard.getRank() + "?");
					
					boolean nextPlayerHadCard = game.requestCard(turn, requestedCard);
					
					System.out.print(game.playerList.get((turn+1)%game.getNumPlayers()).getName() + " says - ");
					printWriter.print(game.playerList.get((turn+1)%game.getNumPlayers()).getName() + " says - ");
					
					if(nextPlayerHadCard) {
						
						System.out.println("Yes. I have a " + requestedCard.getRank() + ".");
						printWriter.println("Yes. I have a " + requestedCard.getRank() + ".");
						
						System.out.println(game.playerList.get(turn).getName() + " books the " + requestedCard.getRank() + ".");
						printWriter.println(game.playerList.get(turn).getName() + " books the " + requestedCard.getRank() + ".");
					}
					else { //if the next player did NOT have the requested Card
						
						System.out.println("Go Fish");
						printWriter.println("Go Fish");
						
						/*
						 * current player goes fishing
						 */
						try {
							
							Card newCard = game.gameDeck.dealCard();
							
							System.out.println(game.playerList.get(turn).getName() + " draws " + newCard.toString());
							printWriter.println(game.playerList.get(turn).getName() + " draws " + newCard.toString());
							
							game.playerList.get(turn).addCardToHand(newCard); //add card from gameDeck to hand
						}
						catch (IllegalStateException ex) { //drew from an empty deck
							throw ex;
						}
					}
					
				}
				else {
					
					System.out.println(game.playerList.get(turn).getName() + " had no cards ");
					printWriter.println(game.playerList.get(turn).getName() + " had no cards ");
					
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
				
//				roundCounter++;
			}
			
			/*
			 * Post Game code
			 * 
			 * Who won...
			 * With how many pairs
			 */
			
			Player winner = game.printWinner();
			
			if(winner == null)
			{
				printWriter.print("\r\n\r\nThe game resulted in a tie!\r\n");
			}
			else {
				printWriter.println("\r\n\r\n" + winner.getName() 
				+ " wins with " + winner.getBookSize() + " booked pairs.\r\nPairs:");
				
				printWriter.println(winner.bookToString());
			}
			
			for(Player player : game.playerList) {
				
				if(!player.equals(winner) || winner.equals(null) == true) {
					
					System.out.print("\r\n\r\n" + player.getName() 
						+ " has " + player.getBookSize() + " booked pairs.\r\nPairs:\r\n");
					printWriter.println("\r\n\r\n" + player.getName() 
					+ " has " + player.getBookSize() + " booked pairs.\r\nPairs:\r\n");
					
					System.out.println(player.bookToString());
					printWriter.print(player.bookToString());
				}
			}			
			
		}
		catch (IllegalStateException ex){
			
			System.out.println(ex);
		}
		printWriter.close();
		
	}

}
