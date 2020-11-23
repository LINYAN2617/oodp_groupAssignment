package DBRepo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.AdminModel;
import model.CourseModel;
import model.StudentModel;

public class StudRepo {
	public static ArrayList<StudentModel> GetStud() {
		
		return DBContext.student;
	}
	
	public static StudentModel GetStudentByStudID(String StudentID) {
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
	
	
	public static void save(List<StudentModel> al)  {
		
		List<String> alw = new ArrayList<String>() ;
		alw.add("UserID|Password|FirstName|LastName|Gender|Nationality|UserType|AccessTimeStart|AccessTimeEnd|Email|MatricNumber|PhoneNumber");
		String SEPARATOR = DBContext.SEPARATOR;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0 ; i < al.size() ; i++) {
        	StudentModel stud = (StudentModel)al.get(i);
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
        	st.append("-");
			st.append(SEPARATOR);
			st.append("-");
			st.append(SEPARATOR);
			st.append("-");
			alw.add(st.toString()) ;
        }
        
		try {
			FileHandle.write(DBContext.UserFileName,alw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
