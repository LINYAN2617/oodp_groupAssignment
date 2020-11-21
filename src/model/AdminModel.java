package model;

import java.util.Date;

public class AdminModel extends UserModel {



	public AdminModel(String UserID, String Password, String FirstName, String LastName, String Gender,
			String Nationality, char UserType, Date AccessTimeStart, Date AccessTimeEnd) {
		super(UserID, Password, FirstName, LastName, Gender, Nationality, UserType, AccessTimeStart, AccessTimeEnd);
		// TODO Auto-generated constructor stub
	}

	private String StaffNumber;
	
	
}
