package main.view;

import main.controller.Message;
import main.controller.SubmitAnswerMessage;
import main.model.Problem;
import main.model.Round;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class RoundView extends View{
	private BlockingQueue<Message> queue;
	private JFrame frame;
	private Round round;
	private ArrayList<Problem> problems;
	private Problem currentProblem;
	private JTextField answer;
	private int current;
	private JButton submit;
	private JLabel problemLabel;

	public RoundView(BlockingQueue<Message> q, Round r) {
		this.frame = new JFrame();
		this.queue = q;
		this.round = r;
		this.current = 0;
		this.problems = round.getProblems();
		this.currentProblem = problems.get(current);
		this.answer = new JTextField(10);

		this.submit = new JButton("submit");


		submit.addActionListener(e -> {
			try {
				double givenAnswer = Double.parseDouble(answer.getText().trim());
				Message submitMessage = new SubmitAnswerMessage(givenAnswer);
				queue.put(submitMessage);
			} catch (InterruptedException exception) {
				System.out.println("Error: " + exception.getMessage());
			}
		});

		frame.setLayout(new GridLayout(0, 3, 5, 5));
		frame.setSize(1000, 1000);

		Dimension minSize = new Dimension(5, 50);
		Dimension prefSize = new Dimension(5, 50);
		Dimension maxSize = new Dimension(Short.MAX_VALUE, 50);

		problemLabel = new JLabel(currentProblem.getProblem());
		problemLabel.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		JLabel directions = new JLabel("Please pick a topic");
		directions.setAlignmentX(JFrame.CENTER_ALIGNMENT);

		frame.add(new Box.Filler(minSize, prefSize, maxSize));
		frame.add(problemLabel);
		frame.add(new Box.Filler(minSize, prefSize, maxSize));
		frame.add(new Box.Filler(minSize, prefSize, maxSize));
		frame.add(submit);

		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public int getCurrent() {
		return current;
	}

	public void incrementCount() {
		current++;
	}

	public void updateRoundView(int newCount) {
		currentProblem = problems.get(current);
	}

	public void dispose() {

	}
}

