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
	    public static Scene createScene3_2(Stage stage, Student student, ArrayList<String> requiredClasses) {
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
//import javafx.application.Application;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.Scene;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class CurriculumMapGenerator extends Application {
//
//    private TableView<Curriculum> tableView = new TableView<>();
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) {
//        VBox root = new VBox();
//        Scene scene = new Scene(root, 800, 600);
//
//        TableColumn<Curriculum, String> yearColumn = new TableColumn<>("Year");
//        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
//
//        TableColumn<Curriculum, String> fallColumn = new TableColumn<>("Fall Classes");
//        fallColumn.setCellValueFactory(cellData -> cellData.getValue().fallClassesProperty());
//
//        TableColumn<Curriculum, String> springColumn = new TableColumn<>("Spring Classes");
//        springColumn.setCellValueFactory(cellData -> cellData.getValue().springClassesProperty());
//
//        TableColumn<Curriculum, String> summerColumn = new TableColumn<>("Summer Classes");
//        summerColumn.setCellValueFactory(cellData -> cellData.getValue().summerClassesProperty());
//
//        tableView.getColumns().addAll(yearColumn, fallColumn, springColumn, summerColumn);
//        root.getChildren().add(tableView);
//
//        File file = new File("remaining_classes.txt"); // Change this to your file name
//        HashMap<String, String> remainingClasses = readRemainingClassesFromFile(file);
//
//        generateCurriculumMap(remainingClasses);
//
//        primaryStage.setTitle("Curriculum Map Generator");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    private HashMap<String, String> readRemainingClassesFromFile(File file) {
//        HashMap<String, String> remainingClasses = new HashMap<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(":");
//                if (parts.length == 2) {
//                    remainingClasses.put(parts[0].trim(), parts[1].trim());
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return remainingClasses;
//    }
//
//    private void generateCurriculumMap(HashMap<String, String> remainingClasses) {
//        ObservableList<Curriculum> curriculumList = FXCollections.observableArrayList();
//        remainingClasses.forEach((year, classes) -> {
//            Curriculum curriculum = distributeClasses(year, classes);
//            curriculumList.add(curriculum);
//        });
//        tableView.setItems(curriculumList);
//    }
//
//    private Curriculum distributeClasses(String year, String classes) {
//        String[] classList = classes.split(",");
//        int numClasses = classList.length;
//
//        // Calculate number of classes per semester
//        int classesPerSemester = numClasses / 3; // Assuming equal distribution, you may adjust logic as needed
//
//        // Distribute classes among semesters
//        String[] fallClasses = new String[classesPerSemester];
//        String[] springClasses = new String[classesPerSemester];
//        String[] summerClasses = new String[numClasses - 2 * classesPerSemester];
//
//        int fallIndex = 0, springIndex = 0, summerIndex = 0;
//
//        for (int i = 0; i < numClasses; i++) {
//            if (i < classesPerSemester)
//                fallClasses[fallIndex++] = classList[i].trim();
//            else if (i < 2 * classesPerSemester)
//                springClasses[springIndex++] = classList[i].trim();
//            else
//                summerClasses[summerIndex++] = classList[i].trim();
//        }
//
//        return new Curriculum(year, arrayToString(fallClasses), arrayToString(springClasses), arrayToString(summerClasses));
//    }
//
//    private String arrayToString(String[] array) {
//        StringBuilder result = new StringBuilder();
//        for (String s : array) {
//            if (s != null) {
//                if (result.length() > 0) {
//                    result.append(", ");
//                }
//                result.append(s);
//            }
//        }
//        return result.toString();
//    }
//
//    public static class Curriculum {
//        private final String year;
//        private final String fallClasses;
//        private final String springClasses;
//        private final String summerClasses;
//
//        public Curriculum(String year, String fallClasses, String springClasses, String summerClasses) {
//            this.year = year;
//            this.fallClasses = fallClasses;
//            this.springClasses = springClasses;
//            this.summerClasses = summerClasses;
//        }
//
//        public String getYear() {
//            return year;
//        }
//
//        public String getFallClasses() {
//            return fallClasses;
//        }
//
//        public String getSpringClasses() {
//            return springClasses;
//        }
//
//        public String getSummerClasses() {
//            return summerClasses;
//        }
//
//        public StringProperty yearProperty() {
//            return new SimpleStringProperty(this.year);
//        }
//
//        public StringProperty fallClassesProperty() {
//            return new SimpleStringProperty(this.fallClasses);
//        }
//
//        public StringProperty springClassesProperty() {
//            return new SimpleStringProperty(this.springClasses);
//        }
//
//        public StringProperty summerClassesProperty() {
//            return new SimpleStringProperty(this.summerClasses);
//        }
//    }
//}
//
