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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Scene3_2 {

		public static Scene createScene3_2(Stage stage, StudentInfo student, ArrayList<String> requiredClasses) {
	        
	    	VBox root = new VBox();
	    	Collection<String> semester = new ArrayList<String>();
	    	semester.add("Fall");
	    	semester.add("Spring");
	    	semester.add("Summer");
	    	int year;
	    	switch(student.getStudentYear()) {
	    	  case "Freshman":
	    		  year = 1;
	    		  break;
	    	  case "Sophmore":
	    	    year = 2;
	    	    break;
	    	  case "Junior":
	    	    year = 3;
	    	    break;
	    	  default:
	    	    year = 4;
	    	    break;
	    	}
	        String csvFile = "src/studentSchedule.csv";
	        FileWriter writer;
			try {
				writer = new FileWriter(csvFile);	       
				writer.append("Year,Semester,Course\n");
				writer.append("1,fall,math");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//	        try (FileWriter writer = new FileWriter(csvFile)) {
//	        	writer.append("Year,Semester,Course\n");
//
//	            int classIndex = 0;
//	            for (int i = 0; i < 3; i++) {
//	                Iterator<String> s = semester.iterator();
//	                while (s.hasNext() && classIndex < requiredClasses.size()) {
//	                    String currentSemester = s.next();
//	                    for (int j = 0; j < 3 && classIndex < requiredClasses.size(); j++) {
//	                        writer.append(String.format("%d,%s,%s%n", year, currentSemester, requiredClasses.get(classIndex)));
//	                        classIndex++;
//	                    }
//	                }
//	                if (year <= 4) {
//	                    year++;
//	                }
//	            }
//	            writer.close();
//	            System.out.println("CSV file has been created successfully!");
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
	        Text sceneTitle = new Text("Thank you for your patience. Your College Road Map has been sent to your email.");
	        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        root.getChildren().add(sceneTitle);
	        Scene scene = new Scene(root, 800, 600);
	        // Set the data to the table
	        return scene;
	    }
}
//	            writer.append("1,Introduction to Computer Science,\n");
//	            writer.append("2,Programming Fundamentals,1\n");
//	            writer.append("3,Data Structures and Algorithms,2\n");
//	            writer.append("4,Database Management Systems,2\n");
//	            writer.append("5,Computer Networks,2\n");
//	            writer.append("6,Software Engineering,2\n");
//	            writer.append("7,Operating Systems,3\n");
//	            writer.append("8,Web Development,2\n");
//	            writer.append("9,Computer Security,3\n");
//	            writer.append("10,Artificial Intelligence,3\n");
//	            writer.append("11,Mobile App Development,2\n");
//	            writer.append("12,Software Testing,2\n");