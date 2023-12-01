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

    public static Scene createScene2(Stage stage,StudentInfo student) {

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
            if (student.getMajor().equals("Computer Science")) {
                file = new File("src/application/CompSci.txt");
            } else if (student.getMajor().equals("Information Technology")) {
                file = new File("src/application/InformationTech.txt");
            } else if (student.getMajor().equals("Computer Networking")) {
                file = new File("src/application/CompNetworking.txt");
            } else if (student.getMajor().equals("Data Science")) {
                file = new File("src/application/DataScience.txt");
            } else if (student.getMajor().equals("CyberSecurity")) {
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
		
		Label generalElective = new Label("Current General Elective Credits:");
		grid.add(generalElective, 0, 1);
		TextField genElectiveCredits = new TextField();
		grid.add(genElectiveCredits, 1, 1);
		
		Label humanityElective = new Label("Current Humanities/Social Science Elective Credits:");
		grid.add(humanityElective, 0, 2);
		TextField humanElectiveCredits = new TextField();
		grid.add(humanElectiveCredits, 1, 2);
		
		Label scienceElective = new Label("Current Science Elective Credits:");
		grid.add(scienceElective, 0, 3);
		TextField sciElectiveCredits = new TextField();
		grid.add(sciElectiveCredits, 1, 3);
		
		Label majorElective = new Label("Current Major Elective Credits:");
		grid.add(majorElective, 0, 4);
		TextField majorElectiveCredits = new TextField();
		grid.add(majorElectiveCredits, 1, 4);
		
		
		
		layout.getChildren().add(grid);
        Button nextButton = new Button();
        nextButton.setText("Next");

        nextButton.setOnAction(e -> {
        	if(
        	validateCredits(genElectiveCredits.getText(), 20)&&
        	validateCredits(humanElectiveCredits.getText(), 20)&&
        	validateCredits(sciElectiveCredits.getText(), 20)&&
        	validateCredits(majorElectiveCredits.getText(), 20)
        	) {
        		student.setGeneralElective(Integer.parseInt("5"/*genElectiveCredits.getText()*/));
        		student.setHumanityElective(Integer.parseInt("5"/*humanElectiveCredits.getText()*/));
        		student.setMajorElective(Integer.parseInt("5"/*sciElectiveCredits.getText()*/));
        		student.setScienceElective(Integer.parseInt("5"/*majorElectiveCredits.getText()*/));
        		Alert confirm = new Alert(AlertType.CONFIRMATION);
        		confirm.setTitle("Confirmation");
        		confirm.setHeaderText("Are you sure you wish to move on?");
        		confirm.setContentText(CONFIRM_ENTRIES);
        		Optional<ButtonType> result = confirm.showAndWait();
        		if (result.isPresent() && result.get() == ButtonType.OK) {
        			stage.setScene(Scene3_2.createScene3_2(stage, student, unselectedClasses));
        		}
        	}
        });
        layout.getChildren().add(nextButton);

        Scene scene = new Scene(layout, 1100, 680);
        return scene;
    }
    public static boolean validateCredits(String credits, int limit) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Please try again");
    	if(credits.isEmpty()) {
    		alert.setHeaderText("No entry was made");
    		alert.setContentText("We ask that you enter the number of credits you have for all subjects");
    		alert.showAndWait();
    		return false;
    	}
    	try {
    		int credit = Integer.parseInt(credits);
    		if(credit<0 || credit>limit) {
    			alert.setHeaderText("An invalid entry was made");
        		alert.setContentText("We ask that you enter integers for the credits between 0 and " + limit + " (Inclusive)");
        		alert.showAndWait();
        		return false;
    		}
    		return true;
    	} catch(NumberFormatException e) {
    		alert.setHeaderText("An invalid entry was made");
    		alert.setContentText("We ask that you enter integers for the credits");
    		alert.showAndWait();
    		return false;
    	}
    	
    }
}
//    public static void checkBoxConstructor(String fileName) {
//    	Scanner console = new Scanner(fileName);
//		while(console.hasNextLine()) {
//			CheckBox check_box = new CheckBox(console.nextLine());
//			layout.getChildren().add(check_box);
//		}
//    }
    

