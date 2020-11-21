package main;

import java.util.*;

import model.AllocatedListingModel;
import model.CourseModel;
import model.WaitListingModel;

public class StudentController {
	

	
	private DBController DBContext;
	
	public StudentController( DBController DBContext) {
			this.DBContext=DBContext;
		}
	
	public StudentController() {
	}
	
	public CourseModel FindCourse(int courseIndex) {
		
		CourseModel CModel =null;
		
        for (int i = 0 ; i < DBContext.CourseModelListing.size() ; i++) {
        
			if (DBContext.CourseModelListing.get(i).getIndexNumber()==courseIndex) {
				
				CModel =  DBContext.CourseModelListing.get(i);
				//course=CourseModelListing.get(i);
				break;
			
			}
			else {
				System.out.print("Invialed Index Number!");
			}
		}
        
        
        
        return CModel;
		
	}
	
	public String addCourse(String StudID, CourseModel CModel) {
		//Search for index number (pull info)
		//FindCourse(courseIndex);
		String returnMessage = "";
		//show course info
		Date date = new Date();
		
		if(CModel.getVacancy() <= DBContext.readAllocateListingByCourseIndex(CModel.getIndexNumber()).size()) {
			WaitListingModel wlist = new WaitListingModel(CModel.getIndexNumber(),StudID,date);
			DBContext.WaitListing.add(wlist);
			returnMessage = "Course Vancancy is full, your request is added in waiting list.";
		}else{
			AllocatedListingModel alist = new AllocatedListingModel(CModel.getIndexNumber(),StudID,date);
			DBContext.AllocatedListing.add(alist);
			returnMessage = "Successfully registered!";
		};
		
		return returnMessage;
		//Confirm to register(push to add)
	}
	
}
