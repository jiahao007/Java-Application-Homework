package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;

import model.ShapeMode;

public class ApplicationPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	public DrawPanel drawPanel;
	private JPanel colorJapnel;
	private HashMap<String, JButton> choice = new HashMap<String, JButton>();
	private static Color[] color = {
			Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, 
			Color.GREEN, Color.ORANGE, Color.PINK, Color.YELLOW, 
			Color.RED,Color.LIGHT_GRAY,Color.MAGENTA, Color.WHITE,
			new Color(100, 128, 234), new Color(26, 125, 127),
			new Color(123, 12, 45), new Color(185, 45, 37)
			};

	JButton line;
	JButton rectangle;
	JButton ellipse;
	JButton fillrect;
	JButton fillellipse;
	JButton polyline;
	JButton polygon;
	JButton text;
	public ApplicationPanel(DrawPanel drawPanel)
	{
		this.drawPanel = drawPanel;
		this.setLayout(new GridLayout(9, 2));
		
		line = MyButton("Line");
		rectangle = MyButton("Rectangle");
		ellipse = MyButton("Ellipse");
		fillrect = MyButton("Fillrect");
		fillellipse = MyButton("Fillellipse");
		polyline = MyButton("Polyline");
		polygon = MyButton("Polygon");
		text = MyButton("Text");
		
		this.colorJapnel = new JPanel();
		this.colorJapnel.setLayout(new GridLayout(4, 4));
		for(int i = 0; i < color.length; i++)
		{
			JButton jb = new JButton();
			Dimension preferredSize = new Dimension(10, 10);
			jb.setPreferredSize(preferredSize);
			jb.setBackground(color[i]);
			jb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					Color choose = jb.getBackground();
					drawPanel.color = choose;
					if(drawPanel.selectShape != null)
						drawPanel.selectShape.setcolor(choose);
					drawPanel.requestFocus();
				}
			}
			);
			this.colorJapnel.add(jb);
		}
		
		add(line);
		add(rectangle);
		add(ellipse);
		add(fillrect);
		add(fillellipse);
		add(polyline);
		add(polygon);
		add(text);
		add(colorJapnel);
		
		
		MysetSelect(ShapeMode.Line);
		line.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				MysetSelect(ShapeMode.Line);
			}
		});
		
		rectangle.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				MysetSelect(ShapeMode.Rectangle);
			}
		});
		
		ellipse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				MysetSelect(ShapeMode.Ellipse);
			}
		});
		
		fillrect.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				MysetSelect(ShapeMode.Fillrect);
			}
		});
		
		fillellipse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				MysetSelect(ShapeMode.Fillellipse);
			}
		});
		
		polyline.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				MysetSelect(ShapeMode.Polyline);
			}
		});
		
		polygon.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				MysetSelect(ShapeMode.Polygon);
			}
		});
		
		text.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				MysetSelect(ShapeMode.Text);
			}
		});
		
	}
	
	private JButton MyButton(String s) 
	{
		JButton jb = new JButton(s);
		choice.put(s, jb);
		return jb;
	}
	private void MysetSelect(ShapeMode shapeMode)
	{
		String s = shapeMode.toString();
		JButton btn = choice.get(s);
		Dimension preferredSize = new Dimension(40, 40);
		btn.setPreferredSize(preferredSize);
		btn.setSelected(true);
		drawPanel.requestFocus();
		drawPanel.currentShapeMode = shapeMode;
	}
}
