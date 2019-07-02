package model;

import java.awt.*;

public class Text extends Shape 
{
	private static final long serialVersionUID = 1L;
	private String text = "";
	private int fontsize = 30;
	
	public Text(float thickness, Color color, Point begin, Point end, String text)
	{
		this.thickness = thickness;
		this.color = color;
		this.pb = begin;
		this.pe = end;
		this.text = text;
		
	}
	@Override
	public void draw(Graphics g) 
	{
		
		Graphics2D g2d = (Graphics2D)g;
		
		if(status == 0)//if is draw a new line instead of selecting
		{
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontsize));
			g2d.setStroke(new BasicStroke(this.thickness));
		}
		else
		{
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, fontsize));
			g2d.setStroke(new BasicStroke(this.thickness + 1));
		}
		g2d.setColor(color);
		if(text.length() > 0)
			g2d.drawString(text, pb.x, pb.y);
		if(status != 0)
		{
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(this.thickness + 3));
			g2d.drawLine(pb.x, pb.y, pb.x, pb.y);
		}
		
	}

	@Override
	public boolean isSelect(Point p) {
		//System.out.println(pb.x + " # " + pb.y);
		//System.out.println(p.x + " % " + p.y);
		if((p.x - pb.x) * (p.x - pb.x) + (p.y - pb.y) * (p.y - pb.y) < thickness * 2 && p.x >= pb.x && p.y >= pb.y)
			return true;
		if(p.x >= pb.x && p.x <= pb.x + fontsize * text.length() && p.y <= pb.y && p.y >= pb.y - fontsize)
			return true;
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

	@Override
	public void changeSize(double sx, double sy) 
	{
		this.fontsize = (int)(this.fontsize * sx);
	}
	
	public void setcontent(String s)
	{
		this.text = s;
	}
	@Override
	public void changeAngle(double angle) {
		
	}

}
