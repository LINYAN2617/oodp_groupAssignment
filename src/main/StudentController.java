package main;

import java.util.*;

import model.AllocatedListingModel;
import model.CourseModel;
import model.StudentModel;
import model.TimeTableModel;
import model.WaitListingModel;
import DBRepo.AllocatedListingRepo;
import DBRepo.CourseRepo;
import DBRepo.StudRepo;
import DBRepo.TimeTableRepo;
import DBRepo.WaitListingRepo;
import NotificationService.EmailService;
import NotificationService.Notification;
public class StudentController {
	
	private static StudentModel LoggedStudent;
	private static Notification EmailService = new EmailService();
	public StudentController(StudentModel LoggedStudent) {
		
		this.LoggedStudent=LoggedStudent;
	}
	
	
	public void StartStudPage() {
	
		System.out.println ("Successfully logged in! ");
		System.out.println ("Welcome (Student) " + LoggedStudent.getFullName());
		System.out.println ("--------------------------------------------------------------");

		int option = 0;
		while (option != -1) {
			System.out.println("\nPlease select an option:\n" +
							"1. Add courses\n"+
							"2. Drop courses\n"+
							"3. Check Courses Registered\n" + 
							"4. Check Vacancies Available\n"+
							"5. Change Index Number of Course\n"+
							"6. Swop Index Number with Another Student\n"+
							"-1: To exit\n"+
							"Enter your choice: ");
			Scanner scan = new Scanner(System.in);
			int courseIndex;
			
			
			 if(scan.hasNextInt())  
			 {  
				 option = scan.nextInt();  
				
			 }   
			 else {  
	            System.out.println("\nPlease entered Integer value. ");  
	            System.out.println("---------------------------------------- \n");
			 }  
		
			
			switch (option) {
			case 1:
				System.out.println ("Enter the Index Number of the course:");
				courseIndex = scan.nextInt();
				CourseModel returnCModel =  DBRepo.CourseRepo.GetCourseByIndexNumber(courseIndex);
				
				if(returnCModel == null) {
					System.out.println("Course Not Found, Please try again!");
				}else {
					returnCModel.getIndexNumber();
					int taken = AllocatedListingRepo.GetTakenSlotByCourseIndex(courseIndex);
					int available = returnCModel.getVacancy() - taken;
					System.out.format("|%-13s |%-40s | %-13s | %-13s | %-20s| %-4s |\n", "Course Code", "Course Name","Max Vacancy","Available Slot", "School", "AU");
					System.out.format("|%-13s |%-40s | %-13s | %-13s | %-20s| %-4s |\n", returnCModel.getCourseCode(), returnCModel.getCourseName(),returnCModel.getVacancy(), available ,returnCModel.getSchool(),returnCModel.getAU());
				
					if(getConfirmation(scan, "register") == 1)  {
						int isRegistered = CheckIsregisteredCourse(returnCModel.getIndexNumber(),LoggedStudent);
						if (isRegistered == -1) {
							
							Stud_addCourse(LoggedStudent.getUserID(), returnCModel);
						
							
							System.out.println("\n ---------------------------------------------------------------");
						}else if(isRegistered == 1) {
							System.out.println("You already registered the course");

							System.out.println("---------------------------------------------- \n");
						}else if(isRegistered == 2) {
							System.out.println("You already registered the course and is under the waiting list");

							System.out.println("---------------------------------------------- \n");
						}else {
							System.out.println("You already registered up to 21 AU!");

							System.out.println("---------------------------------------------- \n");
					
						}
					}
				}
				break;					
			case 2: 
				DisplayRegisteredListReturnAU();
				System.out.println("Select course to drop: ");
				int dropIndex = scan.nextInt(); 
				Stud_dropCourse(LoggedStudent.getUserID(), dropIndex);
				break;
				
			case 3:
				int totalAU = DisplayRegisteredListReturnAU();
				
				DisplayWaitlist();
				System.out.println("Total registered AU: " + totalAU + "\n");
				break;
				
			case 4:
				System.out.println ("Enter the Index Number of the course:");
				courseIndex = scan.nextInt();
				CourseModel returnCModelVa =  DBRepo.CourseRepo.GetCourseByIndexNumber(courseIndex);
				if(returnCModelVa == null) {
					System.out.println("Course Not Found, Please try again!");
				}else {
					
					int taken = AllocatedListingRepo.GetTakenSlotByCourseIndex(courseIndex);
					int available = returnCModelVa.getVacancy() - taken;
					System.out.format("|%-13s |%-40s | %-13s | %-13s | %-20s| %-4s |\n", "Course Code", "Course Name","Max Vacancy","Available Slot", "School", "AU");
					System.out.format("|%-13s |%-40s | %-13s | %-13s | %-20s| %-4s |\n", returnCModelVa.getCourseCode(), returnCModelVa.getCourseName(),returnCModelVa.getVacancy(), available ,returnCModelVa.getSchool(),returnCModelVa.getAU());
				}
					break;
			case 5:
				boolean isChanged = false;
				int currentIndex = 0;
				while (!isChanged) {
					System.out.println ("Enter the current Index Number of the course (-1 return to student main page):");
					currentIndex = scan.nextInt();
					if (currentIndex == -1) {break;}
					System.out.println ("Enter the new Index Number of the course:");
					int newIndex = scan.nextInt();
					isChanged = changeIndex(LoggedStudent.getUserID(), currentIndex, newIndex, scan);
				}
				break;
			case 6:
				boolean isSwopped = false;
				while (!isSwopped) {
					
					isSwopped = SwopIndex(LoggedStudent.getUserID(),scan);
				}
				break;
				
				
			}
		}
		System.out.println("Program exiting......");
		
	}
	
	
	public void Stud_addCourse(String StudID, CourseModel CModel) {
		//Search for index number (pull info)
		//FindCourse(courseIndex);
		String returnMessage = "";
		//show course info
		Date date = new Date();
		
		if(CModel.getVacancy() <= AllocatedListingRepo.GetTakenSlotByCourseIndex(CModel.getIndexNumber())){
			WaitListingModel wlist = new WaitListingModel(CModel.getIndexNumber(),StudID,date);
			DBRepo.WaitListingRepo.add(wlist);
			LoggedStudent.AddWaitListing(wlist);
			
			returnMessage = "Course Vancancy is full, your request is added in waiting list.";
			System.out.println(returnMessage);
			
			ArrayList<String> SendInfo = new ArrayList<String>();
			SendInfo.add(LoggedStudent.Email);
			SendInfo.add("Course registration - Successfully apply for queue in Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")" );
			SendInfo.add("Dear " + LoggedStudent.getFullName() +  ", \n\n" + "Successfully apply for queue in Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")");
			
			EmailService.send(SendInfo);
			
		}else{
			AllocatedListingModel alist = new AllocatedListingModel(CModel.getIndexNumber(),StudID,date);
			AllocatedListingRepo.add(alist);
			LoggedStudent.AddAllocateListing(alist);
			
			returnMessage = "Successfully registered! Please wait while we sending an email notification...\n";
			
			System.out.println(returnMessage);
			
			ArrayList<String> SendInfo = new ArrayList<String>();
			SendInfo.add(LoggedStudent.Email);
			SendInfo.add("Course registration - Successfully register to Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")" );
			SendInfo.add("Dear " + LoggedStudent.getFullName() +  ", \n\n" + "Successfully register to Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")");
			
			
			EmailService.send(SendInfo);
			returnMessage = "An email notification sent!";
			

		};
		
		//Confirm to register(push to add)
	}
	
