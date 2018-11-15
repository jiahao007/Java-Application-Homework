import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.ArrayList;

class Line
{
	int ori_x, ori_y;
	int cur_x, cur_y;
	float width;
	Color color;
	Line(int ori_x, int ori_y, int cur_x, int cur_y, float width, Color color)
	{
		this.ori_x = ori_x; this.ori_y = ori_y;
		this.cur_x = cur_x; this.cur_y = cur_y;
		this.width = width; this.color = color;
	}	
}

public class RandomDraw extends JPanel
{
	private static final long serialVersionUID = 1L;
	Color color = Color.BLACK;
	ArrayList<Line> line = new ArrayList<Line>();
	
	RandomDraw()
	{
		mydraw();
		setVisible(true);
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		for(Line l : line)
		{
			g2.setColor(l.color);
			g2.setStroke(new BasicStroke(l.width));
			g2.drawLine(l.ori_x, l.ori_y, l.cur_x, l.cur_y);
		}
	}
	
	public float pen_size(int ori_x, int ori_y, int cur_x, int cur_y)
	{
		final float MIN_SIZE = 1.0f, MAX_SIZE = 8.0f, MIN_DISTANCE = 0.0f, MAX_DISTANCE = 25.0f;
		float pen_size = MIN_SIZE;
		float distance = (float)(Math.sqrt(Math.pow(cur_x-ori_x, 2) + Math.pow(cur_y - ori_y, 2)));
		if(distance > MAX_DISTANCE)
			pen_size = MIN_SIZE;
		else if(distance < MIN_DISTANCE)
			pen_size = MAX_SIZE;
		else
		{
			pen_size = (distance - MIN_DISTANCE) / (MAX_DISTANCE - MIN_DISTANCE) * (-1) + 1;
			pen_size = pen_size * 7 + 1;
		}
		
		return pen_size;
	}
		
	public void mydraw()
	{
		MouseAdapter MA = new MouseAdapter()
		{
			int ori_x = 0, ori_y = 0;
			int cur_x = 0, cur_y = 0;
			
			@Override
			public void mouseMoved(MouseEvent e)
			{
				ori_x = e.getX();
				ori_y = e.getY();
				System.out.println("ori_x: " + ori_x + " ori_y: " +ori_y);
			}
			
			@Override
			public void mouseDragged(MouseEvent e)
			{
				float width = 8.0f;
				cur_x = e.getX();
				cur_y = e.getY();
				width = pen_size(ori_x, ori_y, cur_x, cur_y);
				Line tmp = new Line(ori_x, ori_y, cur_x, cur_y, width, color);
				line.add(tmp);
				repaint();
				ori_x = cur_x;
				ori_y = cur_y;
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(e.getButton() == MouseEvent.BUTTON1)
					color = Color.BLACK;
				if(e.getButton() == MouseEvent.BUTTON3)
					color = Color.WHITE;
			}
		};
		this.addMouseMotionListener(MA);
		this.addMouseListener(MA);
	}
	public static void main(String[] args)
	{
		JFrame jf = new JFrame();
		RandomDraw rd = new RandomDraw();
		jf = new JFrame("Random Draw");
		jf.setBounds(300, 100, 600, 500);
		jf.setBackground(Color.WHITE);
		jf.add(rd);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}