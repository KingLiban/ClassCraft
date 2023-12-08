/**
 * The Scene3 class represents the third scene of a JavaFX application.
 * It displays a message to the user, indicating that a CSV file has 
 * been created successfully and is avaliable for download.
 *
 * @author Ibukunoluwa Folajimi, Davud Azizov, Liban Mohamed
 *
 */

package application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The Scene3 class is  creating the third scene of the application.
 */
public class Scene3 {

	/**
	 * Creates and returns Scene 3 for the application where the user can download the csv file
	 *
	 * @param stage    The primary stage of the application
	 * @param student  The student object containing user information
	 * @param menuBar  The menu bar to be displayed in the scene
	 * @return         The created Scene 3 for the application
	 */
	public static Scene createScene3(Stage stage, Student student, MenuBar menuBar) {
		// Create a VBox for the scene and add the menu bar
		VBox root = new VBox();
		root.getChildren().add(menuBar);
	    // Create the scene title 
		Text sceneTitle = new Text("Thank you for your patience. CSV file has been created successfully");
		sceneTitle.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
		
		// Create a VBox for the content with certain formatting and alignment
		VBox contentBox = new VBox();
		contentBox.setAlignment(Pos.CENTER);
		contentBox.setSpacing(20);
		contentBox.setPadding(new Insets(20));
		//Add scene title to the contentBox
		contentBox.getChildren().addAll(sceneTitle);
		
		// Create a 'download' button and set an action for downloading the CSV file
		Button downloadButton = new Button("Download CSV");
		downloadButton.setOnAction(e -> downloadCSV(stage));
		downloadButton.setPadding(new Insets(10));
		//Add 'download' button to the layout
		contentBox.getChildren().add(downloadButton);
		
		// Load an image from a file and display it using an ImageView
		ImageView imageView = null;
		Image image = null;
		try {
			image = new Image(new FileInputStream("src/media/WIT3.png"));//Set the image to the one in the project folder
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		imageView = new ImageView(image);
		//Add image to the contentBox
		contentBox.getChildren().add(imageView);
		// Add the content box to the root VBox(main layout) and return Scene containing the layout
		root.getChildren().add(contentBox);
		return new Scene(root, 1200, 880);
	}

	/**
	 * Downloads the CSV file by allowing the user to choose the location for saving.
	 *
	 * @param stage The primary stage of the application
	 */
	private static void downloadCSV(Stage stage) {
		//String hold the path to the csv file
		String sourceFilePath = "src/application/StudentSchedule.csv";

		File sourceFile = new File(sourceFilePath);
		// Check if the csv file exists
		if (!sourceFile.exists()) {
			showAlert("File Not Found", "The CSV file does not exist.");//Display alert if file is not found
			return;
		}
		// Create a FileChooser for selecting the target location to save the file
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save CSV File");

		fileChooser.setInitialFileName("StudentSchedule.csv");

		File targetFile = fileChooser.showSaveDialog(stage);
		// Check if a target file location was selected
		if (targetFile != null) {
			try {
				// Copy the source CSV file to the selected target file location
				Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				System.out.println("CSV file downloaded successfully");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			showAlert("Download Canceled", "No file selected for download.");//Display an alert if a target file location isn't chosen
		}
	}

	/**
	 * Displays an information alert with the provided title and content.
	 *
	 * @param title   The title of the alert
	 * @param content The content message to be displayed in the alert
	 */
	private static void showAlert(String title, String content) {
		 // Create an information alert
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		//Set all properties of the alert
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
