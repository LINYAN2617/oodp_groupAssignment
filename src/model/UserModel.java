package model;

import java.io.Serializable;
import java.util.Date;

public class UserModel  implements Serializable {
	private String UserID;
	private String Password;
	private String FirstName;
	private String LastName;
	private String Gender;
	private String Nationality;
	private char UserType;
	private Date  AccessTimeStart;
	
	private Date AccessTimeEnd;
	
	public UserModel (String UserID,String Password,String FirstName,String LastName,String Gender,String Nationality,char UserType, Date AccessTimeStart, Date AccessTimeEnd) {
		this.UserID = UserID;
		this.Password = Password;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.Gender = Gender;
		this.Nationality = Nationality;
		this.UserType = UserType;
		this.AccessTimeStart = AccessTimeStart;
		this.AccessTimeEnd = AccessTimeEnd;
	}
	
	public boolean validatePwd(String Pwd) {
		if(Pwd.equals(Password)) {

			return true;
		}
		
		return false;
	}
	
	public String getUserID() {
		return UserID;
	}

	public String getPassword() {
		return Password;
	}
	public char getUserType() {
		return UserType;
	}
	
	public String getFullName() {
		return FirstName + " " + LastName;
	}
	public String getFirstName() {
		return FirstName;
	}
	public String getLastName() {
		return LastName;
	}
	public String getGender() {
		return Gender;
	}
	public String getNationality() {
		return Nationality;
	}
	
	public Date getAccessTimeStart() {
		return AccessTimeStart;
	}
	public Date getAccessTimeEnd() {
		return AccessTimeEnd;
	}
	
	public void setAccessTimeStart(Date AccessTimeStart) {
		this.AccessTimeStart=AccessTimeStart;
	}
	public void setAccessTimeEnd(Date AccessTimeEnd) {
		this.AccessTimeEnd=AccessTimeEnd;
	}
}
