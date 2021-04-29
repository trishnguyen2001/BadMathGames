package main.view;

import javax.swing.*;

public class View extends JFrame{
	
	public void changeTo(View view) {
		this.setVisible(false);
		view.setVisible(true);
	}
}
