package d1;

import java.awt.*;
import javax.swing.*;
import java.util.concurrent.BlockingQueue;


public class HomeView{
	private JFrame home;
	private BlockingQueue<Message> q;
	
	public HomeView(BlockingQueue<Message> q) {
		this.q = q;
		
		JButton start = new JButton("START");
		
		start.addActionListener(event -> {
			Message msg = new StartGameMessage("START");
			try {
				q.put(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		home = new JFrame();
		home.add(start);
		home.setLayout(new FlowLayout());
		home.pack();
		home.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		home.setVisible(true);
		
	}
	
}
