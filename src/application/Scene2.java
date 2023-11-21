package application;
	
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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;



public class Scene2 {
	
    public static Scene createScene2(Stage stage) throws IOException {
//    	stage.setTitle("Course Generator");
		
    	VBox layout = new VBox(20);
    	layout.setAlignment(Pos.CENTER);
		Text scenetitle = new Text("Now, We ask That You Select The Classes You have Completed");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));	
        //layout.getChildren().add(scenetitle);
		
		CheckBox checkBox = new CheckBox("Check me");
		Button nextButton = new Button();
		nextButton.setText("Next");
		layout.getChildren().addAll(scenetitle,checkBox,nextButton);
//		Button goBackButton = new Button();
//		goBackButton.setText("Go Back");
//		StackPane layout2 = new StackPane();
//		layout2.getChildren().add(someButton);
//		scene2 = new Scene(layout2,600,300);
//		Scene scene = new Scene(grid, 700, 480);
//		stage.setScene(scene);
//		stage.show();
		Scene scene = new Scene(layout, 700, 480);
		
		return scene;
    }
    
}
