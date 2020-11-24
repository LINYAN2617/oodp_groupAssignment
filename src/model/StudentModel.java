package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
	
	public void addWaitListing(WaitListingModel wlist) {
		WaitListing.add(wlist);
		
	}
	public void addAllocateListing(AllocatedListingModel alist) {
		AllocateListing.add(alist);
		
	}
	
	public  ArrayList<WaitListingModel> getWaitListing() {
		return WaitListing;
		
	}
	public ArrayList<AllocatedListingModel> getAllocateListing() {
		return AllocateListing;
		
		
	}
	
	public void removeWaitListing(int CourseIndex) {	
		Iterator<WaitListingModel> iterator = WaitListing.iterator();

		while (iterator.hasNext()){
			WaitListingModel mp = iterator.next();

		     if (mp.getCourseIndex() == CourseIndex){
		         iterator.remove();    // You can do the modification here.
		     }
		 }
	}
	public void removeAllocateListing(int CourseIndex) {
	
		Iterator<AllocatedListingModel> iterator = AllocateListing.iterator();

		while (iterator.hasNext()){
			AllocatedListingModel mp = iterator.next();

		     if (mp.getCourseIndex() == CourseIndex){
		         iterator.remove();    // You can do the modification here.
		     }
		 }
	}
	
}
