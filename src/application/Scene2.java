package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

import java.util.*;

public class Scene2 {
	private static final String CONFIRM_ENTRIES = "Know that you won't be able to return to this page. If you are ready to move on, click 'OK', otherwise, click 'cancel' and look over your entries.";
	private static TextField genElectiveCredits;
	private static TextField humanElectiveCredits;
	private static TextField sciElectiveCredits;
	private static TextField majorElectiveCredits;
	private static CoursePrerequisites coursePrerequisites;

	public static Scene createScene2(Stage stage, Student student, MenuBar menuBar) {
		SplitPane splitPane = new SplitPane();
		VBox layout = new VBox(6);
		VBox layout2 = new VBox(6);
		Text sceneTitle = new Text("Now, kindly choose the courses you've finished and input the current credit count for each subject.");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		layout.getChildren().addAll(menuBar,sceneTitle);
		ArrayList<String> selectedClasses = new ArrayList<>();
		ArrayList<String> unselectedClasses = new ArrayList<>();
		Scanner console = createScanner(student);

		createCheckBoxes(console, selectedClasses, unselectedClasses, layout);

		GridPane grid = getGridPane();
		layout2.getChildren().add(grid);

		Button nextButton = getButton(stage, student, unselectedClasses, selectedClasses, menuBar);
		layout2.getChildren().add(nextButton);
		splitPane.getItems().addAll(layout,layout2);
		return new Scene(splitPane, 1200, 780);
	}

