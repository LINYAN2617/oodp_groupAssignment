package DBRepo;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import model.AdminModel;
import model.AllocatedListingModel;
import model.CourseModel;
import model.StudentModel;
import model.TimeTableModel;
import model.WaitListingModel;

import model.NotificationSettingModel;
public class DBContext {
	
	public static FileHandle File= new FileHandle();
	public static ArrayList<StudentModel> student;
	public static ArrayList<AdminModel> admin;
	public static ArrayList<WaitListingModel> WaitListing;
	public static ArrayList<AllocatedListingModel> AllocatedListing;
	public static ArrayList<CourseModel> CourseModelListing;
	public static ArrayList<TimeTableModel> TimeTableListing;
	public static ArrayList<NotificationSettingModel> NotificationSettingListing;
	
	public static final String WLFileName = "FileDB/WaitingListing.txt"; 
	public static final String ALFileName = "FileDB/AllocatedListing.txt"; 
	public static final String TBFileName = "FileDB/TimeTable.txt"; 
	public static final String CourseFileName = "FileDB/Course.txt"; 
	public static final String UserFileName = "FileDB/User.txt"; 
	public static final String NotificationFile = "FileDB/NotificationSetting.txt"; 
	public static final String SEPARATOR = "|";
	
	public DBContext() {
		try {
			
			WaitListing = readWaitListing(WLFileName);
			AllocatedListing = readAllocateListing(ALFileName);
			TimeTableListing = readTimeTableListing(TBFileName);
			CourseModelListing =  readCourseListing(CourseFileName);
			NotificationSettingListing =  readNotificationSettingListing(NotificationFile);
			
			ArrayList readuser = new ArrayList();
			readuser = readUsers(UserFileName);
			student = (ArrayList<StudentModel>) readuser.get(1);
			admin = (ArrayList<AdminModel>) readuser.get(0);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("Error while loading files");
		}
	}
	
	
	// ----------- Load Data ------------------------// 
	
	public static ArrayList readUsers(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)FileHandle.read(filename);
		ArrayList<AdminModel> admin = new ArrayList<AdminModel>() ;// to store Professors data
		ArrayList<StudentModel> student = new ArrayList<StudentModel>() ;// to store Professors data
		ArrayList alr = new ArrayList();
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				String  UserID = star.nextToken().trim();	// first token
				String  Password = star.nextToken().trim();	// second token
				String  FirstName = star.nextToken().trim();
				String  LastName = star.nextToken().trim();
				String  Gender = star.nextToken().trim();
				String  Nationality = star.nextToken().trim();
				char  UserType = star.nextToken().trim().charAt(0);
				