	public static void Stud_dropCourse(String StudentID, int dropIndex) {
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		AList = AllocatedListingRepo.readAllocateListingByStudentID(StudentID);
		for (int i = 0; i<AList.size(); i++) {
			if (AList.get(i).getCourseIndex() == dropIndex) {
				AllocatedListingRepo.remove(AList.get(i));
				LoggedStudent.RemoveAllocateListing(dropIndex);
				PushWaitListToAllocateList(dropIndex);
				System.out.println("Course has dropped. An email notification is sent. \n");
				return;
			}
		}
		
		
		System.out.println("Invaild index,please try again.\n");
	}
	
	public static void Stud_ChangeCourse(StudentModel Student, int dropIndex, int addIndex) {
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		AList = AllocatedListingRepo.readAllocateListingByStudentID(Student.getUserID());
		for (int i = 0; i<AList.size(); i++) {
			if (AList.get(i).getCourseIndex() == dropIndex) {
				AllocatedListingRepo.remove(AList.get(i));
				break;
			}
		}
		Student.RemoveAllocateListing(dropIndex);
		
		Date date = new Date();
		AllocatedListingModel newAL = new AllocatedListingModel(addIndex, Student.getUserID(),date);
		AllocatedListingRepo.add(newAL);
		Student.AddAllocateListing(newAL);
		
	}
	
	public int DisplayRegisteredListReturnAU() {
		int totalAU =0;
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		AList = LoggedStudent.GetAllocateListing();
		
		System.out.format("|%-15s | %-4s | %-15s | %-10s | %10s | \n", "Course Code", "AU","Course Type", "Index", "Status");
		for (int i = 0; i<AList.size(); i++) {
			int courseIndex = AList.get(i).getCourseIndex();
			CourseModel courselist = DBRepo.CourseRepo.GetCourseByIndexNumber(courseIndex);
			
			System.out.format("|%-15s | %-4d | %-15s | %-10d | %10s | \n", courselist.getCourseCode(), courselist.getAU(),courselist.getCourseType(), courseIndex, "Registered");
			totalAU+=courselist.getAU();
		}
		return totalAU;
	}
	
