/**
 * Utility class for managing course prerequisites and checking whether selected courses meet the prerequisites.
 */

package application;

import java.util.*;

public class CoursePrerequisites {
    // Declare fields for storing the adjacency list, selected new courses, and missing courses
    private static Map<String, Prerequisite> adjacencyList = null;
    private static List<String> newCourse;
    private static List<String> missingCourses;

    /**
     * Represents a prerequisite for a course, including the type (AND/OR) and a list of required courses.
     */
    public static class Prerequisite  {
        // Declaration of fields for the type of relationship and the list of courses
    	String type;
        List<String> courses;
        // Constructor initializing the type and list of courses for the prerequisite
        Prerequisite(String type, List<String> courses) {
            this.type = type;
            this.courses = courses;
        }
    }

    /**
     * Constructor to create an instance of CoursePrerequisites with selected classes.
     * Initializes the adjacency list and populates it with predefined course prerequisites
     * @param selectedClasses List of selected classes.
     */
    public CoursePrerequisites(ArrayList<String> selectedClasses) {
        adjacencyList = new HashMap<>();
        newCourse = new ArrayList<>();

        // Unique classes mapped.

        // Computer Networking
        addPrerequisite(adjacencyList, "COMP1050", "OR", Arrays.asList("COMP1000"));
        addPrerequisite(adjacencyList, "COMP1150", "OR", Arrays.asList("COMP1100", "COMP2100"));
        addPrerequisite(adjacencyList, "COMP1200", "AND", Arrays.asList("COMP1000", "MATH2300"));
        addPrerequisite(adjacencyList, "COMP2500", "OR", Arrays.asList("COMP1100", "COMP2100"));
        addPrerequisite(adjacencyList, "COMP2150", "OR", Arrays.asList("COMP1100", "COMP2100"));
        addPrerequisite(adjacencyList, "COMP2160", "OR", Arrays.asList("COMP1100", "COMP2100"));
        addPrerequisite(adjacencyList, "COMP2650", "OR", Arrays.asList("COMP1050", "MATH2300"));
        addPrerequisite(adjacencyList, "COMP3100", "OR", Arrays.asList("COMP1000", "COMP1100"));
        addPrerequisite(adjacencyList, "COMP3500", "OR", Arrays.asList("COMP2150", "COMP2500"));
        addPrerequisite(adjacencyList, "COMP3550", "AND", Arrays.asList("COMP2500", "COMP3100"));
        addPrerequisite(adjacencyList, "MATH1900", "OR", Arrays.asList("MATH1500", "MATH2800"));
        addPrerequisite(adjacencyList, "COMP4650", "OR", Arrays.asList("COMP2650"));
        addPrerequisite(adjacencyList, "COMP4950", "OR", Arrays.asList("COMP2650"));
        addPrerequisite(adjacencyList, "COMP5500", "OR", Arrays.asList("COMP4550", "COMP4960", "COMP4950"));

        // Computer Science
        addPrerequisite(adjacencyList, "MATH1876", "AND", Arrays.asList("MATH1776"));
        addPrerequisite(adjacencyList, "MATH1877", "OR", Arrays.asList("MATH1876", "MATH1777"));
        addPrerequisite(adjacencyList, "COMP2000", "AND", Arrays.asList("COMP1050", "MATH2300"));
        addPrerequisite(adjacencyList, "COMP2100", "AND", Arrays.asList("COMP1050"));
        addPrerequisite(adjacencyList, "MATH2860", "OR", Arrays.asList("MATH1876", "MATH1877"));
        addPrerequisite(adjacencyList, "COMP2350", "AND", Arrays.asList("COMP2000"));
        addPrerequisite(adjacencyList, "COMP3400", "AND", Arrays.asList("COMP2000"));
        addPrerequisite(adjacencyList, "MATH2100", "OR", Arrays.asList("MATH1876", "MATH1877"));
        addPrerequisite(adjacencyList, "COMP3350", "OR", Arrays.asList("COMP2000", "COMP2350"));
        addPrerequisite(adjacencyList, "COMP4960", "AND", Arrays.asList("COMP2000"));
        addPrerequisite(adjacencyList, "COMP3450", "AND", Arrays.asList("COMP2000", "COMP2350", "COMP2100"));

        // CyberSecurity
        addPrerequisite(adjacencyList, "COMP4500", "AND", Arrays.asList("COMP3500"));
        addPrerequisite(adjacencyList, "COMP4550", "AND", Arrays.asList("COMP4500"));

        // Data Science
        addPrerequisite(adjacencyList, "COMP3125", "AND", Arrays.asList("COMP1000", "MATH2100"));
        addPrerequisite(adjacencyList, "MATH2200", "AND", Arrays.asList("MATH2100"));
        addPrerequisite(adjacencyList, "MATH2025", "OR", Arrays.asList("MATH1876", "MATH1877"));
        addPrerequisite(adjacencyList, "COMP/MATH4050", "AND", Arrays.asList("MATH2100", "COMP1000"));
        addPrerequisite(adjacencyList, "DATA3010", "AND", Arrays.asList("COMP3125"));

        // Information Technology
        addPrerequisite(adjacencyList, "COMP2010", "AND", Arrays.asList("COMP1010"));
        addPrerequisite(adjacencyList, "COMP2110", "AND", Arrays.asList("COMP1100"));
        addPrerequisite(adjacencyList, "COMP2160", "OR", Arrays.asList("COMP1100", "COMP2100"));
        addPrerequisite(adjacencyList, "COMP2210", "AND", Arrays.asList("COMP1050", "MATH2300"));
        addPrerequisite(adjacencyList, "COMP3010", "AND", Arrays.asList("COMP1050", "COMP2010"));

        // Applied Mathematics
        addPrerequisite(adjacencyList, "MATH2550", "AND", Arrays.asList("MATH2300"));
        addPrerequisite(adjacencyList, "MATH3900", "AND", Arrays.asList("COMP1000", "MATH1876", "MATH1877"));
        addPrerequisite(adjacencyList, "MATH3950", "AND", Arrays.asList("MATH2860"));
        addPrerequisite(adjacencyList, "MATH3700", "AND", Arrays.asList("MATH2860"));
        addPrerequisite(adjacencyList, "MATH4900", "AND", Arrays.asList("MATH2500"));

        newCourse = new ArrayList<>();

        // Splits the course string that was originally found in src/majors and adds the course code.
        for (String completedCourse : selectedClasses) {
            String[] parts = completedCourse.split("\\s+", 2);
            if (parts.length >= 1) {
                String course = parts[0];
                newCourse.add(course);
            }
        }

        missingCourses = getMissingClasses();
    }

