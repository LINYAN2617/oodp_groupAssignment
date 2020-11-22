package DBRepo;

import java.util.ArrayList;

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
	
	
	
	
}
