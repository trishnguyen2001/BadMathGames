package main.view;

import java.awt.*;
import javax.swing.*;

import main.controller.Message;
import main.controller.TopicSelectMessage;
import main.controller.ViewScoreboardMessage;

import java.util.concurrent.BlockingQueue;


public class HomeView extends View{
	private static final long serialVersionUID = 1L;
	private JFrame home;
	private JPanel topicSelect;
	private BlockingQueue<Message> q;

	public HomeView(BlockingQueue<Message> q) {
		System.out.println("HOMEVIEW: new homeview created");
		
		this.q = q;

		Font bold = new Font("bolded", Font.BOLD, 30);
		Font bold2 = new Font("bolded2", Font.BOLD, 20);
		
		//ALG BTN
		JButton algBtn = new JButton("Algebra");
		algBtn.setSize(200, 20);
		algBtn.setFont(bold2);
		algBtn.addActionListener(event -> {
			Message msg = new TopicSelectMessage("alg");
			try {
				this.q.put(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		//MULT BTN 
		JButton multBtn = new JButton("Multiplication Tables");
		multBtn.setSize(200, 20);
		multBtn.setFont(bold2);
		multBtn.addActionListener(event -> {
			Message msg = new TopicSelectMessage("mult");
			try {
				this.q.put(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		//DISPLAY SCOREBOARD BTN
		JButton scoreboardBtn = new JButton("View Scoreboards");
		scoreboardBtn.addActionListener(event-> {
			Message msg = new ViewScoreboardMessage();
			try {
				this.q.put(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});


		home = new JFrame();
		home.setLayout(new FlowLayout());
		home.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		topicSelect = new JPanel();
		topicSelect.setLayout(new BoxLayout(topicSelect, BoxLayout.Y_AXIS));
		
		Dimension minSize = new Dimension(5, 50);
		Dimension prefSize = new Dimension(5, 50);
		Dimension maxSize = new Dimension(Short.MAX_VALUE, 50);
		
		JLabel title = new JLabel("BAD MATH GAMES");
		title.setFont(bold);
		
		JLabel directions = new JLabel("PICK A TOPIC");
		directions.setFont(bold2);
		
		topicSelect.add(title);
		topicSelect.add(new Box.Filler(minSize, prefSize, maxSize));
		topicSelect.add(directions);
		topicSelect.add(new Box.Filler(minSize, prefSize, maxSize));
		topicSelect.add(algBtn);
		topicSelect.add(new Box.Filler(minSize, prefSize, maxSize));
		topicSelect.add(multBtn);
		topicSelect.add(new Box.Filler(minSize, prefSize, maxSize));
		topicSelect.add(scoreboardBtn);
		home.add(topicSelect);
		home.pack();
		home.setSize(800, 500);
		home.setVisible(true);
	}
	
	public void close() {
        home.dispose();
    }
}
