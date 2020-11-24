package main;
import java.io.Console;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import dbrepo.AdminRepo;
import dbrepo.StudRepo;
import model.StudentModel;
import model.UserModel;
import model.AdminModel;
import main.MainProgram;

public class LoginController {
	
	private static final String SECRETKEY = "tBJCPuoiynMYps8W";
	private static final String SALT = "YxzDI5ie9uqGicjk";
	
	public String getPasswordMasked(Console cons, String msg)
    {
        char[] passwd;
        while (true) {
            passwd = cons.readPassword("%s", msg);
            if (passwd != null) {
                if (passwd.length > 0) {
                    return new String(passwd);
                } else {
                    System.out.println("Invalid input\n");
                }
            }
        }
    }

	public String encrypt(String strToEncrypt) 
	{
		
	    try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(SECRETKEY.toCharArray(), SALT.getBytes(), 65536, 256);
	        SecretKey tmp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	         
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
	        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	    } 
	    catch (Exception e) 
	    {
	        System.out.println("Error while encrypting: " + e.toString());
	    }
	    return null;
	}
	
	public String decrypt(String strToDecrypt) {
	    try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(SECRETKEY.toCharArray(), SALT.getBytes(), 65536, 256);
	        SecretKey tmp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	         
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
	        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	    } 
	    catch (Exception e) {
	        System.out.println("Error while decrypting: " + e.toString());
	    }
	    return null;
	}
	
	public StudentModel validateStud(String ID, String Pwd) {
	
		StudentModel returnresult = null;

		 for(int i =0 ; i< StudRepo.getStud().size(); i++) {
				 if(StudRepo.getStud().get(i).getUserID().equals(ID)) {
					 
					 String encryptedString = encrypt(Pwd) ;
					 Date date = new Date();
					 if(StudRepo.getStud().get(i).validatePwd(encryptedString)) {
					 returnresult = StudRepo.getStud().get(i);
						
					 }
				 }
			
			 
		 }
		 
		 return returnresult;
	}
	
	public AdminModel validateAdmin(String ID, String Pwd) {
		
		AdminModel returnresult = null;
	
		 for(int i =0 ; i< AdminRepo.getAdmin().size(); i++) {
			
			 if(AdminRepo.getAdmin().get(i).getUserID().equals(ID)) {

				 String encryptedString = encrypt(Pwd) ;

				 if(AdminRepo.getAdmin().get(i).validatePwd(encryptedString)) {
					 returnresult = AdminRepo.getAdmin().get(i);
					
				 }
			 }
		 }
		 
		 return returnresult;
	}
	
	public void logout() {
		
		System.out.println("\nYou are logged out");
		MainProgram.isloginAsAdmin = false;
		MainProgram.isloginAsStud = false;
		MainProgram.LoggedStudent = null;
		MainProgram.LoggedAdmin = null;
		
		MainProgram.programInterface();
	}
	
	public boolean checkAccessPeriod(UserModel Stud) {
		boolean allow = false;
		Date date = new Date();
		 if(Stud.getAccessTimeStart().compareTo(date) < 0 &&  Stud.getAccessTimeEnd().compareTo(date) > 0 ){
			
			 allow = true;
		 }
		 
		 return allow;
	}
}
