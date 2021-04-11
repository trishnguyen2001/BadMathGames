package d1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
	
	public static void main(String args[]) {
		BlockingQueue<Message> q = new LinkedBlockingQueue<>();
		
		HomeView start = new HomeView(q);
		
	}
}
