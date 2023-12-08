/**
 * The Scene1 class represents the first scene of a JavaFX application for course generation.
 * It collects user information such as name, student year, current semester, WIT email, and major.
 * Upon valid entries, it transitions to the second scene (Scene2) after confirming that all inputs are correct.
 *
 * @author Ibukunoluwa Folajimi, Davud Azizov, Liban Mohamed
 *
 */

package application;

import java.io.FileInputStream;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * The Scene1 class extends Application
 */
public class Scene1 extends Application {
	//Error messages displayed along with Error Alerts
    private static final String ERR_USERNAME = "A proper name must be provided (Numbers must NOT be included).";
    private static final String ERR_EMAIL = "Your WIT Email must be in the following Format: username@wit.edu (replace with your own WIT Email).";
	private static final String ERR_STUDENT_YEAR = "Please select a student year out of the ones provided";
	private static final String ERR_MAJOR = "Please select a major out of the ones provided (As of now, only the ones listed are available.)";
	private static final String ERR_SEMESTER = "Please select your current semester out of the ones provided";
    //Confirmation message displayed along with Confirmation Alerts
	private static final String CONFIRM_ENTRIES = "Know that you won't be able to return to this page. If you are ready to move on, click 'OK', otherwise, click 'cancel' and look over your entries.";

