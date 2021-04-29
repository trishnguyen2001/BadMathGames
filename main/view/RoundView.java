package main.view;

import main.controller.Message;
import main.controller.SubmitAnswerMessage;
import main.model.MultDB;
import main.model.Problem;
import main.model.ProblemGenerator;
import main.model.Round;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RoundView extends View{
	private BlockingQueue<Message> queue;
	private JFrame frame;
	private Problem p;
	private JTextField answerInput;
	private JButton submitBtn;
	private JLabel problemText, directions;
	private JPanel problemPanel;
	private Box.Filler box;

	public RoundView(BlockingQueue<Message> q, Problem p) {
		//initialize elements
		this.frame = new JFrame();
		this.queue = q;
		this.p = p;
		
		answerInput = new JTextField();
		
		directions = new JLabel("Input your answer in the box below.");
		
		problemText = new JLabel(p.getProblem());
		Font font = new Font("bolded", Font.BOLD, 30);
		problemText.setFont(font);
		
		Font font2 = new Font("bolded", Font.BOLD, 20);
		submitBtn = new JButton("SUBMIT");
		submitBtn.setFont(font2);
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		problemPanel = new JPanel();
		

		Dimension minSize = new Dimension(50, 50);
		Dimension prefSize = new Dimension(50, 50);
		Dimension maxSize = new Dimension(Short.MAX_VALUE, 50);
		box = new Box.Filler(minSize, prefSize, maxSize);

		//SUBMIT BUTTON ACTION LISTENER
		submitBtn.addActionListener(e -> {
			try {
				double givenAnswer = Double.parseDouble(answerInput.getText().trim());
				Message submitMessage = new SubmitAnswerMessage(givenAnswer, p);
				queue.put(submitMessage);
				System.out.println("NEW ANSWER SUBMITTED: " + givenAnswer);
			} catch (InterruptedException exception) {
				System.out.println("Error: " + exception.getMessage());
			}
		});

		//PACK FRAME
		frame.setLayout(new FlowLayout());
		problemPanel.setLayout(new BoxLayout(problemPanel, BoxLayout.Y_AXIS));
		problemPanel.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		problemPanel.add(box);
		problemPanel.add(box);
		problemPanel.add(directions);
		problemPanel.add(box);
		problemPanel.add(problemText);
		problemPanel.add(box);
		problemPanel.add(answerInput);
		answerInput.setSize(200,20);
		problemPanel.add(box);
		problemPanel.add(submitBtn);
		frame.add(problemPanel);
		frame.pack();
		frame.setSize(800, 500);
		frame.setVisible(true);
	}

	public void updateRoundView(Problem p) {
		this.setVisible(false);
		problemText.setText(p.getProblem());
		answerInput.setText("");
		
		//repack
		problemPanel.setLayout(new BoxLayout(problemPanel, BoxLayout.Y_AXIS));
		problemPanel.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		problemPanel.add(box);
		problemPanel.add(box);
		problemPanel.add(directions);
		problemPanel.add(box);
		problemPanel.add(problemText);
		problemPanel.add(box);
		problemPanel.add(answerInput);
		answerInput.setSize(200,20);
		problemPanel.add(box);
		problemPanel.add(submitBtn);
		frame.add(problemPanel);
		frame.pack();
		frame.setSize(800, 500);
		frame.setVisible(true);
	}
	
	
	public static void main(String args[]) {
		BlockingQueue<Message> q = new LinkedBlockingQueue<>();
		Problem p = new Problem("1 + 1 = x", 2);
		RoundView test = new RoundView(q, p);
		Problem p2 = new Problem("1 + 3 = x", 4);
		//test.updateRoundView(p);
	}
}

