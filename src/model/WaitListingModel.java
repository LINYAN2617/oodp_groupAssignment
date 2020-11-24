package model;

import java.util.Date;

public class WaitListingModel {
	private int CourseIndex;
	private String UserID;
	private Date ApplyTime;
	private String CourseCode;
	
	public WaitListingModel(int CourseIndex,String CourseCode, String UserID, Date ApplyTime) {
		this.CourseIndex = CourseIndex;
		this.UserID = UserID;
		this.ApplyTime = ApplyTime;
		this.CourseCode = CourseCode;
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
	
	public String getCourseCode() {
		return CourseCode;
	}
	
	public void setCourseCode(String CourseCode) {
		this.CourseCode = CourseCode;
	}
	public void setApplyTime(Date ApplyTime) {
		this.ApplyTime = ApplyTime;
	}
	public void setCourseIndex(int CourseIndex) {
		this.CourseIndex = CourseIndex;
	}
	public void setUserID(String UserID) {
		this.UserID = UserID;
	}
}
	