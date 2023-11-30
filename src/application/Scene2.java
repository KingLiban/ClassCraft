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

    public static Scene createScene2(Stage stage, Student student) {

        VBox layout = new VBox(6);
        layout.setAlignment(Pos.CENTER);
        Text sceneTitle = new Text("Now, we ask that you select the classes you have completed "
        		+ "and enter the current number of credits you have for each subject");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        layout.getChildren().add(sceneTitle);

        ArrayList<String> selectedClasses = new ArrayList<>();
        ArrayList<String> unselectedClasses = new ArrayList<>();

//		File file = null;
//		try {
//            file = switch (student.getMajor()) {
//                case "Computer Science" -> new File("src/application/CompSci.txt");
//                case "Information Technology" -> new File("src/application/InformationTech.txt");
//                case "Computer Networking" -> new File("src/application/CompNetworking.txt");
//                case "Data Science" -> new File("src/application/DataScience.txt");
//                case "CyberSecurity" -> new File("src/application/CyberSecurity.txt");
//                default -> new File("src/application/Math.txt");
//            };
//		} catch (Exception e) {
//			System.out.println("Something went wrong: " + e.getMessage());
//		}
//		Scanner console = null;
//		try {
//            assert file != null;
//            console = new Scanner(file);
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException(e);
//		}

		Scanner console = new Scanner("src/application/Math.txt");

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


		GridPane grid = getGridPane();
		layout.getChildren().add(grid);
		Button nextButton = getButton(stage, student, unselectedClasses);
		layout.getChildren().add(nextButton);
        return new Scene(layout, 1200, 680);
    }

	private static Button getButton(Stage stage, Student student, ArrayList<String> unselectedClasses) {
		Button nextButton = new Button();
		nextButton.setText("Next");

		nextButton.setOnAction(e -> {
			// Uncomment the following block if you want to include the validation
			// if (
			//     validateCredits(genElectiveCredits.getText(), 20) &&
			//     validateCredits(humanElectiveCredits.getText(), 20) &&
			//     validateCredits(sciElectiveCredits.getText(), 20) &&
			//     validateCredits(majorElectiveCredits.getText(), 20)
			// ) {
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
				stage.setScene(Scene3.createScene3(stage, student, unselectedClasses));
			}
			// }
			// System.out.println("Selected Classes: " + selectedClasses);
			// System.out.println("Unselected Classes: " + unselectedClasses);
		});
		return nextButton;
	}

	private static GridPane getGridPane() {
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
		return grid;
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
    

