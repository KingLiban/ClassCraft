import java.util.ArrayList;
import java.util.Collections;
public class Major {
	private int studentYear;
	private String studentName;
	private int studentID;
	private ArrayList<String> requiredClasses;
	private String studentEmail;
	private int totalCredit;
	public Major(int studYear, String studName, int iD, int credits, String email) {
		studentYear = studYear;
		studentName = studName;
		studentID = iD;
		studentEmail = email;
		totalCredit = credits;
		String[] classes = {"Calculus 1A","Calculus 1B","Calculus 2A", "Calculus 2B", "English I", "English II"};
		Collections.addAll(requiredClasses, classes);
		
	}
	public void addClasses(String c) {
		requiredClasses.add(c);
	}
	public int getStudentYear() {
		return studentYear;
	}
	public void setStudentYear(int studentYear) {
		this.studentYear = studentYear;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getTotalCredit() {
		return totalCredit;
	}
	public void setTotalCredit(int totalCredit) {
		this.totalCredit = totalCredit;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	@Override
	public String toString() {
		return "Student Name: " + this.studentName + 
				"/nStudentYear:" + this.studentYear +
				"/nStudent ID: " + this.studentID +
				"/nTotal Credit: " + this.totalCredit;
	}

}
