package DBRepo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import File.FileHandle;
import model.AllocatedListingModel;
import model.DBContext;
import model.WaitListingModel;

public class WaitListingRepo {

	public static ArrayList<WaitListingModel> readAllWaitListingByStudentID(String StudentID){
		ArrayList<WaitListingModel> wList = new ArrayList<WaitListingModel>();
		for(int i=0; i< DBContext.WaitListing.size(); i++) {
			
			if(DBContext.WaitListing.get(i).getUserID().equals(StudentID)) {
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
	public static void add(WaitListingModel WModel) {
		DBContext.WaitListing.add(WModel);
		save(DBContext.WaitListing);
	}
	public static void remove(WaitListingModel WModel) {
		DBContext.WaitListing.remove(WModel);
		save(DBContext.WaitListing);
	}
	
	
	public static void save(List<WaitListingModel> al)  {
		
		List<String> alw = new ArrayList<String>() ;
		alw.add("CourseIndex|UserID|ApplyTime");
		String SEPARATOR = DBContext.SEPARATOR;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0 ; i < al.size() ; i++) {
        		WaitListingModel wlist = (WaitListingModel)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(wlist.getCourseIndex());
				st.append(SEPARATOR);
				st.append(wlist.getCourseCode());
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
	
	public static WaitListingModel GetLastWaitListingByCourseIndex(int CourseIndex) {
		Date date = null;
		WaitListingModel WModel = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse("3000-01-01");
		} catch (ParseException e) {
		}
		for(int i =0; i<DBContext.WaitListing.size(); i++) {
			if(DBContext.WaitListing.get(i).getCourseIndex() == CourseIndex && DBContext.WaitListing.get(i).getApplyTime().compareTo(date) < 0) {
				 date = DBContext.WaitListing.get(i).getApplyTime();
				 WModel = DBContext.WaitListing.get(i);
			}
		}
		
		return WModel;
		
		
	}
	
	public static ArrayList<WaitListingModel> GetWaitListModelByCourseCode(String CourseCode) {
		ArrayList<WaitListingModel> Wlist = new ArrayList<WaitListingModel>();
		
		for(int i =0; i<DBContext.WaitListing.size(); i++) {
			
			if(DBContext.WaitListing.get(i).getCourseCode().equals(CourseCode)) {
				WaitListingModel WModel = DBContext.WaitListing.get(i);
				Wlist.add(WModel);
			}
			
		}
		
		return Wlist;
		
	}
	
	public static ArrayList<WaitListingModel> GetWaitListModelByCourseCodeByStudID(String CourseCode,String StudID) {
		ArrayList<WaitListingModel> Wlist = new ArrayList<WaitListingModel>();
		
		for(int i =0; i<DBContext.WaitListing.size(); i++) {
			
			if(DBContext.WaitListing.get(i).getCourseCode().equals(CourseCode) && DBContext.WaitListing.get(i).getUserID().equals(StudID)) {
				WaitListingModel WModel = DBContext.WaitListing.get(i);
				Wlist.add(WModel);
			}
			
		}
		
		return Wlist;
		
	}
}
