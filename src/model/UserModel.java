package model;

import java.io.Serializable;

public class UserModel  implements Serializable {
	private String UserID;
	private String Password;
	private String FirstName;
	private String LastName;
	private String Gender;
	private String Nationality;
	private char UserType;
	
	public UserModel (String UserID,String Password,String FirstName,String LastName,String Gender,String Nationality,char UserType) {
		this.UserID = UserID;
		this.Password = Password;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.Gender = Gender;
		this.Nationality = Nationality;
		this.UserType = UserType;
		
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

}
