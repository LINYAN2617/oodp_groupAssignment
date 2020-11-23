package DBRepo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.AllocatedListingModel;

public class AllocatedListingRepo {
	
	public static void add(AllocatedListingModel WModel) {
		DBContext.AllocatedListing.add(WModel);
		save(DBContext.AllocatedListing);
	}
	
	public static void remove(AllocatedListingModel WModel) {
		DBContext.AllocatedListing.remove(WModel);
		save(DBContext.AllocatedListing);
	}
	
	public static void save(List<AllocatedListingModel> al)  {
		
		List<String> alw = new ArrayList<String>() ;
		alw.add("CourseIndex|CourseCode|UserID|AllocatedTime");
		String SEPARATOR = DBContext.SEPARATOR;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0 ; i < al.size() ; i++) {
        		AllocatedListingModel alist = (AllocatedListingModel)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(alist.getCourseIndex());
				st.append(SEPARATOR);
				st.append(alist.getCourseCode().trim());
				st.append(SEPARATOR);
				st.append(alist.getUserID().trim());
				st.append(SEPARATOR);
				st.append(formatter.format(alist.getRegisterTime()));
							
				alw.add(st.toString()) ;
			}

		try {
			FileHandle.write(DBContext.ALFileName,alw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<AllocatedListingModel> readAllocateListingByStudentID(String StudentID){
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		for(int i=0; i< DBContext.AllocatedListing.size(); i++) {
			
			if(DBContext.AllocatedListing.get(i).getUserID().equals(StudentID)) {
				AList.add(DBContext.AllocatedListing.get(i));
			}
		
		}
		return AList;
	}
	public static ArrayList<AllocatedListingModel> readAllocateListingByCourseIndex(int CourseIndex){
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		for(int i=0; i< DBContext.AllocatedListing.size(); i++) {
			
			if(DBContext.AllocatedListing.get(i).getCourseIndex() == CourseIndex) {
				AList.add(DBContext.AllocatedListing.get(i));
			}
		
		}
		return AList;
	}
	
	public static ArrayList<AllocatedListingModel> readAllocateListingByCourseCode(String CourseCode){
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		for(int i=0; i< DBContext.AllocatedListing.size(); i++) {
			
			if(DBContext.AllocatedListing.get(i).getCourseCode().equals(CourseCode)) {
				AList.add(DBContext.AllocatedListing.get(i));
			}
		
		}
		return AList;
	}
	
	public static ArrayList<AllocatedListingModel> readAllocateListingByStudentIDByCourseIndex(int CourseIndex, String StudentID){
		ArrayList<AllocatedListingModel> AList = new ArrayList<AllocatedListingModel>();
		for(int i=0; i< DBContext.AllocatedListing.size(); i++) {
			
			if(DBContext.AllocatedListing.get(i).getCourseIndex() == CourseIndex && DBContext.AllocatedListing.get(i).getUserID().equals(StudentID)) {
				AList.add(DBContext.AllocatedListing.get(i));
			}
		
		}
		return AList;
	}
	
	public static int GetTakenSlotByCourseIndex(int courseIndex) {
		int taken = 0;
			 for (int i = 0 ; i < DBContext.AllocatedListing.size() ; i++) {
			        
					if (DBContext.AllocatedListing.get(i).getCourseIndex()==courseIndex) {
						taken++;
					}
				}
		return taken;
	}
}
