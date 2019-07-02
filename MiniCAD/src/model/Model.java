package model;

import java.util.ArrayList;
public class Model 
{
	ArrayList<Shape> listShape = new ArrayList<Shape>();
	
	public void add(Shape s) 
	{
		listShape.add(s);
	}
	
	public void delete(Shape s) 
	{
		listShape.remove(s);
	}

	public void setdata(ArrayList<Shape> listShape) 
	{
		for(Shape shape : listShape)
		{
			this.listShape.add(shape);
		}
	}
	
	public ArrayList<Shape> getdata()
	{
		return listShape;
	}
}
