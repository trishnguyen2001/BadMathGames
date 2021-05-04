package main.view;

import java.awt.FlowLayout;

import javax.swing.*;

import main.model.Score;

public class ScoreLabel extends JPanel{
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
