package main.view;

public class StartRoundMessage implements Message{
	private boolean start;
	
	public StartRoundMessage(boolean start) {
		this.start = start;
	}

	public boolean start() {
		return start;
	}
}
