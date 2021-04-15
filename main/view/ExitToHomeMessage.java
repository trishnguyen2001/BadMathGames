package main.view;

public class ExitToHomeMessage implements Message{
	private boolean exitToHome;
	
	public ExitToHomeMessage(boolean b) {
		exitToHome = b;
	}
	
	public boolean exit() {
		return exitToHome;
	}
}
