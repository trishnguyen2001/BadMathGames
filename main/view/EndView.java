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


    public EndView(BlockingQueue<Message> queue, Score score) {
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
            
            try {
                Message msg = new NewScoreboardEntryMessage(name, theScore);
                queue.put(msg);

            } catch (InterruptedException exception) {
                // do nothing
            }
        });
       
        
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
        endView.add(panel);
        endView.pack();
        endView.setSize(800, 500);
        endView.setVisible(true);
        
        
    }

    //not sure if we need
    public void updateScoreBoard() {
        // called in controller
        // this.theScore.updateHeight(red, green, blue); <- however it updates the score display
        scoreboard.repaint();
    }
    
    public void close() {
        endView.dispose();
    }
    
}
