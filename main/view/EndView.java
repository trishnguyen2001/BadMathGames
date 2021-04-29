package main.view;

import javax.swing.*;

import main.controller.Message;
import main.controller.NewScoreboardEntryMessage;
import main.model.Score;
import main.model.Score;

import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EndView extends View {
    BlockingQueue<Message> queue;

    JButton newScoreboardEntryBtn, exitToHomeMessageButton;
    JTextField nameField;

    JLabel scoreLabel;
    JLabel nameLabel;

    Score theScore;

    JLabel scoreboard;


    public EndView(BlockingQueue<Message> queue, Score score) {
        this.queue = queue;
        theScore = score;

        this.nameField = new JTextField(10);
        
        this.scoreLabel = new JLabel("Your score! " + theScore.getCorrect());
        this.nameLabel = new JLabel("Enter your name! ");

        // this.scoreboard = new JLabel(theScore); <- however score is displayed
        
        //return to home Btn
        this.newScoreboardEntryBtn = new JButton("Submit Name");
        newScoreboardEntryBtn.addActionListener(e -> {
            String name = nameField.getText();

            try {
                Message msg = new NewScoreboardEntryMessage(name, theScore);
                queue.put(msg);

            } catch (InterruptedException exception) {
                // do nothing
            }
        });
        newScoreboardEntryBtn.setVisible(false);
       
        //this.add(scoreboard);
        this.add(scoreLabel);
        this.add(nameLabel);
        this.add(nameField);


        this.add(newScoreboardEntryBtn);
        //this.add(exitToHomeMessageButton);


        this.setSize(1000, 1000);
        this.setLayout(new FlowLayout());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //not sure if we need
    public void updateScoreBoard() {
        // called in controller
        // this.theScore.updateHeight(red, green, blue); <- however it updates the score display
        scoreboard.repaint();
    }
    
    public static void main(String args[]) {
    	BlockingQueue<Message> q = new LinkedBlockingQueue<>();
    	Score s = new Score("alg", 9);
    	s.setPlayer("tom");
    	EndView test = new EndView(q, s);
    }
    
}
