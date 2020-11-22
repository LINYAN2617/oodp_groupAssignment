package model;

public class NotificationSettingModel {
	private String NotificationType;
	private String FirstCredentialsDetail;
	private String SecondCredentialsDetail;
	
	public NotificationSettingModel(String NotificationType, String FirstCredentialsDetail, String SecondCredentialsDetail) {
		this.NotificationType = NotificationType;
		this.FirstCredentialsDetail = FirstCredentialsDetail;
		this.SecondCredentialsDetail = SecondCredentialsDetail;
		
	}
	
	public String getNotificationType() {
		return NotificationType;
	}
	public String getFirstCredentialsDetail() {
		return FirstCredentialsDetail;
	}
	public String getSecondCredentialsDetail() {
		return SecondCredentialsDetail;
	}
	public void SetNotificationType(String NotificationType) {
		this.NotificationType = NotificationType;
	}
	public void SetFirstCredentialsDetail(String FirstCredentialsDetail) {
		this.FirstCredentialsDetail = FirstCredentialsDetail;
	}
	public void SetSecondCredentialsDetail(String SecondCredentialsDetail) {
		this.SecondCredentialsDetail = SecondCredentialsDetail;
	}
}
