package NotificationService;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import DBRepo.NotificationRepo;
import model.NotificationSettingModel;

public class EmailService implements Notification {
	
	public void send(ArrayList<String> SendingInfo) {
		ArrayList<String> c = getAuthDetails("Email");

		String username = c.get(0); // to be added
		String password = c.get(1); // to be added
		String Recipent =  "";
		String Subject =  "";
		String EmailMessage =  "";
		
		Recipent = SendingInfo.get(0);
		Subject = SendingInfo.get(1);
		EmailMessage = SendingInfo.get(2);
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("NoReply@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(Recipent)); // to be added an email addr
			message.setSubject(Subject);
			message.setText(EmailMessage);
			
			Transport.send(message);

			System.out.println("A email notification is sent!");

		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<String> getAuthDetails(String Type) {
	
		NotificationSettingModel nmodel = NotificationRepo.GetNotificationModelByType(Type);
		ArrayList<String> credential = new ArrayList<String>();
		if(nmodel != null) {
			credential.add(nmodel.getFirstCredentialsDetail());
			credential.add(nmodel.getSecondCredentialsDetail());
			
		}else {
			credential.add("");
			credential.add("");
		}
	
		
		return credential;
	}
}