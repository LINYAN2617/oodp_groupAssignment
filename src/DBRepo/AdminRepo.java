package DBRepo;

import java.util.ArrayList;

import model.AdminModel;
import model.DBContext;

public class AdminRepo {
	public static ArrayList<AdminModel> GetAdmin() {
		
		return DBContext.admin;
	}
}
