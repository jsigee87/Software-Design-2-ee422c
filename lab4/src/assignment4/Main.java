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
import java.io.*;
import assignment4.Critter.TestCritter;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String assignment4;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        assignment4 = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }
       
        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        //TODO make this parser user safe
        //create new CritterWorld
        CritterWorld world = new CritterWorld();
        
        //create test Craig and test Algae
        Critter testCraig = new Craig();        
        Algae testAlgae = new Algae();
        world.addCritter(testCraig, 4,4);
        world.addCritter(testAlgae, 10,10);
        
//        /*
//         * Take in width and height of the world as an input 
//         */
//        System.out.println("Enter the width of the world: ");
//        if(!kb.hasNextInt()) {
//        	System.out.println("Please enter an integer:" ); 
//        }
//        
//        Params.world_width = kb.nextInt();
//        
//        System.out.println("Enter the height of the world: ");
//        if(!kb.hasNextInt()) {
//        	System.out.println("Please enter an integer:" ); 
//        }
//        
//        Params.world_height = kb.nextInt();
        
        
//        try {
//			TestCritter.makeCritter("CRAIG");
//		} catch (InvalidCritterException e) {
//			// TODO make this safe instead of just printing out the stack trace
//			e.printStackTrace();
//		}
        
//        testAlgae.setX_coord(4);
//        testAlgae.setY_coord(4);
        
        while(!world.shouldQuit()) {
        	System.out.print("critters> ");
        	if(kb.hasNextLine()) {
        		String str = kb.nextLine();
        		
        		if(parse(str)) {
        			world.quit();
        		}
        	}
        	System.out.println();
        }
        
        // System.out.println("GLHF");
        
        /* Write your code above */
        System.out.flush();

    }
    //TODO complete parser
    public static boolean parse(String str) {
    	
    	str = str.toLowerCase();
    	
    	if(!str.contains(" ")) {
    		switch (str) {
    		case "show":
    			TestCritter.displayWorld();
    			return false;
    		case "step":
    			TestCritter.worldTimeStep();
    			return false;
    		case "quit":
    			return true;
    		default:
    			return false;
    		}
    	}
    	else {
    		System.out.println("lol");
    		return false;
    	}
    }
    
    
}
