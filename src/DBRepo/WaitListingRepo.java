package DBRepo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.WaitListingModel;

public class WaitListingRepo {

	public static ArrayList<WaitListingModel> readAllWaitListingByStudentID(String StudentID){
		ArrayList<WaitListingModel> wList = new ArrayList<WaitListingModel>();
		for(int i=0; i< DBContext.WaitListing.size(); i++) {
			
			if(DBContext.WaitListing.get(i).getUserID() == StudentID) {
				wList.add(DBContext.WaitListing.get(i));
			}
		
		}
		return wList;
	}
	public static ArrayList<WaitListingModel> readAllWaitListingByStudentIDByCourseIndex(int IndexNumber ,String StudentID){
		ArrayList<WaitListingModel> wList = new ArrayList<WaitListingModel>();
		for(int i=0; i< DBContext.WaitListing.size(); i++) {
			
			if(DBContext.WaitListing.get(i).getUserID().equals(StudentID)  && DBContext.WaitListing.get(i).getCourseIndex() == IndexNumber) {
				wList.add(DBContext.WaitListing.get(i));
			}
		
		}
		return wList;
	}
	public static void addWaitListing(WaitListingModel WModel) {
		DBContext.WaitListing.add(WModel);
		saveWaitListing(DBContext.WaitListing);
	}
	
	
	public static void saveWaitListing(List<WaitListingModel> al)  {
		
		List<String> alw = new ArrayList<String>() ;
		alw.add("CourseIndex|UserID|ApplyTime");
		String SEPARATOR = DBContext.SEPARATOR;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0 ; i < al.size() ; i++) {
        		WaitListingModel wlist = (WaitListingModel)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(wlist.getCourseIndex());
				st.append(SEPARATOR);
				st.append(wlist.getUserID().trim());
				st.append(SEPARATOR);
				st.append(formatter.format(wlist.getApplyTime()));
							
				alw.add(st.toString()) ;
			}

		try {
			FileHandle.write(DBContext.WLFileName,alw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
