package main;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import model.StudentModel;
import model.UserModel;
import model.AdminModel;
public class Function_LoginHandling {
	
	private static String secretKey = "tBJCPuoiynMYps8W";
	private static String salt = "YxzDI5ie9uqGicjk";
	
	
	
	public static String getPasswordMasked(Console cons, String msg)
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

	public static String encrypt(String strToEncrypt) 
	{
	    try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
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
	
	public static String decrypt(String strToDecrypt) {
	    try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
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
	
	public StudentModel validateStud(String ID, String Pwd, ArrayList<StudentModel> stud) {
	
		StudentModel returnresult = null;
	
		 for(int i =0 ; i< stud.size(); i++) {
			
			 if(stud.get(i).getUserID().equals(ID)) {

				 String encryptedString = encrypt(Pwd) ;

				 if(stud.get(i).validatePwd(encryptedString)) {
					 returnresult = stud.get(i);
					
				 }
			 }
		 }
		 
		 return returnresult;
	}
	
	public AdminModel validateAdmin(String ID, String Pwd, ArrayList<AdminModel> admin) {
		
		AdminModel returnresult = null;
	
		 for(int i =0 ; i< admin.size(); i++) {
			
			 if(admin.get(i).getUserID().equals(ID)) {

				 String encryptedString = encrypt(Pwd) ;

				 if(admin.get(i).validatePwd(encryptedString)) {
					 returnresult = admin.get(i);
					
				 }
			 }
		 }
		 
		 return returnresult;
	}
	
	
}
