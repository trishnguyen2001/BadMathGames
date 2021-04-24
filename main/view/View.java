package main.view;

import javax.swing.*;

public class View extends JFrame{
//	public boolean isEnabled() {
//		
//	}
	
	public void change(View view) {
		this.setVisible(false);
		view.setVisible(true);
	}
}
