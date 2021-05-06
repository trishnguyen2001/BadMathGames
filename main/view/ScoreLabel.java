package main.view;

import java.awt.FlowLayout;

import javax.swing.*;

import main.model.Score;

public class ScoreLabel extends JPanel{
	private static final long serialVersionUID = 1L;
	JLabel name, score, placeholder;
	
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
