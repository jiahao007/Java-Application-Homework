package model;

import java.awt.*;
import java.util.ArrayList;

public class Poly extends Line
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Line> selectpoly = new ArrayList<Line>();
	private int [] xpoints = new int[50];
	private int [] ypoints = new int[50];
	private int size;
	ShapeMode polyshape;
	
	public Poly(float thickness, Color color, Point begin, Point end, ArrayList<Point> poly, ShapeMode polyshape) 
	{
		super(thickness, color, begin, end);
		for(int i = 0; i < poly.size(); i++)
		{
			xpoints[i] = poly.get(i).x;
			ypoints[i] = poly.get(i).y;
		}
		for(int i = 0; i < poly.size() - 1; i++)
		{
			selectpoly.add(new Line(3, color, poly.get(i), poly.get(i+1)));
		}
		if(polyshape == ShapeMode.Polygon)
			selectpoly.add(new Line(3, color, poly.get(0), poly.get(poly.size()-1)));
		this.size = poly.size();
		this.polyshape = polyshape;
	}
	
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
		if(polyshape == ShapeMode.Polyline)
			g2d.drawPolyline(xpoints, ypoints, size);
		else
			g2d.drawPolygon(xpoints, ypoints, size);
		if(status != 0)
		{
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(this.thickness + 3));
			for(int i = 0; i < size; i++)
			{
				g2d.drawLine(xpoints[i], ypoints[i], xpoints[i], ypoints[i]);
			}
		}
	}
	
	public boolean isSelect(Point p)
	{
		for(Line line : selectpoly)
		{
			if(line.isSelect(p))
				return true;
		}
		return false;
	}
	
	@Override
	public void move(Point start, Point end)
	{
		for(int i = 0; i < size; i++)
		{
			xpoints[i] += end.x - start.x;
			ypoints[i] += end.y - start.y;
		}
		for(int i = 0; i < size - 1; i++)
		{
			Line line = selectpoly.get(i);
			line.pb.x = xpoints[i];
			line.pb.y = ypoints[i];
			line.pe.x = xpoints[i+1];
			line.pe.y = ypoints[i+1];
		}
	}
	
	public void changeSize(double sx, double sy)
	{
		Point base = new Point(0, 0);
		for(int i = 0; i < size; i++)
		{
			base.x += xpoints[i];
			base.y += ypoints[i];
		}
		base.x /= size;
		base.y /= size;
		for(int i = 0; i < size; i++)
		{
			Point res = resize(sx, sy, new Point(xpoints[i], ypoints[i]), base);
			xpoints[i] = res.x;
			ypoints[i] = res.y;
		}
		for(int i = 0; i < size - 1; i++)
		{
			Line line = selectpoly.get(i);
			line.pb.x = xpoints[i];
			line.pb.y = ypoints[i];
			line.pe.x = xpoints[i+1];
			line.pe.y = ypoints[i+1];
		}
	}
	
	@Override
	public void changeAngle(double angle) 
	{
		Point base = new Point(0, 0);
		for(int i = 0; i < size; i++)
		{
			base.x += xpoints[i];
			base.y += ypoints[i];
		}
		base.x /= size;
		base.y /= size;
		for(int i = 0; i < size; i++)
		{
			Point res = rotate(angle, new Point(xpoints[i], ypoints[i]), base);
			xpoints[i] = res.x;
			ypoints[i] = res.y;
		}
		for(int i = 0; i < size - 1; i++)
		{
			Line line = selectpoly.get(i);
			line.pb.x = xpoints[i];
			line.pb.y = ypoints[i];
			line.pe.x = xpoints[i+1];
			line.pe.y = ypoints[i+1];
		}
	}
}
