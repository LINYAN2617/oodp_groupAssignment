package notificationservice;

import java.util.ArrayList;

public interface Notification {
	public void send(ArrayList<String> SendingInfo);
	public ArrayList<String> getAuthDetails(String Type);
}

