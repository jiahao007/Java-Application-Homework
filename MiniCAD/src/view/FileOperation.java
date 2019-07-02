package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import control.Control;
import model.Shape;

public class FileOperation implements ActionListener 
{

	private int item;
	FileOperation(int item)
	{
		this.item = item;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(item == 0)
		{
			filesave();
		}
		else if(item == 1)
		{
			fileopen();
		}
	}
	
	private void filesave()
	{
		JFileChooser savechooser = new JFileChooser();
		int returnVal = savechooser.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			String path = savechooser.getSelectedFile().getPath();
			try 
			{
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + ".jag"));
				oos.writeObject(Control.model.getdata());
				oos.close();
			}catch(IOException e1)
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,"保存失败","错误",JOptionPane.ERROR_MESSAGE);
			}
			JOptionPane.showMessageDialog(null,"保存成功","提示",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void fileopen()
	{
		ArrayList<Shape> listShape = new ArrayList<Shape>();
		JFileChooser openchooser = new JFileChooser();
		int returnVal = openchooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			String path = openchooser.getSelectedFile().getPath();
			try 
			{
				System.out.println(path);
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
				listShape = (ArrayList<Shape>)ois.readObject();
				ois.close();
			}catch(IOException e1)
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,"打开失败","错误",JOptionPane.ERROR_MESSAGE);
				return;
			} catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"打开失败","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null,"打开成功","提示",JOptionPane.INFORMATION_MESSAGE);
			Control.setdata(listShape);
		}
	}

}
