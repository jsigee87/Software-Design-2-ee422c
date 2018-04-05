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
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Timeline timeline;
    private AnimationTimer timer;

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
    	new CritterWorld();
    	
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
		Button clear = new Button("Clear");

		// Add buttons to gridpane
		pane.add(make, 0, 0);
		pane.add(step, 1, 0);
		pane.add(show, 2, 0);
		pane.add(help, 0, 1);
		pane.add(quit, 1, 2);
		pane.add(seed, 2, 1);
		pane.add(stats, 1, 1);
		pane.add(clear, 2, 2);
		
		// Add button handlers
		
		make.setOnAction(new MakeHandler());
		step.setOnAction(new StepHandler());
		show.setOnAction(new ShowHandler());
		help.setOnAction(new HelpHandler());
		quit.setOnAction(new QuitHandler());
		seed.setOnAction(new SeedHandler());
		stats.setOnAction(new StatsHandler());
		clear.setOnAction(e ->{
			Critter.clearWorld();
		});
		
		Scene scene = new Scene(pane, width, height);
		
		stage.setScene(scene);
		stage.show();
		
	}    
    
	//TODO implement exception handling
	// Handlers
	private class MakeHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			Stage make_popup = new Stage();
			make_popup.setTitle("What Kind of Critter Do You Want?");
			HBox bigBox = new HBox();
			VBox comp = new VBox();
			VBox buttonBox = new VBox();
			HBox box1 = new HBox(100);
			HBox box2 = new HBox(62);
			box1.setPadding(new Insets(8, 5, 5, 5));
			box2.setPadding(new Insets(5, 5, 8, 5));
			buttonBox.setPadding(new Insets(8, 5, 8, 5));
			Label critter_type = new Label("Critter Type");
			TextField critter_type_field = new TextField();
			Label num_crits_label = new Label("Number of Critters");
			TextField num_crits_field = new TextField();
			
			
			//buttons			
			Button submit = new Button("Submit");			
			
			box1.getChildren().addAll(critter_type, critter_type_field);			
			box2.getChildren().addAll(num_crits_label, num_crits_field);		
			buttonBox.getChildren().addAll(submit);
			comp.getChildren().addAll(box1, box2);
			bigBox.getChildren().addAll(comp, buttonBox);
		
			// Handle Submit Button
			submit.setOnAction(x->{
				if(critter_type_field.getText() != null &&
						!critter_type_field.getText().isEmpty() &&
						num_crits_field.getText() != null &&
						!num_crits_field.getText().isEmpty()) {
					
					int num_crits = 1;
					
					try {
						num_crits = Integer.valueOf(num_crits_field.getText());
						
						if(num_crits < 0) {
							Stage invalidNum = new Stage();
							invalidNum.setTitle("Invalid Number!");
							Label invalid = new Label("Error processing: " + num_crits);
							invalidNum.setScene(new Scene(invalid, 250,50));
							invalidNum.show();
						}
						
						for(int i = 0; i < num_crits; i++) {
							Critter.makeCritter(critter_type_field.getText());
						}
					}
					catch(InvalidCritterException e) {
						//print invalid critter message
						Stage invalidCritter = new Stage();
						invalidCritter.setTitle("Invalid Critter!");
						Label invalid = new Label("You entered an invalid critter! Try again...");
						Scene invalid_scene = new Scene(invalid, 250,50);
						invalidCritter.setScene(invalid_scene);
						invalidCritter.show();
					}
					catch(NumberFormatException e) {
						Stage invalidNum = new Stage();
						invalidNum.setTitle("Invalid Number!");
						Label invalid = new Label("Error processing: " + num_crits_field.getText());
						invalidNum.setScene(new Scene(invalid, 250,50));		
						invalidNum.show();
					}
					
					make_popup.close();
				}
			});

			Scene stageScene = new Scene(bigBox, 400, 75);
			make_popup.setScene(stageScene);
			make_popup.show();	
			
		}
		
	}
	
	private class StepHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			Stage make_popup = new Stage();
			make_popup.setTitle("Steps");
			HBox bigBox = new HBox();
			VBox comp = new VBox();
			VBox buttonBox = new VBox();
			HBox box1 = new HBox();
			box1.setPadding(new Insets(8, 5, 5, 5));
			buttonBox.setPadding(new Insets(8, 5, 8, 5));
			Label step_label = new Label("Number of Steps: ");
			TextField step_num_field = new TextField();
			Label hint_label = new Label("Enter -1 for infinite steps\nSet speed with slider.");
			
			 Slider slider = new Slider(0, 1, 0.5);
			 slider.setShowTickMarks(true);
			 slider.setShowTickLabels(true);
			 slider.setMajorTickUnit(0.25f);
			 slider.setBlockIncrement(0.1f);
			 
			//buttons			
			Button submit = new Button("Animate");			
			
			//critter_type.setOnAction(new critter_type_h());
			box1.getChildren().addAll(step_label, step_num_field);				
			buttonBox.getChildren().addAll(submit);
			comp.getChildren().addAll(box1,slider,hint_label);
			bigBox.getChildren().addAll(comp, buttonBox);
		
			// Handle Submit Button
			submit.setOnAction(x->{
				if(step_num_field.getText() != null && 
						!step_num_field.getText().isEmpty()
						) {

					int steps = 0;
					try {
						steps = Integer.valueOf(step_num_field.getText());
						
						if(steps < -1) {
							throw new NumberFormatException();
						}
						
						new AnimationView(steps, slider.getValue());
					}
					catch(NumberFormatException e) {
						//invalid step size
				    	Stage invalidStep = new Stage();
						invalidStep.setTitle("Invalid Step Size!");
						Label invalid = new Label("Error processing: " + step_num_field.getText());
						Scene invalid_scene = new Scene(invalid, 300,50);
						invalidStep.setScene(invalid_scene);
						invalidStep.show();
					}
					
					make_popup.close();
				}
			});

			Scene stageScene = new Scene(bigBox, 350, 120);
			make_popup.setScene(stageScene);
			make_popup.show();			
		}

	}
	
	private class ShowHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			
			Critter.displayWorld();
		}
	}
	
	private class HelpHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			Stage make_popup = new Stage();
			make_popup.setTitle("Help");
			FlowPane fp = new FlowPane();
			Canvas canvas = new Canvas(190, 150);
	        GraphicsContext gc = canvas.getGraphicsContext2D();
	        
	        Text t = new Text("Valid commands are:\r\n "+
	        		"\tquit : \n\t\tQuits the game.\r\n" + 
	        		"\tshow : \n\t\tDisplays the game world.\r\n" + 
	        		"\tstep <count> : \n\t\tImplements time steps.\r\n" + 
	        		"\tmake <class_name> <count> : \n\t\tCreates Critters of type " +
	        		"class name and \n\t\tadds the single or <count> critters to the world.\r\n" + 
	        		"\tstats : \n\t\treturns the number of critters of the specified class.\r\n" + 
	        		"\tseed  : \n\t\tSets the random seed for the simulator.\r\n" + 
	        		"\thelp  : \n\t\tDisplays this help manual :)");
	        
	        t.setFill(Color.BLACK);
	        Font f = new Font(14);
	        t.setFont(f);
	        
	        Button btn = new Button ("Quit Help...");
	        btn.setOnAction(new EventHandler<ActionEvent>() {     
	            @Override 
	            public void handle(ActionEvent e) {
	            	make_popup.close();
	            }
	        });
	        
	        fp.getChildren().add(t);
	        fp.getChildren().add(canvas);
	        fp.getChildren().add(btn);


	        Scene scene = new Scene(fp);
	        make_popup.setScene(scene);
	        make_popup.show();
		}

	}
	
	private class QuitHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			System.exit(0);
		}
	}
	
	private class SeedHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			Stage make_popup = new Stage();
			make_popup.setTitle("Setting the Random Seed");
			HBox bigBox = new HBox();
			VBox comp = new VBox();
			VBox buttonBox = new VBox();
			HBox box1 = new HBox(100);
			box1.setPadding(new Insets(8, 5, 5, 5));
			buttonBox.setPadding(new Insets(8, 5, 8, 5));
			Label seed_label = new Label("Enter Random Seed");
			TextField seed_num_field = new TextField();			
			
			//buttons			
			Button submit = new Button("Submit");			
			
			//critter_type.setOnAction(new critter_type_h());
			box1.getChildren().addAll(seed_label, seed_num_field);				
			buttonBox.getChildren().addAll(submit);
			comp.getChildren().addAll(box1);
			bigBox.getChildren().addAll(comp, buttonBox);
		
			// Handle Submit Button
			submit.setOnAction(x->{
				if (seed_num_field.getText() != null &&
						!seed_num_field.getText().isEmpty()) {
					long seed;
				    try {
				    	seed = Long.parseLong(seed_num_field.getText().trim());
				    	
				    	//error check user input
				    	if(seed < 0) {
				    		throw new NumberFormatException();
				    	}
				    	
				    	TestCritter.setSeed(seed);
				    }
				    catch(NumberFormatException e) {
				    	//invalid seed
				    	Stage invalidSeed = new Stage();
						invalidSeed.setTitle("Invalid Seed!");
						Label invalid = new Label("Error processing: " + seed_num_field.getText());
						Scene invalid_scene = new Scene(invalid, 250,50);
						invalidSeed.setScene(invalid_scene);
						invalidSeed.show();
					}
				}	
			   			   
			    make_popup.close();
			});

			Scene stageScene = new Scene(bigBox, 600, 70);
			make_popup.setScene(stageScene);
			make_popup.show();	

			
		}
	}
	
	private class StatsHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			//input Critter and create new screen on which to show the stats
			Stage make_popup = new Stage();
			make_popup.setTitle("Find a Critter's Stats");
			HBox bigBox = new HBox();
			VBox comp = new VBox();
			VBox buttonBox = new VBox();
			HBox box1 = new HBox(100);
			box1.setPadding(new Insets(8, 5, 5, 5));
			buttonBox.setPadding(new Insets(8, 5, 8, 5));
			Label crit_type_label = new Label("Critter Type");
			TextField crit_type_field = new TextField();			
			
			//buttons			
			Button submit = new Button("Submit");			
			
			//critter_type.setOnAction(new critter_type_h());
			box1.getChildren().addAll(crit_type_label, crit_type_field);				
			buttonBox.getChildren().addAll(submit);
			comp.getChildren().addAll(box1);
			bigBox.getChildren().addAll(comp, buttonBox);
		
			// Handle Submit Button
			submit.setOnAction(x->{
				
				String crit_type = new String(crit_type_field.getText());
				
				String unqualifiedClassName = crit_type.trim();	  
				
				String stats = null;
				
				try {
					 List<Critter> list = CritterWorld.getInstances(
							 unqualifiedClassName);

					 if(unqualifiedClassName.toLowerCase().equals("critter")) {
						  stats = Critter.runStats(list);
					 }
					 else {
						  Class<?> c = Class.forName(returnClassName(
								  unqualifiedClassName));
						  @SuppressWarnings("rawtypes")
						  Class[] cArg = new Class[1];
					      cArg[0] = List.class;
						  Method m = c.getMethod("runStats", cArg);
						  
						  stats = (String) m.invoke(c, list);
					 }
					 
				 	Stage stats_stage = new Stage();
					stats_stage.setTitle("Stats for " + crit_type_field.getText());
					FlowPane fp = new FlowPane();
					Canvas canvas = new Canvas(370, 80);
			        GraphicsContext gc = canvas.getGraphicsContext2D();
			        
			        Text t = new Text(stats);
			        
			        t.setFill(Color.BLACK);
			        Font f = new Font(14);
			        t.setFont(f);
			        
			        Button btn = new Button ("Quit Stats...");
			        btn.setOnAction(y->{
			        	stats_stage.close();
			        });
			        
			        fp.getChildren().add(t);
			        fp.getChildren().add(canvas);
			        fp.getChildren().add(btn);


			        Scene scene = new Scene(fp);
			        stats_stage.setScene(scene);
			        stats_stage.show();
				  
				 } 
				 catch (InvalidCritterException | IllegalAccessException e) {
					//print invalid critter message
						Stage invalidCritter = new Stage();
						invalidCritter.setTitle("Invalid Critter!");
						Label invalid = new Label("You entered an invalid critter! Try again...");
						Scene invalid_scene = new Scene(invalid, 250,50);
						invalidCritter.setScene(invalid_scene);
						invalidCritter.show();
				 }
				 catch (InvocationTargetException | ClassNotFoundException |
						 NoSuchMethodException | SecurityException e){
					  e.printStackTrace();
				 } 			
			});

			Scene stageScene = new Scene(bigBox, 500, 70);
			make_popup.setScene(stageScene);
			make_popup.show();	
		}
	}
	
	private static String returnClassName(String critter_class_name) {
		critter_class_name = critter_class_name.toLowerCase();					//
		String string = new String();											//
		char first = Character.toUpperCase(critter_class_name.charAt(0));		//
		string = assignment5 + "." + first + critter_class_name.substring(1);
		
		List<String> classList = CritterWorld.getClassList(assignment5);
		
		List<String> lowClassList = new ArrayList<String>();
		
		for(String s : classList) {
			lowClassList.add(s.toLowerCase());
		}
		if(lowClassList.contains(string.toLowerCase())) {
			int idx = lowClassList.indexOf(string.toLowerCase());
			return classList.get(idx);
		}
		return string;
	}
	
}