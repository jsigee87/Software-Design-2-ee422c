package assignment4;

import assignment4.Critter.TestCritter;

public class JohnCritter extends TestCritter{
	/**
	 * @return 
	 * 
	 */
	@Override
	public String toString() {
		String str = new String("J");
		return str;
	}
	
	/**
	 * 
	 */
	@Override
	public void doTimeStep() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	@Override
	public boolean fight(String oponent) {
		// TODO Auto-generated method stub
		return false;
	}

}
