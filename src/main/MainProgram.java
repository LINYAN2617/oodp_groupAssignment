package main;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;

import File.*;
import java.io.Console;
import java.io.IOException;
import File.TextDB;
public class MainProgram {
	
	public static ArrayList<StudentModel> student = new ArrayList<StudentModel>();
	public static ArrayList<AdminModel> admin = new ArrayList<AdminModel>();
	public static boolean isloginAsAdmin;
	public static boolean isloginAsStud;
	public static StudentModel LoggedStudent;
	public static AdminModel LoggedAdmin;
	
	public static void main(String[] args) {
		
		TextDB LoadData = new TextDB();
		ArrayList readfile = new ArrayList();
	
		try {
			readfile = LoadData.readUsers("FileDB/User.txt");
			student = (ArrayList<StudentModel>) readfile.get(1);
			admin = (ArrayList<AdminModel>) readfile.get(0);
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("Error while loading files");
		}
		
		if(admin.size() == 0 ) {
			System.out.print("Please initial an admin in User DB.");
		}else {
			
		
			String LoginID;
			String Password;
			Console cons = System.console();
			Function_LoginHandling LoginHandling = new Function_LoginHandling();
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
				
				AdminModel checkIsAdmin = LoginHandling.validateAdmin(LoginID,Password,admin);
				if( checkIsAdmin == null) {
					
					StudentModel checkIsStud = LoginHandling.validateStud(LoginID,Password,student);
					
					if(checkIsStud == null) {
						System.out.println ("Failed for validation try again:");
						System.out.println ("--------------------------------------------------------------");
				
					}else {

						isloginAsStud = true;
						LoggedStudent =  checkIsStud;

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
	}
	
	public static void DisplayStudentScreen() {
		System.out.println ("Successfully logged in! ");
		System.out.println ("Welcome (Student) " + LoggedStudent.getFullName());
		System.out.println ("--------------------------------------------------------------");
	}
}

