package model;

import java.awt.*;

public class Rectangle extends Shape 
{
	private static final long serialVersionUID = 1L;
	private boolean flag = false;
	public Rectangle(float thickness, Color color, Point begin, Point end) 
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
		
		if(status == 0)
		{
			g2d.setStroke(new BasicStroke(this.thickness));
		}
		else
		{
			g2d.setStroke(new BasicStroke(this.thickness + 1));
		}
		g2d.setColor(color);
		if(flag)
		{
			g2d.fillRect(Math.min(pb.x, pe.x), Math.min(pb.y, pe.y), Math.abs(pe.x - pb.x), Math.abs(pe.y - pb.y));
		}
		else
		{
			g2d.drawRect(Math.min(pb.x, pe.x), Math.min(pb.y, pe.y), Math.abs(pe.x - pb.x), Math.abs(pe.y - pb.y));
		}
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
		//四条直线将矩形包围了起来，只需判断点的位置即可
		double minx = Math.min(pb.getX(), pe.getX());
		double maxx = Math.max(pb.getX(), pe.getX());
		double miny = Math.min(pb.getY(), pe.getY());
		double maxy = Math.max(pb.getY(), pe.getY());
		double px = p.getX();
		double py = p.getY();
		
		if(px <= minx + thickness / 2 && px >= minx - thickness / 2 || px <= maxx + thickness / 2 && px >= maxx - thickness / 2)
		{
			if(py <= maxy + thickness / 2 && py >= miny - thickness / 2)
			{
				return true;
			}
		}
		if(py <= miny + thickness / 2 && py >= miny - thickness / 2 || py <= maxy + thickness / 2 && py >= maxy - thickness / 2)
		{
			if(px <= maxx + thickness / 2 && px >= minx - thickness / 2)
			{
				return true;
			}
		}
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
	
	public void setfill(boolean fillflag)
	{
		this.flag = fillflag;
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
