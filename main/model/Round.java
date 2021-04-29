package main.model;

import java.util.ArrayList;

public class Round {
	private ArrayList<Problem> problems;
	private ProblemGenerator pg;
	private int current;

	public Round() {
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
			current++;
			return p;
		}
		return null;
	}
}