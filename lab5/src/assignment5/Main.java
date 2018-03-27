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

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;

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
    private static String assignment4;
    // Use it or not, as you wish!
    public static final boolean DEBUG = false; 
    // If you want to restore output to console
    static PrintStream old = System.out;	

    // Gets the package name.  The usage assumes that Critter and its sub-
    //classes are all in the same package.
    static {
        assignment4 = Critter.class.getPackage().toString().split(" ")[1];
    }
    
    public static void main(String[] args) {
    	launch(args);
    }
    
    private static final int width = 600;
    private static final int height = 600;
    private GraphicsContext gc;
    private ColorPicker colorPicker;
    private Color color = Color.BLACK;
    private Button clrButton;
    BorderPane pane;

    @Override
    public void start(Stage stage) throws Exception {
  	    pane = new BorderPane();
  	    Canvas canvas = new Canvas(width, height);
  	    pane.setCenter(canvas);
  	    gc = canvas.getGraphicsContext2D();

  	    MouseHandler handler = new MouseHandler();
  	    canvas.setOnMouseClicked(handler);
  	    canvas.setOnMouseMoved(handler);

  	    
  	    Scene scene = new Scene(pane, width, height);
  	    stage.setScene(scene);
  	    stage.show();
    }

    private class MouseHandler implements EventHandler<MouseEvent> {
  	  
  	  public MouseHandler() {
  	  }
  	
      @Override
      public void handle(MouseEvent event) {
      	     
      }

    }    
}
