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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import assignment5.Critter.TestCritter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
			Stage newStage = new Stage();
			VBox comp = new VBox();
			TextField nameField = new TextField("Name");
			TextField phoneNumber = new TextField("Phone Number");
			comp.getChildren().add(nameField);
			comp.getChildren().add(phoneNumber);

			Scene stageScene = new Scene(comp, 300, 300);
			newStage.setScene(stageScene);
			newStage.show();
			
		}
		
	}
	
	
    
}
