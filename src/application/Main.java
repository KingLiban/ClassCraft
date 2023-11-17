package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    @Override
    public void start(Stage stage) throws IOException {
    	stage.setTitle("Course Generator");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text scenetitle = new Text("Hello Wentworth Student! Please Enter the Following Information"
									+ " to Begin");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));	
		grid.add(scenetitle, 0, 0, 2, 1);
		
		Label userName = new Label("Name:");
		grid.add(userName, 0, 1);
		TextField userTextField = new TextField();
		String userN = userTextField.getText();
		grid.add(userTextField, 1, 1);
		
		Label stuY = new Label("Student Year:");
		grid.add(stuY, 0, 2);
		TextField studYear = new TextField();
		String studY = studYear.getText();
		grid.add(studYear, 1, 2);
		
		Label witEmail = new Label("WIT Email:");
		grid.add(witEmail, 0, 3);
		TextField userWitEmail = new TextField();
		String email = userWitEmail.getText();
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
		nextButton.setOnAction(e->{
			if(!validateName(userN)) {
				
			}else if(!validateStudentYear(studY)) {
				
			}else if(!validateEmail(email)) {
				
			}else {
				stage.setScene(scene2);
			}
			});
		
		Button someButton = new Button();
		someButton.setText("something");
		StackPane layout2 = new StackPane();
		layout2.getChildren().add(someButton);
		scene2 = new Scene(layout2,600,300);
		Scene scene = new Scene(grid, 700, 480);
		stage.setScene(scene);
		stage.show();
    }
    
    public static boolean validateName(String name) {
    	if(name.length() == 0) {
    		return false;
    	}
    	for(int i=0;i<name.length();i++) {
    		char c = name.charAt(i);
    		if(!(Character.isLetter(c)) && !Character.isWhitespace(c)) {
    			return false;
    		}
    	}
    	return true;
    }
    public static boolean validateStudentYear(String y) {
    	if(y.length() == 0) {
    		return false;
    	}
    	try {
    		int year = Integer.parseInt(y);
    		return true;
    	}catch(NumberFormatException e) {
    		return false;
    	}
    }
    public static boolean validateEmail(String email) {
    	if(email.length() == 0 && email.length()>8) {
    		return false;
    	}
    	String subString = email.substring(email.length()-9,email.length());
    	return subString.equals("@wit.edu");
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
