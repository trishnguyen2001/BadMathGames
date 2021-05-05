package main.view;

import java.awt.*;
import javax.swing.*;

import main.controller.Message;
import main.controller.TopicSelectMessage;
import main.controller.ViewScoreboardMessage;
import main.model.Problem;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class HomeView extends View{
	private JFrame home;
	private BlockingQueue<Message> q;

	public HomeView(BlockingQueue<Message> q) {
		System.out.println("HOMEVIEW: new homeview created");
		
		
		
		
		
		this.q = q;

		//ALG BTN
		JButton algBtn = new JButton("Algebra");
		algBtn.setBounds(40, 200, 100, 60);
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
		multBtn.setBounds(40, 100, 100, 30);
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
		home.setLayout(new GridLayout(0, 3, 5, 5));
		home.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		home.setSize(1000, 1000);
		
		Dimension minSize = new Dimension(5, 50);
		Dimension prefSize = new Dimension(5, 50);
		Dimension maxSize = new Dimension(Short.MAX_VALUE, 50);
		
		JLabel title = new JLabel("BAD MATH GAMES");
		title.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		JLabel directions = new JLabel("Please pick a topic");
		directions.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		
		home.add(new Box.Filler(minSize, prefSize, maxSize));
		home.add(title);
		home.add(scoreboardBtn);
		home.add(new Box.Filler(minSize, prefSize, maxSize));
		home.add(directions);
		home.add(new Box.Filler(minSize, prefSize, maxSize));
		home.add(algBtn);
		home.add(new Box.Filler(minSize, prefSize, maxSize));
		home.add(multBtn);
		home.add(new Box.Filler(minSize, prefSize, maxSize));
		home.add(new Box.Filler(minSize, prefSize, maxSize));
		
		home.pack();
		home.setSize(800, 500);
		home.setVisible(true);

	}

	public static void main(String args[]) {
		BlockingQueue<Message> q = new LinkedBlockingQueue<>();
		HomeView test = new HomeView(q);
	}
	
	public void close() {
        home.dispose();
    }
}
