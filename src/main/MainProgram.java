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
		System.out.println ("Successfully logged in! ");
		System.out.println ("Welcome (Student) " + LoggedStudent.getFullName());
		System.out.println ("--------------------------------------------------------------");
	}
}

