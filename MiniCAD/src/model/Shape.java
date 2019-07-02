package model;

import java.awt.*;
import java.io.Serializable;

public abstract class Shape implements Serializable
{
	private static final long serialVersionUID = 1L;
	public Point pb;
	public Point pe;//two point to confirm the size and location of the shape
	Color color;//the color of the shape
	float thickness;//the pen size when drawing the shape
	final static double PI = 3.1415926;
	
	public int status;
	public abstract void draw(Graphics g);
	public abstract boolean isSelect(Point p);
	public boolean fullfill(boolean flag)
	{
		return false;
	}
	public Point resize(double sx, double sy, Point p, Point base)
	{
		Point res = new Point((int)(p.getX() * sx + base.getX() * (1 - sx)), (int)(p.getY() * sy + base.getY() * (1 - sy)));
		return res;	
	}
	public Point rotate(double theta, Point p, Point base)
	{
		double angle = theta * PI / 180;
		Point res = new Point((int)(p.getX() * Math.cos(angle) - p.getY() * Math.sin(angle) + base.x * (1 - Math.cos(angle)) + base.y * Math.sin(angle)), 
				(int)(p.getX() * Math.sin(angle) + p.getY() * Math.cos(angle) + base.y * (1 - Math.cos(angle)) - base.x * Math.sin(angle)));
		return res;
	}
	public abstract void move(Point start, Point end);
	public abstract void changeAngle(double angle);
	public abstract void changeSize(double sx, double sy);
	public void setcolor(Color color)
	{
		this.color = color;
	}
	public void setthickness(int flag)
	{
		if(flag == 0)
			this.thickness += 10; 
		else if(flag == 1)
			this.thickness -= 2;
	}
	public void setcontent(String s) {

	}
}