	/**
	 * The start method is called
	 * right after the application is launched.
	 */
    @Override
    public void start(Stage stage) {
    	try {
            // Set the title of the stage
    		stage.setTitle("Course Generator");
            // Create File menu with items(currently do nothing)
    		Menu fileMenu = new Menu("File");
    		MenuItem menuItem1 = new MenuItem("Item 1");
    		MenuItem menuItem2 = new MenuItem("Item 2");
    		fileMenu.getItems().add(menuItem1);
    		fileMenu.getItems().add(menuItem2);
            // Create Help menu with items(currently do nothing)
    		Menu helpMenu = new Menu("Help");
    		MenuItem menuItem3 = new MenuItem("Item 3");
    		MenuItem menuItem4 = new MenuItem("Item 4");
    		helpMenu.getItems().add(menuItem3);
    		helpMenu.getItems().add(menuItem4);
            // Create a menu bar and add File and Help menus
    		MenuBar menuBar = new MenuBar();
    		menuBar.getMenus().addAll(fileMenu,helpMenu);
            // Create layout components
    		VBox vBox = new VBox();
    		GridPane grid = new GridPane();
    		grid.setAlignment(Pos.CENTER);
    		grid.setHgap(10);
    		grid.setVgap(10);
    		grid.setPadding(new Insets(25, 25, 25, 25));
            
    		// Add a title to the UI
    		Text sceneTitle = new Text("Hello Wentworth Student! Please enter the following information to begin:");
    		sceneTitle.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
    		grid.add(sceneTitle, 0, 0, 2, 1);
            
    		// Add labels, text fields, and combo boxes for user input
    		//Adds the objects to the gridPane
    		Label userName = new Label("Name:");
    		grid.add(userName, 0, 1);
    		TextField userTextField = new TextField();
    		grid.add(userTextField, 1, 1);

    		String[] years = {
    				"Freshman", "Sophomore", "Junior", "Senior"
    		};
    		Label studYear = new Label("Your Year:");
    		grid.add(studYear, 0, 2);
    		ComboBox<String> studentYear = new ComboBox<>(FXCollections.observableArrayList(years));
    		grid.add(studentYear, 1, 2);

    		String[] seasons = {
    				"Fall", "Spring", "Summer"
    		};
    		Label  season = new Label("Current Semester:");
    		grid.add(season, 0, 3);
    		ComboBox<String> choicesOfSeasons = new ComboBox<>(FXCollections.observableArrayList(seasons));
    		grid.add(choicesOfSeasons, 1, 3);

    		Label witEmail = new Label("WIT Email:");
    		grid.add(witEmail, 0, 4);
    		TextField userWitEmail = new TextField();
    		grid.add(userWitEmail, 1, 4);

    		String[] majors = {
    				"Computer Science", "Information Technology", "Computer Networking", "Data Science", "CyberSecurity", "Applied Mathematics"
    		};
    		Label major = new Label("Your Major:");
    		grid.add(major, 0, 5);
    		ComboBox<String> comboBox2 = new ComboBox<>(FXCollections.observableArrayList(majors));
    		grid.add(comboBox2, 1, 5);
    		//  ^
    		//  |
    		//Creating labels, text fields, and combo boxes for user input end here
    		
            // Creates a 'Next' button along with it's setOnAction using user's input
    		Button nextButton = getButton(
    				userTextField, choicesOfSeasons, studentYear,userWitEmail, comboBox2, stage, menuBar
    		);
    		nextButton.setText("Next");
    		//Add the objects to the gridPane
    		grid.add(nextButton, 0, 6);
            // Create an image view and set to an image avaliable in the project folder
    		Image image = new Image(new FileInputStream("src/media/WIT.png"), 500.0, 132.5, false, false);
    		ImageView imageView = new ImageView(image);
    		//Adds the menuBar, grid, and image to the vBox
    		vBox.getChildren().add(menuBar);
    		vBox.getChildren().add(grid);
    		vBox.getChildren().add(imageView);
    		//Sets alignment of the items in vBox to center
    		vBox.setAlignment(Pos.CENTER);
    		
    		//creates the main layout of the scene
    		VBox root = new VBox();
    		//Add all the components to the main layout(root)
    		root.getChildren().addAll(menuBar, vBox);
            // Create and set the scene for the stage
    		Scene scene = new Scene(root, 1200, 680);
    		stage.setScene(scene);

    		stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * Validates that the provided name contains only letters and whitespaces.
	 *
	 * @param name The name to validate.
	 * @return True if the name is valid, false otherwise.
	 */
    public static boolean validName(String name) {
        // Check if the name is empty
    	if (name.isEmpty()) return false;// if name is empty, return false

        // Iterate through each character in the name
		for (char c : name.toCharArray()) {
	        // Check if the character is not a letter or a whitespace(" ")
			if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
				return false;// if Character is not a letter or whitespace, return false
			}
		}
	    // if all characters are either letters or whitespaces, return true
    	return true;
    }

	/**
	 * Displays an alert for invalid name input.
	 */
    public static void invalidNameAlert() {
    	 // Create an error alert using AlertType.ERROR
        Alert alert = new Alert(AlertType.ERROR);
        // Set the title, header text, and content text for the error alert
        alert.setTitle("Please try again");
        alert.setHeaderText("Incorrect input for Name");
        alert.setContentText(ERR_USERNAME);
        // Display the error alert and wait for user interaction
        alert.showAndWait();    }

	/**
	 * Displays an alert for invalid email input.
	 */
    public static void invalidEmailAlert() {
    	// Create an error alert using AlertType.ERROR
    	Alert alert3 = new Alert(AlertType.ERROR);
    	// Set the title, header text, and content text for the error alert
		alert3.setTitle("Please try again");
		alert3.setHeaderText("Incorrect input for WIT Email");
		alert3.setContentText(ERR_EMAIL);
		// Display the error alert and wait for user interaction
		alert3.showAndWait();
    }

    /**
     * Displays an alert for invalid selection.
     *
     * @param ERR Error message to display in the alert
     */
    public static void invalidSelection(String ERR) {
    	Alert alert4 = new Alert(AlertType.ERROR);
		alert4.setTitle("Please Try Again");
		alert4.setHeaderText("No selection made");
		alert4.setContentText(ERR);
		alert4.showAndWait();
    }

    /**
     * Checks if the email is in the correct format and contains "@wit.edu".
     *
     * @param email Email address to validate
     * @return True if the email is valid, otherwise false
     */
	public static boolean validEmail(String email) {
	    // Check if the length of the email is less than or equal to 8 characters
		if (email.length() <= 8) {
    		return false;// Return false if the email length is insufficient
    	}
		// Extract the last 8 characters of the email
    	String subString = email.substring(email.length() - 8);
    	// Check if the extracted substring matches "@wit.edu"
    	return subString.equals("@wit.edu");// Return true if the email ends with "@wit.edu"
    }

	/**
     * Creates and returns the "Next" button with input validation.
     *
     * @param userTextField    TextField for user's name
     * @param choicesOfSeasons ComboBox for selecting seasons
     * @param studentYear      ComboBox for selecting student year
     * @param userWitEmail     TextField for WIT email input
     * @param comboBox2        ComboBox for selecting a major
     * @param stage            Current stage of the application
     * @param menuBar          MenuBar in the application
     * @return Next Button with event handling and input validation
     */
	private static Button getButton(
			TextField userTextField,
			ComboBox choicesOfSeasons,
			ComboBox studentYear,
			TextField userWitEmail,
			ComboBox comboBox2,
			Stage stage,
			MenuBar menuBar
	) {
		Button nextButton = new Button();// Create a new "Next" button
		nextButton.setOnAction(event -> {
				String userN = userTextField.getText();// Get user's name input
				String email = userWitEmail.getText();// Get WIT email input
		        // Check if the user's name is valid; if not, display an error alert
				if (!validName(userN)) {
					invalidNameAlert();//display related alert if is invalid
				} else if (studentYear.getSelectionModel().isEmpty()) {// Check if student year is not selected
					invalidSelection(ERR_STUDENT_YEAR);// Display an error alert for missing selection
				} else if(choicesOfSeasons.getSelectionModel().isEmpty()) {// Check if season choice is not selected
					invalidSelection(ERR_SEMESTER);// Display an error alert for missing selection
				} else if (!validEmail(email)) {// Check if the email is not in a valid format
					invalidEmailAlert();// Display an error alert for invalid email format
				} else if (comboBox2.getSelectionModel().isEmpty()) {// Check if the major is not selected
					invalidSelection(ERR_MAJOR);// Display an error alert for missing selection
				} else {
					// If all inputs are valid, prompt a confirmation alert before proceeding
					Optional<ButtonType> result = getButtonType();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						// If confirmed, create a Student object with user inputs and move to the next scene
						Student userEntries = new Student(
								userTextField.getText(),
                                (String) choicesOfSeasons.getValue(),
                                (String) studentYear.getValue(),
								userWitEmail.getText(),
                                (String) comboBox2.getValue()
                        );
						stage.setScene(Scene2.createScene2(stage, userEntries, menuBar));
					}
				}
				}
		);
		return nextButton;// Return the created "Next" button with event handling
	}

	/**
	 * Creates and returns an Optional<ButtonType> for the confirmation alert.
	 *
	 * @return Optional containing the button type selected in the confirmation alert
	 */
	private static Optional<ButtonType> getButtonType() {
	    // Create a confirmation alert using getAlert() method
		Alert confirm = getAlert();
		// Show the confirmation alert and wait for user interaction
        return confirm.showAndWait();// Returns an Optional containing the button type selected by the user
	}

	/**
	 * Creates and returns a confirmation alert for moving on to the next scene.
	 *
	 * @return Confirmation alert dialog
	 */
	private static Alert getAlert() {
		// Create a confirmation alert with AlertType.CONFIRMATION
	    Alert confirm = new Alert(AlertType.CONFIRMATION);
	    // Set the title, header text, and content text for the confirmation alert
	    confirm.setTitle("Confirmation");
	    confirm.setHeaderText("Are you sure you wish to move on?");
	    confirm.setContentText(CONFIRM_ENTRIES);
	    return confirm; // Return the created confirmation alert
	}

	/**
	 * The main method is the entry point of the JavaFX application.
	 *
	 * @param args Command-line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
