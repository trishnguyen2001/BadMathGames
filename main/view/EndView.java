import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;

public class EndView extends JFrame {
    BlockingQueue<Message> queue;

    JButton updateMessageButton;
    JButton exitToHomeMessageButton;
    JTextField nameField;


    JLabel nameLabel;

    Score theScore;

    JLabel scoreboard;


    public EndView(BlockingQueue<Message> queue, Score score) {
        this.queue = queue;
        theScore = score;

        this.nameField = new JTextField(10);
        this.updateMessageButton = new JButton("Submit Name");

        this.nameLabel = new JLabel("Enter your name! ");

        // this.scoreboard = new JLabel(theScore); <- however score is displayed

        updateMessageButton.addActionListener(e -> {
            String name = nameField.getText();

            try {
                Message msg = new NewScoreboardEntryMessage(name);
                queue.put(msg);

            } catch (InterruptedException exception) {
                // do nothing
            }
        });

        exitToHomeMessageButton.addActionListener(e -> {
            try {
                queue.put(new ExitToHomeMessage());
            } catch (InterruptedException exception) {
                // do nothing
            }
        });


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
