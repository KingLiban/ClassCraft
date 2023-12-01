package application;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class Scene3{
	 private static final TableView<Person> table = new TableView<>();
	 private static final ObservableList<Person> data = FXCollections.observableArrayList(new Person("A", "B"),
	            new Person("D", "E"));
	    public static class Person {

	        private final SimpleStringProperty firstName;
	        private final SimpleStringProperty lastName;

	        private Person(String fName, String lName) {
	            this.firstName = new SimpleStringProperty(fName);
	            this.lastName = new SimpleStringProperty(lName);
	        }

	        public String getFirstName() {
	            return firstName.get();
	        }

	        public void setFirstName(String fName) {
	            firstName.set(fName);
	        }

	        public String getLastName() {
	            return lastName.get();
	        }

	        public void setLastName(String fName) {
	            lastName.set(fName);
	        }
	    }
	    public static Scene createScene3(Stage stage, Student student, ArrayList<String> requiredClasses) {
	    	Scene scene = new Scene(new Group());
	        stage.setWidth(450);
	        stage.setHeight(550);

	        TableColumn firstNameCol = new TableColumn("First Name");
	        firstNameCol.setMinWidth(100);
	        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

	        TableColumn lastNameCol = new TableColumn("Last Name");
	        lastNameCol.setMinWidth(100);
	        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

	        table.setItems(data);
	        table.getColumns().addAll(firstNameCol, lastNameCol);

	        firstNameCol.setSortType(TableColumn.SortType.DESCENDING);
	        lastNameCol.setSortable(false);

	        final VBox vbox = new VBox();
	        vbox.setSpacing(5);
	        vbox.setPadding(new Insets(10, 0, 0, 10));
	        vbox.getChildren().addAll(table);

	        ((Group) scene.getRoot()).getChildren().addAll(vbox);

		    return scene;
//		VBox layout = new VBox(10);
//		
//        layout.setAlignment(Pos.CENTER);
//        Text sceneTitle = new Text("Thank you for your entries. Here is the recommend cirliculum map for you!");
//        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        layout.getChildren().add(sceneTitle);
//        
//        ObservableList<String> classes = FXCollections.observableList(requiredClasses);
//        TableView table = new TableView();
//        table.setItems(classes);
//        TableColumn<StudentInfo, String> firstYear = new TableColumn<>("1st Year");
//        TableColumn<StudentInfo, String> secondYear = new TableColumn<>("2nd Year");
//        TableColumn<StudentInfo, String> thirdYear = new TableColumn<>("3rd Year");
//        TableColumn<StudentInfo, String> fourthYear = new TableColumn<>("4th Year");
//        TableColumn<StudentInfo, String> fall = new TableColumn<>("Fall");
//        TableColumn<StudentInfo, String> spring = new TableColumn<>("Spring");
//        TableColumn<StudentInfo, String> summer = new TableColumn<>("Summer");
//        firstYear.getColumns().addAll(fall, spring, summer);
//        secondYear.getColumns().addAll(fall, spring, summer);
//        thirdYear.getColumns().addAll(fall, spring, summer);
//        fourthYear.getColumns().addAll(fall, spring, summer);
//        fall.setCellValueFactory(new PropertyValueFactory<>("fall"));
//        spring.setCellValueFactory(new PropertyValueFactory<>("spring"));
//        summer.setCellValueFactory(new PropertyValueFactory<>("summer"));
//        table.getItems().add(student.toString());
//        table.getColumns().addAll(firstYear,secondYear,thirdYear,fourthYear);
//
////        columnOne.setCellValueFactory(c -> new SimpleStringProperty(requiredClasses.get(0)));
////        columnTwo.setCellValueFactory(c -> new SimpleStringProperty("456"));
//        
//       // table.getColumns().add(firstYear);
//        //table.getItems().addAll(firstYear,secondYear,thirdYear,fourthYear);
//        layout.getChildren().add(table);
//        
//        Button finishButton = new Button();
//        finishButton.setText("Finish");
//        layout.getChildren().add(finishButton);
//        Scene scene = new Scene(layout, 700, 680);
//        System.out.printf("asdf");
//        return scene;
	}

//	@Override
//	public void start(Stage stage) {
//		 Scene scene = new Scene(new Group());
//	        stage.setWidth(450);
//	        stage.setHeight(550);
//
//	        TableColumn firstNameCol = new TableColumn("First Name");
//	        firstNameCol.setMinWidth(100);
//	        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//
//	        TableColumn lastNameCol = new TableColumn("Last Name");
//	        lastNameCol.setMinWidth(100);
//	        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//
//	        table.setItems(data);
//	        table.getColumns().addAll(firstNameCol, lastNameCol);
//
//	        firstNameCol.setSortType(TableColumn.SortType.DESCENDING);
//	        lastNameCol.setSortable(false);
//
//	        final VBox vbox = new VBox();
//	        vbox.setSpacing(5);
//	        vbox.setPadding(new Insets(10, 0, 0, 10));
//	        vbox.getChildren().addAll(table);
//
//	        ((Group) scene.getRoot()).getChildren().addAll(vbox);
//
//	        stage.setScene(scene);
//	        stage.show();
//	}
//	 public static void main(String[] args) {
//	        launch(args);
//	 }

}
