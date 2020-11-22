package DBRepo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.StudentModel;

public class StudRepo {
	public StudRepo(DBContext DBContext) {
	}
	public static void saveStudent(String filename, List<?> al)  {
		
		List<String> alw = new ArrayList<String>() ;
		alw.add("UserID|Password|FirstName|LastName|Gender|Nationality|UserType|AccessTimeStart|AccessTimeEnd");
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
			
			alw.add(st.toString()) ;
		}

		try {
			FileHandle.write(filename,alw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
