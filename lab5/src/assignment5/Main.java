package assignment5;
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import assignment5.Critter.TestCritter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional. If input file is specified, the word 'test' is 
 * optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {
	// Scanner connected to keyboard input, or input file.
    static Scanner kb;	
    // Input file, used instead of keyboard input if specified.
    private static String inputFile;	
    // If test specified, holds all console output.
    static ByteArrayOutputStream testOutputString;	
    // Package of Critter file.  Critter cannot be in default package.
    private static String assignment5;
    // Use it or not, as you wish!
    public static final boolean DEBUG = false; 
    // If you want to restore output to console
    static PrintStream old = System.out;	
    
    private static final int height = 600;
    private static final int width = 600;
    GridPane pane;
    private GraphicsContext gc;

    // Gets the package name.  The usage assumes that Critter and its sub-
    //classes are all in the same package.
    static {
        assignment5 = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters: 
     * the first is a file name, and the second is test (for test output, where
     *  all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
    	launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		//final int COLSPAN = 1;
		//final int ROWSPAN = 1;
		final int numCols = 3 ;
        final int numRows = 3 ;
        pane = new GridPane();
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            pane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            pane.getRowConstraints().add(rowConst);         
        }
		
		
		Canvas canvas = new Canvas(width, height);
		pane.add(canvas, 300, 300);
		gc = canvas.getGraphicsContext2D();
		
		stage.setTitle("Test");
		
		// Set up the buttons
		Button make = new Button("Make");
		Button step = new Button("Step");
		Button show = new Button("Show");
		Button help = new Button("Help");
		Button quit = new Button("Quit");
		Button seed = new Button("Seed");
		Button stats = new Button("Stats");

		//pane.getChildren().add(make);
		//GridPane.setColumnSpan(make, 2);
		//GridPane.setRowSpan(make, 2);
		// Add buttons to gridpane
		pane.add(make, 0, 0);
		pane.add(step, 1, 0);
		pane.add(show, 2, 0);
		pane.add(help, 0, 1);
		pane.add(quit, 1, 1);
		pane.add(seed, 2, 1);
		pane.add(stats, 1, 2);
		
		// Add button handlers
		
		make.setOnAction(new MakeHandler());

		
		
	
		
		Scene scene = new Scene(pane, width, height);
		
		stage.setScene(scene);
		stage.show();
		
	}
    
    
	
	// Handlers
	private class MakeHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			System.out.println("You clicked on Make");
			Stage make_popup = new Stage();
			make_popup.setTitle("What Kind of Critter Do You Want?");
			VBox comp = new VBox();
			HBox box1 = new HBox(100);
			HBox box2 = new HBox(62);
			box1.setPadding(new Insets(8, 5, 5, 5));
			box2.setPadding(new Insets(5, 5, 8, 5));
			Label critter_type = new Label("Critter Type");
			TextField critter_type_field = new TextField("Enter Critter Type Here");
			Label num_crits = new Label("Number of Critters");
			TextField num_crits_field = new TextField("Enter Number of Critters Here");
			
			//critter_type.setOnAction(new critter_type_h());
			box1.getChildren().addAll(critter_type, critter_type_field);
			
			box2.getChildren().addAll(num_crits, num_crits_field);
			
			comp.getChildren().addAll(box1, box2);
			
			ArrayList<String> args = new ArrayList<String>();
			// Set text handlers
			critter_type_field.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					String critter_type_input = new String();
					critter_type_input = critter_type_field.getText();
					args.add(critter_type_input);
					
					try {
						CritterWorld.makeCritter(critter_type_input);
					} catch (InvalidCritterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(args);
					CritterWorld.displayWorld();
				}	
			});
			
			num_crits_field.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					String num_crits_input = new String();
					num_crits_input = num_crits_field.getText();
					args.add(num_crits_input);
				}	
			});			
			
			
			try {
				if(args.size() == 2) {
					CritterWorld.makeCritter(args.get(0));
					System.out.println(args);
					CritterWorld.displayWorld();
				}
			}
			catch(InvalidCritterException e) {
				System.out.println("Invalid Critter!");
			}
			
			Scene stageScene = new Scene(comp, 350, 75);
			make_popup.setScene(stageScene);
			make_popup.show();
			
			
			
		}
		
	}
	
	private class critter_type_h implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			System.out.println("You typed" + arg0.);
		}
		
	}

	
	
	
	
    
}