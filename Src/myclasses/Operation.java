package myclasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Operation 
{
	public List<FileRecord> readInfo(String fileName)
	{
		List<FileRecord> stu_info = new LinkedList<FileRecord>();
		
		try
		{
			File csv = new File(fileName);
			if(!csv.exists())
				csv.createNewFile();
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			//reader.readLine();
			String line= "";
			while((line = reader.readLine()) != null)
			{
				String[] item = line.split(",");
				StringBuffer temp = new StringBuffer();
				if(line.length() > 2)
				{
					for(int i = 0; i < item.length - 2; i++)
						temp.append(item[i]);
					String name = temp.toString();
					String course_name = item[item.length - 2];
					double score = Double.parseDouble(item[item.length - 1]);
					FileRecord ans = new FileRecord(name, course_name, score);
					stu_info.add(ans);
				}
			}
			reader.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return stu_info;
	}
	
	public List<FileRecord> checkRepetition(FileRecord record)
	{
		String fileName = "info.csv";
		List<FileRecord> stu_info = readInfo(fileName);
		boolean flag = false;
		for(int i = 0; i < stu_info.size(); i++)
		{
			FileRecord temp = stu_info.get(i);
			if(record.getName().equals(temp.getName()) && record.getCourse_name().equals(temp.getCourse_name()))
			{
				stu_info.remove(i);
				stu_info.add(record);
				flag = true;
				break;
			}
		}
		if(!flag)
			stu_info.add(record);
		return stu_info;
	}
	
	public boolean addRecord(FileRecord info)
	{
		boolean result = false;
		
		List<FileRecord> stu_info = checkRepetition(info);
		try
		{
			File csv = new File("info.csv");
			if(!csv.exists())
				csv.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv)); //append a new line.
			for(int i = 0; i < stu_info.size(); i++)
			{
				FileRecord temp = stu_info.get(i);
				String record = temp.getName() + "," + temp.getCourse_name() + "," + temp.getScore();
				bw.write(record);
				bw.newLine();
			}
			bw.close();
			result = true;
		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean jugeInfo(FileRecord info, String condition, boolean flag)
	{
		boolean res = false;
		
		if(flag)
			res = info.getName().equals(condition);
		else
			res = info.getCourse_name().equals(condition);
		return res;
	}
}
