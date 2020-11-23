package model;

public class TimeTableModel {
	private int IndexNumber;
	private String Type; String Day;String TimeStart; String TimeEnd; String Venue;
	private int Group;
	public TimeTableModel() {
		
	}
	public TimeTableModel(int IndexNumber, String Type, int Group, String Day, String TimeStart,String TimeEnd, String Venue){
		this.IndexNumber = IndexNumber;
		this.Type = Type;
		this.Day = Day;
		this.TimeStart = TimeStart;
		this.TimeEnd = TimeEnd;
		this.Group = Group;
		this.Venue = Venue;
	}
	
	public int getIndexNumber() {
		return IndexNumber;
	}
	
	public void setIndexNumber(int Index) {
		this.IndexNumber = Index;
		
	}
	
	public String getType() {
		return Type;
		
	}
	
	public void setType(String Type) {
		this.Type = Type;
		
	}
	
	public String getDay() {
		return Day;
		
	}
	
	public void setDay(String Day) {
		this.Day = Day;
		
	}
	
	public String getTimeStart() {
		return TimeStart;
		
	}
	public void setTimeStart(String TimeStart) {
		this.TimeStart = TimeStart;
		
	}
	
	public String getTimeEnd() {
		return TimeEnd;
		
	}
	public void setTimeEnd(String TimeEnd) {
		this.TimeEnd = TimeEnd;
		
	}
	public void setVenue(String Venue) {
		this.Venue = Venue;
		
	}
	public int getGroup() {
		return Group;
		
	}
	public void setGroup(int Group) {
		this.Group = Group;
		
	}
	
	public String getVenue() {
		return Venue;
	}
}
