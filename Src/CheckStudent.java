import java.util.*;
import myclasses.*;


public class CheckStudent 
{
	public static void showInfo(List<FileRecord> stu_info)
	{
		double total = 0;
		int cnt = 0;
		double average = 0.0;
		for(FileRecord temp : stu_info)
		{
			//System.out.println(temp.getCourse_name() + "	" + temp.getScore());
			System.out.printf("%-20s%-10s\n", temp.getCourse_name(), ""+temp.getScore());
			total += temp.getScore();
			cnt++;
		}
		if(cnt > 0)
			average = total / cnt;
		//System.out.println("total:  " + "   " + total);
		//System.out.println("average:" + "   " + average);	
		System.out.printf("%-20s%-10s\n","total", ""+total);
		System.out.printf("%-20s%-15s", "average", ""+average);	
	}
	public static void main(String[] args) 
	{
		Operation op = new Operation();
		List<FileRecord> stu_info = new LinkedList<FileRecord>();
		StringBuffer name = new StringBuffer();
		for(int i = 0; i < args.length; i++)
		{
			name.append(args[i]);
			if(i != args.length - 1)
				name.append(" ");
		}
		String condition = name.toString();
		String fileName = "info.csv";
		List<FileRecord> data_info = op.readInfo(fileName);
		for(FileRecord temp : data_info)
		{
			if(op.jugeInfo(temp, condition, true))
				stu_info.add(temp);
		}
		showInfo(stu_info);
	}

}
