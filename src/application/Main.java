package application;
	
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	private static Scene scene;
    private static Scene scene2;
    private static final String ERR_USERNAME = "A proper name must be provided (Numbers must NOT be included).";
    private static final String ERR_STUDENT_YEAR = "An integer between 1 and 4 (inclusive) must be enter for student year.";
    private static final String ERR_EMAIL = "Your WIT Email must be in the following Format: username@wit.edu (replace with your own WIT Email).";
	private static final String ERR_COMBO_BOX1 = "Please select a student year out of the ones provided";

	private static final String ERR_COMBO_BOX2 = "Please select a major out of the ones provided (As of now, only the ones listed are available.)";
    private static final String CONFIRM_ENTRIES = "Know that you won't be able to return to this page. If you are ready to move on, click 'OK', otherwise, click 'cancel' and look over your entries.";
    @Override
    public void start(Stage stage) {
    	stage.setTitle("Course Generator");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text sceneTitle = new Text("Hello Wentworth Student! Please enter the following Information"
									+ " to begin:");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0, 2, 1);

		Label userName = new Label("Name:");
		grid.add(userName, 0, 1);
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		String[] years = {
				"Freshman", "Sophmore", "Junior", "Senior"
		};
		Label studYear = new Label("Your Year:");
		grid.add(studYear, 0, 2);
		ComboBox<String> comboBox1 = new ComboBox<>(FXCollections.observableArrayList(years));
		grid.add(comboBox1, 1, 2);
		Label witEmail = new Label("WIT Email:");
		grid.add(witEmail, 0, 3);
		TextField userWitEmail = new TextField();
		grid.add(userWitEmail, 1, 3);
		
		String[] majors = {
				"Computer Science", "Information Technology", "Computer Networking", "Data Science", "CyberSecurity", "Applied Mathematics"
		};
		Label major = new Label("Your Major:");
		grid.add(major, 0, 4);

		ComboBox<String> comboBox2 = new ComboBox<>(FXCollections.observableArrayList(majors));
		grid.add(comboBox2, 1, 4);
		
		Button nextButton = new Button();
		nextButton.setText("Next");
		grid.add(nextButton, 0, 5);
		nextButton.setOnAction(event -> {
				String userN = userTextField.getText();
				String studY = studYear.getText();
				String email = userWitEmail.getText();
				if (!validName(userN)) {
					invalidNameAlert();
				} else if (comboBox1.getSelectionModel().isEmpty()) {
					invalidSelection(ERR_COMBO_BOX1);
				} else if (!validEmail(email)) {
					invalidEmailAlert();
				} else if (comboBox2.getSelectionModel().isEmpty()) {
					invalidSelection(ERR_COMBO_BOX2);
				} else {
					String userSelection = comboBox2.getValue();
					Alert confirm = new Alert(AlertType.CONFIRMATION);
					confirm.setTitle("Confirmation");
					confirm.setHeaderText("Are you sure you wish to move on?");
					confirm.setContentText(CONFIRM_ENTRIES);
					Optional<ButtonType> result = confirm.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						stage.setScene(Scene2.createScene2(stage, userSelection));
					}
				}
			}
		);

		Scene scene = new Scene(grid, 700, 480);
		stage.setScene(scene);
		stage.show();
    }
    
    public static boolean validName(String name) {
    	if (name.isEmpty()) {
    		return false;
    	}
    	name = name.toLowerCase();
    	for (int i = 0; i < name.length(); i++) {
    		char c = name.charAt(i);
    		if (!(c >= 'a' && c <= 'z')) {
    			return false;
    		}
    	}
    	return true;
    }

    public static void invalidNameAlert() {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Please try again");
		alert.setHeaderText("Incorrect input for Name");
		alert.setContentText(ERR_USERNAME);
		alert.showAndWait();
    }
    public static void invalidYearAlert() {
    	Alert alert2 = new Alert(AlertType.ERROR);
		alert2.setTitle("Please try again");
		alert2.setHeaderText("Incorrect input for Student Year");
		alert2.setContentText(ERR_STUDENT_YEAR);
		alert2.showAndWait();
    }
    public static void invalidEmailAlert() {
    	Alert alert3 = new Alert(AlertType.ERROR);
		alert3.setTitle("Please try again");
		alert3.setHeaderText("Incorrect input for WIT Email");
		alert3.setContentText(ERR_EMAIL);
		alert3.showAndWait();
    }
    public static void invalidSelection(String ERR) {
    	Alert alert4 = new Alert(AlertType.ERROR);
		alert4.setTitle("Please Try Again");
		alert4.setHeaderText("No selection made");
		alert4.setContentText(ERR);
		alert4.showAndWait();
    }
    public static boolean validEmail(String email) {
    	if (email.length() <= 8) {
    		return false;
    	}
    	String subString = email.substring(email.length() - 8);
    	return subString.equals("@wit.edu");
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
