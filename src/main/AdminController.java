package main;
import DBRepo.DBContext;
import DBRepo.CourseRepo;

import DBRepo.FileHandle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.AdminModel;
import model.CourseModel;
import model.TimeTableModel;
import model.StudentModel;
public class AdminController {

	private static CourseRepo CourseRepo;
	private static AdminModel LoggedAdmin;
	public AdminController(DBContext loadData,AdminModel LoggedAdmin) {
			AdminController.LoggedAdmin=LoggedAdmin;
			
	}
	
	public void StartAdminPage() {
		System.out.println ("Successfully logged in! ");
		System.out.println ("Welcome (Admin) " + LoggedAdmin.getFullName());
		System.out.println ("--------------------------------------------------------------");
		
		System.out.println(
				"1. Edit student access period\n" + 
				"2. Add a student\n"+
				"3. Add a course\n"+
				"4. Update a course\n"+
				"5. Check available slot for an index number\n"+
				"6. Print student list by index number\n"+
				"7. Print student list by course\n"+
				"8. Log out");
		System.out.println("Please Select Function: ");
		Scanner sc = new Scanner(System.in);
		int choice;
		int courseIndex;
		choice = sc.nextInt();
		while(choice<8) {
			switch(choice) {
			case 1:

				break;
			case 2:
				break;
			case 3:
				addCourseView();
				break;
			case 4:
				System.out.println(".............Update Course....... ");
				System.out.println("Please Enter the Course Index Number: ");
				Scanner sc1 = new Scanner(System.in);
				courseIndex = sc1.nextInt();
				
				CourseModel CModel = DBRepo.CourseRepo.GetCourseByIndexNumber(courseIndex);
				
				if(CModel == null) {
					System.out.println("Course not found, please try again");
					 
				}else {
					updateCourseView(CModel);
				}
				
				break;
			case 5:
				System.out.println("Please Enter the Course Index Number: ");
				Scanner sc2 = new Scanner(System.in);
				courseIndex = sc2.nextInt();
				//Wait for student code
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
		}
			
		}
	}
	
	
	public TimeTableModel newTimetableView(int newindexNumber, String newType) {
		Scanner sc = new Scanner(System.in);
		TimeTableModel timeTable= new TimeTableModel();
		
		timeTable.setIndexNumber(newindexNumber);
		timeTable.setType(newType);
		
		System.out.println("Please Select the Day: ");
		System.out.println("1. Mon;  2. Tus; 3. Wed; 4. Thu; 5.Fri; ");	
		int day = sc.nextInt();
		switch(day) {
		case 1:
			timeTable.setDay("Mon");
			break;
		case 2:
			timeTable.setDay("Tus");
			break;
		case 3:
			timeTable.setDay("Wed");
			break;
		case 4:
			timeTable.setDay("Thu");
			break;
		case 5:
			timeTable.setDay("Fri");
			break;
		}
		
		System.out.println("Please Enter the Time(HH:MM-HH:MM): ");
		String time=sc.next();
		timeTable.setTime(time);
		// set input format
		
		
		System.out.println("Please Enter the Venue: ");
		String Venue=sc.next();
		timeTable.setVenue(Venue);
	
		return timeTable;
		
	}
	
		
	
