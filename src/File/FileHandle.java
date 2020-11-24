package File;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandle {
	  
	  public static void write(String fileName, List<?> data) throws IOException  {
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
	  public static List<String> read(String fileName) throws IOException {
		List<String> data = new ArrayList<String>() ;
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


}
