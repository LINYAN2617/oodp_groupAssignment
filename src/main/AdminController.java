package main;
import model.DBContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import dbrepo.AllocatedListingRepo;
import dbrepo.CourseRepo;
import dbrepo.StudRepo;
import dbrepo.TimeTableRepo;

import java.util.List;

import model.AdminModel;
import model.AllocatedListingModel;
import model.CourseModel;
import model.StudentModel;
import model.TimeTableModel;
public class AdminController{
	private static AdminModel LoggedAdmin;
	private LoginController LoginHandle = new LoginController();

	public AdminController(AdminModel LoggedAdmin) {

		this.LoggedAdmin=LoggedAdmin;
	}
	
	public int validationInt() {
		Scanner input = new Scanner(System.in);
		int option = -2;
		while (input.hasNextInt()) {
			option = input.nextInt();
			break;
		}
		return option;
	}
	
	
	public void StartAdminPage() {
	
		int option = 0;
		while (option != -1) {
			
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
		
		option = validationInt();
		if ( option > 8 || option < -1 || option == 0) {
			System.out.println ("Invalid choice, please try again.");continue;
		}
		
		switch(option) {
				case 1:
					editAccessTime();
					break;
				case 2:
					addStudent();
					break;
				case 3:
					addCourseView();
					break;
				case 4:
					
					updateCourseView();
					break;
				case 5:
					checkSlot();
					break;
				case 6:
					printSLByIndex();
					break;
				case 7:
					printSLByCour();
					break;
				case 8:
					LoginHandle.logout();
					break;
			}
				
		}
	}
	
