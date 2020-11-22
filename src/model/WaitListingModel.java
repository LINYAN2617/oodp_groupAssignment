package model;

import java.util.Date;

public class WaitListingModel {
	private int CourseIndex;
	private String UserID;
	private Date ApplyTime;
	
	public WaitListingModel(int CourseIndex, String UserID, Date ApplyTime) {
		this.CourseIndex = CourseIndex;
		this.UserID = UserID;
		this.ApplyTime = ApplyTime;
	}
	public String getUserID() {
		return UserID;
	}
	
	public int getCourseIndex() {
		return CourseIndex;
	}
	
	public Date getApplyTime() {
		return ApplyTime;
	}
	
}