				Date AccessTimeStart;
				Date  AccessTimeEnd;
				try {
					AccessTimeStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(star.nextToken().trim());
					AccessTimeEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(star.nextToken().trim());
				} catch (ParseException e) {
					AccessTimeStart = null;
					AccessTimeEnd = null;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(UserType == 'S') {

					String MatricNumber = star.nextToken().trim();
					String EmailAddress = star.nextToken().trim();
					String PhoneNumber = star.nextToken().trim();
					
					StudentModel Student = new StudentModel(UserID, Password,FirstName,LastName,Gender,Nationality,UserType,AccessTimeStart,AccessTimeEnd);
					Student.setEmail(EmailAddress);
					Student.setMatricNumber(MatricNumber);
					Student.setPhoneNumber(PhoneNumber);
					Student.AllocateListing = 	AllocatedListingRepo.readAllocateListingByStudentID(Student.getUserID());
					Student.WaitListing = 	WaitListingRepo.readAllWaitListingByStudentID(Student.getUserID());
					student.add(Student) ;
				}else if(UserType == 'A') {
					AdminModel Admin = new AdminModel(UserID, Password,FirstName,LastName,Gender,Nationality,UserType,AccessTimeStart,AccessTimeEnd);
					admin.add(Admin) ;
					
				}
				
				
				// add to UserModel list
				
			}
	        alr.add(admin);
	        alr.add(student);
			return alr ;
	}

	public static ArrayList<WaitListingModel> readWaitListing(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)FileHandle.read(filename);
		ArrayList<WaitListingModel> WaitList = new ArrayList<WaitListingModel>() ;
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				int CourseIndex = Integer.parseInt(star.nextToken().trim());	// first token
				String  UserID = star.nextToken().trim();	// second token
				Date  ApplyTime;
				try {
					ApplyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(star.nextToken().trim());
				} catch (ParseException e) {
					ApplyTime = null;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				WaitListingModel WaitItem = new WaitListingModel(CourseIndex,UserID,ApplyTime); 
				
				// add to UserModel list

		        WaitList.add(WaitItem);
		}
		return WaitList ;
	}

	public static ArrayList<AllocatedListingModel> readAllocateListing(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)FileHandle.read(filename);
		ArrayList<AllocatedListingModel> AllocateListing = new ArrayList<AllocatedListingModel>() ;
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				int CourseIndex = Integer.parseInt(star.nextToken().trim());	
				String CourseCode = star.nextToken().trim();
				String  UserID = star.nextToken().trim();	
				Date  RegisterTime;
				try {
					RegisterTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(star.nextToken().trim());
				} catch (ParseException e) {
					RegisterTime = null;
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AllocatedListingModel AllocateItem = new AllocatedListingModel(CourseIndex,CourseCode,UserID,RegisterTime); 
				
				// add to UserModel list

				AllocateListing.add(AllocateItem);
		}
		return AllocateListing ;
	}
	
	public static ArrayList<CourseModel> readCourseListing(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)FileHandle.read(filename);
		ArrayList<CourseModel> CourseListing = new ArrayList<CourseModel>() ;
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				int IndexNumber = Integer.parseInt(star.nextToken().trim());	// first token
				String  CourseCode = star.nextToken().trim();	
				String  CourseName = star.nextToken().trim();
				String  CourseType = star.nextToken().trim();	
				int  Vacancy = Integer.parseInt(star.nextToken().trim());
				String  School = star.nextToken().trim();	
				int  AU = Integer.parseInt(star.nextToken().trim());	
				
				CourseModel AllocateItem = new CourseModel(IndexNumber,CourseCode,CourseName,CourseType,Vacancy,School,AU); 
				AllocateItem.TimeTableList = TimeTableRepo.readTimeTableByCourseIndex(AllocateItem.getIndexNumber());
				
				
				// add to UserModel list

				CourseListing.add(AllocateItem);
		}
		return CourseListing ;
	}
	
	public static ArrayList<TimeTableModel> readTimeTableListing(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)FileHandle.read(filename);
		ArrayList<TimeTableModel> TimeTableListing = new ArrayList<TimeTableModel>() ;
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				int IndexNumber = Integer.parseInt(star.nextToken().trim());	// first token
				String  Type = star.nextToken().trim();	
				int  Group = Integer.parseInt(star.nextToken().trim());
				String  Day = star.nextToken().trim();	
				String  TimeStart = star.nextToken().trim();		
				String  TimeEnd = star.nextToken().trim();
				String  Venue = star.nextToken().trim();
				
				TimeTableModel TimeTableItem = new TimeTableModel(IndexNumber,Type,Group,Day,TimeStart,TimeEnd,Venue); 
				
				// add to UserModel list

				TimeTableListing.add(TimeTableItem);
		}
		return TimeTableListing ;
	}
	
	public static ArrayList<NotificationSettingModel> readNotificationSettingListing(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)FileHandle.read(filename);
		ArrayList<NotificationSettingModel> NotificationSettingModelListing = new ArrayList<NotificationSettingModel>() ;
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

			
				String  NotificationType = star.nextToken().trim();	
				String  FirstCredentialsDetail = star.nextToken().trim();
				String  SecondCredentialsDetail = star.nextToken().trim();	
				
				NotificationSettingModel NotificationSettingItem = new NotificationSettingModel(NotificationType,FirstCredentialsDetail,SecondCredentialsDetail); 
				
				// add to UserModel list

				NotificationSettingModelListing.add(NotificationSettingItem);
		}
		return NotificationSettingModelListing ;
	}
	
	
}
