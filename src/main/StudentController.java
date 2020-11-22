package main;

import java.io.IOException;
import java.util.*;

import model.AllocatedListingModel;
import model.CourseModel;
import model.StudentModel;
import model.WaitListingModel;
import DBRepo.AllocatedListingRepo;

import DBRepo.WaitListingRepo;
import DBRepo.CourseRepo;
import DBRepo.DBContext;

import DBRepo.FileHandle;
public class StudentController {
	
	private static StudentModel LoggedStudent;
	
	
	public StudentController(DBContext loadData,StudentModel LoggedStudent) {
		
		StudentController.LoggedStudent=LoggedStudent;
	}
	
	public void StartStudPage() {
	
		System.out.println ("Successfully logged in! ");
		System.out.println ("Welcome (Student) " + LoggedStudent.getFullName());
		System.out.println ("--------------------------------------------------------------");

		int option = 0;
		while (option != -1) {
			System.out.println("Please select an option:\n" +
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
			option = scan.nextInt();
			switch (option) {
			case 1:
				System.out.println ("Enter the Index Number of the course:");
				courseIndex = scan.nextInt();
				CourseModel returnCModel =  DBRepo.CourseRepo.GetCourseByIndexNumber(courseIndex);
				
				if(returnCModel == null) {
					System.out.println("Course Not Found, Please try again!");
				}else {
					returnCModel.getIndexNumber();
					System.out.format("|%-13s | %-40s | %-10s | %-20s| %-4s |\n", "Course Code", "Course Name","Vacancy", "School", "AU");
					System.out.format("|%-13s | %-40s | %-10s | %-20s| %-4s |\n", returnCModel.getCourseCode(), returnCModel.getCourseName(),returnCModel.getVacancy(), returnCModel.getSchool(),returnCModel.getAU());
					System.out.println("\nPlease select an option:\n" +
							"1. Confirm to register?\n"+
							"2. Cancel\n");
				
					if(scan.nextInt() == 1) {
						int isRegistered = CheckIsregisteredCourse(returnCModel.getIndexNumber(), LoggedStudent.getUserID());
						if (isRegistered == -1) {
							
							
							String returnMsg = Stud_addCourse(LoggedStudent.getUserID(), returnCModel);
							System.out.println(returnMsg);
						}else if(isRegistered == 1) {
							System.out.println("You already registered the course");

							System.out.println("---------------------------------------------- \n");
						}else {
							System.out.println("You already registered the course and is under the waiting list");

							System.out.println("---------------------------------------------- \n");
						}
					}
				}
				break;					
			case 2: 
				DisplayRegisteredListReturnAU(LoggedStudent.getUserID());
				System.out.println("Select course to drop: ");
				int dropIndex = scan.nextInt(); 
				Stud_dropCourse(LoggedStudent.getUserID(), dropIndex);
				break;
				
			case 3:
				int totalAU = DisplayRegisteredListReturnAU(LoggedStudent.getUserID());
				
				DisplayWaitlist(LoggedStudent.getUserID());
				System.out.println("Total registered AU: " + totalAU + "\n");
				break;
				
			case 4:
				System.out.println ("Enter the Index Number of the course:");
				courseIndex = scan.nextInt();
				CourseModel returnCModelVa =  DBRepo.CourseRepo.GetCourseByIndexNumber(courseIndex);
				if(returnCModelVa == null) {
					System.out.println("Course Not Found, Please try again!");
				}else {
					System.out.format("|%-13s | %-40s | %-10s | %-20s| %-4s |\n", "Course Code", "Course Name","Vacancy", "School", "AU");
					System.out.format("|%-13s | %-40s | %-10s | %-20s| %-4s |\n", returnCModelVa.getCourseCode(), returnCModelVa.getCourseName(),returnCModelVa.getVacancy(), returnCModelVa.getSchool(),returnCModelVa.getAU());
				}
					break;
			case 5:
				
				break;
			case 6:
				
				break;
				
			}
		}
		System.out.println("Program exiting......");
	}
	
	
	public String Stud_addCourse(String StudID, CourseModel CModel) {
		//Search for index number (pull info)
		//FindCourse(courseIndex);
		String returnMessage = "";
		//show course info
		Date date = new Date();
		
		if(CModel.getVacancy() <= AllocatedListingRepo.readAllocateListingByCourseIndex(CModel.getIndexNumber()).size()){
			WaitListingModel wlist = new WaitListingModel(CModel.getIndexNumber(),StudID,date);
			DBRepo.WaitListingRepo.addWaitListing(wlist);
			returnMessage = "Course Vancancy is full, your request is added in waiting list.";
		}else{
			AllocatedListingModel alist = new AllocatedListingModel(CModel.getIndexNumber(),StudID,date);
			AllocatedListingRepo.add(alist);
			returnMessage = "Successfully registered!";
		};
		
		return returnMessage;
		//Confirm to register(push to add)
	}
	
	public void Stud_dropCourse(String StudentID, int dropIndex) {
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		AList = AllocatedListingRepo.readAllocateListingByStudentID(StudentID);
		for (int i = 0; i<AList.size(); i++) {
			if (AList.get(i).getCourseIndex() == dropIndex) {
				AllocatedListingRepo.remove(AList.get(i));
				System.out.println("Course has dropped. \n");
				return;
			}
		}
		System.out.println("Invaild index,please try again.\n");
	}
	
	public int DisplayRegisteredListReturnAU(String StudentID) {
		int totalAU =0;
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		AList = AllocatedListingRepo.readAllocateListingByStudentID(StudentID);
		
		System.out.format("|%-15s | %-4s | %-15s | %-10s | %10s | \n", "Course Code", "AU","Course Type", "Index", "Status");
		for (int i = 0; i<AList.size(); i++) {
			int courseIndex = AList.get(i).getCourseIndex();
			CourseModel courselist = DBRepo.CourseRepo.GetCourseByIndexNumber(courseIndex);
			
			System.out.format("|%-15s | %-4d | %-15s | %-10d | %10s | \n", courselist.getCourseCode(), courselist.getAU(),courselist.getCourseType(), courseIndex, "Registered");
			totalAU+=courselist.getAU();
		}
		return totalAU;
	}
	
	public void DisplayWaitlist(String StudentID) {
		ArrayList<WaitListingModel> wList = new ArrayList<WaitListingModel>();
		wList = DBRepo.WaitListingRepo.readAllWaitListingByStudentID(StudentID);
		
		for (int i = 0; i<wList.size(); i++) {
			int courseIndex = wList.get(i).getCourseIndex();
			CourseModel courselist = DBRepo.CourseRepo.GetCourseByIndexNumber(courseIndex);
			System.out.format("|%-15s | %-4d | %-15s | %-10d | %10s | \n", courselist.getCourseCode(), courselist.getAU(),courselist.getCourseType(), courseIndex, "Waitlist");

		}
	}
	
	public int CheckIsregisteredCourse(int CourseIndex, String StudentID) {
		
		if(AllocatedListingRepo.readAllocateListingByStudentIDByCourseIndex(CourseIndex, StudentID).size() >0) {
			return 1;
		}else if(DBRepo.WaitListingRepo.readAllWaitListingByStudentIDByCourseIndex(CourseIndex, StudentID).size() >0) {
			return 2;
		};
		
		return -1;
	}
}
