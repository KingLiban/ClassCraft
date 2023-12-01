package application;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Scene3_2 extends Application {
	   public static class Person {
	        private final SimpleStringProperty name; // Use JavaFX Properties
	        private final SimpleIntegerProperty age; // Use JavaFX Properties

	        public Person(String name, int age) {
	            this.name = new SimpleStringProperty(name);
	            this.age = new SimpleIntegerProperty(age);
	        }

	        public String getName() {
	            return name.get();
	        }

	        public int getAge() {
	            return age.get();
	        }

	        // Getters for JavaFX Properties
	        public SimpleStringProperty nameProperty() {
	            return name;
	        }

	        public SimpleIntegerProperty ageProperty() {
	            return age;
	        }
	    }
	    public static Scene createScene3_2(Stage stage, StudentInfo student, ArrayList<String> requiredClasses) {
	        TableView<Person> tableView = new TableView<>();

	        // Create columns with CellValueFactory using JavaFX Properties
	        TableColumn<Person, String> nameColumn = new TableColumn<>("Name");
	        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

	        TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");
	        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());

	        // Add columns to the table
	        tableView.getColumns().addAll(nameColumn, ageColumn);

	        // Create data for the table
	        ObservableList<Person> data = FXCollections.observableArrayList(
	                new Person("Alice", 25),
	                new Person("Bob", 30),
	                new Person("Charlie", 22),
	                new Person("Diana", 28)
	        );

	        // Set the data to the table
	        tableView.setItems(data);

	        // Create the scene and set it to the stage
	        Scene scene = new Scene(tableView, 300, 200);
	        return scene;
	    }
		 public static void main(String[] args) {
		        launch(args);
		 }
		@Override
		public void start(Stage arg0) throws Exception {
			// TODO Auto-generated method stub
			
		}
}

