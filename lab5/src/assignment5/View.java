package assignment5;

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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View extends Stage {
	
	private static final int height = 600;
    private static final int width = 600;
    GridPane pane;
    private GraphicsContext gc;
    
    /**
     * Standard parameter-less Constructor
     */
    View() {
    	
    	final int numCols = Params.world_height;
	    final int numRows = Params.world_width;
	      
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
	    
	    //pane.setStyle("-fx-background-color: black, -fx-control-inner-background; -fx-background-insets: 0, 2; -fx-padding: 2;");
	    int i = 0;
		int j = 0;
		
		for (i = 0; i < Params.world_height; i++) {
//			System.out.print("|");
			for(j = 0; j < Params.world_width; j++) {
				
				//if the spot (i,j) is occupied
				if(CritterWorld.virtual_map.get(i).get(j).isEmpty() == false) {
					//print out the critter
					Rectangle rec = new Rectangle(50,50,Color.BLACK);
					GridPane.setConstraints(rec,i,j);
					pane.getChildren().add(rec);
				}
				else {
					//leave empty
				}
			}
		}
		
		Canvas canvas = new Canvas(width, height);
		pane.add(canvas, width, height);
		gc = canvas.getGraphicsContext2D();
		
		this.setTitle("View");
		
		Scene scene = new Scene(pane, width, height);
		
		this.setScene(scene);
		this.show();
	}

}
