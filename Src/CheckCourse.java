import myclasses.*;
import java.util.*;
public class CheckCourse {

	public static void showInfo(List<FileRecord>stu_info)
	{
		int cnt = 0;
		double total = 0.0;
		for(FileRecord temp : stu_info)
		{
			//System.out.println(temp.getName() + "	" + temp.getScore());
			System.out.printf("%-20s%-10s\n", temp.getName(), ""+temp.getScore());
			total += temp.getScore();
			cnt++;
		}
		//System.out.println("number " + cnt);
		//System.out.println("average " + total / cnt);
		System.out.printf("%-20s%-10s\n","number", ""+cnt);
		System.out.printf("%-20s%-15s", "average", ""+total / cnt);
	}
	public static void main(String[] args) 
	{
		Operation op = new Operation();
		List<FileRecord> stu_info = new LinkedList<FileRecord>();
		String condition = args[0];
		String fileName = "info.csv";
		List<FileRecord> data_info = op.readInfo(fileName);
		for(FileRecord temp : data_info)
		{
			if(op.jugeInfo(temp, condition, false))
				stu_info.add(temp);
		}
		showInfo(stu_info);
	}

}