	private static Scanner createScanner(Student student) {
		File file = null;
		try {
			file = switch (student.getMajor()) {
				case "Computer Science" -> new File("src/majors/CompSci.txt");
				case "Information Technology" -> new File("src/majors/InformationTech.txt");
				case "Computer Networking" -> new File("src/majors/CompNetworking.txt");
				case "Data Science" -> new File("src/majors/DataScience.txt");
				case "CyberSecurity" -> new File("src/majors/CyberSecurity.txt");
				default -> new File("src/majors/Math.txt");
			};
		} catch (Exception e) {
			System.out.println("Something went wrong: " + e.getMessage());
		}
		Scanner console = null;
		try {
			assert file != null;
			console = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return console;
	}

	private static void createCheckBoxes(
			Scanner console,
			ArrayList<String> selectedClasses,
			ArrayList<String> unselectedClasses,
			VBox layout
	) {
		while (console.hasNextLine()) {
			String className = console.nextLine();
			CheckBox checkBox = new CheckBox(className);
			checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue) {
					selectedClasses.add(className);
					unselectedClasses.remove(className);
				} else {
					selectedClasses.remove(className);
					unselectedClasses.add(className);
				}
			});
			layout.getChildren().add(checkBox);
			unselectedClasses.add(className);
		}
		console.close();
	}

	private static Button getButton(
			Stage stage,
			Student student,
			ArrayList<String> unselectedClasses,
			ArrayList<String> selectedClasses,
			MenuBar menuBar
	) {
		Button nextButton = new Button();
		nextButton.setText("Next");

		nextButton.setOnAction(e -> {
			if (!validatePrerequisites(selectedClasses)) {
				getMissingPrerequisitesMessage();
			} else {
				if (
						validateCredits(genElectiveCredits.getText(), 20) &&
								validateCredits(humanElectiveCredits.getText(), 20) &&
								validateCredits(sciElectiveCredits.getText(), 20) &&
								validateCredits(majorElectiveCredits.getText(), 20)
				) {
					student.setGeneralElective(Integer.parseInt(genElectiveCredits.getText()));
					student.setHumanityElective(Integer.parseInt(humanElectiveCredits.getText()));
					student.setMajorElective(Integer.parseInt(sciElectiveCredits.getText()));
					student.setScienceElective(Integer.parseInt(majorElectiveCredits.getText()));

					Alert confirm = new Alert(AlertType.CONFIRMATION);
					confirm.setTitle("Confirmation");
					confirm.setHeaderText("Are you sure you wish to move on?");
					confirm.setContentText(CONFIRM_ENTRIES);

					Optional<ButtonType> result = confirm.showAndWait();

					if (result.isPresent() && result.get() == ButtonType.OK) {
						generateSchedule(student, unselectedClasses);
						stage.setScene(Scene3.createScene3(stage, student, unselectedClasses, menuBar));
					}
				} else {
					Alert error = new Alert(AlertType.ERROR);
					error.setTitle("Missing classes");
					error.setHeaderText("Please choose your classes");
					error.setContentText(CONFIRM_ENTRIES);
				}
			}

		});
		return nextButton;
	}

	private static GridPane getGridPane() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Label generalElective = new Label("Current General Elective Credits:");
		grid.add(generalElective, 0, 1);
		genElectiveCredits = new TextField();
		grid.add(genElectiveCredits, 1, 1);

		Label humanityElective = new Label("Current Humanities/Social Science Elective Credits:");
		grid.add(humanityElective, 0, 2);
		humanElectiveCredits = new TextField();
		grid.add(humanElectiveCredits, 1, 2);

		Label scienceElective = new Label("Current Science Elective Credits:");
		grid.add(scienceElective, 0, 3);
		sciElectiveCredits = new TextField();
		grid.add(sciElectiveCredits, 1, 3);

		Label majorElective = new Label("Current Major Elective Credits:");
		grid.add(majorElective, 0, 4);
		majorElectiveCredits = new TextField();
		grid.add(majorElectiveCredits, 1, 4);
		return grid;
	}

	public static void generateSchedule(Student student, ArrayList<String> requiredClasses) {
		Collection<String> semester = new ArrayList<String>();

		switch (student.getSemester()) {
			case "Summer":
				semester.add("Summer");
				semester.add("Fall");
				semester.add("Spring");
				break;
			case "Spring":
				semester.add("Spring");
				semester.add("Summer");
				semester.add("Fall");
				break;
			default:
				semester.add("Fall");
				semester.add("Spring");
				semester.add("Summer");
				break;
		}

		int year = switch (student.getStudentYear()) {
			case "Freshman" -> 1;
			case "Sophomore" -> 2;
			case "Junior" -> 3;
			default -> 4;
		};

		String csvFile = "src/application/StudentSchedule.csv";
		File file = new File(csvFile);
		file.setWritable(true);
		try (FileWriter writer = new FileWriter(csvFile)) {
			writer.append("Year,Semester,Course\n");

			int classIndex = 0;
            while (year < 5) {
                Iterator<String> s = semester.iterator();
                while (s.hasNext()) {
                    String currentSemester = s.next();
                    for (int j = 0; j < 3; j++) {
                        if (classIndex < requiredClasses.size()) {
                            if (year == 1 && currentSemester.equals("Summer")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "No Classes"));
                            } else if (year == 2 && currentSemester.equals("Summer")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "COOP3000 Pre Co-op Work Term (Optional)"));
                            } else if (year == 3 && currentSemester.equals("Spring")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "COOP3500 Co-op Education I(Required)"));
                            } else if (year == 4 && currentSemester.equals("Fall")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "COOP4500 Co-op Education II(Required)"));
                            } else {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, requiredClasses.get(classIndex)));
                                classIndex++;
                            }
                        } else {
                            // Write default lines for remaining semesters if requiredClasses is exhausted
                            if (year == 1 && currentSemester.equals("Summer")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "No Classes"));
                            } else if (year == 2 && currentSemester.equals("Summer")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "COOP3000 Pre Co-op Work Term (Optional)"));
                            } else if (year == 3 && currentSemester.equals("Spring")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "COOP3500 Co-op Education I(Required)"));
                            } else if (year == 4 && currentSemester.equals("Fall")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "COOP4500 Co-op Education II(Required)"));
                            } else {
                                // You might want to add some default courses for unspecified cases
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "Remaining Course"));
                            }
                        }
                    }
                }
                year++;
            }
            if(classIndex<requiredClasses.size()-1) {
            	ArrayList<String> unlistedClasses = new ArrayList<String>();
            	while(classIndex<requiredClasses.size()) {
            		unlistedClasses.add(requiredClasses.get(classIndex));
            		classIndex++;
            	}
        		writer.append(String.format("Classes not listed:,%s%n",unlistedClasses.toString()));
            }
			writer.append(String.format("Remaining General Elective Credits:,%d%n", (student.getGeneralElective() <= 8) ? 8 - student.getGeneralElective() : 0));
			writer.append(String.format("Remaining Science Elective Credits:,%d%n", (student.getScienceElective() <= 8) ? 8 - student.getScienceElective() : 0));
			writer.append(String.format("Remaining Humanities/Social Science Elective Credits:,%d%n", (student.getHumanityElective() <= 20) ? 20 - student.getHumanityElective() : 0));
			writer.append(String.format("Remaining Major Credits:,%d%n", (student.getMajorElective() < 36) ? 36 - student.getMajorElective() : 0));
			writer.close();
			System.out.println("CSV file has been created successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean validateCredits(String credits, int limit) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Please try again");
		if (credits.isEmpty()) {
			alert.setHeaderText("No entry was made");
			alert.setContentText("We ask that you enter the number of credits you have for all subjects");
			alert.showAndWait();
			return false;
		}
		try {
			int credit = Integer.parseInt(credits);
			if (credit < 0 || credit > limit) {
				alert.setHeaderText("An invalid entry was made");
				alert.setContentText("We ask that you enter integers for the credits between 0 and " + limit + " (Inclusive)");
				alert.showAndWait();
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			alert.setHeaderText("An invalid entry was made");
			alert.setContentText("We ask that you enter integers for the credits");
			alert.showAndWait();
			return false;
		}
	}

	private static boolean validatePrerequisites(ArrayList<String> selectedClasses) {
		coursePrerequisites = new CoursePrerequisites(selectedClasses);

		return coursePrerequisites.checkPrerequisites();
	}

	private static void getMissingPrerequisitesMessage() {
		Map<String, CoursePrerequisites.Prerequisite> adjacencyList = coursePrerequisites.getAdjacencyList();
		StringBuilder stringBuilder = new StringBuilder();



		for (String courseName : adjacencyList.keySet()) {
			if (coursePrerequisites.getClasses().contains(courseName)) {
				CoursePrerequisites.Prerequisite prerequisite = adjacencyList.get(courseName);

				stringBuilder
						.append("Course name: ").append(courseName)
						.append(", Prerequisites: ");

				if (prerequisite.courses.isEmpty()) {
					stringBuilder.append("None");
				} else {
					for (int i = 0; i < prerequisite.courses.size(); i++) {
						stringBuilder.append(prerequisite.courses.get(i));
						if (i < prerequisite.courses.size() - 1) {
							stringBuilder.append(", ");
						}
					}
				}

				stringBuilder.append("\n");
			}
		}

		String result = stringBuilder.toString();
		System.out.println(result);
		
		Alert confirm = new Alert(AlertType.ERROR);
		confirm.setTitle("Missing Prerequisites");
		confirm.setHeaderText("Some prerequisites are missing.");
		System.out.println("Missing prereq: " + stringBuilder.toString());
		confirm.setContentText(stringBuilder.toString());
		confirm.showAndWait();

	}
}
    

