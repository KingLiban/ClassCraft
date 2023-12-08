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

	// Declaring confirmation message and the text field the user will enter 
	//information
	private static final String CONFIRM_ENTRIES = "Know that you won't be able to return to this page. If you are ready to move on, click 'OK', otherwise, click 'cancel' and look over your entries.";
	private static TextField genElectiveCredits;
	private static TextField humanElectiveCredits;
	private static TextField sciElectiveCredits;
	private static TextField majorElectiveCredits;
	//Declare Object containing information about the prerequisites for a course
	private static CoursePrerequisites coursePrerequisites;
	/**
	 * Creates and returns Scene 2 for the application where the user can select courses
	 * they have completed and enter the credits for certain electives
	 *
	 * @param stage    The primary stage of the application
	 * @param student  The student object containing user information
	 * @param menuBar  The menu bar to be displayed in the scene
	 * @return         The created Scene 3 for the application
	 */
	public static Scene createScene2(Stage stage, Student student, MenuBar menuBar) {

		// Create two VBox layouts for the left and right sections
		VBox layout = new VBox(3);
		VBox layout2 = new VBox(3);

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
	/**
	 * Creates a Scanner object to read course information from a file based on the student's major.
	 *
	 * @param student The Student object containing major information
	 * @return Scanner object initialized to read course information from the respective file
	 */
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
			// Ensure the file object is not null before creating a Scanner
			assert file != null;
			console = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return console;// Return the initialized Scanner object
	}
	/**
	 * Reads course names from a Scanner and creates checkboxes for each course.
	 * Allows users to select/deselect courses, updating corresponding lists and UI layout.
	 *
	 * @param console          Scanner object used to read course names from a file
	 * @param selectedClasses  List to store selected classes
	 * @param unselectedClasses List to store unselected classes
	 * @param layout           VBox layout to which checkboxes are added
	 */
	private static void createCheckBoxes(
			Scanner console,
			ArrayList<String> selectedClasses,
			ArrayList<String> unselectedClasses,
			VBox layout
	) {

		// Read course names from the file and create checkboxes for each
		while (console.hasNextLine()) {
			String className = console.nextLine();
			CheckBox checkBox = new CheckBox(className);// Create a checkbox for the course name
			checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {// Add a listener to the checkbox for selection changes
				// Update selected and unselected classes based on checkbox changes
				if (newValue) {
					selectedClasses.add(className);// Add to selected classes if checkbox is selected
					unselectedClasses.remove(className);// Remove from unselected classes
				} else {
					selectedClasses.remove(className);// Remove from selected classes if checkbox is deselected
					unselectedClasses.add(className);// Add to unselected classes
				}
			});
			// Add the checkbox to the layout and to the unselected classes list
			layout.getChildren().add(checkBox);
			unselectedClasses.add(className);
		}
		console.close();// Close the scanner after reading
	}
	/**
	 * Creates a "Next" button with action handling for validation and navigation to the next stage.
	 *
	 * @param stage              The current stage of the application
	 * @param student            The Student object containing student information
	 * @param unselectedClasses  List of unselected classes
	 * @param selectedClasses    List of selected classes
	 * @param menuBar            The MenuBar in the application
	 * @return Next Button with event handling for validation and scene navigation
	 */
	private static Button getButton(
			Stage stage,
			Student student,
			ArrayList<String> unselectedClasses,
			ArrayList<String> selectedClasses,
			MenuBar menuBar
	) {
		//create a 'Next' button
		Button nextButton = new Button();
		nextButton.setText("Next");

		// Define the action to be taken when the 'Next' button is clicked
		nextButton.setOnAction(e -> {
			if (!validatePrerequisites(selectedClasses)) {
				getMissingPrerequisitesMessage();
			} else {
				// Validate elective credits for the student
				if (
						validateCredits(genElectiveCredits.getText(), 20) &&
								validateCredits(humanElectiveCredits.getText(), 20) &&
								validateCredits(sciElectiveCredits.getText(), 20) &&
								validateCredits(majorElectiveCredits.getText(), 40)
				) {
					//set elective credits for the student
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

					// Proceed to the next scene if the user clicks OK
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
		return nextButton;// Return the created "Next" button with event handling
	}

	/**
	 * Creates a GridPane layout containing labels and text fields for elective credits.
	 *
	 * @return GridPane layout containing labels and text fields for elective credits
	 */
	private static GridPane getGridPane() {
		//Create and set components of GridPane
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
		return grid;// Return the created GridPane layout
	}

	/**
	 * Utility method for generating a student schedule
	 * and saving it to a CSV file.
	 * @param student         The Student object containing student information
	 * @param requiredClasses List of required classes
	 */
	public static void generateSchedule(Student student, ArrayList<String> requiredClasses) {
		Collection<String> semester = new ArrayList<String>();
		// Define the order of semesters for scheduling based on student information
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
			//write headers for the file
			writer.append("Year,Semester,Course\n");
			// Write user's information at the top of the file
			writer.append(String.format("Name: %s%n", student.getStudentName()));
			writer.append(String.format("Email: %s%n", student.getEmail()));
			writer.append(String.format("Major: %s%n", student.getMajor()));
			//int to keep track of the elements in requiredClasses
			int classIndex = 0;
            while (year < 5) {
            	//Iterator to iterate through each semester
                Iterator<String> s = semester.iterator();

				while (s.hasNext()) {
					//store the semester as a 'String'
                    String currentSemester = s.next();
                    for (int j = 0; j < 3; j++) {//controls how many classes for each semester
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
                                //classIndex Increment by 1 to move to the next required class
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
                                writer.append(String.format("%d,%s,%s%n", year, currentSemester, "Required Elective"));
                            }
                        }
                    }
                }
				//increment year by 1 to simulate the completion of a year in the school
                year++;
            }

			// Write remaining elective credits information
			writer.append(String.format("Remaining General Elective Credits:,%d%n", (student.getGeneralElective() <= 8) ? 8 - student.getGeneralElective() : 0));
			writer.append(String.format("Remaining Science Elective Credits:,%d%n", (student.getScienceElective() <= 8) ? 8 - student.getScienceElective() : 0));
			writer.append(String.format("Remaining Humanities/Social Science Elective Credits:,%d%n", (student.getHumanityElective() <= 20) ? 20 - student.getHumanityElective() : 0));
			writer.append(String.format("Remaining Major Credits:,%d%n", (student.getMajorElective() < 36) ? 36 - student.getMajorElective() : 0));
			writer.close();
			//message in console acknowledging the successful creation of the csv file
			System.out.println("CSV file has been created successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validates the input credits against a specified limit.
	 *
	 * @param credits The input credits as a String
	 * @param limit   The upper limit for credits
	 * @return True if the input credits are valid within the limit, otherwise false
	 */
	public static boolean validateCredits(String credits, int limit) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Please try again");
		// Check if the input credits are empty
		if (credits.isEmpty()) {
			//Displays related alert
			alert.setHeaderText("No entry was made");
			alert.setContentText("We ask that you enter the number of credits you have for all subjects");
			alert.showAndWait();
			return false;
		}
		try {
			int credit = Integer.parseInt(credits);
			// Check if the input credits are beyond a set bound
			if (credit < 0 || credit > limit) {
				//Displays related alert
				alert.setHeaderText("An invalid entry was made");
				alert.setContentText("We ask that you enter integers for the credits between 0 and " + limit + " (Inclusive)");
				alert.showAndWait();
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			//Display an alert for when integers are not provided
			alert.setHeaderText("An invalid entry was made");
			alert.setContentText("We ask that you enter integers for the credits");
			alert.showAndWait();
			return false;
		}
	}

	/**
	 * Validates prerequisites for selected classes using a CoursePrerequisites instance.
	 *
	 * @param selectedClasses List of selected classes
	 * @return True if all prerequisites for selected classes are met, otherwise false
	 */	private static boolean validatePrerequisites(ArrayList<String> selectedClasses) {
		 // Create an instance of CoursePrerequisites with the selected classes
		 coursePrerequisites = new CoursePrerequisites(selectedClasses);
		return coursePrerequisites.checkPrerequisites();// Check if all prerequisites for selected classes are satisfied
	}

	 /**
	  * Generates and displays an alert for missing prerequisites.
	  */
	private static void getMissingPrerequisitesMessage() {
		// gets the adjacency list and missing courses from CoursePrerequisites
		Map<String, CoursePrerequisites.Prerequisite> adjacencyList = coursePrerequisites.getAdjacencyList();
		// Create a StringBuilder to build the message for when prerequisites are missing
		StringBuilder stringBuilder = new StringBuilder();


	    // Iterate through the courses in the adjacency list
		for (String courseName : adjacencyList.keySet()) {
			// Check if the current course is among the missing prerequisites
			if (coursePrerequisites.getMissingCourses().contains(courseName)) {
			    // Get the prerequisite details for the current course
				CoursePrerequisites.Prerequisite prerequisite = adjacencyList.get(courseName);
				// Build the message for the missing prerequisites
				stringBuilder
						.append("Course name: ").append(courseName)
						.append(", Prerequisites: ");
				// Append prerequisites or indicate if none exist
				if (prerequisite.courses.isEmpty()) {
					stringBuilder.append("None");
				} else {
					// Loop through the prerequisite courses
					for (int i = 0; i < prerequisite.courses.size(); i++) {
						stringBuilder.append(prerequisite.courses.get(i));
						// Check if there are more prerequisites to append
						if (i < prerequisite.courses.size() - 1) {
							// Check the type of prerequisite logic ('AND', 'OR', or default to comma)
							if ("AND".equals(prerequisite.type)) {
								stringBuilder.append(" and ");// change 'and' for 'AND' logic
							} else if ("OR".equals(prerequisite.type)) {
								stringBuilder.append(" or ");// change 'or' for 'OR' logic
							} else {
								stringBuilder.append(", ");// uses comma if logic isn't specified
							}
						}
					}
				}

				stringBuilder.append("\n");
			}
		}
	    // Convert the StringBuilder content to a string
		String result = stringBuilder.toString();
		System.out.println(result);// Print the missing prerequisites information to the console(for checking)
		// Create an alert to display missing prerequisites
		Alert confirm = new Alert(AlertType.ERROR);
		confirm.setTitle("Missing Prerequisites");
		confirm.setHeaderText("Some prerequisites are missing.");
		System.out.println("Missing prereq: " + stringBuilder.toString());
		for (String s: coursePrerequisites.getMissingCourses()) {
			System.out.println(s);
		}
		confirm.setContentText(stringBuilder.toString());// Set the content of the alert to the missing prerequisites
		confirm.showAndWait();// Display the alert to the user

	}
}


