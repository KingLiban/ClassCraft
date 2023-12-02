package application;

import java.io.File;
import java.util.ArrayList;

public class StudentInfo {
	private String studentName;
	private String semester;
	private String studentYear;
	private String witEmail;
	private String major;
	private int generalElective;
	private int humanityElective;
	private int scienceElective;
	private int majorElective;
	private ArrayList<String> fall;
	private ArrayList<String> spring;
	private ArrayList<String> summer;
	private File studentFile;
	public StudentInfo(String name,String season, String year, String email, String major) {
		setStudentName(name);
		setSemester(season);
		setStudentYear(year);
		setWitEmail(email);
		this.setMajor(major);
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getStudentYear() {
		return studentYear;
	}
	public void setStudentYear(String studentYear) {
		this.studentYear = studentYear;
	}
	public String getWitEmail() {
		return witEmail;
	}
	public void setWitEmail(String witEmail) {
		this.witEmail = witEmail;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String toString() {
		return String.format("Student Name: %s%n"
				+ "Semester: %s%n"
				+ "Student Year: %s%n"
				+ "WIT Email: %s%n"
				+ "Major: ",studentName,semester,studentYear,witEmail,major);
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
	public File getStudentFile() {
		return studentFile;
	}
	public void setStudentFile(String s) {
		File f = new File("s");
		this.studentFile = f;
	}
}