	public void DisplayWaitlist() {
		ArrayList<WaitListingModel> wList = new ArrayList<WaitListingModel>();
		wList =  LoggedStudent.GetWaitListing();
		
		for (int i = 0; i<wList.size(); i++) {
			int courseIndex = wList.get(i).getCourseIndex();
			CourseModel courselist = DBRepo.CourseRepo.GetCourseByIndexNumber(courseIndex);
			System.out.format("|%-15s | %-4d | %-15s | %-10d | %10s | \n", courselist.getCourseCode(), courselist.getAU(),courselist.getCourseType(), courseIndex, "Waitlist");

		}
	}
	
	public int CheckIsregisteredCourse(int CourseIndex, StudentModel Stud) {
		int AU = 0;
		for (AllocatedListingModel al : Stud.GetAllocateListing()) { 
			if (al.getCourseIndex() == CourseIndex) { 
				return 1;
			} 
			AU += CourseRepo.GetCourseByIndexNumber(al.getCourseIndex()).getAU();
		}
		
		for (WaitListingModel wl : Stud.GetWaitListing()) { 
			if (wl.getCourseIndex() == CourseIndex) { 
				return 2;
			} 

			AU += CourseRepo.GetCourseByIndexNumber(wl.getCourseIndex()).getAU();
		}
		
		if(AU >= 21) {
			return 3;
		}
		
		return -1;
	}
	
	public boolean changeIndex(String StudentID, int currentIndex, int newIndex, Scanner scan) {
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		AList = LoggedStudent.AllocateListing;
		boolean isChanged = false;
		int isRegister = CheckIsregisteredCourse(currentIndex,LoggedStudent);
		if (isRegister == -1) {
			System.out.println("You haven't registered course class " + currentIndex + "\n");
		}
		else if (isRegister == 2) {
			System.out.println("You're at Waitlist and you have't successfully registered course class " + currentIndex + "\n");
		}
		else {
			CourseModel currentClass = CourseRepo.GetCourseByIndexNumber(currentIndex);
			CourseModel newClass = CourseRepo.GetCourseByIndexNumber(newIndex);

			if (newClass == null) {
				System.out.println("Course class" + newIndex + " not found.\n");
			}else if(!newClass.getCourseCode().equalsIgnoreCase(currentClass.getCourseCode())) {
				System.out.format("Course class %d and Course Class %d are not for the same course. \n\n", currentIndex, newIndex);
			}
			else if (newClass.getVacancy() <= AllocatedListingRepo.GetTakenSlotByCourseIndex(newIndex)) {
				System.out.println("Course class " + newIndex + " Vancancy is full.\n");
			}else {
				//Print Current Index info
				CourseModel CModel = CourseRepo.GetCourseByIndexNumber(currentIndex);
				System.out.println("Current Index Number: " + currentIndex);
				DisplayTimetableByCourse(CModel);
				//Print New Index info
				System.out.println("New Index Number: " + newIndex);
				DisplayTimetableByCourse(CModel);

				if (getConfirmation(scan, "change") == 1) {
					Stud_ChangeCourse(LoggedStudent, currentIndex, newIndex);
					
					System.out.println("Course class has been changed from " + currentIndex + " to " + newIndex);
					isChanged = true;
				}
			}
		}
		return isChanged;
	}
	
