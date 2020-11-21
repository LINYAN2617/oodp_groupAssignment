package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import File.TextDB;

public class StudentModel extends UserModel {
	public static ArrayList<StudentModel> course = new ArrayList<StudentModel>();
	
	public StudentModel(String UserID, String Password, String FirstName, String LastName, String Gender,
			String Nationality,char UserType) {
		super(UserID, Password, FirstName, LastName, Gender, Nationality, UserType);
		// TODO Auto-generated constructor stub
		
	}
	private String MatricNumber;
	private String Email;
	private String PhoneNumber;
	TextDB LoadData = new TextDB();
	ArrayList readfile = new ArrayList();

	
	public void setMatricNumber(String MatricNumber) {
		this.MatricNumber = MatricNumber;
	}
	
	public void addCourse() throws IOException {
		//Search for index number (pull info)
		int courseIndex;
		Scanner scan = new Scanner(System.in);
		System.out.println ("Enter the Index Number of the course:");
		courseIndex = scan.nextInt();
		
		
		//show course info
		
			
		//Confirm to register(push to add)
	}
	
	public void dropCourse() {
		
	}
	
	public void checkCourseRegistered() {
		
	}
	
	public void checkVacancy() {
		
	}
	
	
	public void changeIndexNum() {
		
	}
	
	public void swopIndexNum() {
		
	}
	
	

}
