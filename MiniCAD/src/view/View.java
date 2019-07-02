package view;

import java.awt.*;
import javax.swing.*;

public class View extends JFrame
{
	private static final long serialVersionUID = 1L;
	private DrawPanel drawPanel;
	private ApplicationPanel applicationPanel;
	private JMenuBar jmenuBar;
	
	View()
	{
		this.drawPanel = new DrawPanel();
		this.applicationPanel = new ApplicationPanel(drawPanel);
		this.add(applicationPanel, BorderLayout.EAST);
		this.add(drawPanel, BorderLayout.CENTER);
		this.jmenuBar = new JMenuBar();
		JMenu menu = new JMenu("文件");
		JMenuItem item1 = new JMenuItem("打开");
		JMenuItem item2 = new JMenuItem("保存");
		item1.addActionListener(new FileOperation(1));
		item2.addActionListener(new FileOperation(0));
		menu.add(item1);
		menu.add(item2);
		this.jmenuBar.add(menu);
		this.jmenuBar.setBackground(Color.WHITE);
		setJMenuBar(this.jmenuBar);
	}
}