	public boolean SwopIndex(String StudentID,Scanner scan) {
		
		boolean isSwopped = false;
		
		System.out.println("Enter your Course index number (-1 return to main page): ");
		int CourseIndex = scan.nextInt();
		if (CourseIndex == -1) {
			return true;
		}
		
		
		int isRegister = CheckIsregisteredCourse(CourseIndex,LoggedStudent);
		if (isRegister == -1) {
			System.out.println("You haven't registered course class " + CourseIndex + "\n");
			return false;
		}
		else if (isRegister == 2) {
			System.out.println("You're at Waitlist and you have't successfully registered course class " + CourseIndex + "\n");
			return false;
		}
		else {
			System.out.println("Enter peer's UserID: ");
			String PeerUserID = scan.next();
			System.out.println("Enter peer's Password: ");
			String PeerPassword= scan.next();

			LoginController LoginHandling = new LoginController();
			StudentModel checkIsStud = LoginHandling.validateStud(PeerUserID,PeerPassword);
			
			
			
			if(checkIsStud == null) {
				System.out.println ("Failed for validation try again");
				return false;
				
			}else {
				System.out.println("Enter index number to swap: ");
				int PeerCourseIndex = scan.nextInt();
				int check = -1;
				CourseModel studentClass = CourseRepo.GetCourseByIndexNumber(CourseIndex);
				CourseModel peerClass = CourseRepo.GetCourseByIndexNumber(PeerCourseIndex);
				check = CheckIsregisteredCourse(PeerCourseIndex, checkIsStud);
			
				if (check == -1 || check ==3 ){
					System.out.println(PeerUserID + " hasn't registered course class " + PeerCourseIndex + "\n");
				}
				else if(!studentClass.getCourseCode().equalsIgnoreCase(peerClass.getCourseCode())) {
					System.out.format("Course class %d and Course Class %d are not for the same course. \n\n", CourseIndex, PeerCourseIndex);
				}
				else {
					System.out.println("Student 1 - Matric: " + LoggedStudent.getMatricNumber() + " Index number: " + CourseIndex);
					DisplayTimetableByCourse(studentClass);
					System.out.println("Student 2 - Matric: " + checkIsStud.getMatricNumber() + " Index number: " + PeerCourseIndex);
					DisplayTimetableByCourse(peerClass);
					if (getConfirmation(scan, "swap") == 1) {
						//change student 1 index to student 2 index
					
						Stud_ChangeCourse(LoggedStudent, CourseIndex, PeerCourseIndex);
						Stud_ChangeCourse(checkIsStud, CourseIndex, PeerCourseIndex);
						
						System.out.println(LoggedStudent.getMatricNumber() + 
								" - Index Number " + CourseIndex +
								" has been successfully swopped with " + checkIsStud.getMatricNumber() +
								" - Index Number " + PeerCourseIndex);
						isSwopped = true;
					}
				}
			}
		}
		return isSwopped;
	}
	
	public void DisplayTimetableByCourse(CourseModel CModel) {
		
		ArrayList<TimeTableModel> courseTimetable = TimeTableRepo.GetTimeTableByCourseIndex(CModel.getIndexNumber());
				
	
		if(courseTimetable != null) { 
			System.out.format("|%-12s | %-40s | %-5s | %-12s | %-10s | %-10s | %-5s |\n", 
					"Course Code", "Course Name","Group", "Class Type", "Day", "Time", "Venue");
			
			for(int i = 0; i < courseTimetable.size(); i++) {
				System.out.format("|%-12s | %-40s | %-5s | %-12s | %-10s | %-10s | %-5s |\n", CModel.getCourseCode(), CModel.getCourseName(),courseTimetable.get(i).getGroup(), courseTimetable.get(i).getType(),courseTimetable.get(i).getDay(),courseTimetable.get(i).getTime(),courseTimetable.get(i).getVenue());
			}
			
		}else {
			System.out.println("There is no table time for Course Name (" + CModel.getIndexNumber() + ") Course Index ("+CModel.getCourseName() +")") ;
		}
	
	}
	
	public int getConfirmation(Scanner scan, String action) {
		System.out.println("\nPlease select an option:\n" +
				"1. Confirm to " + action + "?\n"+
				"2. Cancel\n");
		return scan.nextInt();
	}
	
	public static void PushWaitListToAllocateList(int CourseIndex) {
		CourseModel CModel = CourseRepo.GetCourseByIndexNumber(CourseIndex);
		int taken = AllocatedListingRepo.GetTakenSlotByCourseIndex(CourseIndex);
		if(CModel.getVacancy() - taken > 0) {
		  WaitListingModel  WModel = WaitListingRepo.GetLastWaitListingByCourseIndex(CourseIndex);
		  if(WModel != null) {
			  WaitListingRepo.remove(WModel);
			  
			  StudentModel Stud = StudRepo.GetStudentByStudID(WModel.getUserID());
			  
			  Date date = new Date();
			  AllocatedListingModel AModel = new AllocatedListingModel(WModel.getCourseIndex(), WModel.getUserID(), date);
			  AllocatedListingRepo.add(AModel);
			  
			  ArrayList<String> SendInfo_CurrentUser = new ArrayList<String>();
			  SendInfo_CurrentUser.add(LoggedStudent.Email);
			  SendInfo_CurrentUser.add("Course registration - Successfully drop the Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")" );
			  SendInfo_CurrentUser.add("Dear " + LoggedStudent.getFullName() +  ", \n\n" + "Successfully drop the Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")");
			  EmailService.send(SendInfo_CurrentUser);
			  
			  ArrayList<String> SendInfo_WaitListUser = new ArrayList<String>();
			  SendInfo_WaitListUser.add(Stud.getEmail());
			  SendInfo_WaitListUser.add("Course registration - You are allocaed to the Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")" );
			  SendInfo_WaitListUser.add("Dear " + LoggedStudent.getFullName() +  ", \n\n" + "A slot is open for your queue. \nYou are successfully register to Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")");
			  EmailService.send(SendInfo_WaitListUser);
			  
			  
		  }
		}
	}
	
}
