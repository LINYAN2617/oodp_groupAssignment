package dbrepo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import file.FileHandle;
import model.AdminModel;
import model.DBContext;
import model.StudentModel;

public class StudRepo {
	public static ArrayList<StudentModel> getStud() {
		
		return DBContext.student;
	}
	
	public static StudentModel getStudentByStudID(String StudentID) {
		StudentModel SModel =null;
		
        for (int i = 0 ; i < DBContext.student.size() ; i++) {
        
			if (DBContext.student.get(i).getUserID().equals(StudentID)) {
				
				SModel =  DBContext.student.get(i);
				//course=CourseModelListing.get(i);
				break;
			
			}
		}
        return SModel;
	}
	
	public static void add(StudentModel SModel) {
		DBContext.student.add(SModel);
		save(DBContext.student);
	}
	
	
	public static void updateAccessTime(Date StartTime, Date EndTime) {
		 for (int i = 0 ; i < DBContext.student.size() ; i++) {
	        	StudentModel stud = DBContext.student.get(i);
	        	stud.setAccessTimeEnd(EndTime);
	        	stud.setAccessTimeStart(StartTime);
		 }
		 
		 for (int i = 0 ; i < DBContext.admin.size() ; i++) {
	        	AdminModel admin = DBContext.admin.get(i);
	        	admin.setAccessTimeEnd(EndTime);
	        	admin.setAccessTimeStart(StartTime);
		 }
		 
		 save(DBContext.student);
	}
	
	
	public static void save(List<StudentModel> student)  {
		
		List<String> alw = new ArrayList<String>() ;
		alw.add("UserID|Password|FirstName|LastName|Gender|Nationality|UserType|AccessTimeStart|AccessTimeEnd|MatricNumber|Email|PhoneNumber");
		String SEPARATOR = DBContext.SEPARATOR;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0 ; i < student.size() ; i++) {
        	StudentModel stud = (StudentModel)student.get(i);
			StringBuilder st =  new StringBuilder() ;
			st.append(stud.getUserID().trim());
			st.append(SEPARATOR);
			st.append(stud.getPassword().trim());
			st.append(SEPARATOR);
			st.append(stud.getFirstName());
			st.append(SEPARATOR);
			st.append(stud.getLastName());
			st.append(SEPARATOR);
			st.append(stud.getGender());
			st.append(SEPARATOR);
			st.append(stud.getNationality());
			st.append(SEPARATOR);
			st.append(stud.getUserType());
			st.append(SEPARATOR);
			st.append(formatter.format(stud.getAccessTimeStart()));
			st.append(SEPARATOR);
			st.append(formatter.format(stud.getAccessTimeEnd()));
			st.append(SEPARATOR);
			st.append(stud.getMatricNumber());
			st.append(SEPARATOR);
			st.append(stud.getEmail());
			st.append(SEPARATOR);
			st.append(stud.getPhoneNumber());
			
			alw.add(st.toString()) ;
		}
        
        for (int i = 0 ; i < DBContext.admin.size() ; i++) {
        	AdminModel admin=DBContext.admin.get(i);
        	StringBuilder st =  new StringBuilder() ;
        	st.append(admin.getUserID().trim());
			st.append(SEPARATOR);
			st.append(admin.getPassword().trim());
			st.append(SEPARATOR);
			st.append(admin.getFirstName());
			st.append(SEPARATOR);
			st.append(admin.getLastName());
			st.append(SEPARATOR);
			st.append(admin.getGender());
			st.append(SEPARATOR);
			st.append(admin.getNationality());
			st.append(SEPARATOR);
			st.append(admin.getUserType());
			st.append(SEPARATOR);
			st.append(formatter.format(admin.getAccessTimeStart()));
			st.append(SEPARATOR);
			st.append(formatter.format(admin.getAccessTimeEnd()));
			st.append(SEPARATOR);
        	st.append("-");
			st.append(SEPARATOR);
			st.append("-");
			st.append(SEPARATOR);
			st.append("-");
			alw.add(st.toString()) ;
        }
        
		try {
			FileHandle.write(DBContext.USERFILENAME,alw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	



	
}
