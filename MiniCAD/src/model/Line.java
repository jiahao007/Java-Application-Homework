package model;

import java.awt.*;

public class Line extends Shape 
{
	private static final long serialVersionUID = 1L;

	public Line(float thickness, Color color, Point begin, Point end)
	{
		this.thickness = thickness;
		this.color = color;
		this.pb = begin;
		this.pe = end;
		this.status = 0;
	}

	@Override
	public void draw(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D)g;
		
		if(status == 0)//if is draw a new line instead of selecting
		{
			g2d.setStroke(new BasicStroke(this.thickness));
		}
		else
		{
			g2d.setStroke(new BasicStroke(this.thickness + 1));
		}
		g2d.setColor(color);
		g2d.drawLine(pb.x, pb.y, pe.x, pe.y);
		if(status != 0)
		{
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(this.thickness + 3));
			g2d.drawLine(pb.x, pb.y, pb.x, pb.y);
			g2d.drawLine(pe.x, pe.y, pe.x, pe.y);	
		}
	}

	@Override
	public boolean isSelect(Point p) 
	{
		double minx = Math.min(pb.getX(), pe.getX());
		double maxx = Math.max(pb.getX(), pe.getX());
		double miny = Math.min(pb.getY(), pe.getY());
		double maxy = Math.max(pb.getY(), pe.getY());
		if(pldistance(p) < thickness / 2 && p.getX() >= minx - thickness / 2 && p.getX() <= maxx + thickness / 2 && p.getY() >= miny - thickness / 2 && p.getY() <= maxy + thickness / 2)
			return true;
		else
			return false;
	}
	
	@Override
	public void move(Point start, Point end)
	{
		pb.x += end.x - start.x;
		pb.y += end.y - start.y;
		pe.x += end.x - start.x;
		pe.y += end.y - start.y;
	}
	
	protected double pldistance(Point p) 
	{
		double distance = 10e6;
		double k = 0;
		if(pe.x == pb.x)
			distance = Math.abs(p.getX() - pb.getX());
		else if(pb.y == pe.y)
			distance = Math.abs(p.getY() - pb.getY());
		else
		{
			k = (pe.getY() - pb.getY()) / (pe.getX() - pb.getX());
			distance = Math.abs(k * p.getX() - p.getY() - k * pb.getX() + pb.getY()) / Math.sqrt(1 + k * k);
		}
		return distance;
	}

	@Override
	public void changeSize(double sx, double sy) 
	{
		
		Point base = new Point(0, 0);
		base.x = (pb.x + pe.x) / 2;
		base.y = (pb.y + pe.y) / 2;
		
		pb = resize(sx, sy, pb, base);
		pe = resize(sx, sy, pe, base);
	}

	@Override
	public void changeAngle(double angle) 
	{
		Point base = new Point(0, 0);
		base.x = (pb.x + pe.x) / 2;
		base.y = (pb.y + pe.y) / 2;
		
		pb = rotate(angle, pb, base);
		pe = rotate(angle, pe, base);
	}

}
