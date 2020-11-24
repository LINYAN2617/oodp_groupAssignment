package dbrepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import file.FileHandle;
import model.CourseModel;
import model.DBContext;
public class CourseRepo {

	public static CourseModel getCourseByIndexNumber(int courseIndex) {
		
		CourseModel CModel =null;
		
        for (int i = 0 ; i < DBContext.CourseModelListing.size() ; i++) {
        
			if (DBContext.CourseModelListing.get(i).getIndexNumber()==courseIndex) {
				
				CModel =  DBContext.CourseModelListing.get(i);
				//course=CourseModelListing.get(i);
				break;
				
			}
		}
        return CModel;
	}
	
	public static CourseModel getCourseByCourseCode(String CourseCode) {
		CourseModel CModel =null;
		
        for (int i = 0 ; i < DBContext.CourseModelListing.size() ; i++) {
        
			if (DBContext.CourseModelListing.get(i).getCourseCode().equals(CourseCode)) {
		
				CModel =  DBContext.CourseModelListing.get(i);
				break;

			}
		}

        return CModel;
	}
	
	public static void add(CourseModel CModel) {
		DBContext.CourseModelListing.add(CModel);
		save(DBContext.CourseModelListing);
	}
	
	public static void update(CourseModel CModel) {
		
		save(DBContext.CourseModelListing);
		
	}
	
	public static void save(List<CourseModel> al)  {
		
		List<String> alw = new ArrayList<String>() ;
		alw.add("IndexNumber|CourseCode|CourserName|CourseType|Vacancy|School|AU");
		String SEPARATOR = DBContext.SEPARATOR;
        for (int i = 0 ; i < al.size() ; i++) {
				CourseModel course = (CourseModel)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(course.getIndexNumber());
				st.append(SEPARATOR);
				st.append(course.getCourseCode().trim());
				st.append(SEPARATOR);
				st.append(course.getCourseName());
				st.append(SEPARATOR);
				st.append(course.getCourseType());
				st.append(SEPARATOR);
				st.append(course.getVacancy());
				st.append(SEPARATOR);
				st.append(course.getSchool());
				st.append(SEPARATOR);
				st.append(course.getAU());
				
				alw.add(st.toString()) ;
			}

		try {
			FileHandle.write(DBContext.COURSEFILENAME,alw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static int getNewCourseIndexNumber() {
		if(DBContext.CourseModelListing.size()-1 == -1) {
			return 1;
		}
		return DBContext.CourseModelListing.get(DBContext.CourseModelListing.size()-1).getIndexNumber()+1;
	}
	

}
