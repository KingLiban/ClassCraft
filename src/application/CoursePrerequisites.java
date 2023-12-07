/**
 * Utility class for managing course prerequisites and checking whether selected courses meet the prerequisites.
 */

package application;

import java.util.*;

public class CoursePrerequisites {
    private static Map<String, Prerequisite> adjacencyList = null;
    private static List<String> newCourse;
    private static List<String> missingCourses;

    /**
     * Represents a prerequisite for a course, including the type (AND/OR) and a list of required courses.
     */
    public static class Prerequisite  {
        String type;
        List<String> courses;

        Prerequisite(String type, List<String> courses) {
            this.type = type;
            this.courses = courses;
        }
    }

    /**
     * Maps ALL core classes found in the School of Computing & Data Science
     * to their respective prerequisites,
     * with types based on if all courses must be met or either,
     * @param selectedClasses
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
        for (String course : newCourse) {
            if (!adjacencyList.containsKey(course)) {
                continue;
            }

            Prerequisite prerequisites = adjacencyList.get(course);
            boolean allPrerequisitesMet;

            if (prerequisites.type.equals("AND")) {
                allPrerequisitesMet = true;
                for (String s : prerequisites.courses) {
                    System.out.println(s);
                    if (!newCourse.contains(s)) {
                        return false;
                    }
                }
            } else {
                allPrerequisitesMet = false;
                for (String s : prerequisites.courses) {
                    System.out.println(s);
                    if (newCourse.contains(s)) {
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
     * Returns a list of classes whose
     * prerequisites are not found in
     * the selected classes list.
     */
    private static List<String> getMissingClasses() {
        List<String> missing = new ArrayList<>();

        for (String course : newCourse) {
            if (!adjacencyList.containsKey(course)) {
                continue;
            }

            Prerequisite prerequisites = adjacencyList.get(course);
            boolean allPrerequisitesMet;

            // Different conditions for "AND" and "OR"
            if (prerequisites.type.equals("AND")) {
                allPrerequisitesMet = true;
                for (String s : prerequisites.courses) {
                    System.out.println(s);
                    if (!newCourse.contains(s)) {
                        // When "AND" condition is not met, add to list.
                        missing.add(course);
                    }
                }
            } else {
                allPrerequisitesMet = false;
                for (String s : prerequisites.courses) {
                    System.out.println(s);
                    if (newCourse.contains(s)) {
                        allPrerequisitesMet = true;
                        break;
                    }
                }
            }

            if (!allPrerequisitesMet) {
                missing.add(course);
                // When "OR" condition is not met, add to list.
            }
        }

        System.out.println("Missing classes:");
            for (String s : missing) {
                System.out.println("We ran one time");
                System.out.println(s);
            }

        return missing;
    }

    /**
     * Returns reference of missing course list.
     *
     */
    public List<String> getMissingCourses() { return missingCourses; }

    /**
     * Returns reference of adjacency HashMap.
     *
     */
    public Map<String, Prerequisite> getAdjacencyList () { return adjacencyList; }

}
