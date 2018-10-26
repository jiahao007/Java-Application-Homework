import myclasses.*;
import java.util.*;

public class Import
{

	public static void main(String[] args) 
	{
		String fileName = args[0];
		Operation op = new Operation();
		List<FileRecord> stu_info = op.readInfo(fileName);
		for(int i = 0; i < stu_info.size(); i++)
			op.addRecord(stu_info.get(i));
	}

}
