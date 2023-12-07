/**
 * Utility class for managing course prerequisites and checking whether selected courses meet the prerequisites.
 */

package application;

import java.util.*;

public class CoursePrerequisites {
    private final Map<String, Prerequisite> adjacencyList;
    private List<String> newCourse;


    public CoursePrerequisites(ArrayList<String> selectedClasses) {
        this.adjacencyList = new HashMap<>();
        this.newCourse = new ArrayList<>();

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
        addPrerequisite(adjacencyList, "COMP5500", "OR", Arrays.asList("COMP4950", "COMP4960"));

        // Computer Science
        addPrerequisite(adjacencyList, "MATH1876", "OR", Arrays.asList("MATH1776", "MATH1700"));
        addPrerequisite(adjacencyList, "MATH1877", "OR", Arrays.asList("MATH1876", "MATH1777"));
        addPrerequisite(adjacencyList, "COMP2000", "AND", Arrays.asList("COMP1050", "MATH2300"));
        addPrerequisite(adjacencyList, "COMP2100", "AND", Arrays.asList("COMP1050"));
        addPrerequisite(adjacencyList, "MATH2860", "OR", Arrays.asList("MATH1876", "MATH1877"));
        addPrerequisite(adjacencyList, "COMP2350", "AND", Arrays.asList("COMP2000"));
        addPrerequisite(adjacencyList, "COMP3400", "AND", Arrays.asList("COMP2000"));
        addPrerequisite(adjacencyList, "MATH2100", "OR", Arrays.asList("MATH1800", "MATH1850", "MATH1875"));
        addPrerequisite(adjacencyList, "COMP3350", "OR", Arrays.asList("COMP2000", "COMP2350"));
        addPrerequisite(adjacencyList, "COMP4960", "AND", Arrays.asList("COMP2000"));
        addPrerequisite(adjacencyList, "COMP3450", "AND", Arrays.asList("COMP2000", "COMP2350", "COMP2100"));

        // CyberSecurity

        addPrerequisite(adjacencyList, "COMP4500", "AND", Arrays.asList("COMP3500"));
        addPrerequisite(adjacencyList, "COMP4550", "AND", Arrays.asList("COMP4500"));

        // Data Science
        addPrerequisite(adjacencyList, "COMP3125", "AND", Arrays.asList("COMP1000", "MATH2100"));
        addPrerequisite(adjacencyList, "MATH2200", "AND", Arrays.asList("MATH2100"));
        addPrerequisite(adjacencyList, "MATH2025", "OR", Arrays.asList("MATH1800", "MATH1875"));
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
        addPrerequisite(adjacencyList, "MATH3900", "AND", Arrays.asList("COMP1000", "MATH1800"));
        addPrerequisite(adjacencyList, "MATH3950", "AND", Arrays.asList("MATH2860"));
        addPrerequisite(adjacencyList, "MATH3700", "AND", Arrays.asList("MATH2860"));
        addPrerequisite(adjacencyList, "MATH4900", "AND", Arrays.asList("MATH2500"));

        newCourse = new ArrayList<>();

     // Process selected courses and extract course codes
        for (String completedCours : selectedClasses) {
            String[] parts = completedCours.split("\\s+", 2);
            if (parts.length >= 1) {
                String course = parts[0];
                newCourse.add(course);
                System.out.printf("%s ", course);
            }
        }

    }
    
    /**
     * Represents a prerequisite for a course, including the type (AND/OR) and a list of required courses.
     */
    public static class Prerequisite {
        String type;
        List<String> courses;

        Prerequisite(String type, List<String> courses) {
            this.type = type;
            this.courses = courses;
        }
    }

    /**
     * Checks whether the selected courses meet the prerequisites defined in the adjacency list.
     *
     * @return True if all prerequisites are met; false otherwise.
     */
    public boolean checkPrerequisites() {
        return checkPrerequisites(adjacencyList, newCourse);
    }
    /**
     * Adds a new course with its prerequisites to the adjacency list.
     *
     * @param adjacencyList   The map representing the adjacency list of courses and their prerequisites.
     * @param course          The code of the course.
     * @param type            The type of prerequisite relationship (AND/OR).
     * @param prerequisites   The list of prerequisite courses.
     */
    private static void addPrerequisite(Map<String, Prerequisite> adjacencyList, String course, String type, List<String> prerequisites) {
        Prerequisite prerequisite = new Prerequisite(type, prerequisites);
        adjacencyList.put(course, prerequisite);
    }
    
    /**
     * Checks whether the selected courses meet their prerequisites.
     *
     * @param adjacencyList     The map representing the adjacency list of courses and their prerequisites.
     * @param selectedClasses   The list of selected courses.
     * @return                  True if all prerequisites are met; false otherwise.
     */
    private static boolean checkPrerequisites(Map<String, Prerequisite> adjacencyList, List<String> selectedClasses) {
        System.out.println("Method ran:");
        for (String course : selectedClasses) {
            if (!adjacencyList.containsKey(course)) {
                continue;
            }

            Prerequisite prerequisites = adjacencyList.get(course);
            boolean allPrerequisitesMet;

            if (prerequisites.type.equals("AND")) {
                allPrerequisitesMet = true;
                for (String s : prerequisites.courses) {
                    System.out.println(s);
                    if (!selectedClasses.contains(s)) {
                        return false;
                    }
                }
            } else {
                allPrerequisitesMet = false;
                for (String s : prerequisites.courses) {
                    System.out.println(s);
                    if (selectedClasses.contains(s)) {
                        allPrerequisitesMet = true;
                        break;
                    }
                }
            }

            if (!allPrerequisitesMet) {
                return false;
            }
        }

        return true;
    }
    /**
     * Retrieves the adjacency list containing courses and their prerequisites.
     *
     * @return The map representing the adjacency list.
     */
    public Map<String, Prerequisite> getAdjacencyList () {
        return adjacencyList;
    }
    /**
     * Retrieves the list of course codes from the selected courses.
     *
     * @return The list of course codes.
     */
    public List<String> getClasses() {
        return newCourse;
    }

}
