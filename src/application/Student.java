package application;

import java.util.ArrayList;

public class Student {
	private String name;
	private String semester;
	private String year;
	private String email;
	private String major;
	private int generalElective;
	private int humanityElective;
	private int scienceElective;
	private int majorElective;
	private ArrayList<String> fall;
	private ArrayList<String> spring;
	private ArrayList<String> summer;
	public Student(String name, String semester, String year, String email, String major) {
		this.name = name;
		this.semester = semester;
		this.year = year;
		this.email = email;
		this.major = major;
	}

	public String getStudentName() {
		return name;
	}
	public String getSemester() {
		return semester;
	}
	public String getStudentYear() {
		return year;
	}
	public String getEmail() {
		return email;
	}
	public String getMajor() {
		return major;
	}
	public String toString() {
		return String.format(
				"Student Name: %s%n"
						+ "Semester: %s%n"
						+ "Student Year: %s%n"
						+ "WIT Email: %s%n"
						+ "Major: ", name, semester, year, email, major
		);
	}
	public ArrayList<String> getFall() {
		return fall;
	}
	public void populateFall(ArrayList<String> fall) {
		this.fall = fall;
	}
	public ArrayList<String> getSpring() {
		return spring;
	}
	public void populateSpring(ArrayList<String> spring) {
		this.spring = spring;
	}
	public ArrayList<String> getSummer() {
		return summer;
	}
	public void populateSummer(ArrayList<String> summer) {
		this.summer = summer;
	}
	public int getGeneralElective() {
		return generalElective;
	}
	public void setGeneralElective(int generalElective) {
		this.generalElective = generalElective;
	}
	public int getHumanityElective() {
		return humanityElective;
	}
	public void setHumanityElective(int humanityElective) {
		this.humanityElective = humanityElective;
	}
	public int getScienceElective() {
		return scienceElective;
	}
	public void setScienceElective(int scienceElective) {
		this.scienceElective = scienceElective;
	}
	public int getMajorElective() {
		return majorElective;
	}
	public void setMajorElective(int majorElective) {
		this.majorElective = majorElective;
	}
}
