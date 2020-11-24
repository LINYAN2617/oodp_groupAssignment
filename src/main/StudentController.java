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
	private static LoginController LoginHandle = new LoginController();
	public StudentController(StudentModel LoggedStudent) {
		
		this.LoggedStudent=LoggedStudent;
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
	public void StartStudPage() {
	
	
		int option = 0;
		while (option != -1) {

			System.out.println ("-------------------------Student Menu------------------------");
			System.out.println("\nPlease select an option:\n" +
					"1. Add courses\n"+
					"2. Drop courses\n"+
					"3. Check Courses Registered\n" + 
					"4. Check Vacancies Available\n"+
					"5. Change Index Number of Course\n"+
					"6. Swap Index Number with Another Student\n"+
					"7. Log out\n"+
					"-1: To exit\n");
	
			System.out.println("Enter your choice: ");
			option = validationInt();
			if ( option > 7 || option < -1 || option == 0) {
				System.out.println ("Invalid choice, please try again.");continue;
			}

			
			switch (option) {
			case 1:
				int courseIndex = 0;
				while (courseIndex != -1) {
					System.out.println ("Enter the Index Number of the course you want to add (-1 return to main page):");
					courseIndex = validationInt();
					if (courseIndex == -1) {
						break;
					}else if (courseIndex == -2) {
						System.out.println("Invalid Index number, please try again.");
						continue;
					}
					CourseModel returnCModel =  CourseRepo.GetCourseByIndexNumber(courseIndex);
					
					if(returnCModel == null) {
						System.out.println("Course Not Found, Please try again!");
					}else {
						returnCModel.getIndexNumber();
						int taken = AllocatedListingRepo.GetTakenSlotByCourseIndex(courseIndex);
						int available = returnCModel.getVacancy() - taken;
						System.out.format("|%-13s |%-40s | %-13s | %-13s | %-20s| %-4s |\n", "Course Code", "Course Name","Max Vacancy","Available Slot", "School", "AU");
						System.out.format("|%-13s |%-40s | %-13s | %-13s | %-20s| %-4s |\n", returnCModel.getCourseCode(), returnCModel.getCourseName(),returnCModel.getVacancy(), available ,returnCModel.getSchool(),returnCModel.getAU());
					
						int isConfirmed = getConfirmation("register");
						while (isConfirmed == -2) {
							System.out.println("Invalid input, please try again!");
							isConfirmed = getConfirmation("register");
							
						}
						
						if(isConfirmed == 1)  {
							int isRegistered = CheckCourseAvailability(returnCModel.getIndexNumber(),LoggedStudent,false);
							if (isRegistered == -1) {
								
								Stud_addCourse(LoggedStudent.getUserID(), returnCModel);
							
								System.out.println("\n ---------------------------------------------------------------");
							}else if(isRegistered == 1) {
								System.out.println("You already registered the course - " + returnCModel.getCourseCode() + " " + returnCModel.getCourseName());
	
								System.out.println("---------------------------------------------- \n");
							}else if(isRegistered == 2) {
								System.out.println("You already registered the course and is under the waiting list");
	
								System.out.println("---------------------------------------------- \n");
							}else if(isRegistered == 4){
								System.out.println("You have selected a course Index will crash on you existing courses");
								System.out.println("---------------------------------------------- \n");
								
							}else {
								System.out.println("You already registered up to 21 AU!");
	
								System.out.println("---------------------------------------------- \n");
						
							}
						}
					}
				}
				break;					
			case 2: 
				int dropIndex =0;
				while (dropIndex != -1) {
					DisplayRegisteredListReturnAU();
					System.out.println("Select course to drop (-1 return to main page): ");
					dropIndex = validationInt();
					if (dropIndex != -2){
						Stud_dropCourse(LoggedStudent.getUserID(), dropIndex);
						
					}
					else {
						System.out.println("Invalid Index number.Please re-enter."); 
					}
					
				}
				break;
				
			case 3:
				int totalAU = DisplayRegisteredListReturnAU();
				
				DisplayWaitlist();
				System.out.println("Total registered AU: " + totalAU + "\n");

				break;

			case 4:
				int VaIndex = 0;
				while (VaIndex != -1) {
				
					
					System.out.println ("Enter the Index Number of the course (-1 return to main page):");
					 VaIndex = validationInt();
					
						while (VaIndex == -2) {
							System.out.println ("Invalid input, please re-enter (-1 return to main page):");
							VaIndex = validationInt();
						}
						CourseModel returnCModelVa =  CourseRepo.GetCourseByIndexNumber(VaIndex);
						
						
						if(returnCModelVa == null) {
							if(VaIndex != -1) {

								System.out.println("Course Not Found, Please try again!");
							}
						}else {
							
							int taken = AllocatedListingRepo.GetTakenSlotByCourseIndex(VaIndex);
							int available = returnCModelVa.getVacancy() - taken;
							System.out.format("|%-13s |%-40s | %-13s | %-13s | %-20s| %-4s |\n", "Course Code", "Course Name","Max Vacancy","Available Slot", "School", "AU");
							System.out.format("|%-13s |%-40s | %-13s | %-13s | %-20s| %-4s |\n", returnCModelVa.getCourseCode(), returnCModelVa.getCourseName(),returnCModelVa.getVacancy(), available ,returnCModelVa.getSchool(),returnCModelVa.getAU());
						}
					

					}
				
					break;
			case 5:
				boolean isChanged = false;
				
				while (!isChanged) {
					System.out.println ("Enter the current Index Number of the course (-1 return to student main page):");
					int currentIndex = validationInt();
					if (currentIndex == -1) {break;}
					else if(currentIndex == -2) {System.out.println ("Invalid input.");continue;}
					System.out.println ("Enter the new Index Number of the course:");
					int newIndex = validationInt();
					if(newIndex == -2) {System.out.println ("Invalid input.");continue;}
					isChanged = changeIndex(LoggedStudent.getUserID(), currentIndex, newIndex);
				}
				break;
			case 6:
				boolean isSwapped = false;
				while (!isSwapped) {
					
					isSwapped = SwapIndex(LoggedStudent.getUserID());
				}
				break;
			case 7:
				LoginHandle.logout();
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
			WaitListingModel wlist = new WaitListingModel(CModel.getIndexNumber(),CModel.getCourseCode(),StudID,date);
			WaitListingRepo.add(wlist);
			LoggedStudent.AddWaitListing(wlist);
			
			returnMessage = "Course Vancancy is full, your request is added in waiting list.";
			System.out.println(returnMessage);
			
			ArrayList<String> SendInfo = new ArrayList<String>();
			SendInfo.add(LoggedStudent.Email);
			SendInfo.add("Course registration - Successfully apply for queue in Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")" );
			SendInfo.add("Dear " + LoggedStudent.getFullName() +  ", \n\n" + "Successfully apply for queue in Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")");
			
			EmailService.send(SendInfo);
			
		}else{
			AllocatedListingModel alist = new AllocatedListingModel(CModel.getIndexNumber(),CModel.getCourseCode(),StudID,date);
			AllocatedListingRepo.add(alist);
			LoggedStudent.AddAllocateListing(alist);
			
			ArrayList<WaitListingModel> wlist = WaitListingRepo.GetWaitListModelByCourseCodeByStudID(CModel.getCourseCode(),StudID);
			if(wlist.size() >0) {
				for(WaitListingModel wl : wlist) {

					WaitListingRepo.remove(wl);
				}
			}
			LoggedStudent.WaitListing.remove(wlist);
			
			
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

				CourseModel CModel = CourseRepo.GetCourseByIndexNumber(dropIndex);
				PushWaitListToAllocateList(CModel);
				
				 
				  ArrayList<String> SendInfo_CurrentUser = new ArrayList<String>();
				  SendInfo_CurrentUser.add(LoggedStudent.Email);
				  SendInfo_CurrentUser.add("Course registration - Successfully drop the Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")" );
				  SendInfo_CurrentUser.add("Dear " + LoggedStudent.getFullName() +  ", \n\n" + "Successfully drop the Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")");
				  EmailService.send(SendInfo_CurrentUser);
				  
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
		CourseModel courselist = CourseRepo.GetCourseByIndexNumber(addIndex);
		AllocatedListingModel newAL = new AllocatedListingModel(addIndex,courselist.getCourseCode(), Student.getUserID(),date);
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
			CourseModel courselist = CourseRepo.GetCourseByIndexNumber(courseIndex);
			
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
			CourseModel courselist = CourseRepo.GetCourseByIndexNumber(courseIndex);
			System.out.format("|%-15s | %-4d | %-15s | %-10d | %10s | \n", courselist.getCourseCode(), courselist.getAU(),courselist.getCourseType(), courseIndex, "Waitlist");

		}
	}
	public int CheckIsCrashTimeTable(int CourseIndex, StudentModel Stud) {
		CourseModel newCModel = CourseRepo.GetCourseByIndexNumber(CourseIndex);
		
		for (AllocatedListingModel al : Stud.GetAllocateListing()) { 
		
				CourseModel CurrCModel = CourseRepo.GetCourseByIndexNumber(al.getCourseIndex());
				ArrayList<TimeTableModel> currtm = CurrCModel.GetTimeTableListing();
				for(TimeTableModel tm : currtm) {
					
					int timeStartHour = Integer.parseInt(tm.getTimeStart().substring(0,2));
					int timeStartMin = Integer.parseInt(tm.getTimeStart().substring(2,4));
					int timeEndHour = Integer.parseInt(tm.getTimeEnd().substring(0,2));
					int timeEndMin = Integer.parseInt(tm.getTimeEnd().substring(2,4));
					
					for(TimeTableModel tm2 : newCModel.GetTimeTableListing()) {
						
						
						int CtimeStartHour = Integer.parseInt(tm2.getTimeStart().substring(0,2));
						int CtimeStartMin = Integer.parseInt(tm2.getTimeStart().substring(2,4));
						int CtimeEndHour = Integer.parseInt(tm2.getTimeEnd().substring(0,2));
						int CtimeEndMin = Integer.parseInt(tm.getTimeEnd().substring(2,4));
						
						if(tm.getDay().equals(tm2.getDay()) 
						   && ((timeStartHour <= CtimeStartHour && timeEndHour >= CtimeStartHour)
						  || (timeStartHour  >= CtimeStartHour && timeStartHour  <= CtimeEndHour))
						) {
							
							return 4;
						}
					}
				}
		
		}
		return -1;
		
	}
	
	public int CheckCourseAvailability(int CourseIndex, StudentModel Stud, boolean isforchangeIndex) {
		int AU = 0;
		CourseModel newCModel = CourseRepo.GetCourseByIndexNumber(CourseIndex);
		if(!isforchangeIndex) {
				
			for (AllocatedListingModel al : Stud.GetAllocateListing()) { 
				if (al.getCourseCode().equals(newCModel.getCourseCode())) { 
					
					return 1;
				}else {
					if(CheckIsCrashTimeTable(CourseIndex, Stud) == 4) {
						return 4;
					};
				}
				AU += CourseRepo.GetCourseByIndexNumber(al.getCourseIndex()).getAU();
			}

			
			for (WaitListingModel wl : Stud.GetWaitListing()) { 

				if (wl.getCourseIndex() == CourseIndex) { 
					return 2;
				} 
	
				AU += CourseRepo.GetCourseByIndexNumber(wl.getCourseIndex()).getAU();
			}
		

		}else {
			for (AllocatedListingModel al : Stud.GetAllocateListing()) { 
				if (!al.getCourseCode().equals(newCModel.getCourseCode())) { 
					
					if(CheckIsCrashTimeTable(CourseIndex, Stud) == 4) {
						return 4;
					};
				}
				AU += CourseRepo.GetCourseByIndexNumber(al.getCourseIndex()).getAU();
			}
			
		}
		
		if(AU >= 21) {
			return 3;
		}

		return -1;
	}
	
	public boolean changeIndex(String StudentID, int currentIndex, int newIndex) {
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		AList = LoggedStudent.AllocateListing;
		boolean isChanged = false;
		int isRegister = CheckCourseAvailability(currentIndex,LoggedStudent,false);
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
				int avail = CheckCourseAvailability(newIndex,LoggedStudent,true);
				if(avail == 4) {
					System.out.println("Course class " + newIndex + " time table is crashed with your other course.\n");
				}else {
						
					//Print Current Index info
					CourseModel CModel = CourseRepo.GetCourseByIndexNumber(currentIndex);
					System.out.println("Current Index Number: " + currentIndex);
					DisplayTimetableByCourse(CModel);
					//Print New Index info
					System.out.println("New Index Number: " + newIndex);
					DisplayTimetableByCourse(CModel);
	
					int isConfirmed = getConfirmation("change");
					
					while (isConfirmed == -2) {
						System.out.println("Invalid input, please try again.");
						isConfirmed = getConfirmation("change");
					}
					if (isConfirmed == 1) {
						Stud_ChangeCourse(LoggedStudent, currentIndex, newIndex);
						
						System.out.println("Course class has been changed from " + currentIndex + " to " + newIndex);
						isChanged = true;
					}

				}
			}
		}
		return isChanged;
	}
	
	public boolean SwapIndex(String StudentID) {
		
		boolean isSwapped = false;
		
		System.out.println("Enter your Course index number (-1 return to main page): ");
		int CourseIndex = validationInt();
		if (CourseIndex == -1) {
			return true;
		}
		while (CourseIndex == -2) {
			System.out.println("Invalid input, re-enter your Course index number (-1 return to main page): ");
			CourseIndex = validationInt();
		}
		
		int isRegister = CheckCourseAvailability(CourseIndex,LoggedStudent,false);
		if (isRegister == -1) {
			System.out.println("You haven't registered course class " + CourseIndex + "\n");
			return false;
		}
		else if (isRegister == 2) {
			System.out.println("You're at Waitlist and you have't successfully registered course class " + CourseIndex + "\n");
			return false;
		}
		else {
			Scanner scan = new Scanner(System.in);
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
				check = CheckCourseAvailability(PeerCourseIndex, checkIsStud,false);
			
				if (check == -1 || check ==3 ){
					System.out.println(PeerUserID + " hasn't registered course class " + PeerCourseIndex + "\n");
				}
				else if(!studentClass.getCourseCode().equalsIgnoreCase(peerClass.getCourseCode())) {
					System.out.format("Course class %d and Course Class %d are not for the same course. \n\n", CourseIndex, PeerCourseIndex);
				}
				else {
					System.out.println("Student 1 - Matric: " + LoggedStudent.getMatricNumber() + ", Index number: " + CourseIndex );
					System.out.println("TimeTable" );
					DisplayTimetableByCourse(studentClass);
					System.out.println("Student 2 - Matric: " + checkIsStud.getMatricNumber() + " Index number: " + PeerCourseIndex);
					System.out.println("TimeTable" );
					DisplayTimetableByCourse(peerClass);
					int isConfirmed = getConfirmation("Swap");
					while (isConfirmed == -2) {
						System.out.println("Invalid input, please try again.");
						isConfirmed = getConfirmation("Swap");
					}
					if (isConfirmed == 1) {
						//change student 1 index to student 2 index
					
						Stud_ChangeCourse(LoggedStudent, CourseIndex, PeerCourseIndex);
						Stud_ChangeCourse(checkIsStud, CourseIndex, PeerCourseIndex);

						System.out.println("Please wait...");
						String message = LoggedStudent.getMatricNumber() + 
								" - Index Number " + CourseIndex +
								" has been successfully Swapped with " + checkIsStud.getMatricNumber() +
								" - Index Number " + PeerCourseIndex;
						
						  ArrayList<String> SendInfo_CurrentUser = new ArrayList<String>();
						  SendInfo_CurrentUser.add(LoggedStudent.Email);
						  SendInfo_CurrentUser.add("Course registration - Successfully swap the Course Index (" + CourseIndex + ") to Course Index ("+ PeerCourseIndex +"), Course Name (" + studentClass.getCourseName() + ")" );
						  SendInfo_CurrentUser.add("Dear " + LoggedStudent.getFullName() +  ", \n\n" + message);
						  EmailService.send(SendInfo_CurrentUser);
						  
						  ArrayList<String> SendInfo_Peer = new ArrayList<String>();
						  SendInfo_Peer.add(checkIsStud.Email);
						  SendInfo_Peer.add("Course registration - The Course Index (" + PeerCourseIndex + ") is swapped by " + LoggedStudent.getMatricNumber() + " " + LoggedStudent.getFullName() + " to Course Index ("+ CourseIndex +"), Course Name (" + studentClass.getCourseName() + ")" );
						  SendInfo_Peer.add("Dear " + checkIsStud.getFullName() +  ", \n\n" + message);
						  EmailService.send(SendInfo_Peer);
						  
						System.out.println(message);
						System.out.println("\nAn email notification is sent to both student");
						isSwapped = true;
					}
				}
			}
		}
		return isSwapped;
	}
	
	public void DisplayTimetableByCourse(CourseModel CModel) {
		
		ArrayList<TimeTableModel> courseTimetable = TimeTableRepo.readTimeTableByCourseIndex(CModel.getIndexNumber());
				
	
		if(courseTimetable.size()>0) { 
			System.out.format("|%-12s | %-40s | %-5s | %-12s | %-10s | %-10s | %-5s |\n", 
					"Course Code", "Course Name","Group", "Class Type", "Day", "Time", "Venue");
			
			for(int i = 0; i < courseTimetable.size(); i++) {
				System.out.format("|%-12s | %-40s | %-5s | %-12s | %-10s | %-10s | %-5s |\n", CModel.getCourseCode(), CModel.getCourseName(),courseTimetable.get(i).getGroup(), courseTimetable.get(i).getType(),courseTimetable.get(i).getDay(),courseTimetable.get(i).getTimeStart() + "-" + courseTimetable.get(i).getTimeEnd(),courseTimetable.get(i).getVenue());
			}
			
		}else {
			System.out.println("There is no table time for Course Name (" + CModel.getIndexNumber() + ") Course Index ("+CModel.getCourseName() +")") ;
		}
	
	}
	
	
	public int getConfirmation (String action) {
		System.out.println("\nPlease select an option:\n" +
				"1. Confirm to " + action + "?\n"+
				"2. Cancel\n"+
				"Enter your choice: ");
		return validationInt();
	}
	
	
	public static void PushWaitListToAllocateList(CourseModel CModel) {
		int taken = AllocatedListingRepo.GetTakenSlotByCourseIndex(CModel.getIndexNumber());
		if(CModel.getVacancy() - taken > 0) {
		  WaitListingModel  WModel = WaitListingRepo.GetLastWaitListingByCourseIndex(CModel.getIndexNumber());
		  if(WModel != null) {
			 
			  			  
			  StudentModel Stud = StudRepo.GetStudentByStudID(WModel.getUserID());
			  
			  Date date = new Date();
			  AllocatedListingModel AModel = new AllocatedListingModel(WModel.getCourseIndex(),CModel.getCourseCode(), WModel.getUserID(), date);
			  AllocatedListingRepo.add(AModel);
			  
			  ArrayList<WaitListingModel> wlist = WaitListingRepo.GetWaitListModelByCourseCodeByStudID(CModel.getCourseCode(),WModel.getUserID());
				if(wlist.size() >0) {
					for(WaitListingModel wl : wlist) {
	
						WaitListingRepo.remove(wl);
					}
				}
			 			  
			  ArrayList<String> SendInfo_WaitListUser = new ArrayList<String>();
			  SendInfo_WaitListUser.add(Stud.getEmail());
			  SendInfo_WaitListUser.add("Course registration - You are allocaed to the Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")" );
			  SendInfo_WaitListUser.add("Dear " + LoggedStudent.getFullName() +  ", \n\n" + "A slot is open for your queue. \nYou are successfully register to Course Index (" + CModel.getIndexNumber() + "), Course Name (" + CModel.getCourseName() + ")");
			  EmailService.send(SendInfo_WaitListUser);
			  
			  
		  }
		}
	}
	
}
