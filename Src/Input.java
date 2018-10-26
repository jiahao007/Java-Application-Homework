import myclasses.*;

public class Input 
{
	public static void main(String[] args) 
	{
		Operation op = new Operation();
		StringBuffer temp = new StringBuffer();
		String course_name = args[args.length - 2];
		double score = Double.parseDouble(args[args.length - 1]);
		for(int i = 0; i < args.length - 2; i++)
		{
			temp.append(args[i]);
			if(i != args.length - 3)
				temp.append(" ");
		}
		String name = temp.toString();
		FileRecord stu_info = new FileRecord(name, course_name, score);
		op.addRecord(stu_info);
	}
}
