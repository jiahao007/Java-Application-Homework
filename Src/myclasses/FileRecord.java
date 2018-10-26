package myclasses;

public class FileRecord 
{
	private String name;
	private String course_name;
	private double score;
	
	public FileRecord()
	{
		this.name = "";
		this.course_name = "";
		this.score = 0;
	}
	public FileRecord(String name, String course_name, double score)
	{
		this.name = name;
		this.course_name = course_name;
		this.score = score;
	}
	public void print()
	{
		System.out.println(getName() + "," + getCourse_name() + "," + getScore());
	}
	public double getScore() {
		return score;
	}
	public String getCourse_name() {
		return course_name;
	}
	public String getName() {
		return name;
	}

}
