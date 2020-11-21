package model;

public class WaitListingModel {
	private long CourseIndex;
	private String UserID;
	private String ApplyTime;
	
	public WaitListingModel(long CourseIndex, String UserID, String ApplyTime) {
		this.CourseIndex = CourseIndex;
		this.UserID = UserID;
		this.ApplyTime = ApplyTime;
	}
	public String getUserID() {
		return UserID;
	}
	
	
}