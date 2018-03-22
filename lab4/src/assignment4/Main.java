package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name> //TODO John
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Daniel Diamont>
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import assignment4.Critter.TestCritter;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional. If input file is specified, the word 'test' is 
 * optional.
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
     * @param args args can be empty.  If not empty, provide two parameters: 
     * the first is a file name, and the second is test (for test output, where
     *  all output to be directed to a String), or nothing.
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
    	
    	// Sets up world, initializes virtual map.  
    	new CritterWorld();
//    	System.out.println();
//    	System.out.println("Your world has been created. Enter some commands"
//    			+ " to manipulate your world, or type");
//    	System.out.println(" help for a list of supported commands.");
//    	System.out.println();
    	
        //int blinker = 0; 
    	while(true) {
    		//if (blinker == 0) {
    			System.out.print("critters> ");
    		//}
    		//if (blinker == 1) {
    			//System.out.print("critters> |");
    		//}
    		//blinker ^= blinker;
    		//TODO fix parser to match up exactly with JUNIT test
    		if(kb.hasNextLine()) {
        		String str = kb.nextLine();
        		str = str.trim(); //remove extra spaces at beginning and end of string. Spaces in middle not affected.
        		if(!multipleCommands(str)) {
        			switch (str) {
        			case "quit":
        				CritterWorld.quit();
        				break;
        			case "show":
        		    	System.out.println();
        				TestCritter.displayWorld();
        		    	System.out.println();
        				break;
        			case "step":
        				TestCritter.worldTimeStep();
        				break;
        			case "help":
        				displayHelp();
        				break;
        			default:
        				System.out.print("invalid command: " + str);
        				break;
        			}
        		}
        		else { //we have multiple commands
        			
        			if(str.contains("make")) {
        				makeCommand(str);
        			}
        			else if(str.contains("step")) {
        				stepCommand(str);
        			}
        			else if(str.contains("stats")) {
        				statsCommand(str);
        			}
        			else if(str.contains("seed")) {
        				seedCommand(str);
        			}
        			else if (str.contains("show")) {
        				System.out.print("error processing: " + str);
        			}
        			else { // Invalid command... display help message
        				System.out.print("invalid command: " + str);
        			}
        		
	        	}
//	        	System.out.println();
	        	if (CritterWorld.shouldQuit()) {
	        		break;
	        	}
    		}//end if-block
	        
    	}//end while
//    	System.out.println("Quitting Critters...");
        System.out.flush();
    }
    
   /**
    * This function takes a command from the prompt to make either one or n critters,
    * where n is specified in str after a " " delimiter.
    * @param str argument to parse
    */
   public static void makeCommand(String str) {
	   String className = str.substring(str.indexOf(" ")).trim();
	   int count = 0;
		if(multipleCommands(className)) {
			try {
				count = Integer.parseInt(className.substring(className.indexOf(" ")).trim());
			}
			catch(NumberFormatException e) {
				System.out.print("error processing: " + str);
			}
			
			className = className.substring(0, className.indexOf(" ")).trim();
		}
		else {
			count = 1;
		}
		for (int i = 0; i < count; i++) {
			try {
				CritterWorld.makeCritter(className);
			} catch (InvalidCritterException e) {
				System.out.print("Invalid Critter!");
				return;
			}
		}
   }
   
   /**
    * This function takes a command from the prompt to step either once or n times,
    * where n is specified in str after a " " delimiter.
    * @param str argument to parse
    */
   public static void stepCommand(String str) {
	   int count = 0;
	   if(multipleCommands(str)) {
		   try {
			count = Integer.parseInt(str.substring(str.indexOf(" ")).trim());
		   }
		   catch(NumberFormatException e) {
				System.out.print("error processing: " + str);
			}
		}
		else {
			count = 1;
		}
		for (int i = 0; i < count; i++) {
			CritterWorld.worldTimeStep();
		}
   }
   
   /**
    * This function takes a command from the prompt to set the seed of the 
    * random number generator to the long number specified in str.
    * @param str argument to parse
    */
   public static void seedCommand(String str) {
	   long seed = 0;
	   if(multipleCommands(str)) {
		   try {
			seed = Long.parseLong(str.substring(str.indexOf(" ")).trim());
		   }
		   catch(NumberFormatException e) {
				System.out.print("error processing: " + str);
			}
		}
	   TestCritter.setSeed(seed);	   
   }
   
   public static void statsCommand(String str) {
	  String unqualifiedClassName = str.substring(str.indexOf(" ")).trim();	  
	  
	  try {
		  List<Critter> list = CritterWorld.getInstances(unqualifiedClassName);

		  if(unqualifiedClassName.toLowerCase().equals("critter")) {
			  CritterWorld.runStats(list);
		  }
		  else { //TODO fix command to exactly match up with JUNIT test case
			  Class<?> c = Class.forName(returnClassName(unqualifiedClassName));
			  @SuppressWarnings("rawtypes")
			  Class[] cArg = new Class[1];
		      cArg[0] = List.class;
			  Method m = c.getMethod("runStats", cArg);
			  
			  m.invoke(c, list);
		  }  
		  
	  } 
	  catch (InvalidCritterException | IllegalAccessException e) {
		  System.out.print("Invalid Critter!");
		  return;
	  }
	  catch (InvocationTargetException | NoSuchMethodException | SecurityException e)
	  {
		  e.printStackTrace();
		  return;
	  } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   /**
    * This function provides an easy check to see if the command from the prompt
    * is a single or multiple-word command.
    * @param str argument to parse
    * @return boolean specifying where the command is a single or multi-word 
    * command
    */
   public static boolean multipleCommands(String str) {
    	str = str.toLowerCase();
    	
    	// Multiple commands
    	if (str.contains(" ")) { 
    		return true;
    	}
		return false;
    }
    
   /**
    * This function provides the user with a useful legend of all the valid 
    * commands.
    */
    public static void displayHelp() {
    	System.out.println();
    	System.out.println("Valid commands are:\n");
    	System.out.println("\t quit  \t:\t Quits the game.");
    	System.out.println("\t show  \t:\t Displays the game world.");
    	System.out.println("\t step [<count>] \t:\t Implements time steps.");
    	System.out.println("\t make <class_name> [<count>] \t:\t Creates "
    			+ "Critters of type class name\n\t\t and adds the single or <count>"
    			+ " critters to the world.");
    	System.out.println("\t stats \t:\t returns the number of critters of the specified class");
    	System.out.println("\t seed  \t:\t Sets the random seed for the "
    			+ "simulator.");
    	System.out.println("\t help  \t:\t Displays this help manual.\n");
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

    private static String returnClassName(String critter_class_name) {
		critter_class_name = critter_class_name.toLowerCase();					//
		String string = new String();											//
		char first = Character.toUpperCase(critter_class_name.charAt(0));		//
		string = assignment4 + "." + first + critter_class_name.substring(1);	
		return string;
	}
/***************************************************/

// Dead Code //

/***************************************************/

    /*
     * Takes user input String and sets height parameter.
     */
/*    public static boolean setHeight(String height_str) {
    	int height = Integer.parseInt(height_str);
    	if (height > 0) {
    		Params.world_height = height;
    		return true;
    	}
    	return false;
    }*/
    
    /*
     * Takes user input String and sets width parameter.
     */
/*
    public static boolean setWidth(String width_str) {
    	int width = Integer.parseInt(width_str);
    	if (width > 0) {
    		Params.world_width = width;
    		return true;
    	}
    	return false;
    }*/
    
    /*
     * Prompts the user and sets height and weight parameters.
     *//*
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
    }*/
}