    /**
     * Represents a prerequisite for a course, including the type (AND/OR) and a list of required courses.
     */
    private static void addPrerequisite(Map<String, Prerequisite> adjacencyList, String course, String type, List<String> prerequisites) {
        Prerequisite prerequisite = new Prerequisite(type, prerequisites);
        adjacencyList.put(course, prerequisite);
    }

    /**
     * Checks whether the selected courses meet the prerequisites defined in the adjacency list.
     *
     * @return True if all prerequisites are met; false otherwise.
     */
    public boolean checkPrerequisites() {
        System.out.println("Method ran:");
        // Iterate through each course in the selected courses
        for (String course : newCourse) {
        	//move to the next course if the course is not found in the adjacency list
            if (!adjacencyList.containsKey(course)) {
                continue;
            }
            // Get the prerequisites for the current course
            Prerequisite prerequisites = adjacencyList.get(course);
            //Store the boolean for whether all prerequisites were met or not
            boolean allPrerequisitesMet;
            // Checks the type of prerequisite relationship (AND/OR)
            if (prerequisites.type.equals("AND")) {
            	 //For 'AND' type, initialize allPrerequisitesMet to true
                allPrerequisitesMet = true;
                for (String s : prerequisites.courses) {
                    System.out.println(s);
                    // return false if any prerequisite course is not found in the selected courses
                    if (!newCourse.contains(s)) {
                        return false;
                    }
                }
            } else {
            	// For 'OR' type, initially assume no prerequisites are met
                allPrerequisitesMet = false;
                for (String s : prerequisites.courses) {
                    System.out.println(s);
                    // If at least one prerequisite course is found in the selected courses, set the allPrerequisitesMet to true and stop
                    //the iteration
                    if (newCourse.contains(s)) {
                        allPrerequisitesMet = true;
                        break;
                    }
                }
            }
            // If, by the end, not all prerequisites for the current course are met, return false
            if (!allPrerequisitesMet) {
                return false;
            }
        }

        return true;// return true if all prerequisites for each course are met
    }

    /**
     * Returns a list of classes whose
     * prerequisites are not found in
     * the selected classes list.
     */
    private static List<String> getMissingClasses() {
        List<String> missing = new ArrayList<>();
        // Iterate through each course in the selected courses
        for (String course : newCourse) {
        	// move to the next course when the course is not found in the adjacency list
            if (!adjacencyList.containsKey(course)) {
                continue;
            }
            // Get the prerequisites for the current course
            Prerequisite prerequisites = adjacencyList.get(course);
            boolean allPrerequisitesMet;

            // Checks the type of prerequisite relationship (AND/OR)
            if (prerequisites.type.equals("AND")) {
                allPrerequisitesMet = true;//For 'AND' type, initialize allPrerequisitesMet to true
                for (String s : prerequisites.courses) {
                    System.out.println(s);
                    if (!newCourse.contains(s)) {
                        // When "AND" condition is not met, add to missing list.
                        missing.add(course);
                    }
                }
            } else {
            	// For 'OR' type, initially assume no prerequisites are met
                allPrerequisitesMet = false;
                for (String s : prerequisites.courses) {
                    System.out.println(s);
                    // If one prerequisite course is found in the selected courses, set allPrerequisitesMet to true and end the loop
                    if (newCourse.contains(s)) {
                        allPrerequisitesMet = true;
                        break;
                    }
                }
            }
            // If not all prerequisites for the current course are met, add it to the 'missing' list
            if (!allPrerequisitesMet) {
                missing.add(course);
            }
        }
        // Print the list of missing courses for checking of errors
        System.out.println("Missing classes:");
            for (String s : missing) {
                System.out.println("We ran one time");
                System.out.println(s);
            }

        return missing;// Return the list of courses with unmet prerequisites
    }

    /**
     * Returns the reference for the list of courses that have unmet prerequisites.
     *
     * @return List of courses with unmet prerequisites
     */
    public List<String> getMissingCourses() { return missingCourses; }

    /**
     * Returns the reference of adjacency list containing course prerequisites.
     *
     * @return Map representing the adjacency list with course prerequisites
     */
    public Map<String, Prerequisite> getAdjacencyList () { return adjacencyList; }

}
