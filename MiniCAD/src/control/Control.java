package control;

import java.util.ArrayList;

import model.Model;
import model.Shape;
import view.View;

public class Control {
	public static Model model;
	
	public static void setdata(ArrayList<Shape> listShape)
	{
		model.setdata(listShape);
	}
	
	public Control(Model model, View view) 
	{
		Control.model = model;
	}
	
	public void addShape(Shape s)
	{
		model.add(s);
	}
	
	public void deleteShape(Shape s)
	{
		model.delete(s);
	}
}
