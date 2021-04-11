package d1;

import javax.swing.JFrame;

public class View {
	private JFrame screen;
	private static final JFrame HOME;
	private static final JFrame MENU;
	private static final JFrame ROUND;
	private static final JFrame END;
	private static final JFrame SCOREBOARD;
	
	
	public void setScreen(JFrame j)
	{
		screen = j;
		screen.show(); 
	}
}
