package main.view;

import javax.swing.*;

import main.controller.Message;
import main.controller.NewScoreboardEntryMessage;
import main.model.Score;

import java.awt.*;
import java.util.concurrent.BlockingQueue;

public class EndView extends View {
    BlockingQueue<Message> queue;

    JButton updateMessageButton;
    JButton exitToHomeMessageButton;
    JTextField nameField;

    JLabel scoreLabel;
    JLabel nameLabel;

    Score theScore;

    JLabel scoreboard;


    public EndView(BlockingQueue<Message> queue, Score score) {
        this.queue = queue;
        theScore = score;

        this.nameField = new JTextField(10);
        this.updateMessageButton = new JButton("Submit Name");
        this.scoreLabel = new JLabel("Your score! " + theScore);
        this.nameLabel = new JLabel("Enter your name! ");

        // this.scoreboard = new JLabel(theScore); <- however score is displayed

        updateMessageButton.addActionListener(e -> {
            String name = nameField.getText();

            try {
                Message msg = new NewScoreboardEntryMessage(name, theScore);
                queue.put(msg);

            } catch (InterruptedException exception) {
                // do nothing
            }
        });
       
        this.add(scoreboard);
        this.add(scoreLabel);
        this.add(nameLabel);
        this.add(nameField);


        this.add(updateMessageButton);
        this.add(exitToHomeMessageButton);


        this.setSize(1000, 1000);
        this.setLayout(new FlowLayout());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void updateScoreBoard() {
        // called in controller
        // this.theScore.updateHeight(red, green, blue); <- however it updates the score display
        scoreboard.repaint();

    }

    public void exitToHome() {
        // called in controller
    }
}
