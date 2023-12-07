/**
 * The Scene2 class represents the second scene of a JavaFX application.
 * It lets the user choose courses, input their current credit counts, and proceed to the next stage.
 *
 *
 * @author Ibukunoluwa Folajimi, Davud Azizov, Liban Mohamed
 *
 */

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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

import java.util.*;

public class Scene2 {

	// Declaring attributes
	private static final String CONFIRM_ENTRIES = "Know that you won't be able to return to this page. If you are ready to move on, click 'OK', otherwise, click 'cancel' and look over your entries.";
	private static TextField genElectiveCredits;
	private static TextField humanElectiveCredits;
	private static TextField sciElectiveCredits;
	private static TextField majorElectiveCredits;
	private static CoursePrerequisites coursePrerequisites;

	public static Scene createScene2(Stage stage, Student student, MenuBar menuBar) {

		// Create two VBox layouts for the left and right sections
		VBox layout = new VBox(6);
		VBox layout2 = new VBox(6);

		// Create a Text component with a message for the user
		Text sceneTitle = new Text("Now, kindly choose the courses you've finished and input the current credit count for each subject.");
		sceneTitle.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));

		// Create ArrayLists to store selected and unselected classes
		ArrayList<String> selectedClasses = new ArrayList<>();
		ArrayList<String> unselectedClasses = new ArrayList<>();

		// Create a Scanner for reading course information from files
		Scanner console = createScanner(student);

		layout2.getChildren().add(sceneTitle);
		// Create a GridPane for credit input fields and add it to the second layout
		createCheckBoxes(console, selectedClasses, unselectedClasses, layout2);
		// Create a GridPane for credit input fields and add it to the second layout
		layout2.getChildren().add(getGridPane());

		// Create the "Next" button with its functionality
		Button nextButton = getButton(stage, student, unselectedClasses, selectedClasses, menuBar);

		layout2.getChildren().add(nextButton);
		layout2.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(menuBar, layout2);

		return new Scene(layout, 1200, 880);
	}

	private static Scanner createScanner(Student student) {
		File file = null;
		try {
			// Create a File object based on the student's major
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

		// Initialize a Scanner to read course information from the file
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

		// Read course names from the file and create checkboxes for each
		while (console.hasNextLine()) {
			String className = console.nextLine();
			CheckBox checkBox = new CheckBox(className);
			checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {

				// Update selected and unselected classes based on checkbox changes
				if (newValue) {
					selectedClasses.add(className);
					unselectedClasses.remove(className);
				} else {
					selectedClasses.remove(className);
					unselectedClasses.add(className);
				}
			});
			// Add the checkbox to the layout and to the unselected classes list
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

		// Define the action to be taken when the button is clicked
		nextButton.setOnAction(e -> {
			if (!validatePrerequisites(selectedClasses)) {
				getMissingPrerequisitesMessage();
			} else {
				// Validate and set elective credits for the student
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

					// Display confirmation dialog
					Alert confirm = new Alert(AlertType.CONFIRMATION);
					confirm.setTitle("Confirmation");
					confirm.setHeaderText("Are you sure you wish to move on?");
					confirm.setContentText(CONFIRM_ENTRIES);

					Optional<ButtonType> result = confirm.showAndWait();

					// Proceed to the next stage if the user clicks OK
					if (result.isPresent() && result.get() == ButtonType.OK) {
						generateSchedule(student, unselectedClasses);
						stage.setScene(Scene3.createScene3(stage, student, menuBar));
					}
				} else {
					// Display an error dialog if elective credits are not valid
					Alert error = new Alert(AlertType.ERROR);
					error.setTitle("Missing classes");
					error.setHeaderText("Please choose your classes");
					error.setContentText(CONFIRM_ENTRIES);
				}
			}

		});
		return nextButton;
	}

	/**
	 * Creates and returns a GridPane layout for inputting current elective credits.
	 */
	private static GridPane getGridPane() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Add labels and text fields for different types of elective credits
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

	/**
	 * Utility method for generating a student schedule
	 * and saving it to a CSV file.
	 *
	 */
	public static void generateSchedule(Student student, ArrayList<String> requiredClasses) {
		Collection<String> semester = new ArrayList<String>();

		// Define the order of semesters for scheduling
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

		// Map student's year to an integer value
		int year = switch (student.getStudentYear()) {
			case "Freshman" -> 1;
			case "Sophomore" -> 2;
			case "Junior" -> 3;
			default -> 4;
		};

		// Set the CSV file path and prepare to write to the file
		String csvFile = "src/application/StudentSchedule.csv";
		File file = new File(csvFile);
		file.setWritable(true);
		try (FileWriter writer = new FileWriter(csvFile)) {
			writer.append("Year,Semester,Course\n");
			// Write user's information at the top of the file
			writer.append(String.format("Name: %s%n", student.getStudentName()));
			writer.append(String.format("Email: %s%n", student.getEmail()));
			writer.append(String.format("Major: %s%n", student.getMajor()));

			int classIndex = 0;
            while (year < 5) {
                Iterator<String> s = semester.iterator();

				while (s.hasNext()) {
                    String currentSemester = s.next();
                    for (int j = 0; j < 3; j++) {
                        if (classIndex < requiredClasses.size()) {
							// Write course information for each semester
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
							// Write default lines for remaining semesters if requiredClasses is 0
							if (year == 1 && currentSemester.equals("Summer")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "No Classes"));
                            } else if (year == 2 && currentSemester.equals("Summer")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "COOP3000 Pre Co-op Work Term (Optional)"));
                            } else if (year == 3 && currentSemester.equals("Spring")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "COOP3500 Co-op Education I(Required)"));
                            } else if (year == 4 && currentSemester.equals("Fall")) {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "COOP4500 Co-op Education II(Required)"));
                            } else {
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "Remaining Course"));
                            }
                        }
                    }
                }
                year++;
            }

			// Write remaining elective credits information
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

	/**
	 * Validates the input credits against a specified limit.
	 *
	 */
	public static boolean validateCredits(String credits, int limit) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Please try again");
		// Check if the input credits are empty
		if (credits.isEmpty()) {
			alert.setHeaderText("No entry was made");
			alert.setContentText("We ask that you enter the number of credits you have for all subjects");
			alert.showAndWait();
			return false;
		}
		try {
			int credit = Integer.parseInt(credits);
			// Check if the input credits are empty
			if (credit < 0 || credit > limit) {
				alert.setHeaderText("An invalid entry was made");
				alert.setContentText("We ask that you enter integers for the credits between 0 and " + limit + " (Inclusive)");
				alert.showAndWait();
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			// Check if the input credits are empty
			alert.setHeaderText("An invalid entry was made");
			alert.setContentText("We ask that you enter integers for the credits");
			alert.showAndWait();
			return false;
		}
	}

	/**
	 * Validates prerequisites for selected classes using a CoursePrerequisites instance.
	 */
	private static boolean validatePrerequisites(ArrayList<String> selectedClasses) {
		coursePrerequisites = new CoursePrerequisites(selectedClasses);

		return coursePrerequisites.checkPrerequisites();
	}

	/**
	 * Displays a message about missing prerequisites, including course names and their prerequisites.
	 */
	private static void getMissingPrerequisitesMessage() {
		Map<String, CoursePrerequisites.Prerequisite> adjacencyList = coursePrerequisites.getAdjacencyList();
		StringBuilder stringBuilder = new StringBuilder();



		for (String courseName : adjacencyList.keySet()) {
			if (coursePrerequisites.getMissingCourses().contains(courseName)) {
				CoursePrerequisites.Prerequisite prerequisite = adjacencyList.get(courseName);

				stringBuilder
						.append("Course name: ").append(courseName)
						.append(", Prerequisites: ");

				if (prerequisite.courses.isEmpty()) {
					stringBuilder.append("None");
				} else {
					for (int i = 0; i < prerequisite.courses.size(); i++) {
						stringBuilder.append(prerequisite.courses.get(i));
						// Append prerequisites or indicate if none
						if (i < prerequisite.courses.size() - 1) {
							if ("AND".equals(prerequisite.type)) {
								stringBuilder.append(" and ");
							} else if ("OR".equals(prerequisite.type)) {
								stringBuilder.append(" or ");
							} else {
								stringBuilder.append(", ");
							}
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
		for (String s: coursePrerequisites.getMissingCourses()) {
			System.out.println(s);
		}
		confirm.setContentText(stringBuilder.toString());
		confirm.showAndWait();

	}
}


