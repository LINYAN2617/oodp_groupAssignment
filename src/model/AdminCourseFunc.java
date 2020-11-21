package model;
import java.util.ArrayList;
import java.util.Scanner;
public class AdminCourseFunc {
//private CourseModel course= new CourseModel();
	private ArrayList<CourseModel> courseList;
	private CourseModel course;
	public AdminCourseFunc( ArrayList<CourseModel> courseList) {
			this.courseList=courseList;
		}
	
	public void FindCourse(int courseIndex) {
		for(int i = 0; i < courseList.size(); i++){
			if (courseList.get(i).getIndexNumber()==courseIndex) {
				course=courseList.get(i);
				break;
			}
			else {
				System.out.print("Invialed Index Number!");
			}
			}
		
	}

	
		public void addCourse(int courseIndex) {
			Scanner sc = new Scanner(System.in);
			System.out.print(".............Add New Course....... ");
			
			System.out.print("CourseCode:");
			String CourseCode;
			CourseCode = sc.next();
			course.setCourseCode(CourseCode);
			
			System.out.print("CourseName:");
			String CourseName;
			CourseName = sc.next();
			course.setCourseName(CourseName);
			
			System.out.print("Please Select ClassType: "+
					"1. Lectures Only" + 
					"2. Lectures and Tutorial"+
					"3. Lectures, Tutorial and Laboratory sessions");
					int choice;
					choice = sc.nextInt();
					switch (choice) {
					case 1:
						course.setClassType("Lectures Only");
						break;
					case 2: 
						course.setClassType("Lectures and Tutorial");
						break;
					case 3:
						course.setClassType("Lectures, Tutorial and Laboratory sessions");
						break;
					}
						
			System.out.print("School:");
			String School;
			School = sc.next();
			course.setSchool(School);
			
			System.out.print("Vacancy:");
			int Vacancy;
			Vacancy = sc.nextInt();
			course.setVacancy(Vacancy);
					}	
						

		
		public void updateCourse(int courseIndex) {
			
			Scanner sc = new Scanner(System.in);
			System.out.print(".............Update Course....... ");
			
			System.out.print(
			"1. CourseCode"+
			"2. CourseName"+
			"3. ClassType"+
			"4. Date"+
			"5. Time"+
			"6.Venue"+
			"7.Vacancy"+
			"8.Exit");
			
			
			System.out.print("Please select the update item: ");
			int choice;
			choice = sc.nextInt();
			
			while(choice<8)
			{
				switch (choice) {
				case 1: 
					System.out.print("Please enter new CourseCode:");
					String CourseCode;
					CourseCode = sc.next();
					course.setCourseCode(CourseCode);
					break;
				case 2:
					System.out.print("Please enter new CourseName:");
					String CourseName;
					CourseName = sc.next();
					course.setCourseName(CourseName);
					break;
				case 3:
					System.out.print("Please Select new ClassType: "+
							"1. Lectures Only" + 
							"2. Lectures and Tutorial"+
							"3. Lectures, Tutorial and Laboratory sessions");
							int a;
							a = sc.nextInt();
							switch (a) {
							case 1:
								course.setClassType("Lectures Only");
								break;
							case 2: 
								course.setClassType("Lectures and Tutorial");
								break;
							case 3:
								course.setClassType("Lectures, Tutorial and Laboratory sessions");
								break;
							default:
								System.out.print("Invalid input!");
							}
					break;
				case 4:			
					System.out.print("Please enter new School:");
					String School;
					School = sc.next();
					course.setSchool(School);
					break;
	
				case 7:
					System.out.print("Please enter new Vacancy:");
					int Vacancy;
					Vacancy = sc.nextInt();
					course.setVacancy(Vacancy);
					break;
							}	
				System.out.print("Please select the update item: ");
				choice = sc.nextInt();
				}
			}
					
		
		
		public int checkVacancy(int courseIndex) {
			
			return course.getVacancy();
			
		}

}
