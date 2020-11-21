package model;

import java.util.Date;

public class AllocatedListingModel {
	private int CourseIndex;
	private String UserID;
	private Date RegisterTime;
	
	public AllocatedListingModel(int CourseIndex, String UserID, Date RegisterTime) {
		this.CourseIndex = CourseIndex;
		this.UserID = UserID;
		this.RegisterTime = RegisterTime;
	}
	
	public String getUserID() {
		return UserID;
	}
	

	public int getCourseIndex() {
		return CourseIndex;
	}
	
	
	
}
