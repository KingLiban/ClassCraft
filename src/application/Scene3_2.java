package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Scene3_2 {

		public static Scene createScene3_2(Stage stage, StudentInfo student, ArrayList<String> requiredClasses, MenuBar menuBar) {
	    	VBox root = new VBox();
	        root.getChildren().add(menuBar);

	        Text sceneTitle = new Text("Thank you for your patience. Your College Road Map has been sent to your email.");
	        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        root.getChildren().add(sceneTitle);
	        Scene scene = new Scene(root, 800, 600);
	        // Set the data to the table
	        return scene;
	    }
}