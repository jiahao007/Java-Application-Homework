package view;

import javax.swing.WindowConstants;

import control.Control;
import model.Model;

public class Test{

	public static void main(String[] args) {
		View view = new View();
		Model model = new Model();
		Control control = new Control(model, view);
		view.setSize(800, 600);
		view.setVisible(true);
		view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

}
