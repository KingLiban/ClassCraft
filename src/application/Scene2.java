package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import java.util.*;

public class Scene2 {
    private static final String CONFIRM_ENTRIES = "Know that you won't be able to return to this page. If you are ready to move on, click 'OK', otherwise, click 'cancel' and look over your entries.";

    public static Scene createScene2(Stage stage, String major) {

        VBox layout = new VBox(6);
        layout.setAlignment(Pos.CENTER);
        Text sceneTitle = new Text("Now, We ask that you select the classes you have completed "
        		+ "and enter the current number of credits you have for each subject");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        layout.getChildren().add(sceneTitle);

        ArrayList<String> selectedClasses = new ArrayList<>();
        ArrayList<String> unselectedClasses = new ArrayList<>();

        try {
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

            while (console.hasNextLine()) {
                String className = console.nextLine();
                CheckBox checkBox = new CheckBox(className);

                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        selectedClasses.add(className);
                        unselectedClasses.remove(className); 
                    } else {
                        selectedClasses.remove(className);
                        unselectedClasses.add(className);
                    }
                });

                layout.getChildren().add(checkBox);
                unselectedClasses.add(className); 
            }

            console.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        
        GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Label generalElective = new Label("Current general elective credits:");
		grid.add(generalElective, 0, 1);
		TextField genElectiveCredits = new TextField();
		grid.add(genElectiveCredits, 1, 1);
		
		Label scienceElective = new Label("Current science elective credits:");
		grid.add(scienceElective, 0, 2);
		TextField sciElectiveCredits = new TextField();
		grid.add(sciElectiveCredits, 1, 2);
		
		Label majorElective = new Label("Current major elective credits:");
		grid.add(majorElective, 0, 3);
		TextField majorElectiveCredits = new TextField();
		grid.add(majorElectiveCredits, 1, 3);
		
		layout.getChildren().add(grid);
        Button nextButton = new Button();
        nextButton.setText("Next");

        nextButton.setOnAction(e -> {
        	Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setTitle("Confirmation");
			confirm.setHeaderText("Are you sure you wish to move on?");
			confirm.setContentText(CONFIRM_ENTRIES);
			Optional<ButtonType> result = confirm.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				stage.setScene(Scene3.createScene3(stage, unselectedClasses));
			}
//            System.out.println("Selected Classes: " + selectedClasses);
//            System.out.println("Unselected Classes: " + unselectedClasses);
        });
        layout.getChildren().add(nextButton);

        Scene scene = new Scene(layout, 1100, 680);
        return scene;
    }
}
//    public static void checkBoxConstructor(String fileName) {
//    	Scanner console = new Scanner(fileName);
//		while(console.hasNextLine()) {
//			CheckBox check_box = new CheckBox(console.nextLine());
//			layout.getChildren().add(check_box);
//		}
//    }
    

