package DBRepo;

import model.DBContext;
import model.NotificationSettingModel;

public class NotificationRepo {
	
	public static NotificationSettingModel GetNotificationModelByType(String type) {
		NotificationSettingModel NModel =null;
		
        for (int i = 0 ; i < DBContext.NotificationSettingListing.size() ; i++) {
      
			if (DBContext.NotificationSettingListing.get(i).getNotificationType().equals(type)) {
				
				NModel =  DBContext.NotificationSettingListing.get(i);
				//course=CourseModelListing.get(i);
				break;
			
			}
		}
        return NModel;
	}
}
