package model;

public class AdminModel extends UserModel {
	public AdminModel(String UserID, String Password, String FirstName, String LastName, String Gender,
			String Nationality,char UserType) {
		super(UserID, Password, FirstName, LastName, Gender, Nationality, UserType);
		// TODO Auto-generated constructor stub
	}

	private String StaffNumber;
	
	
}