		public void addCourseView() {
			int newindexNumber = CourseRepo.GetNewCourseIndexNumber();
			CourseModel newCourse = new CourseModel();
			Scanner sc = new Scanner(System.in);
			System.out.println(".............Add New Course....... ");
			System.out.println("The new index Number is "+newindexNumber);
			newCourse.setIndexNumber(newindexNumber);
			
			System.out.println("CourseCode:");
			String CourseCode;
			CourseCode = sc.next();
			newCourse.setCourseCode(CourseCode);
			
			System.out.println("CourseName:");
			String CourseName;
			CourseName = sc.next();
			newCourse.setCourseName(CourseName);
			
			System.out.println("Please Select CourseType: \n"+
					"1. CORE\n" + 
					"2. UNRESTRICTED ELECTIVE\n"+
					"3. PRESCRIBED\n");
					int choice;
					choice = sc.nextInt();
					switch (choice) {
					case 1:
						newCourse.setCourseType("CORE");
						break;
					case 2: 
						newCourse.setCourseType("UNRESTRICTED ELECTIVE");
						break;
					case 3:
						newCourse.setCourseType("PRESCRIBED");
						break;
					}
						
			System.out.println("School:");
			String School;
			School = sc.next();
			newCourse.setSchool(School);
			
			System.out.println("Vacancy:");
			int Vacancy;
			Vacancy = sc.nextInt();
			newCourse.setVacancy(Vacancy);
			
			System.out.println("AU:");
			int AU;
			AU = sc.nextInt();
			newCourse.setAU(AU);
			
			DBRepo.CourseRepo.add(newCourse);

			System.out.println("Successfully Added Course!");

		}	
						

		
		public void updateCourseView(CourseModel CModel) {
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println(
			"1. Course Code\n"+
			"2. Course Name\n"+
			"3. Course Type\n"+
			"4. School\n"+
			"5. AU\n"+
			"6. Vacancy\n"+
			"7. Time Table\n"+
			"8. Commit to save\n");
			System.out.println ("--------------------------------------------------------------");
			
			
			System.out.println("Please select the update item: ");
			int choice;
			choice = sc.nextInt();
			
			while(choice<8)
			{
				switch (choice) {
				case 1: 
					System.out.println("Please enter new Course Code:");
					String CourseCode;
					CourseCode = sc.next();
					CModel.setCourseCode(CourseCode);
					System.out.println("Successfully updated the Course Code! ");
					System.out.println ("--------------------------------------------------------------");
					break;
				case 2:
					System.out.println("Please enter new Course Name:");
					String CourseName;
					CourseName = sc.next();
					CModel.setCourseName(CourseName);
					System.out.println("Successfully updated the Course Name!");
					System.out.println ("--------------------------------------------------------------");

					break;
				case 3:
					System.out.print("Please Select new Course Type:/n "+
							"1.CORE\n" + 
							"2. UNRESTRICTED ELECTIVE\n"+
							"3. PRESCRIBED\n");
							int a;
							a = sc.nextInt();
							switch (a) {
							case 1:
								CModel.setCourseType("CORE");
								System.out.println("Successfully updated the Course Type!");
								System.out.println ("--------------------------------------------------------------");

								break;
							case 2: 
								CModel.setCourseType("UNRESTRICTED ELECTIVE");
								System.out.println("Successfully updated the Course Type!");
								System.out.println ("--------------------------------------------------------------");
								break;
							case 3:
								CModel.setCourseType("PRESCRIBED");
								System.out.println("Successfully updated the Course Type!");
								System.out.println ("--------------------------------------------------------------");
								break;
							default:
								System.out.print("Invalid input!");
							}
						break;
				case 4:			
					System.out.print("Please enter new School:");
					String School;
					School = sc.next();
					CModel.setSchool(School);
					System.out.println("Successfully updated the School!");
					System.out.println ("--------------------------------------------------------------");
					break;
				case 5:			
					System.out.print("Please enter new AU:");
					int AU;
					AU = sc.nextInt();
					CModel.setAU(AU);
					System.out.println("Successfully updated the AU!");
					System.out.println ("--------------------------------------------------------------");
					break;
	
				case 6:
					System.out.print("Please enter new Vacancy:");
					int Vacancy;
					Vacancy = sc.nextInt();
					CModel.setVacancy(Vacancy);
					System.out.println("Successfully updated the Vacancy!");
					System.out.println ("--------------------------------------------------------------");
					break;
				case 7:
					break;
				
				}	
				System.out.print("Please select the update item: ");
				choice = sc.nextInt();
			}
			
				DBRepo.CourseRepo.update(CModel);
			
		}
					
		
		
		
		public void EditAccessTime() {
			
		}
		
		public void AddStudent() {
			System.out.print("..............Add a Student.....................");
			
		}
	
}
