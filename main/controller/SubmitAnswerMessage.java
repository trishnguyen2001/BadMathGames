package main.controller;

import main.model.Problem;

public class SubmitAnswerMessage implements Message{
	private double answer;
	private Problem prob;
	
	public SubmitAnswerMessage(double answer, Problem prob){
		this.answer = answer;
		this.prob = prob;
	}
	
	public double submitAnswer() {
		return answer;
	}
	
	public Problem getCurrent() {
		return prob;
	}
}
