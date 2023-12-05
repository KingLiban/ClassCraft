package application;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Scene3 {

		public static Scene createScene3(Stage stage, Student student, ArrayList<String> requiredClasses, MenuBar menuBar) {
	    	VBox root = new VBox();
	        root.getChildren().add(menuBar);

	        Text sceneTitle = new Text("Thank you for your patience. CSV file has been created successfully");
	        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        root.getChildren().add(sceneTitle);
	        
	        
	        Scene scene = new Scene(root, 1200, 780);
	        // Set the data to the table
	        return scene;
	    }
}