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
				 * the player requests a card from the other (next) player
				 */
				game.requestCard(turn);
				

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
