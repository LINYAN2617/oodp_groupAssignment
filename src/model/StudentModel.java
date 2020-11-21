package model;

public class StudentModel extends UserModel {
	public StudentModel(String UserID, String Password, String FirstName, String LastName, String Gender,
			String Nationality,char UserType) {
		super(UserID, Password, FirstName, LastName, Gender, Nationality, UserType);
		// TODO Auto-generated constructor stub
	}
	private String MatricNumber;
	private String Email;
	private String PhoneNumber;
	
	public void setMatricNumber(String MatricNumber) {
		this.MatricNumber = MatricNumber;
	}

}
