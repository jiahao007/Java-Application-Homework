package view;

import java.awt.*;
import javax.swing.*;

import control.Control;
import model.*;
import model.Shape;
import model.Line;
import model.Rectangle;
import model.Ellipse;
import model.Poly;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener 
{
	private static final long serialVersionUID = 1L;
	private Point pb;
	private ArrayList<Point> point = new ArrayList<Point>();
	private ArrayList<Shape> tmp = new ArrayList<Shape>();
	Operation operation;
	ShapeMode currentShapeMode;
	Shape currentShape;
	Shape selectShape;
	Color color;
	
	
	public DrawPanel()
	{
		this.setBackground(Color.WHITE);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);
		this.addKeyListener(this);
		color = Color.BLACK;
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		Shape tmp = getSelectShape(e.getPoint());
		if(tmp != selectShape)
		{
			if(selectShape != null)
			{
				if(selectShape.status != 2)
				{
					selectShape.status = 0;
					if(tmp != null)
					{
						tmp.status = 1;
					}
					selectShape = tmp;
				}
			}
			else 
			{
				if(tmp != null)
				{
					tmp.status = 1;
				}
				selectShape = tmp;
			}
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		Point edgepoint = null;
		if(selectShape != null)
		{
			if(selectShape.status == 1)
				selectShape.status = 2;
		}
		Shape s = getSelectShape(e.getPoint());
		if(s == null)
		{
			operation = Operation.Draw;
		}
		else
		{
			boolean flag = s.isSelect(e.getPoint());
			if(flag)
				operation = Operation.Select;
		}
		if(operation == Operation.Draw)
		{
			if(currentShapeMode == ShapeMode.Polyline)
			{
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					edgepoint = e.getPoint();
					point.add(edgepoint);
					Line line = new Line(3, color, edgepoint, edgepoint);
					Control.model.add(line);
					tmp.add(line);
				}
				else if(e.getButton() == MouseEvent.BUTTON3)
				{
					if(point.size() >= 2)
					{
						
						Poly polyline = new Poly(3, color, point.get(0), point.get(point.size() - 1), point, ShapeMode.Polyline);
						Control.model.add(polyline);
						for(Shape t : tmp)
						{	
							Control.model.delete(t);
						}
						point.clear();
						tmp.clear();
					}
				}
			}
			else if(currentShapeMode == ShapeMode.Polygon)
			{
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					edgepoint = e.getPoint();
					point.add(edgepoint);
					Line line = new Line(3, color, edgepoint, edgepoint);
					Control.model.add(line);
					tmp.add(line);
				}
				else if(e.getButton() == MouseEvent.BUTTON3)
				{
					if(point.size() >= 2)
					{
						Poly polyline = new Poly(3, color, point.get(0), point.get(point.size() - 1), point, ShapeMode.Polygon);
						Control.model.add(polyline);
						for(Shape t : tmp)
						{	
							Control.model.delete(t);
						}
						point.clear();
						tmp.clear();
					}
				}
			}
			else if(currentShapeMode == ShapeMode.Text)
			{
				
				String content = JOptionPane.showInputDialog("ÇëÊäÈëÄÚÈÝ");
				if(content != null)
				{
					Text text = new Text(3, color, e.getPoint(), e.getPoint(), content);
					Control.model.add(text);
				}			
			}
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		pb = e.getPoint();
		Shape s = getSelectShape(e.getPoint());
		if(s == null)
		{
			operation = Operation.Draw;
		}
		else
		{
			boolean flag = s.isSelect(e.getPoint());
			if(flag)
				operation = Operation.Select;
		}
		if(operation == Operation.Draw)
		{
			if(currentShapeMode == ShapeMode.Line)
			{
				Line line = new Line(3, color, e.getPoint(), e.getPoint());
				currentShape = line;
				Control.model.add(line);
			}
			else if(currentShapeMode == ShapeMode.Rectangle)
			{
				Rectangle rect = new Rectangle(3, color, e.getPoint(), e.getPoint());
				currentShape = rect;
				Control.model.add(rect);
			}
			else if(currentShapeMode == ShapeMode.Ellipse)
			{
				Ellipse ellipse = new Ellipse(3, color, e.getPoint(), e.getPoint());
				currentShape = ellipse;
				Control.model.add(ellipse);
			}
			else if(currentShapeMode == ShapeMode.Fillrect)
			{
				Rectangle rect = new Rectangle(3, color, e.getPoint(), e.getPoint());
				rect.setfill(true);
				currentShape = rect;
				Control.model.add(rect);
			}
			else if(currentShapeMode == ShapeMode.Fillellipse)
			{
				Ellipse ellipse = new Ellipse(3, color, e.getPoint(), e.getPoint());
				ellipse.setfill(true);
				currentShape = ellipse;
				Control.model.add(ellipse);
			}
			
			if(selectShape != null && selectShape.status == 2)
			{
				Shape tmp = getSelectShape(e.getPoint());
				if(tmp != selectShape)
				{
					selectShape.status = 0;
					selectShape = tmp;
				}
			}
		}
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		if(currentShapeMode != ShapeMode.Polyline && currentShapeMode != ShapeMode.Polygon && currentShapeMode != ShapeMode.Text)
		{
			if(operation == Operation.Draw)
			{
				currentShape.pe = e.getPoint();
			}
		}
		if(operation == Operation.Select)
		{
			selectShape.move(pb, e.getPoint());
			pb = e.getPoint();
		}	
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentShape = null;
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		this.requestFocus();
		int keyvalue = e.getKeyCode();
		if(selectShape != null)
		{
			if(keyvalue == KeyEvent.VK_BACK_SPACE)
			{
				Control.model.delete(selectShape);
				selectShape = null;
			}
			if(keyvalue == KeyEvent.VK_UP)
			{
					selectShape.changeSize(1.2, 1.2);
			}
			if(keyvalue == KeyEvent.VK_DOWN)
			{
				selectShape.changeSize(0.8, 0.8);
			}
			if(keyvalue == KeyEvent.VK_RIGHT)
			{
				if(selectShape instanceof Text)
				{
					String content = JOptionPane.showInputDialog("ÇëÊäÈëÄÚÈÝ");
					if(content != null)
					{
						selectShape.setcontent(content);
					}		
				}
			}
			if(keyvalue == KeyEvent.VK_1)
			{
				selectShape.setthickness(0);
			}
			if(keyvalue == KeyEvent.VK_2)
			{
				selectShape.setthickness(1);
			}
			if(keyvalue == KeyEvent.VK_R)
			{
				selectShape.changeAngle(10);
			}
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for(Shape s : Control.model.getdata())
		{
			s.draw(g);
		}
	}
	private Shape getSelectShape(Point p)
	{
		for(Shape s : Control.model.getdata())
		{
			if(s.isSelect(p))
				return s;
		}
		return null;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
