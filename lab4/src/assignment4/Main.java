package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*;
import assignment4.Critter.TestCritter;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {
	// Scanner connected to keyboard input, or input file.
    static Scanner kb;	
    // Input file, used instead of keyboard input if specified.
    private static String inputFile;	
    // If test specified, holds all console output.
    static ByteArrayOutputStream testOutputString;	
    // Package of Critter file.  Critter cannot be in default package.
    private static String assignment4;
    // Use it or not, as you wish!
    private static boolean DEBUG = false; 
    // If you want to restore output to console
    static PrintStream old = System.out;	


    // Gets the package name.  The usage assumes that Critter and its sub-
    //classes are all in the same package.
    static {
        assignment4 = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        //////////////////////////////////////////////////////////////////////////
    	// Do not alter this code below. 									    //
    	if (args.length != 0) {												    //
            try {															    //
                inputFile = args[0];										    //
                kb = new Scanner(new File(inputFile));						    //
            } catch (FileNotFoundException e) {								    //
                System.out.println("USAGE: java Main OR java Main <input file>" //
                		+ " <test output>");								    //
                e.printStackTrace();											//
            } catch (NullPointerException e) {									//
                System.out.println("USAGE: java Main OR java Main <input file>"	//
                		+ " <test output>");									//
            }																	//
            if (args.length >= 2) {												//
            																	//
            	// If the word "test" is the second argument to java			//
                if (args[1].equals("test")) {									//
                    															//
                	// Create a stream to hold the output						//	
                    testOutputString = new ByteArrayOutputStream();				//
                    PrintStream ps = new PrintStream(testOutputString);			//
                    															//
                    // Save the old System.out.									//
                    old = System.out;											//
                    															//
                    // Tell Java to use the special stream; all console 		//
                    //output will be redirected here from now					//
                    System.setOut(ps);											//
                }																//
            }																	//
        } 																		//
    	else { // if no arguments to main										//
            kb = new Scanner(System.in); // use keyboard and console			//
        }																		//
    																			//
        // Do not alter the code above. 										//
    	//////////////////////////////////////////////////////////////////////////
    	// Write your code below. 
        
        /*create new CritterWorld
        CritterWorld world = new CritterWorld();
        
        //create test Craig and test Algae
        Critter testCraig = new Craig();        
        Algae testAlgae = new Algae();
        CritterWorld.addCritter(testCraig, 4,4);
        CritterWorld.addCritter(testAlgae, 10,10);
          */ 
         
    	//Create the world.
    	System.out.println("Let's get some parameters to create a Critters "
    			+ "world...");
    	// Prompts user for height and width.
    	setParams();
    	// Sets up world, initializes virtual map.
    	CritterWorld world = new CritterWorld();
    	System.out.println();
    	System.out.println("Your world has been created. Enter some commands"
    			+ " to manipulate your world, or type");
    	System.out.println(" help for a list of supported commands.");
    	System.out.println();
    	
    	
    	
    	
        // Daniel - flow of control seemed to make more sense to me here in the
    	// while loop. I also added a display help message to catch weird 
    	//commands
         while(true) {
        	System.out.print("critters> ");
        	if(kb.hasNextLine()) {
        		String str = kb.nextLine();
        		// When new commands are added, add them to displayHelp
        		switch (parse(str)) {
        			case "quit":
        				CritterWorld.quit();
        				break;
        			case "show":
        		    	System.out.println();
        				CritterWorld.displayWorld();
        		    	System.out.println();
        				break;
        			case "step":
        				CritterWorld.worldTimeStep();
        				break;
        			case "help":
        				displayHelp();
        				break;
        			default:
        				displayHelp();
        				break;
        		}
        	}
        	System.out.println();
        	if (CritterWorld.shouldQuit()) {
        		break;
        	}
        }
        System.out.println("Quitting Critters...");
        System.out.flush();
    }
    
   public static String parse(String str) {
    	str = str.toLowerCase();
    	//TODO Daniel is if statement needed?
    	//if (!str.contains(" ")) {
    	//	return str;
    	//}
		return str;
    }
    
    public static void displayHelp() {
    	System.out.println();
    	System.out.println("Valid commands are:\n");
    	System.out.println("\t quit  \t:\t Quits the game.");
    	System.out.println("\t show  \t:\t Displays the game world.");
    	System.out.println("\t step  \t:\t Implements a single time step of the"
    			+ " world.");
    	System.out.println("\t help  \t:\t Displays this help manual.\n\n");
    }
    
    /*
     * Takes user input String and sets height parameter.
     */
    public static boolean setHeight(String height_str) {
    	int height = Integer.parseInt(height_str);
    	if (height > 0) {
    		Params.world_height = height;
    		return true;
    	}
    	return false;
    }
    
    /*
     * Takes user input String and sets width parameter.
     */
    public static boolean setWidth(String width_str) {
    	int width = Integer.parseInt(width_str);
    	if (width > 0) {
    		Params.world_width = width;
    		return true;
    	}
    	return false;
    }
    
    /*
     * Prompts the user and sets height and weight parameters.
     */
    public static void setParams() {
    	
    	try {
			TimeUnit.SECONDS.sleep(1);
    	}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
    	
    	HEIGHT: {//restart if bad input
    	System.out.println("Please enter the desired world height...");
    	System.out.print("critters> ");
    	
    	if(kb.hasNextLine()) {
    		if(!setHeight(kb.nextLine())) {
    			break HEIGHT;
    		}
    	}
		}
    	
    
    	try {
			TimeUnit.SECONDS.sleep(1);
    	}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
    	WIDTH: {//restart if bad input
	    	System.out.println("Please enter the desired world width...");
	    	System.out.print("critters> ");
	    	
	    	if(kb.hasNextLine()) {
	    		if(!setWidth(kb.nextLine())) {
	    			break WIDTH;
	    		}
	    	}
		}
    
    }
}





// Take in width and height of the world as an input 
//
//System.out.println("Enter the width of the world: ");
//if(!kb.hasNextInt()) {
//	System.out.println("Please enter an integer:" ); 
//}
//
//Params.world_width = kb.nextInt();
//
//System.out.println("Enter the height of the world: ");
//if(!kb.hasNextInt()) {
//	System.out.println("Please enter an integer:" ); 
//}
//
//Params.world_height = kb.nextInt();


//try {
//	TestCritter.makeCritter("CRAIG");
//} catch (InvalidCritterException e) {
//	// TODO make this safe instead of just printing out the stack trace
//	e.printStackTrace();
//}

//testAlgae.setX_coord(4);
//testAlgae.setY_coord(4);
