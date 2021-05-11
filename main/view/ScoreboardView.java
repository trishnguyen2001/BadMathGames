package main.view;


import main.controller.ExitToHomeMessage;
import main.controller.Message;
import main.model.Score;
import main.model.ScoreboardHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class ScoreboardView extends View {
    private static final long serialVersionUID = 1L;
    JFrame frame;
    BlockingQueue<Message> q;
    JPanel algPanel, multPanel;
    JLabel algTitle, multTitle;
    JButton exitBtn;
    ArrayList<Score> algScores, multScores;

    /**
     * Display scoreboard
     *
     * @param q          queue to save to
     * @param algScores  top 5 algebra scores
     * @param multScores top 5 multiplication scores
     */
    public ScoreboardView(BlockingQueue<Message> q, ArrayList<Score> algScores, ArrayList<Score> multScores) {
        this.q = q;
        this.algScores = algScores;
        this.multScores = multScores;
        Font bold = new Font("bolded", Font.BOLD, 30);

        //exitBtn
        exitBtn = new JButton("BACK");
        exitBtn.addActionListener(e -> {
            try {
                Message exitMessage = new ExitToHomeMessage();
                q.put(exitMessage);
                System.out.println("SCOREBOARD VIEW: EXIT TO HOME");
            } catch (InterruptedException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        });

        //create algScoreboard
        algPanel = new JPanel();
        algPanel.setLayout(new BoxLayout(algPanel, BoxLayout.Y_AXIS));
        algTitle = new JLabel("ALGEBRA SCOREBOARD");
        algTitle.setFont(bold);
        algPanel.add(algTitle);
        ScoreboardHelper sbh = new ScoreboardHelper();
        ArrayList<Score> top5Alg = sbh.getTop5Scores(algScores);
        for (Score s : top5Alg) {
            ScoreLabel entry = new ScoreLabel(s);
            algPanel.add(entry);
        }

        //create multScoreboard
        multPanel = new JPanel();
        multPanel.setLayout(new BoxLayout(multPanel, BoxLayout.Y_AXIS));
        multTitle = new JLabel("MULTIPLICATION SCOREBOARD");
        multTitle.setFont(bold);
        multPanel.add(multTitle);
        ArrayList<Score> top5Mult = sbh.getTop5Scores(multScores);
        for (Score s : top5Mult) {
            ScoreLabel entry = new ScoreLabel(s);
            multPanel.add(entry);
        }

        //create and pack frame
        frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(algPanel);
        frame.add(multPanel);
        frame.add(exitBtn);
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
