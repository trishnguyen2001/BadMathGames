package main.controller;

import main.model.Score;

public class NewScoreboardEntryMessage implements Message{
	private String name;
	private Score s;
	
	public NewScoreboardEntryMessage(String name, Score s) {
		this.name = name;
		this.s = s;
	}
	
	public Score getScore() {
		return s;
	}
	
	public String getName() {
		return name;
	}
}
