package File;
import model.*;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TextDB {
	public static final String SEPARATOR = "|";

    // an example of reading
	public static ArrayList readUsers(String filename) throws IOException {
		// read String from text file
		ArrayList stringArray = (ArrayList)read(filename);
		ArrayList<AdminModel> admin = new ArrayList<AdminModel>() ;// to store Professors data
		ArrayList<StudentModel> student = new ArrayList<StudentModel>() ;// to store Professors data
		ArrayList alr = new ArrayList();
        for (int i = 1 ; i < stringArray.size() ; i++) {
        	
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				String  UserID = star.nextToken().trim();	// first token
				String  Password = star.nextToken().trim();	// second token
				String  FirstName = star.nextToken().trim();
				String  LastName = star.nextToken().trim();
				String  Gender = star.nextToken().trim();
				String  Nationality = star.nextToken().trim();
				char  UserType = star.nextToken().trim().charAt(0);

				if(UserType == 'S') {
					StudentModel Student = new StudentModel(UserID, Password,FirstName,LastName,Gender,Nationality,UserType);
					student.add(Student) ;
				}else if(UserType == 'A') {
					AdminModel Admin = new AdminModel(UserID, Password,FirstName,LastName,Gender,Nationality,UserType);
					admin.add(Admin) ;

				}
				
				
				// add to UserModel list
				
			}
	        alr.add(admin);
	        alr.add(student);
			return alr ;
	}

  // an example of saving
//public static void saveProfessors(String filename, List al) throws IOException {
	//	List alw = new ArrayList() ;// to store Professors data

      //  for (int i = 0 ; i < al.size() ; i++) {
		//		Professor prof = (Professor)al.get(i);
		//		StringBuilder st =  new StringBuilder() ;
		//		st.append(prof.getName().trim());
		//		st.append(SEPARATOR);
		//		st.append(prof.getEmail().trim());
		//		st.append(SEPARATOR);
		//		st.append(prof.getContact());
		//		alw.add(st.toString()) ;
		//	}
		//	write(filename,alw);
//	}

  /** Write fixed content to the given file. */
  public static void write(String fileName, List data) throws IOException  {
    PrintWriter out = new PrintWriter(new FileWriter(fileName));

    try {
		for (int i =0; i < data.size() ; i++) {
      		out.println((String)data.get(i));
		}
    }
    finally {
      out.close();
    }
  }

  /** Read the contents of the given file. */
  public static List read(String fileName) throws IOException {
	List data = new ArrayList() ;
    Scanner scanner = new Scanner(new FileInputStream(fileName));
    try {
      while (scanner.hasNextLine()){
        data.add(scanner.nextLine());
      }
    }
    finally{
      scanner.close();
    }
    return data;
  }



	public ArrayList ReturnFileArr(String FileName) {
		TextDB txtDB = new TextDB();
		ArrayList al = new ArrayList();
	
		return al;
		
	}
	
	
}


