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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	private static Scene scene;
    private static Scene scene2;
    private static final String ERR_USERNAME = "A Proper Name Must Be Provided (Numbers Must NOT Be Included).";
    private static final String ERR_STUDENTYEAR = "An Integer between 1 and 4 (Inclusive) must be enter for Student Year.";
    private static final String ERR_EMAIL = "Your WIT EMAIL Must be in the following Format: username@wit.edu (replace with your own WIT Email).";
    private static final String ERR_COMBOBOX = "Please Select a Major Out of the Ones Provided (As Of Now, Only the Ones Listed are Avaliable).";
    private static final String CONFIRM_ENTRIES = "Know That You Won't Be Able to Return This Page. If You Are Ready To Move On, Click 'OK', Otherwise, Click 'cancel' And Look Over your Entries.";
    @Override
    public void start(Stage stage) throws IOException {
    	stage.setTitle("Course Generator");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text scenetitle = new Text("Hello Wentworth! Please Enter the Following Information"
									+ " to Begin:");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));	
		grid.add(scenetitle, 0, 0, 2, 1);
		
		Label userName = new Label("Name:");
		grid.add(userName, 0, 1);
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);
		
		Label stuY = new Label("Student Year:");
		grid.add(stuY, 0, 2);
		TextField studYear = new TextField();
		grid.add(studYear, 1, 2);
		
		Label witEmail = new Label("WIT Email:");
		grid.add(witEmail, 0, 3);
		TextField userWitEmail = new TextField();
		grid.add(userWitEmail, 1, 3);
		
		String majors[] =
            { "Computer Science", "Information Technology", "Computer Networking",
                             "Data Science", "CyberSecurity", "Applied Mathematics" };
		Label major = new Label("Your Major:");
		grid.add(major, 0, 4);
		ComboBox combo_box = new ComboBox(FXCollections.observableArrayList(majors));
		grid.add(combo_box, 1, 4);
		
		Button nextButton = new Button();
		nextButton.setText("Next");
		grid.add(nextButton, 0, 5);
		nextButton.setOnAction(event->{
			String userN = userTextField.getText();
		    String studY = studYear.getText();
		    String email = userWitEmail.getText();
			if(!validName(userN)) {
				InvalidNameAlert();	
			}else if(!validStudentYear(studY)) {
				InvalidYearAlert();
			}else if(!validEmail(email)) {
				InvalidEmailAlert();
			} else if(combo_box.getSelectionModel().isEmpty()){
				InvalidSelection();
			} else {
		    	Alert confirm = new Alert(AlertType.CONFIRMATION);
				confirm.setTitle("Confirmation");
				confirm.setHeaderText("Are You Sure You Wish to Move On?");
				confirm.setContentText(CONFIRM_ENTRIES);
				Optional<ButtonType> result = confirm.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.OK) {
					stage.setScene(scene2);
				}
			}
			});
		
		Button someButton = new Button();
		someButton.setText("something");
//		Button goBackButton = new Button();
//		goBackButton.setText("Go Back");
		
		StackPane layout2 = new StackPane();
		layout2.getChildren().add(someButton);
		scene2 = new Scene(layout2,600,300);
		Scene scene = new Scene(grid, 700, 480);
		stage.setScene(scene);
		stage.show();
    }
    
    public static boolean validName(String name) {
    	if(name.length() == 0) {
    		return false;
    	}
    	name = name.toLowerCase();
    	for(int i=0;i<name.length();i++) {
    		char c = name.charAt(i);
    		if(!(c >= 'a' && c <='z')) {
    			return false;
    		}
    	}
    	return true;
    }
//    public static void confirmation() {
//    	Alert confirm = new Alert(AlertType.CONFIRMATION);
//		confirm.setTitle("Confirmation");
//		confirm.setHeaderText("Are You Sure You Wish to Move On?");
//		confirm.setContentText(CONFIRM_ENTRIES);
//		confirm.showAndWait();
//    }
    public static void InvalidNameAlert() {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Please Try Again");
		alert.setHeaderText("Incorrect Input For Name");
		alert.setContentText(ERR_USERNAME);
		alert.showAndWait();
    }
    public static void InvalidYearAlert() {
    	Alert alert2 = new Alert(AlertType.ERROR);
		alert2.setTitle("Please Try Again");
		alert2.setHeaderText("Incorrect Input For Student Year");
		alert2.setContentText(ERR_STUDENTYEAR);
		alert2.showAndWait();
    }
    public static void InvalidEmailAlert() {
    	Alert alert3 = new Alert(AlertType.ERROR);
		alert3.setTitle("Please Try Again");
		alert3.setHeaderText("Incorrect Input For WIT Email");
		alert3.setContentText(ERR_EMAIL);
		alert3.showAndWait();
    }
    public static void InvalidSelection() {
    	Alert alert4 = new Alert(AlertType.ERROR);
		alert4.setTitle("Please Try Again");
		alert4.setHeaderText("No Selection Made");
		alert4.setContentText(ERR_COMBOBOX);
		alert4.showAndWait();
    }
    public static boolean validStudentYear(String y) {
    	if(y.length() == 0) {
    		return false;
    	}
    	try {
    		int year = Integer.parseInt(y);
    		if(year<1 || year >4) {
    			return false;
    		}
    		return true;
    	}catch(NumberFormatException e) {
    		return false;
    	}
    }
    public static boolean validEmail(String email) {
    	if(email.length() == 0 || email.length()<=8) {
    		return false;
    	}
    	String subString = email.substring(email.length()-8);
    	return subString.equals("@wit.edu");
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
