package javafxtest;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloWorld extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		Text hello = new Text(25, 25, "Hello World!");
		Scene scene1 = new Scene(new Group(hello)); 
		stage.setTitle("\"Hello World!\"");
		stage.setScene(scene1);
		stage.sizeToScene();
		stage.show();
	}
	
	public static void main(String args[]) {
		launch(args);
	}

}
