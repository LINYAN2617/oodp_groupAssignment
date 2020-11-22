package main;
import DBRepo.DBContext;
import DBRepo.CourseRepo;

import DBRepo.TimeTableRepo;

import java.util.ArrayList;
import java.util.Scanner;

import model.AdminModel;
import model.CourseModel;
import model.TimeTableModel;
public class AdminController {

	private static AdminModel LoggedAdmin;
	public AdminController(AdminModel LoggedAdmin) {
		this.LoggedAdmin=LoggedAdmin;
			
	}
	
	public void StartAdminPage() {
		System.out.println ("Successfully logged in! ");
		System.out.println ("Welcome (Admin) " + LoggedAdmin.getFullName()+"\n");
		System.out.println ("----------------Admin Page Menu---------------");
		
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
				
				CourseModel CModel = CourseRepo.GetCourseByIndexNumber(courseIndex);
				
				if(CModel == null) {
					System.out.println("Course not found, please try again");
					 
				}else {
					//CModel.getIndexNumber();
					System.out.format("|%-13s | %-40s | %-20s | %-10s | %-20s| %-4s |\n", "Course Code", "Course Name","Course Type","Vacancy", "School", "AU");
					System.out.format("|%-13s | %-40s | %-20s | %-10s | %-20s| %-4s |\n", CModel.getCourseCode(), CModel.getCourseName(),CModel.getCourseType(),CModel.getVacancy(), CModel.getSchool(),CModel.getAU());
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
	
	public void PrintTimeTableList(ArrayList<TimeTableModel> timeTableList) {
		//ArrayList<TimeTableModel> timeTableList=DBRepo.TimeTableRepo.readTimeTableByCourseIndex(indexnumber);
	
		//System.out.println("timeTableList Size: "+timeTableList.size());
		System.out.format("|%-13s | %-5s | %-5s | %-5s | %-10s| %-4s |\n", "Course Index", "Type","Group","Day", "Time", "Venue");
		
		for (int i=0;i<timeTableList.size();i++) {
			System.out.format("|%-13s | %-5s | %-5s | %-5s | %-10s| %-4s |\n", timeTableList.get(i).getIndexNumber(),timeTableList.get(i).getType(),timeTableList.get(i).getGroup(),timeTableList.get(i).getDay(),timeTableList.get(i).getTime(),timeTableList.get(i).getVenue());
		}
	}
	
	public void printCourse(CourseModel Course) {
		System.out.format("|%-13s | %-40s | %-20s | %-10s | %-20s| %-4s |\n", "Course Code", "Course Name","Course Type","Vacancy", "School", "AU");
		System.out.format("|%-13s | %-40s | %-20s | %-10s | %-20s| %-4s |\n", Course.getCourseCode(), Course.getCourseName(),Course.getCourseType(),Course.getVacancy(), Course.getSchool(),Course.getAU());
		System.out.println();
	}
	public TimeTableModel newTimetableView(int newindexNumber, String newType) {
		Scanner sc = new Scanner(System.in);
		TimeTableModel timeTable= new TimeTableModel();
		
		timeTable.setIndexNumber(newindexNumber);
		timeTable.setType(newType);
		System.out.println("................New Time Table............................");
		System.out.println("Index Number: "+newindexNumber);
		System.out.println("Class Type: "+newType);
		
		System.out.println("Please enter the Group: ");
		int group = sc.nextInt();
		timeTable.setGroup(group);
		
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
		
		System.out.format("\n|%-13s | %-5s | %-5s | %-5s | %-10s| %-5s |\n", "Course Index", "Type","Group","Day", "Time", "Venue");
		System.out.format("|%-13s | %-5s | %-5s | %-5s | %-10s| %-5s |\n",timeTable.getIndexNumber(),timeTable.getType(),timeTable.getGroup(),timeTable.getDay(),timeTable.getTime(),timeTable.getVenue());

		return timeTable;
	}
	
	public ArrayList<TimeTableModel> NewTimeTableList(int newindexNumber) {
		ArrayList<TimeTableModel> timeTableList=new ArrayList<TimeTableModel>();
		Scanner sc = new Scanner(System.in);
		TimeTableModel timeTable= new TimeTableModel();
		System.out.println("Please select the class type\n"+
				"1. Lecture\n"+
				"2. Tutorial\n"+
				"3. Experimental\n"+
				"-1 Exit\n"
				);
		
		int classType;
		classType = sc.nextInt();
		while(classType!=-1) {
			switch(classType) {
			case 1:
				timeTable = newTimetableView(newindexNumber,"Lec");
				System.out.println(
						"\n1. Confirm\n"+
						"2. Cancel\n"+
						"Please select an option:" );
				int select = sc.nextInt();
				switch(select) {
				case 1:
					timeTableList.add(timeTable);
					break;
				case 2:
					break;
				}
				break;
			case 2:
				timeTable = newTimetableView(newindexNumber,"Tut");
				System.out.println(
						"\n1. Confirm\n"+
						"2. Cancel\n"+
						"Please select an option:" );
				int select2 = sc.nextInt();
				switch(select2) {
				case 1:
					timeTableList.add(timeTable);
					break;
				case 2:
					break;
				}
			break;
			case 3:
				timeTable = newTimetableView(newindexNumber,"Lab");
				System.out.println(
						"\n1. Confirm\n"+
						"2. Cancel\n"+
						"Please select an option:" );
				int select3 = sc.nextInt();
				switch(select3) {
				case 1:
					timeTableList.add(timeTable);
					break;
				case 2:
					break;
				}
				break;
			default:
				System.out.print("Invalid input!");
				break;		
			}
			
			System.out.println("Please select the class type\n"+
					"1. Lecture\n"+
					"2. Tutorial\n"+
					"3. Experimental\n"+
					"-1 Exit\n"
					);
			classType = sc.nextInt();
		}
	return timeTableList;
	}
		
	
		public void addCourseView() {
			int newindexNumber=DBContext.CourseModelListing.get(DBContext.CourseModelListing.size()-1).getIndexNumber()+1;
			
			//int newindexNumber=CourseRepo.GetNewCourseIndexNumber();   Can't call CourseRepo.GetNewCourseIndexNumber()
			ArrayList<TimeTableModel> NewTimeTableList=null;
			CourseModel newCourse = new CourseModel();
			Scanner sc = new Scanner(System.in);
			System.out.println(".............Add New Course....... ");
			System.out.println("Index Number: "+newindexNumber);
			newCourse.setIndexNumber(newindexNumber);
			
			System.out.print("CourseCode:");
			String CourseCode;
			CourseCode = sc.next();
			newCourse.setCourseCode(CourseCode);
			
			System.out.print("CourseName:");
			String CourseName;
			CourseName = sc.next();
			newCourse.setCourseName(CourseName);
			
			System.out.println("Please Select CourseType: \n"+
					"1. CORE\n" + 
					"2. UNRESTRICTED ELECTIVE\n"+
					"3. PRESCRIBED");
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
					default:
						System.out.print("Invalid input!");				
					}
						
			System.out.print("School:");
			String School;
			School = sc.next();
			newCourse.setSchool(School);
			
			System.out.print("Vacancy:");
			int Vacancy;
			Vacancy = sc.nextInt();
			newCourse.setVacancy(Vacancy);
			
			System.out.print("AU:");
			int AU;
			AU = sc.nextInt();
			newCourse.setAU(AU);
			
			System.out.println("Time Table");
			NewTimeTableList=NewTimeTableList(newCourse.getIndexNumber());
			
			
			
			System.out.println("New Cause Review:");
			printCourse(newCourse);
			System.out.println("Time Table:");
			//Timetable update;
			PrintTimeTableList(NewTimeTableList);
			
			System.out.println(
					"\n1. Confirm Update\n"+
					"2. Cancel\n"+
					"Please select an option:" );
			int select3 = sc.nextInt();
			switch(select3) {
			case 1:
				CourseRepo.add(newCourse);
				System.out.println("/nSuccessfully Added Course!/n");
				StartAdminPage();
				break;
			case 2:
				break;
			}
		}	
						

		
		public void updateCourseView(CourseModel CModel) {
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println(
			"............Update Menu.............\n"+
			"1. Course Code\n"+
			"2. Course Name\n"+
			"3. Course Type\n"+
			"4. School\n"+
			"5. AU\n"+
			"6. Vacancy\n"+
			"7. Time Table\n"+
			"-1. Exit\n");
			System.out.println ("--------------------------------------------------------------");
			
			
			System.out.println("Please select the update item: ");
			int choice;
			choice = sc.nextInt();
			
			while(choice<8 & choice!=-1)
			{
				switch (choice) {
				case 1: 
					System.out.println("Please enter new Course Code:");
					String CourseCode;
					CourseCode = sc.next();
					System.out.println(
							"\n1. Confirm\n"+
							"2. Cancel\n"+
							"Please select an option:" );
					int select = sc.nextInt();
					switch(select) {
						case 1:
							CModel.setCourseCode(CourseCode);
							System.out.println("Successfully updated the Course Code!\n");
							printCourse(CModel);
							break;
						case 2:
							updateCourseView(CModel);
							break;
						}
					break;
				case 2:
					System.out.println("Please enter new Course Name:");
					String CourseName;
					CourseName = sc.next();
					
					System.out.println(
							"\n1. Confirm\n"+
							"2. Cancel\n"+
							"Please select an option:" );
					int select1 = sc.nextInt();
					switch(select1) {
					case 1:
						CModel.setCourseName(CourseName);
						System.out.println("Successfully updated the Course Name!\n");
						printCourse(CModel);
						break;
					case 2:
						updateCourseView(CModel);
						break;
					}
					

					break;
				case 3:
					System.out.println("Please Select new Course Type:\n"+
							"1. CORE\n" + 
							"2. UNRESTRICTED ELECTIVE\n"+
							"3. PRESCRIBED");
							int a;
							a = sc.nextInt();
							String courseType = CModel.getCourseType();
							switch (a) {
							case 1:
								courseType="CORE";
								break;
							case 2: 
								courseType="UNRESTRICTED ELECTIVE";
								break;
							case 3:
								courseType="PRESCRIBED";
								break;
							default:
								System.out.print("Invalid input!");
							}
	
							System.out.println(
									"\n1. Confirm\n"+
									"2. Cancel\n"+
									"Please select an option:" );
							int select2 = sc.nextInt();
							switch(select2) {
								case 1:
									CModel.setCourseType(courseType);
								System.out.println("Successfully updated the Course Type!\n");
								printCourse(CModel);
								break;
							case 2:
								updateCourseView(CModel);
								break;
							}
							
						break;
				case 4:			
					System.out.print("Please enter new School:");
					String School;
					School = sc.next();
					
					
					
					System.out.println(
							"\n1. Confirm\n"+
							"2. Cancel\n"+
							"Please select an option:\n" );
					int select3 = sc.nextInt();
					switch(select3) {
						case 1:
							CModel.setSchool(School);
							System.out.println("Successfully updated the School!\n");
							printCourse(CModel);
							break;
					case 2:
						updateCourseView(CModel);
						break;
					}
					break;
					
				case 5:			
					System.out.print("Please enter new AU:");
					int AU;
					AU = sc.nextInt();
					
					

					System.out.println(
							"\n1. Confirm\n"+
							"2. Cancel\n"+
							"Please select an option:\n" );
					int select4 = sc.nextInt();
					switch(select4) {
						case 1:
							CModel.setAU(AU);
							System.out.println("Successfully updated the AU!\n");
							printCourse(CModel);
							break;
					case 2:
						updateCourseView(CModel);
						break;
					}
					break;
	
				case 6:
					System.out.print("Please enter new Vacancy:");
					int Vacancy;
					Vacancy = sc.nextInt();
					
					System.out.println(
							"\n1. Confirm\n"+
							"2. Cancel\n"+
							"Please select an option:\n" );
					int select5 = sc.nextInt();
					switch(select5) {
						case 1:
							CModel.setVacancy(Vacancy);
							System.out.println("Successfully updated the Vacancy!\n");
							printCourse(CModel);
							break;
					case 2:
						updateCourseView(CModel);	
						break;
					}
					break;

				case 7:
					ArrayList<TimeTableModel> NewtimeTableList=new ArrayList<TimeTableModel>();
					System.out.println(".......Current Time Table..........\n");
					ArrayList<TimeTableModel> CMtimeTableList=TimeTableRepo.readTimeTableByCourseIndex(CModel.getIndexNumber());
					PrintTimeTableList(CMtimeTableList);
					System.out.println(".......Please Enter Time Table..........\n");
					NewtimeTableList=NewTimeTableList(CModel.getIndexNumber());
					System.out.println(".......Time Table Review..........\n");
					PrintTimeTableList(NewtimeTableList);
					
					System.out.println(
							"\n1. Confirm Update(Old timetable will be deleted)\n"+
							"2. Cancel\n"+
							"Please select an option:\n" );
					int select7 = sc.nextInt();
					switch(select7) {
					case 1:
						//Remove old time table list
						//add new time table list
						System.out.println("/nSuccessfully updated New Time Table!/n");
						updateCourseView(CModel);
						break;
					case 2:
						updateCourseView(CModel);
						break;
					}
					break;
					
				}	
				System.out.println(
						"............Update Menu.............\n"+
						"1. Course Code\n"+
						"2. Course Name\n"+
						"3. Course Type\n"+
						"4. School\n"+
						"5. AU\n"+
						"6. Vacancy\n"+
						"7. Time Table\n"+
						"-1. Exit\n");
						System.out.println ("--------------------------------------------------------------");
						
						
						System.out.println("Please select the update item: ");
						//int choice;
						choice = sc.nextInt();	
			}
				DBRepo.CourseRepo.update(CModel);
				StartAdminPage();
			
		}
					
		
		
		
		public void EditAccessTime() {
			//return existing student access period
			System.out.println("Please select the option:\n"+
			"1. Edit the access period.\n"+
			"-1. Exit.");
	
			Scanner sc = new Scanner(System.in);
			int choice=sc.nextInt();
			switch(choice) {
			case 1:
				System.out.println("....................................");
				System.out.println("Access Start Time:");
				int StartTime=sc.nextInt();
				System.out.println("Access End Time:");
				int EndTime=sc.nextInt();
				break;
			case 2:
				StartAdminPage();
				break;
			}
			
		}
		
		public void AddStudent() {
			Scanner sc = new Scanner(System.in);
			System.out.print("..............Add a Student.....................");
			//UserModel newStudent=new UserModel();
			//StudentModel;
			//int newindexNumber=DBContext.CourseModelListing.get(DBContext.CourseModelListing.size()-1).getIndexNumber()+1;
			//remenber to set  UserType, AccessTimeStart and AccessTimeEnd;

			System.out.println("FirstName:");
			String FirstName = sc.next();
			System.out.println("LastName:");
			String LastName = sc.next();
			System.out.print("Gender:");
			String Gender = sc.next();
			System.out.println("Nationality:");
			String Nationality = sc.next();
			System.out.println("PhoneNumber:");
			String PhoneNumber = sc.next();
			System.out.println("Email:");
			String Email = sc.next();
			System.out.println("MatricNumber:");
			String MatricNumber = sc.next();
			System.out.println("UserID:");
			String UserID = sc.next();
			System.out.println("Password:");
			String Password = sc.next();
	
		}
		
	
		
		public void PrintSLByCour() {}
	
}
