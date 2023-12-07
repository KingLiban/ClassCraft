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
	 *
	 * @param stage
	 * @param student
	 * @param menuBar
	 * @return
	 */
	public static Scene createScene3(Stage stage, Student student, MenuBar menuBar) {
		VBox root = new VBox();
		root.getChildren().add(menuBar);

		Text sceneTitle = new Text("Thank you for your patience. CSV file has been created successfully");
		sceneTitle.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));

		VBox contentBox = new VBox();
		contentBox.setAlignment(Pos.CENTER);
		contentBox.setSpacing(20);
		contentBox.setPadding(new Insets(20));
		contentBox.getChildren().addAll(sceneTitle);

		Button downloadButton = new Button("Download CSV");
		downloadButton.setOnAction(e -> downloadCSV(stage));
		downloadButton.setPadding(new Insets(10));
		contentBox.getChildren().add(downloadButton);

		ImageView imageView = null;
		Image image = null;
		try {
			image = new Image(new FileInputStream("src/media/WIT3.png"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		imageView = new ImageView(image);
		contentBox.getChildren().add(imageView);

		root.getChildren().add(contentBox);
		return new Scene(root, 1200, 880);
	}

	/**
	 *  Downloads a CSV file. Prompts the user to choose a location for saving.
	 */
	private static void downloadCSV(Stage stage) {
		String sourceFilePath = "src/application/StudentSchedule.csv";

		File sourceFile = new File(sourceFilePath);

		if (!sourceFile.exists()) {
			showAlert("File Not Found", "The CSV file does not exist.");
			return;
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save CSV File");

		fileChooser.setInitialFileName("StudentSchedule.csv");

		File targetFile = fileChooser.showSaveDialog(stage);

		if (targetFile != null) {
			try {
				Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				System.out.println("CSV file downloaded successfully");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			showAlert("Download Canceled", "No file selected for download.");
		}
	}

	/**
	 * Shows alert's screen.
	 */
	private static void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
