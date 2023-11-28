package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import java.util.*;

public class Scene2 {
	
    public static Scene createScene2(Stage stage, String major) {
//    	stage.setTitle("Course Generator");
		
    	VBox layout = new VBox(40);
    	layout.setAlignment(Pos.CENTER);
		Text sceneTitle = new Text("Now, We ask That You Select The Classes You have Completed");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));	
		layout.getChildren().add(sceneTitle);
//		String majors[] =
//            { "Computer Science", "Information Technology", "Computer Networking",
//                             "Data Science", "CyberSecurity", "Applied Mathematics" };
//		ArrayList<String> classes = new ArrayList<>();
		try{
			File file;
	        if (major.equals("Computer Science")) {
	            file = new File("src/application/CompSci.txt");
	        } else if (major.equals("Information Technology")) {
	            file = new File("src/application/InformationTech.txt");
	        } else if (major.equals("Computer Networking")) {
	            file = new File("src/application/CompNetworking.txt");
	        } else if (major.equals("Data Science")) {
	            file = new File("src/application/DataScience.txt");
	        } else if (major.equals("CyberSecurity")) {
	            file = new File("src/application/CyberSecurity.txt");
	        } else {
	            file = new File("src/application/Math.txt");
	        }
	        
			Scanner console = new Scanner(file);
			while(console.hasNextLine()) {
				String className = console.nextLine();
//				classes.add(className);
				CheckBox check_box = new CheckBox(className);
				layout.getChildren().add(check_box);
//				System.out.println(className);
			}
			
			console.close();
		} catch (FileNotFoundException e) {
	        System.out.println("File not found: " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Something went wrong: " + e.getMessage());
	    }

	    Button nextButton = new Button();
	    nextButton.setText("Next");

	    layout.getChildren().add(nextButton);

	    Scene scene = new Scene(layout, 700, 480);
	    return scene;
    }
//    public static void checkBoxConstructor(String fileName) {
//    	Scanner console = new Scanner(fileName);
//		while(console.hasNextLine()) {
//			CheckBox check_box = new CheckBox(console.nextLine());
//			layout.getChildren().add(check_box);
//		}
//    }
    
}
