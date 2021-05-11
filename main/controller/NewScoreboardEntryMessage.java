package main.controller;

import main.model.Score;

public class NewScoreboardEntryMessage implements Message{
	private String name;
	private Score s;

	/**
	* ctor
     	* @param name given name as a String
     	* @param s given score as a Score obj
     	*/
	public NewScoreboardEntryMessage(String name, Score s) {
		this.name = name;
		this.s = s;
	}

	/**
     	* @return score as a score obj
    	*/
	public Score getScore() {
		return s;
	}

	/**
     	* @return name as a string
     	*/
	public String getName() {
		return name;
	}
}
