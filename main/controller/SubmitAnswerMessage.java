package main.controller;

import main.model.Problem;

public class SubmitAnswerMessage implements Message{
	private double answer;
	private Problem prob;

	/**
     	* ctor
     	* @param answer given answer as a double
     	* @param prob   given problem as a problem obj
     	*/
	public SubmitAnswerMessage(double answer, Problem prob){
		this.answer = answer;
		this.prob = prob;
	}

	/**
     	* @return answer as a double
     	*/
	public double submitAnswer() {
		return answer;
	}

	/**
     	* @return problem as a problem object
     	*/
	public Problem getProblem() {
		return prob;
	}
}
