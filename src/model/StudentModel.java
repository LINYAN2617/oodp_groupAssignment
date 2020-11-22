package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class StudentModel extends UserModel {
	public static ArrayList<StudentModel> course = new ArrayList<StudentModel>();
	
	public StudentModel(String UserID, String Password, String FirstName, String LastName, String Gender,
			String Nationality, char UserType, Date AccessTimeStart, Date AccessTimeEnd) {
		super(UserID, Password, FirstName, LastName, Gender, Nationality, UserType, AccessTimeStart, AccessTimeEnd);
		// TODO Auto-generated constructor stub
		
	}

	public String MatricNumber;
	public String Email;
	public String PhoneNumber;
	
	public ArrayList<AllocatedListingModel> AllocateListing;
	public ArrayList<WaitListingModel> WaitListing;
	
	public void setMatricNumber(String MatricNumber) {
		this.MatricNumber = MatricNumber;
	}
	
	public void setEmail(String Email) {
		this.Email = Email;
	}
	public void setPhoneNumber(String PhoneNumber) {
		this.PhoneNumber = PhoneNumber;
	}
	
	public String getMatricNumber() {
		return MatricNumber;
	}
	
	public String getEmail() {
		return Email;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	
	public void AddWaitListing(WaitListingModel wlist) {
		WaitListing.add(wlist);
		
	}
	public void AddAllocateListing(AllocatedListingModel alist) {
		AllocateListing.add(alist);
		
	}
	
	public  ArrayList<WaitListingModel> GetWaitListing() {
		return WaitListing;
		
	}
	public ArrayList<AllocatedListingModel> GetAllocateListing() {
		return AllocateListing;
		
		
	}
	
	public void RemoveWaitListing(int CourseIndex) {
		for (WaitListingModel al : WaitListing) { 
			if (al.getCourseIndex() == CourseIndex) { 
				AllocateListing.remove(al); 
			} 
		}
	
		
	}
	public void RemoveAllocateListing(int CourseIndex) {
	
		for (AllocatedListingModel al : AllocateListing) { 
			if (al.getCourseIndex() == CourseIndex) { 
				AllocateListing.remove(al); 
			} 
		}
	
		
		
	}
	
}
