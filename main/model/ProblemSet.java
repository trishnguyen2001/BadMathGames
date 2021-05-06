package main.model;

import java.util.ArrayList;

/**
 * ProblemSet class (model)
 * implements iterator pattern
 *
 */
public class ProblemSet {
	private ArrayList<Problem> problemSet;
	
	/**
	 * ProblemSet ctor
	 */
	public ProblemSet(ProblemGenerator pg) {
		problemSet = new ArrayList<>();	//initializes problemset
		pg.randomize();					//pg randomizes problems
		problemSet = pg.getSet();		//pg generates problem set and uses it to initialize round's problem set
	}
	
	/**
	 * @return problems
	 */
	public ProblemIterator getProblems() {
        return new ProblemIterator() {
            int index = 0;
            
            public Problem next() {
                Problem currentProb = problemSet.get(index);
                index++;
                return currentProb;
            }

            public boolean hasNext() {
                return index < problemSet.size();
            }
        };
    }

	/**
	 * ProblemIterator interface
	 *
	 */
    interface ProblemIterator {
        Problem next();
        boolean hasNext();
    }
}
