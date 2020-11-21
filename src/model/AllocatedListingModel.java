package model;

public class AllocatedListingModel {
	private long CourseIndex;
	private String UserID;
	private String RegisterTime;
	
	public AllocatedListingModel(long CourseIndex, String UserID, String RegisterTime) {
		this.CourseIndex = CourseIndex;
		this.UserID = UserID;
		this.RegisterTime = RegisterTime;
	}
	
	public String getUserID() {
		return UserID;
	}
	
	
	
}
