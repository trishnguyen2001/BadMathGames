package main.view;

import main.controller.Message;
import main.controller.SubmitAnswerMessage;
import main.model.Problem;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;

public class RoundView extends View {
    private static final long serialVersionUID = 1L;
    private BlockingQueue<Message> queue;
    private JFrame frame;
    private Problem p;
    private JTextField answerInput;
    private JButton submitBtn;
    private JLabel problemText, directions;
    private JPanel problemPanel;
    private Box.Filler box;
    private double givenAnswer;

    /**
     * View to display problem and receive answer
     *
     * @param q queue to save to
     * @param p current problem
     */
    public RoundView(BlockingQueue<Message> q, Problem p) {
        System.out.println("ROUNDVIEW: new roundview created");


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

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        problemPanel = new JPanel();


        Dimension minSize = new Dimension(50, 50);
        Dimension prefSize = new Dimension(50, 50);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 50);
        box = new Box.Filler(minSize, prefSize, maxSize);

        //SUBMIT BUTTON ACTION LISTENER
        submitBtn.addActionListener(e -> {
            try {
                givenAnswer = Double.parseDouble(answerInput.getText().trim());
                Message submitMessage = new SubmitAnswerMessage(givenAnswer, this.p);
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
        answerInput.setSize(200, 20);
        problemPanel.add(box);
        problemPanel.add(submitBtn);
        frame.add(problemPanel);
        frame.pack();
        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    /**
     * Update view with new problem
     *
     * @param p new problem to be displayed
     */
    public void updateRoundView(Problem p) {
        this.setVisible(false);
        this.p = p;
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
        answerInput.setSize(200, 20);
        problemPanel.add(box);
        problemPanel.add(submitBtn);
        frame.add(problemPanel);
        frame.pack();
        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    /**
     * Close view
     */
    public void close() {
        frame.dispose();
    }
}

