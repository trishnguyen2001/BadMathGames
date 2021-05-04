package main.model;

import java.util.ArrayList;

public class Round {
	private ArrayList<Problem> problems;
	private ProblemGenerator pg;
	private int current;

	public Round() {
		System.out.println("ROUND: round created");
		current = 0;
	}

	public void setProbGen(ProblemGenerator pg) {
		this.pg = pg;
		this.pg.randomize();
		problems = pg.getSet();
	}

	public Problem getNext() {
		if(current < problems.size())
		{
			
			Problem p = problems.get(current);
			
			
			System.out.println("ROUND: current problem = " + p.getProblem());
			
			
			current++;
			return p;
		}
		return null;
	}
}