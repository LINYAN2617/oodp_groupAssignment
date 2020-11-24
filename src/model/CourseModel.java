package model;

import java.util.ArrayList;

public class CourseModel {
	private int IndexNumber;
	private String CourseCode;
	private String CourseName;
	private String CourseType;
	public ArrayList<TimeTableModel> TimeTableList;
	private int Vacancy;
	private String School;
	private int AU;
	
	public CourseModel() {
		
	}
	public CourseModel(int IndexNumber,String CourseCode,String CourseName,String CourseType,int Vacancy,String School,int AU) {
		
		this.IndexNumber=IndexNumber;
		this.CourseCode=CourseCode;
		this.CourseName=CourseName;
		this.CourseType=CourseType;
		this.Vacancy=Vacancy;
		this.School=School;
		this.AU=AU;
		
	}
	
	public int getIndexNumber() {
		return IndexNumber;
	}
	
	public String getCourseCode() {
		return CourseCode;
	}
	
	public String getCourseName() {
		return CourseName;
	}
	
	public String getCourseType() {
		return CourseType;
	}
	
	public int getVacancy() {
		return Vacancy;
	}
	
	public String getSchool() {
		return School;
	}
	public int getAU() {
		return AU;
	}
	public void setCourseCode(String CourseCode) {
		this.CourseCode =CourseCode;
	}
	
	public void setCourseName(String CourseName) {
		this.CourseName =CourseName;
	}
	public void setCourseType(String CourseType) {
		this.CourseType =CourseType;
	}
	
	public void setVacancy(int Vacancy) {
		this.Vacancy =Vacancy;
	}
	
	public void setIndexNumber(int IndexNumber) {
		this.IndexNumber =IndexNumber;
	}
	public void setSchool(String School) {
		this.School =School;
	}

	public void setAU(int AU) {
		this.AU =AU;
	}
	
	public ArrayList<TimeTableModel> getTimeTableListing() {
		return TimeTableList;
		
	}

	public void setTimeTable(String IndexNumber) {
		
	}

}
