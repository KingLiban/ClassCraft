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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Scene3 {

	public static Scene createScene3(Stage stage, ArrayList<String> requiredClasses) {
		VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        Text sceneTitle = new Text("Thank you for your entries. Here is the recommend cirliculum map for you!");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        layout.getChildren().add(sceneTitle);
        ObservableList<String> classes = FXCollections.observableList(requiredClasses);
        TableView<String> table = new TableView<String>();
        table.setItems(classes);
        TableColumn columnOne = new TableColumn("Fall");
        TableColumn columnTwo = new TableColumn("Spring");
        TableColumn columnThree = new TableColumn("Summer");
        columnOne.setCellValueFactory(c -> new SimpleStringProperty(new String("123")));
        columnTwo.setCellValueFactory(c -> new SimpleStringProperty(new String("456")));

        table.getItems().addAll("Column one's data", "Column two's data");
        //table.getColumns().addAll(columnOne, columnTwo);
        layout.getChildren().add(table);
        
        Button finishButton = new Button();
        finishButton.setText("Finish");
        layout.getChildren().add(finishButton);
        Scene scene = new Scene(layout, 700, 680);
        return scene;
	}

}
