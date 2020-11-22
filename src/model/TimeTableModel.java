package model;

public class TimeTableModel {
	private int IndexNumber;
	private String Type; String Day;String Time; String Venue;
	private int Group;
	public TimeTableModel() {
		
	}
	public TimeTableModel(int IndexNumber, String Type, int Group, String Day, String Time, String Venue){
		this.IndexNumber = IndexNumber;
		this.Type = Type;
		this.Day = Day;
		this.Time = Time;
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
	
	public String getTime() {
		return Time;
		
	}
	public void setTime(String Time) {
		this.Time = Time;
		
	}
	public void setVenue(String Venue) {
		this.Venue = Venue;
		
	}
}
