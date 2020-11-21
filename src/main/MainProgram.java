package main;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import model.*;

import java.io.Console;
import java.io.IOException;
public class MainProgram {
	
	public static boolean isloginAsAdmin;
	public static boolean isloginAsStud;
	public static StudentModel LoggedStudent;
	public static AdminModel LoggedAdmin;
	public static DBController LoadData = new DBController();
	
	
	public static void main(String[] args) {
		
		if(LoadData.admin.size() == 0 ) {
			System.out.print("Please initial an admin in User DB.");
		}else {
			
			String LoginID;
			String Password;
			Console cons = System.console();
			LoginController LoginHandling = new LoginController();
			//System.out.println(LoginHandling.encrypt("1234"));
			System.out.println ("Welcome to My STudent Automated Registration System (MySTARS)");
			while (isloginAsAdmin == false && isloginAsStud == false) {
				Scanner scan = new Scanner(System.in);
				System.out.println ("--------------------------------------------------------------");
				System.out.println ("Login ID:");
				LoginID = scan.next();
	
			
				if (cons == null) {
					System.out.println ("Development Mode, Password will be displayed!");
	
					System.out.println ("Password:");
					Password = scan.next();
				} else {
	
					System.out.println ("Password:");
					Password = LoginHandling.getPasswordMasked(cons, "Password:");
				}
				
				AdminModel checkIsAdmin = LoginHandling.validateAdmin(LoginID,Password,LoadData.admin);
				if( checkIsAdmin == null) {
					
					StudentModel checkIsStud = LoginHandling.validateStud(LoginID,Password,LoadData.student);
					
					if(checkIsStud == null) {
						System.out.println ("Failed for validation try again:");
						System.out.println ("--------------------------------------------------------------");
				
					}else {
						Date date = new Date();
						 if(checkIsStud.getAccessTimeStart().compareTo(date) < 0 &&  checkIsStud.getAccessTimeEnd().compareTo(date) > 0 ){
								isloginAsStud = true;
								LoggedStudent =  checkIsStud;
						 }else{
								System.out.println ("Access Period has not opened!");
								System.out.println ("--------------------------------------------------------------");
						
						 };
					

					}
				}else {
					
					isloginAsAdmin = true;
					LoggedAdmin = checkIsAdmin;

					DisplayAdminScreen();
				} 
			}
			
			if(isloginAsAdmin == true) {
				DisplayAdminScreen();
			}else if(isloginAsStud == true) {
				DisplayStudentScreen();
			}

		}
		
		
	}
	
	public static void DisplayAdminScreen() {
		System.out.println ("Successfully logged in! ");
		System.out.println ("Welcome (Admin) " + LoggedAdmin.getFullName());
		System.out.println ("--------------------------------------------------------------");
		AdminController AdminFunc = new AdminController(DBController.CourseModelListing);
		
	}
	
	public static void DisplayStudentScreen() {
		StudentController studentControl = new StudentController(LoadData);
		System.out.println ("Successfully logged in! ");
		System.out.println ("Welcome (Student) " + LoggedStudent.getFullName());
		System.out.println ("--------------------------------------------------------------");

		System.out.println("Please select an option:\n" +
						"1. Add courses\n"+
						"2. Drop courses\n"+
						"3. Check Courses Registered\n" + 
						"4. Check Vacancies Available\n"+
						"5. Change Index Number of Course\n"+
						"6. Swop Index Number with Another Student\n");
		Scanner scan = new Scanner(System.in);
		int option,courseIndex;
		option = scan.nextInt();
				switch (option) {
				case 1:
					
					System.out.println ("Enter the Index Number of the course:");
					courseIndex = scan.nextInt();
					CourseModel returnCModel =  studentControl.FindCourse(courseIndex);
					if(returnCModel == null) {
						System.out.println("Course Not Found, Please try again!");
					}else {
						System.out.println(returnCModel.getCourseCode() + " | " + returnCModel.getCourseName() + " | " + returnCModel.getVacancy() );
						System.out.println("Please select an option:\n" +
								"1. Confirm to register?\n"+
								"2. Cancel\n");
						
						if(scan.nextInt() == 1) {
						  String returnMsg = studentControl.addCourse(LoggedStudent.getUserID(), returnCModel);
						  System.out.println(returnMsg);
						  
						  for(int i=0; i<LoadData.AllocatedListing.size();i++) {
							  System.out.println(LoadData.AllocatedListing.get(i).getUserID() + " - " + LoadData.AllocatedListing.get(i).getCourseIndex());
						  }
						}
					}
					
					break;
				case 2: 
				
					break;
				case 3:
					
					break;
				case 4:
					
					break;
				case 5:
					
					break;
				case 6:
					
					break;
					
				}
	}
}

