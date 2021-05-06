package main.view;

import javax.swing.*;

public class View extends JFrame{
	private static final long serialVersionUID = 1L;

	public void changeTo(View show, View hide) {
		System.out.println("VIEW: changeTo called");
		show.setVisible(true);
		hide.close();
		
	}
	
	public void close() {
		this.dispose();
	}
}