	public void printTimeTableList(ArrayList<TimeTableModel> timeTableList) {
		//ArrayList<TimeTableModel> timeTableList=DBRepo.TimeTableRepo.readTimeTableByCourseIndex(indexnumber);
	
		//System.out.println("timeTableList Size: "+timeTableList.size());
		System.out.format("|%-13s | %-5s | %-5s | %-5s | %-10s| %-4s |\n", "Course Index", "Type","Group","Day", "Time", "Venue");
		
		for (int i=0;i<timeTableList.size();i++) {
			System.out.format("|%-13s | %-5s | %-5s | %-5s | %-10s| %-4s |\n", timeTableList.get(i).getIndexNumber(),timeTableList.get(i).getType(),timeTableList.get(i).getGroup(),timeTableList.get(i).getDay(),timeTableList.get(i).getTimeStart() + "-" + timeTableList.get(i).getTimeEnd(),timeTableList.get(i).getVenue());
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
		
		System.out.println("Please Enter the Time Start (HHMM): ");
		String time=sc.next();
		timeTable.setTimeStart(time);
		// set input format
		System.out.println("Please Enter the Time End (HHMM): ");
		String time2=sc.next();
		timeTable.setTimeEnd(time2);
		
		System.out.println("Please Enter the Venue: ");
		String Venue=sc.next();
		timeTable.setVenue(Venue);
		
		System.out.format("\n|%-13s | %-5s | %-5s | %-5s | %-10s| %-5s |\n", "Course Index", "Type","Group","Day", "Time", "Venue");
		System.out.format("|%-13s | %-5s | %-5s | %-5s | %-10s| %-5s |\n",timeTable.getIndexNumber(),timeTable.getType(),timeTable.getGroup(),timeTable.getDay(),timeTable.getTimeStart() + "-" + timeTable.getTimeEnd() ,timeTable.getVenue());
 
		return timeTable;
	}
	
	public ArrayList<TimeTableModel> newTimeTableList(int newindexNumber) {
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
				System.out.println("Invalid input!");
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
			
			int newindexNumber = CourseRepo.getNewCourseIndexNumber();  
			ArrayList<TimeTableModel> NewTimeTableList=null;
			CourseModel newCourse = new CourseModel();
			Scanner sc = new Scanner(System.in);
			System.out.println(".............Add New Course....... ");
			System.out.println("Index Number: " + newindexNumber);
			newCourse.setIndexNumber(newindexNumber);
			
			System.out.println("CourseCode:");
			String CourseCode;
			CourseCode = sc.next();
			newCourse.setCourseCode(CourseCode);
			
			System.out.println("CourseName:");
			String CourseName;
			CourseName = sc.next();
			CourseName += sc.nextLine();
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
						System.out.println("Invalid input!");				
					}
						
			System.out.println("School:");
			String School;
			School = sc.next();
			School += sc.nextLine();
			newCourse.setSchool(School);
			
			System.out.println("Vacancy:");
			int Vacancy;
			Vacancy = sc.nextInt();
			newCourse.setVacancy(Vacancy);
			
			System.out.println("AU:");
			int AU;
			AU = sc.nextInt();
			newCourse.setAU(AU);
			
			System.out.println("Time Table");
			NewTimeTableList=newTimeTableList(newCourse.getIndexNumber());

			System.out.println("New Cause Review:");
			printCourse(newCourse);
			System.out.println("Time Table:");
			//Timetable update;
			printTimeTableList(NewTimeTableList);
			
			System.out.println(
					"\n1. Confirm Update\n"+
					"2. Cancel\n"+
					"Please select an option:" );
			int select3 = sc.nextInt();
			switch(select3) {
			case 1:
				CourseRepo.add(newCourse);
				TimeTableRepo.add(NewTimeTableList);
				System.out.println("\nSuccessfully Added Course!\n");
				StartAdminPage();
				break;
			case 2:
				break;
			}
		}	
						

		
		public void updateCourseView() {
			
			boolean disable = LoginHandle.checkAccessPeriod(LoggedAdmin);
			
			Scanner sc = new Scanner(System.in);
			System.out.println(".............Update Course....... ");
			CourseModel CModel  = null;
			boolean loopInputIndex = false;
			while (!loopInputIndex) {
				int courseIndex = 0;
				System.out.println ("Please Enter the Course Index Number:  (-1 return to main page):");
				courseIndex = validationInt();
				if (courseIndex == -1) {
					return;
				}else if (courseIndex == -2) {
					System.out.println("Invalid Index number, please try again.");
					
				}else {
					CModel = CourseRepo.getCourseByIndexNumber(courseIndex);
					
					if(CModel == null) {
						System.out.println("Course not found, please try again!\n");
						 
					}else {
						
						loopInputIndex = true;
					}
				}
			
			
			}
			
			System.out.format("|%-13s | %-40s | %-20s | %-10s | %-20s| %-4s |\n", "Course Code", "Course Name","Course Type","Vacancy", "School", "AU");
			System.out.format("|%-13s | %-40s | %-20s | %-10s | %-20s| %-4s |\n", CModel.getCourseCode(), CModel.getCourseName(),CModel.getCourseType(),CModel.getVacancy(), CModel.getSchool(),CModel.getAU());
			
			boolean loopUpdateMenu = false;
			
			while (!loopUpdateMenu) {
				
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
			
			
			int choice = 0;
			boolean checkIndex = false;
			while (!checkIndex) {

				System.out.println("Please select the update item: ");
				choice = validationInt();
				if (choice == -1) {
					return;
				}else if (choice == -2) {
					System.out.println("Invalid Index number, please try again.");
					
				}else if(choice < 8 && choice > 0) {
					
						checkIndex = true;
					
				}else {

					System.out.println("Please select a valid option!");
				}
				
			}
			
				int select =0;
				switch (choice) {
				case 1: 
					if(disable) {

						System.out.println("Access period open! Edit Course Code is disabled.");
						break;
					}
					System.out.println("Please enter new Course Code:");
					String CourseCode;
					CourseCode = sc.next();
					System.out.println(
							"\n1. Confirm\n"+
							"2. Cancel\n"+
							"Please select an option:" );

					select = sc.nextInt();
					if(select == 1) {
						CModel.setCourseCode(CourseCode);
						System.out.println("Successfully updated the Course Code!\n");
						printCourse(CModel);
					}
					
					break;
				case 2:
					
					System.out.println("Please enter new Course Name:");
					String CourseName;
					CourseName = sc.next();
					CourseName += sc.nextLine();
					
					System.out.println(
							"\n1. Confirm\n"+
							"2. Cancel\n"+
							"Please select an option:" );
					select = sc.nextInt();
					if(select == 1) {
						CModel.setCourseName(CourseName);
						System.out.println("Successfully updated the Course Name!\n");
						printCourse(CModel);
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
								System.out.println("Invalid input!");
							}
	
							System.out.println(
									"\n1. Confirm\n"+
									"2. Cancel\n"+
									"Please select an option:" );
							select = sc.nextInt();
							if(select == 1) {
								CModel.setCourseType(courseType);
								System.out.println("Successfully updated the Course Type!\n");
								printCourse(CModel);
								
							}
							
						break;
				case 4:			
					System.out.println("Please enter new School:");
					String School;
					School = sc.next();
					School += sc.nextLine();
					
					
					System.out.println(
							"\n1. Confirm\n"+
							"2. Cancel\n"+
							"Please select an option:\n" );
				
					select = sc.nextInt();
					if(select == 1) {
						CModel.setSchool(School);
						System.out.println("Successfully updated the School!\n");
						printCourse(CModel);
					}
					
					break;
					
				case 5:			
					System.out.println("Please enter new AU:");
					int AU;
					AU = sc.nextInt();
					
					System.out.println(
							"\n1. Confirm\n"+
							"2. Cancel\n"+
							"Please select an option:\n" );
					select = sc.nextInt();
					if(select == 1) {
						CModel.setAU(AU);
						System.out.println("Successfully updated the AU!\n");
						printCourse(CModel);
						
					}
					
					break;
	
				case 6:
					System.out.println("Please enter new Vacancy:");
					int Vacancy;
					Vacancy = sc.nextInt();
					
					System.out.println(
							"\n1. Confirm\n"+
							"2. Cancel\n"+
							"Please select an option:\n" );
					select = sc.nextInt();
					if(select == 1) {
						CModel.setVacancy(Vacancy);
						System.out.println("Successfully updated the Vacancy!\n");
						printCourse(CModel);
						
					}
					
					break;

				case 7:
					
					if(disable) {

						System.out.println("Access period open! Edit Time Table is disabled.");
						break;
					}
					ArrayList<TimeTableModel> NewtimeTableList=new ArrayList<TimeTableModel>();
					System.out.println(".......Current Time Table..........\n");
					ArrayList<TimeTableModel> CMtimeTableList=TimeTableRepo.readTimeTableByCourseIndex(CModel.getIndexNumber());
					printTimeTableList(CMtimeTableList);
					System.out.println(".......Please Enter Time Table..........\n");
					NewtimeTableList=newTimeTableList(CModel.getIndexNumber());
					System.out.println(".......Time Table Review..........\n");
					printTimeTableList(NewtimeTableList);
					
					System.out.println(
							"\n1. Confirm Update(Old timetable will be deleted)\n"+
							"2. Cancel\n"+
							"Please select an option:\n" );
				
					select = sc.nextInt();
					if(select == 1) {
						TimeTableRepo.update(CMtimeTableList, NewtimeTableList);
						System.out.println("\nSuccessfully updated New Time Table!\n");
					}
					
					break;
					
				}	
				
				CourseRepo.update(CModel);

			}
		}
					
		
		
		
		public void editAccessTime() {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Scanner sc = new Scanner(System.in);
			Date OldStart, OldEnd;
			OldStart= DBContext.student.get(0).getAccessTimeStart();
			OldEnd= DBContext.student.get(0).getAccessTimeEnd();
			System.out.println("Current Student Access Time:");
			System.out.println("Access Star Time: "+OldStart);
			System.out.println("Access End Time: "+OldEnd);
			System.out.println("....................................");
			
			
			boolean checkinput = false;
			while (!checkinput) {
				System.out.println("Please select the option:\n"+
						"1. Edit the access period.\n"+
						"-1. Exit.");

				int choice=validationInt();
				
				if(choice == 1) {
					checkinput = true;
				}else if(choice == -1) {
					return;
				}else {
					System.out.println ("Invalid choice, please try again.\n");
				}
				
			}
			
		
			
				System.out.println("....................................");
				System.out.println("Access Start Time(yyyy-MM-dd HH:mm:ss):");
				String StartTime;
				Date start=null;
				boolean founderror = true;
				while(founderror) {
					try {
						StartTime=sc.next();
						StartTime+=sc.nextLine();
						start = formatter.parse(StartTime);
						founderror = false;
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
				
				founderror = true;
				System.out.println("Access End Time(yyyy-MM-dd HH:mm:ss):");
				String EndTime;
				Date end=null;
				while(founderror) {
					try {
						EndTime=sc.next();
						EndTime +=sc.nextLine();
						end = formatter.parse(EndTime);
						founderror = false;
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
				
				Date date =new Date();
				if (end.compareTo(start)<=0) {
					System.out.println("The Access End Time can't earlier than the Access Start Time.");
					System.out.println("Please try again!\n");
					editAccessTime();
				}
				else if (end.compareTo(date)<=0) {
					System.out.println("The Access End Time is earlier than current time. Please confirm:\n"+
					"1. Yes\n"+
					"2. No (return to top level)");
					int option=sc.nextInt();
					switch(option) {
					case 1:
						StudRepo.updateAccessTime(start, end);
						System.out.println("The Access Period Successfully Updated!\n");
						break;
					case 2:
						editAccessTime();
						break;
					}
				}
				else {
					StudRepo.updateAccessTime(start, end);
					System.out.println("The Access Period Successfully Updated!\n");
				}
		}

		
		public void addStudent() {
			Scanner sc = new Scanner(System.in);
			System.out.println("..............Add a Student.....................");
			System.out.println("MatricNumber:");
			String MatricNumber = sc.next();
			System.out.println("UserID:");
			String UserID = sc.next();
			boolean update=true;
			
			for(int i=0; i<DBContext.student.size();i++) {
				if (DBContext.student.get(i).getMatricNumber().equals(MatricNumber)&&DBContext.student.get(i).getUserID().equals(UserID)) {
					System.out.println("MatricNumber and UserID is same to another account! This Student already exists.");
					update=false;
					break;
				}
				else if (DBContext.student.get(i).getMatricNumber().equals(MatricNumber)) {
					System.out.println("This MatricNumber already exists.");
					update=false;
					break;
				}
				else if (DBContext.student.get(i).getUserID().equals(UserID)) {
					System.out.println("This UserID already exists.");
					update=false;
					break;
				}
			}
			if (!update) 
			{

				return ;
			}
			System.out.println("Password:");
			String Password = LoginHandle.encrypt(sc.next());
			System.out.println("FirstName:");
			String FirstName;
			FirstName = sc.next();
			FirstName += sc.nextLine();
			//newStudent.
			System.out.println("LastName:");
			String LastName;
			LastName = sc.next();
			LastName += sc.nextLine();
			System.out.println("Gender:");
			String Gender = sc.next();
			System.out.println("Nationality:");
			String Nationality = sc.next();
			System.out.println("PhoneNumber:");
			String PhoneNumber = sc.next();
			System.out.println("Email:");
			String Email = sc.next();
			
			char UserType = 'S' ;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date AccessTimeStart=DBContext.student.get(0).getAccessTimeStart();
			Date AccessTimeEnd=DBContext.student.get(0).getAccessTimeEnd();
			StudentModel newStudent= new StudentModel(UserID,Password,FirstName,LastName,Gender,Nationality,UserType,AccessTimeStart,AccessTimeEnd);
			newStudent.setEmail(Email);
			newStudent.setMatricNumber(MatricNumber);
			newStudent.setPhoneNumber(PhoneNumber);
			
			StudRepo.add(newStudent);
			

			System.out.println("Student successfully Added!\n");
			
			System.out.println(
					"1. Add other student\n"+
					"2. Back\n"+
					"Please select an option:" );
			int select = validationInt();
			if (select == 2) {
				return;
			}else if(select == 1) {
				addStudent();
			}
			while (select == -2) {
				System.out.println("Invalid input, re-enter your option: ");
				select = validationInt();
			}
			
			
			
		}
		
	public void checkSlot() {
		ArrayList<AllocatedListingModel> StuList=new ArrayList<AllocatedListingModel>();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the course index number (-1 to return):");
		int IndexNumber=sc.nextInt();
		
		
		if(IndexNumber == -1) {
			return;
		}else {
			
			if(checkIndex(IndexNumber) == false) {
				checkSlot(); 
			}	else {
				StuList=AllocatedListingRepo.readAllocateListingByCourseIndex(IndexNumber);
				CourseModel CModel = CourseRepo.getCourseByIndexNumber(IndexNumber);
				int slot;
				slot= CModel.getVacancy()-StuList.size();
				System.out.println("The amount of slot available for this course: "+ slot+"\n");
			}
		}
	
		System.out.println(
				"1. Check Other Index Number\n"+
				"2. Back\n"+
				"Please select an option:" );
		
		int select = validationInt();
		if (select == 2) {
			return;
		}else if(select == 1) {
			checkSlot();
		}
		while (select == -2) {
			System.out.println("Invalid input, re-enter your option: ");
			select = validationInt();
		}
		
		
	}
	
	public void printSLByIndex() {
			List<String> StuIDList=new ArrayList<String>();
			ArrayList<AllocatedListingModel> StuList=new ArrayList<AllocatedListingModel>();
			StudentModel SModel=null;
			
			Scanner sc = new Scanner(System.in);
			
			boolean checkinput = false;
			int IndexNumber =0;
			while (checkinput == false) {
				System.out.println("Please enter the course index number:");
				IndexNumber=validationInt();
				
				if(checkIndex(IndexNumber) == true) {
					checkinput = true;
				}
				
			}
			
			if(checkinput) {
				StuList=AllocatedListingRepo.readAllocateListingByCourseIndex(IndexNumber);
				for (int i=0;i<StuList.size();i++) {
					StuIDList.add(StuList.get(i).getUserID());
				}
			
				if(StuIDList.size() == 0) {
					System.out.println("No student register this course.");
				}
				else {
					System.out.println("..............The Student List of IndexNumber "+IndexNumber+"..................");
					System.out.format("|%-20s | %-7s | %-10s |\n", "Student Name", "Gender","Nationality");
					for (int i=0;i<StuIDList.size();i++) {
						SModel=StudRepo.getStudentByStudID(StuIDList.get(i));
						System.out.format("|%-20s | %-7s | %-10s |\n", SModel.getFullName(), SModel.getGender(),SModel.getNationality());
					}
					System.out.println("");
				}
			}
			
			System.out.println(
					"1. Check Other Index Number\n"+
					"2. Back\n"+
					"Please select an option:" );
						
			int select = validationInt();
			if (select == 2) {
				return;
			}else if(select == 1) {
				printSLByIndex();
			}
			
			
		}
		
			
		
		public void printSLByCour() {
			
			List<String> StuIDList=new ArrayList<String>();
			ArrayList<AllocatedListingModel> StuList=new ArrayList<AllocatedListingModel>();
			StudentModel SModel=null;

			Scanner sc = new Scanner(System.in);
			
			boolean checkCourseCode = false;
			String courseCode = "";
			while (checkCourseCode == false) {
				System.out.println("Please enter the course code:");
				courseCode=sc.next();
				
				if(checkCusCodeExist(courseCode) == true) {
					checkCourseCode = true;
				}
				
			}
			
			
			if(checkCourseCode) {
				StuList=AllocatedListingRepo.readAllocateListingByCourseCode(courseCode);
				for (int i=0;i<StuList.size();i++) {
					StuIDList.add(StuList.get(i).getUserID());
				}
			
				if(StuIDList.size() == 0) {
					System.out.println("No student register this course.");
				}
				else {
					System.out.println("..............The Student List of Course Code "+courseCode+"..................");
					System.out.format("|%-20s | %-7s | %-10s |\n", "Student Name", "Gender","Nationality");
					for (int i=0;i<StuIDList.size();i++) {
						SModel=StudRepo.getStudentByStudID(StuIDList.get(i));
						System.out.format("|%-20s | %-7s | %-10s |\n", SModel.getFullName(), SModel.getGender(),SModel.getNationality());
					}
					System.out.println("");
				}
			}
			
			System.out.println(
					"1. Check Other Course Code\n"+
					"2. Back\n"+
					"Please select an option:" );
				
			int select = validationInt();
			if (select == 2) {
				return;
			}else if(select == 1) {
				printSLByCour();
			}
			
			
		}
		
		public boolean checkIndex(int CusIndexnumber) {
			CourseModel CModel = CourseRepo.getCourseByIndexNumber(CusIndexnumber);
			boolean result;
			if(CModel == null) {
				System.out.println("Course not found, please try again!\n");
				result=false;
			}
			else {
				result=true;
			}
			return result;
		}
		
		public boolean checkCusCodeExist(String courseCode) {
			CourseModel CusMode = CourseRepo.getCourseByCourseCode(courseCode);
			boolean result;
			if(CusMode == null) {
				System.out.println("Course not found, please try again!\n");
				result=false;
			}
			else {
				result=true;
			}
			return result;
		}
	
	
}
