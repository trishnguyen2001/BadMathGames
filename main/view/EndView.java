package main.view;

import javax.swing.*;

import main.controller.Message;
import main.controller.NewScoreboardEntryMessage;
import main.model.Score;
import java.awt.*;
import java.util.concurrent.BlockingQueue;

public class EndView extends View {
	private static final long serialVersionUID = 1L;

	BlockingQueue<Message> queue;

	JFrame endView;
	JButton newScoreboardEntryBtn;
	JTextField nameField;
	JPanel panel;
	JLabel scoreLabel, nameLabel, scoreboard;
	Score theScore;


	/**
	 * Display score and allow user to enter name into scoreboard
	 *
	 * @param queue queue to save to
	 * @param score final score from game
	 */
	public EndView(BlockingQueue<Message> queue, Score score) {
		System.out.println("ENDVIEW: new endview created");


		System.out.println("ENDVIEW: new endview created");


		this.queue = queue;
		theScore = score;
		Font bold = new Font("bolded", Font.BOLD, 30);


		endView = new JFrame();
		endView.setLayout(new FlowLayout());
		endView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


		scoreLabel = new JLabel("YOUR SCORE: " + theScore.getCorrect());
		scoreLabel.setFont(bold);


		nameLabel = new JLabel("PLEASE ENTER YOUR NAME: ");
		nameField = new JTextField(10);


		//ScoreboardEntryBtn
		newScoreboardEntryBtn = new JButton("SUBMIT");
		newScoreboardEntryBtn.addActionListener(e -> {
			String name = nameField.getText();

			//Put new scoreboard entry message into queue
			try {
				Message msg = new NewScoreboardEntryMessage(name, theScore);
				queue.put(msg);
			} catch (InterruptedException exception) {
				// do nothing
			}
		});

		//LOGO
		JLabel logo = new JLabel(new ImageIcon("src/main/toast.png"));

		//filler
		Dimension minSize = new Dimension(5, 50);
		Dimension prefSize = new Dimension(5, 50);
		Dimension maxSize = new Dimension(Short.MAX_VALUE, 50);
		Box.Filler box = new Box.Filler(minSize, prefSize, maxSize);


		//pack frame
		panel.add(box);
		panel.add(scoreLabel);
		panel.add(box);
		panel.add(box);
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(box);
		panel.add(newScoreboardEntryBtn);
		endView.add(logo);
		endView.add(panel);
		endView.pack();
		endView.setSize(800, 500);
		endView.setVisible(true);


	}

	//not sure if we need

	/**
	 * Redisplay scoreboard with updated values
	 */
	public void updateScoreBoard() {
		// called in controller
		// this.theScore.updateHeight(red, green, blue); <- however it updates the score display
		scoreboard.repaint();
	}


	/**
	 * Close view
	 */
	public void close() {
		endView.dispose();
	}


}