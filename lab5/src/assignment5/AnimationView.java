package assignment5;

import javafx.application.Application;
import javafx.stage.Stage;
import assignment5.Critter.CritterShape;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
 
public class AnimationView extends Stage {
    
	    //main timeline
	    private AnimationTimer timer;
	    
	    private static final int height = 600;
	    private static final int width = 600;
	    static GridPane pane;
	    private GraphicsContext gc;
	    
	    private Integer step;
	    private Integer counter = 0;
	    
	    static int box_width = width / (Params.world_width + 10); 
	    static int box_height = height / (Params.world_height + 10);
	    static int box_area = box_width*box_height;
	    
	    AnimationView(Integer step){
	    	final int numCols = Params.world_height;
		    final int numRows = Params.world_width;
		    
		    this.step = step;
		      
		    pane = new GridPane();
		    
		    for (int i = 0; i < numCols; i++) {
		    	ColumnConstraints colConst = new ColumnConstraints(width / (Params.world_width + 1));
//		        ColumnConstraints colConst = new ColumnConstraints();
		        //colConst.setPercentWidth(100.0 / numCols);
		        pane.getColumnConstraints().add(colConst);
		    }
		    for (int i = 0; i < numRows; i++) {
		        RowConstraints rowConst = new RowConstraints( height / (Params.world_height + 1));
		        //rowConst.setPercentHeight(100.0 / numRows);
		        pane.getRowConstraints().add(rowConst);         
		    }
		    
		    pane.setStyle("-fx-background-color: black, -fx-control-inner-background; -fx-background-insets: 0, 2; -fx-padding: 2;");
		    pane.setGridLinesVisible(true);
			
			Group root = new Group();
			
			this.setTitle("Animation View");
			
			timer = new AnimationTimer() {
	            @Override
	            public void handle(long l) {
	                update();
	                if(counter == step && step != -1) {
	                	timer.stop();
	                }
	                counter++;
	            }	 
	        };
	        
	        Button stop = new Button("Stop");
	        Button resume = new Button("Resume");
	        
	        stop.setOnAction(e -> {
	        	timer.stop();
	        });
	        
	        resume.setOnAction(e ->{
	        	timer.start();
	        });
			
	        VBox box = new VBox();
	        box.setPadding(new Insets(0, width - 20, 0, width));
	        box.getChildren().add(stop);
	        box.getChildren().add(resume);
			root.getChildren().addAll(pane,box);
			Scene scene = new Scene(root, width + 100, height);
			
			timer.start();
			
			this.setScene(scene);
			this.show();
		}
	    
	    public static void update() {
		    int i = 0;
			int j = 0;
			pane.getChildren().removeAll(pane.getChildren());
			Critter.worldTimeStep();
			
	    	for (i = 0; i < Params.world_height; i++) {
//				System.out.print("|");
				for(j = 0; j < Params.world_width; j++) {
					
					//if the spot (i,j) is occupied
					if(CritterWorld.virtual_map.get(i).get(j).isEmpty() == false) {
						//print out the critter
						CritterShape shape = CritterWorld.virtual_map.get(i).get(j).get(0).viewShape();
						Color edge = CritterWorld.virtual_map.get(i).get(j).get(0).viewOutlineColor();
						Color fill = CritterWorld.virtual_map.get(i).get(j).get(0).viewFillColor();
						
						switch(shape) {
							case CIRCLE:
								double radius = Math.sqrt(box_area/(Math.PI));
								Circle circle = new Circle(radius,Color.BLUE);
								circle.setFill(fill);
								circle.setStroke(edge);
								GridPane.setConstraints(circle,i,j);
							    pane.getChildren().add(circle);
								break;
							case DIAMOND:
								break;
							case SQUARE:
								break;
							case STAR:
								break;
							case TRIANGLE:
								break;
							default:
								break;
						
						}
					}
					else {
						//leave empty
					}
				}
			}
	    }
 }