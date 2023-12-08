package application;

/**
 * Represents a student with basic information such as name, semester, year, email, and major,
 * as well as elective credit information.
 */
public class Student {
	//Declare all necessary information of the student 
	private String name;
	private String semester;
	private String year;
	private String email;
	private String major;
	private int generalElective;
	private int humanityElective;
	private int scienceElective;
	private int majorElective;
	
	/**
     * Constructs a Student object with the specified information.
     *
     * @param name     The name of the student.
     * @param semester The current semester of the student.
     * @param year     The current academic year of the student.
     * @param email    The WIT email of the student.
     * @param major    The major of the student.
     */
	public Student(String name, String semester, String year, String email, String major) {
		this.name = name;
		this.semester = semester;
		this.year = year;
		this.email = email;
		this.major = major;
	}
	/**
     * Gets the name of the student.
     *
     * @return The name of the student.
     */
	public String getStudentName() {
		return name;
	}
	
	/**
     * Gets the current semester of the student.
     *
     * @return The current semester of the student.
     */
	public String getSemester() {
		return semester;
	}
	/**
     * Gets the current academic year of the student.
     *
     * @return The current academic year of the student.
     */
	public String getStudentYear() {
		return year;
	}
	/**
     * Gets the WIT email of the student.
     *
     * @return The WIT email of the student.
     */
	public String getEmail() {
		return email;
	}
	/**
     * Gets the major of the student.
     *
     * @return The major of the student.
     */
	public String getMajor() {
		return major;
	}
	
	/**
     * Gets a string representation of the student, including basic information.
     *
     * @return A formatted string with student information.
     */
	public String toString() {
		return String.format(
				"Student Name: %s%n"
						+ "Semester: %s%n"
						+ "Student Year: %s%n"
						+ "WIT Email: %s%n"
						+ "Major: ", name, semester, year, email, major
		);
	}
	
	/**
     * Gets the number of general elective credits the student has.
     *
     * @return The number of general elective credits.
     */
	public int getGeneralElective() {
		return generalElective;
	}
	/**
     * Sets the number of general elective credits for the student.
     *
     * @param generalElective The new number of general elective credits.
     */
	public void setGeneralElective(int generalElective) {
		this.generalElective = generalElective;
	}
	/**
     * Gets the number of humanity elective credits the student has.
     *
     * @return The number of humanity elective credits.
     */
	public int getHumanityElective() {
		return humanityElective;
	}
	/**
     * Sets the number of humanity elective credits for the student.
     *
     * @param humanityElective The new number of humanity elective credits.
     */
	public void setHumanityElective(int humanityElective) {
		this.humanityElective = humanityElective;
	}
	/**
     * Gets the number of science elective credits the student has.
     *
     * @return The number of science elective credits.
     */
	public int getScienceElective() {
		return scienceElective;
	}
	
	 /**
     * Sets the number of science elective credits for the student.
     *
     * @param scienceElective The new number of science elective credits.
     */
	public void setScienceElective(int scienceElective) {
		this.scienceElective = scienceElective;
	}
	/**
     * Gets the number of major elective credits the student has.
     *
     * @return The number of major elective credits.
     */
	public int getMajorElective() {
		return majorElective;
	}
	/**
     * Sets the number of major elective credits for the student.
     *
     * @param majorElective The new number of major elective credits.
     */
	public void setMajorElective(int majorElective) {
		this.majorElective = majorElective;
	}
}
