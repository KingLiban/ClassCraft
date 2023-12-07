/**
 * The Scene3 class represents the third scene of a JavaFX application.
 * It displays a message to the user, indicating that a CSV file has been created successfully.
 *
 * @author Ibukunoluwa Folajimi, Davud Azizov, Liban Mohammed
 *
 */


package application;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * The Scene3 class is  creating the third scene of the application.
 */
public class Scene3 {

		public static Scene createScene3(Stage stage, Student student, ArrayList<String> requiredClasses, MenuBar menuBar) {
	    	
			// Create a VBox to hold the components of the scene
			VBox root = new VBox();
			
			// Add the MenuBar to the VBox
	        root.getChildren().add(menuBar);

	        // Display a message indicating successful CSV file creation
	        Text sceneTitle = new Text("Thank you for your patience. CSV file has been created successfully");
	        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        root.getChildren().add(sceneTitle);
	        
	        // Create the Scene with a specified width and height
	        Scene scene = new Scene(root, 1200, 780);
	        return scene;
	    }
}