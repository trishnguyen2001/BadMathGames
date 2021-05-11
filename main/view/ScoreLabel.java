package main.view;

import main.model.Score;

import javax.swing.*;
import java.awt.*;

public class ScoreLabel extends JPanel {
    private static final long serialVersionUID = 1L;
    JLabel name, score, placeholder;

    /**
     * Used to display scores in ScoreboardView
     *
     * @param s current score
     */
    public ScoreLabel(Score s) {
        name = new JLabel(s.getPlayer());
        placeholder = new JLabel("                ");
        score = new JLabel(String.valueOf(s.getCorrect()));

        this.setLayout(new FlowLayout());
        this.add(name);
        this.add(placeholder);
        this.add(score);
    }
}
