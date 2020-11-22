package DBRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.TimeTableModel;

public class TimeTableRepo {

	public static ArrayList<TimeTableModel> GetTimeTableByCourseIndex(int courseIndex) {
		ArrayList<TimeTableModel> timeTableList = null;
		for(int i = 0; i < DBContext.TimeTableListing.size(); i++){
			if (DBContext.TimeTableListing.get(i).getIndexNumber()==courseIndex) {
				timeTableList.add(DBContext.TimeTableListing.get(i));
			}
		}
	return timeTableList;
	
	}
	public static ArrayList<TimeTableModel> readTimeTableByCourseIndex(int CourseID){
		ArrayList<TimeTableModel> tList = new ArrayList<TimeTableModel>();
		for(int i=0; i< DBContext.TimeTableListing.size(); i++) {
			
			if(DBContext.TimeTableListing.get(i).getIndexNumber() == CourseID) {
				tList.add(DBContext.TimeTableListing.get(i));
			}
		
		}
		return tList;
	}
	
	
	public static void save(List<TimeTableModel> al)  {
		
		List<String> alw = new ArrayList<String>() ;
		alw.add("CourseIndex|Type|Group|Day|Time|Venue");
		String SEPARATOR = DBContext.SEPARATOR;
        for (int i = 0 ; i < al.size() ; i++) {
        	TimeTableModel timeTable = (TimeTableModel)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(timeTable.getIndexNumber());
				st.append(SEPARATOR);
				st.append(timeTable.getType());
				st.append(SEPARATOR);
				st.append(timeTable.getGroup());
				st.append(SEPARATOR);
				st.append(timeTable.getDay());
				st.append(SEPARATOR);
				st.append(timeTable.getTime());
				st.append(SEPARATOR);
				st.append(timeTable.getVenue());
				st.append(SEPARATOR);
				
				alw.add(st.toString()) ;
			}

		try {
			FileHandle.write(DBContext.TBFileName,alw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
