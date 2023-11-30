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

public class Scene3 {

	public static Scene createScene3(Stage stage, StudentInfo student, ArrayList<String> requiredClasses) {
		VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        Text sceneTitle = new Text("Thank you for your entries. Here is the recommend cirliculum map for you!");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        layout.getChildren().add(sceneTitle);
        
        ObservableList<String> classes = FXCollections.observableList(requiredClasses);
        TableView table = new TableView();
        table.setItems(classes);
        TableColumn<StudentInfo, String> firstYear = new TableColumn<>("1st Year");
        TableColumn<StudentInfo, String> secondYear = new TableColumn<>("2nd Year");
        TableColumn<StudentInfo, String> thirdYear = new TableColumn<>("3rd Year");
        TableColumn<StudentInfo, String> fourthYear = new TableColumn<>("4th Year");
        TableColumn<StudentInfo, String> fall = new TableColumn<>("Fall");
        TableColumn<StudentInfo, String> spring = new TableColumn<>("Spring");
        TableColumn<StudentInfo, String> summer = new TableColumn<>("Summer");
        firstYear.getColumns().addAll(fall, spring, summer);
        secondYear.getColumns().addAll(fall, spring, summer);
        thirdYear.getColumns().addAll(fall, spring, summer);
        fourthYear.getColumns().addAll(fall, spring, summer);
        fall.setCellValueFactory(new PropertyValueFactory<>("fall"));
        spring.setCellValueFactory(new PropertyValueFactory<>("spring"));
        summer.setCellValueFactory(new PropertyValueFactory<>("summer"));
        table.getItems().add(student.toString());
        table.getColumns().addAll(firstYear,secondYear,thirdYear,fourthYear);

//        columnOne.setCellValueFactory(c -> new SimpleStringProperty(requiredClasses.get(0)));
//        columnTwo.setCellValueFactory(c -> new SimpleStringProperty("456"));
        
       // table.getColumns().add(firstYear);
        //table.getItems().addAll(firstYear,secondYear,thirdYear,fourthYear);
        layout.getChildren().add(table);
        
        Button finishButton = new Button();
        finishButton.setText("Finish");
        layout.getChildren().add(finishButton);
        Scene scene = new Scene(layout, 700, 680);
        return scene;
	}

}
