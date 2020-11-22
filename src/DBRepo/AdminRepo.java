package DBRepo;

import java.util.ArrayList;

import model.AdminModel;

public class AdminRepo {
	public static ArrayList<AdminModel> GetAdmin() {
		
		return DBContext.admin;
	}
}
