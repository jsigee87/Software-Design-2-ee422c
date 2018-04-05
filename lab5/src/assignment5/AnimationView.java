package assignment5;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;

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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.util.Duration;
 
public class AnimationView extends Stage {
    
	    //main timeline
	    private AnimationTimer timer;
	    
	    private static final int height = 600;
	    private static final int width = 600;
		private static final int scene_width = Params.world_width*(width/Params.world_width);
		private static final int scene_height = Params.world_height*(height/Params.world_height);
	    static GridPane pane;
	    private GraphicsContext gc;
	    
	    private Integer step;
	    private Integer counter = 0;
	    private Double speed;
	    private long lastUpdate = 0;
	    
	    static int box_width = width / (Params.world_width + 10); 
	    static int box_height = height / (Params.world_height + 10);
	    static int box_area = box_width*box_height;
	    
	    AnimationView(Integer step, Double speed){
	    	final int numCols = Params.world_width;
		    final int numRows = Params.world_height;
		    
		    this.speed = speed;
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
			
			Slider slider = new Slider(0, 1, 0.5);
			slider.setValue(speed);
			slider.setShowTickMarks(true);
			slider.setShowTickLabels(true);
			slider.setMajorTickUnit(0.25f);
			slider.setBlockIncrement(0.1f);
			
			Label speed_label = new Label("Set the speed: ");
			
			timer = new AnimationTimer() {
	            @Override
	            public void handle(long l) {
	            	
	            	if (l - lastUpdate >= 28000000*(1/slider.getValue())) {
	            		update();
		                if(counter == step && step != -1) {
		                	timer.stop();
		                }
		                counter++;
                        lastUpdate = l;
                    }
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
	        box.getChildren().add(speed_label);
	        box.getChildren().add(slider);
			root.getChildren().addAll(pane,box);
			Scene scene = new Scene(root, scene_width + 200, scene_height);
			
			timer.start();
			
			this.setScene(scene);
			this.show();
		}
	    
	    public static void update() {
		    int i = 0;
			int j = 0;
			pane.getChildren().removeAll(pane.getChildren());
			Critter.worldTimeStep();
			
	    	for (i = 0; i < Params.world_width; i++) {
				for(j = 0; j < Params.world_height; j++) {
					
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
							GridPane.setConstraints(circle, i, j);
						    pane.getChildren().add(circle);
							break;
						case DIAMOND:
							{Polygon diamond = new Polygon();
							
							double [] diamond_points = new double[]{
								   1.0,0.0,
								   0.0,1.0,
								   1.0,2.0,
								   2.0,1.0};
							
							Scale diamond_sc = new Scale(box_width*0.3, box_height*0.3,i,j);

							for (Transform transform : Arrays.asList(diamond_sc)) {
							    transform.transform2DPoints(diamond_points, 0, diamond_points, 0, diamond_points.length/2);
							}
							
							Double [] diamondPointArr = new Double [diamond_points.length];
							for (int x = 0; x < diamond_points.length; x++) {
								diamondPointArr[x] = diamond_points[x];
							}
							
							diamond.getPoints().addAll(diamondPointArr);
							diamond.setFill(fill);;
							diamond.setStroke(edge);
							GridPane.setConstraints(diamond, i, j);
							pane.getChildren().add(diamond);
							break;}
						case SQUARE:
							Rectangle square = new Rectangle(box_width, box_width);
							square.setFill(fill);
							square.setStroke(edge);
							GridPane.setConstraints(square, i, j);
							pane.getChildren().add(square);
							break;
						case STAR:
							Polygon star = new Polygon();
							
							double [] star_points = new double[]{
								    3.0,1.0,
								    2.25,3.5,
								    0.0,3.75,
								    2.0,4.5,
								    1.0,7.0,
								    3.0,5.0,
								    5.0,7.0,
								    4.0,4.5,
								    6.0,3.75,
								    3.75,3.5};
							
							Scale star_sc = new Scale(box_width*0.2, box_height*0.2,i,j);

							for (Transform transform : Arrays.asList(star_sc)) {
							    transform.transform2DPoints(star_points, 0, star_points, 0, star_points.length/2);
							}
							
							Double [] starPointArr = new Double [star_points.length];
							for (int x = 0; x < star_points.length; x++) {
								starPointArr[x] = star_points[x];
							}
							
							star.getPoints().addAll(starPointArr);
							star.setFill(fill);
							star.setStroke(edge);
							GridPane.setConstraints(star, i, j);
							pane.getChildren().add(star);
							break;
						case TRIANGLE:
							Polygon triangle = new Polygon();
							double [] tri_points = new double[]{
							    1.0, 0.0,
							    0.0, 1.0,
							    2.0, 1.0 };
						
							Scale tri_sc = new Scale(box_width/2, box_height*0.80,i,j);

							for (Transform transform : Arrays.asList(tri_sc)) {
							    transform.transform2DPoints(tri_points, 0, tri_points, 0, tri_points.length/2);
							}
							
							Double [] triPointArr = new Double [tri_points.length];
							for (int i1 = 0; i1 < tri_points.length; i1++) {
								triPointArr[i1] = tri_points[i1];
							}
							
							triangle.getPoints().addAll(triPointArr);
							triangle.setFill(fill);
							triangle.setStroke(edge);
							GridPane.setConstraints(triangle, i, j);
							pane.getChildren().add(triangle);
							break;
						default:	//get a circle
							{double rad = Math.sqrt(box_area/(Math.PI));
							Circle cir = new Circle(rad,Color.BLUE);
							cir.setFill(fill);
							cir.setStroke(edge);
							GridPane.setConstraints(cir, i, j);
						    pane.getChildren().add(cir);
							break;}				
						}
					}
					else {
						//leave empty
					}
				}
			}
	    }
 }