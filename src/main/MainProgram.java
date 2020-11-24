package main;
import java.util.Date;
import java.util.Scanner;

import dbrepo.AdminRepo;
import model.*;

import java.io.Console;
public class MainProgram {
	
	public static boolean isloginAsAdmin;
	public static boolean isloginAsStud;
	public static StudentModel LoggedStudent;
	public static AdminModel LoggedAdmin;
	public static DBContext LoadData = new DBContext();
	public static LoginController LoginHandling = new LoginController();
	public MainProgram(){
		
	}
	
	public static void programInterface() {
		String LoginID;
		String Password;
		Console cons = System.console();
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

				Password = LoginHandling.getPasswordMasked(cons, "Password:");
			}
			
			AdminModel checkIsAdmin = LoginHandling.validateAdmin(LoginID,Password);
			if( checkIsAdmin == null) {
				
				StudentModel checkIsStud = LoginHandling.validateStud(LoginID,Password);
				
				if(checkIsStud == null) {
					System.out.println ("Failed for validation try again:");
					System.out.println ("--------------------------------------------------------------");
			
				}else {
					Date date = new Date();
					 if(LoginHandling.checkAccessPeriod(checkIsStud)){
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
			displayAdminScreen();
		}else if(isloginAsStud == true) {
			displayStudentScreen();
		}
	}
	
	public static void main(String[] args) {
		
		if(AdminRepo.getAdmin().size() == 0 ) {
			System.out.print("Please initial an admin in User DB.");
		}else {
			
			programInterface();
			
		}
		
		
	}
	
	public static void displayAdminScreen() {
		System.out.println ("Successfully logged in! ");
		System.out.println ("Welcome (Admin) " + LoggedAdmin.getFullName()+"\n");
		

		AdminController AdminFunc = new AdminController(LoggedAdmin);
		AdminFunc.StartAdminPage();
		
	}
	
	
	public static void displayStudentScreen() {
		System.out.println ("Successfully logged in! ");
		System.out.println ("Welcome (Student) " + LoggedStudent.getFullName());

		StudentController StudFunc = new StudentController(LoggedStudent);
		StudFunc.StartStudPage();
		
	}
}

