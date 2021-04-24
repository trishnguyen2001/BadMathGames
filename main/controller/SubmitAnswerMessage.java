package main.controller;

public class SubmitAnswerMessage implements Message{
	private double answer;
	
	public SubmitAnswerMessage(double answer){
		this.answer = answer;
	}
	
	public double getAnswer() {
		return answer;
	}
}
