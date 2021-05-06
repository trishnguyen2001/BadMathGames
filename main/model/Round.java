package main.model;

import main.model.ProblemSet.ProblemIterator;

/**
 * Round class (model)
 *
 */
public class Round {
    private ProblemIterator problems;		//problem set for this round

    /**
     * Round ctor
     * initializes current index @ 0
     */
    public Round() {
    	///////TEST//////////////////////////////////////////////////////
        System.out.println("ROUND: round created");
        ///////TEST//////////////////////////////////////////////////////
        
    }

    /**
     * sets problem generator to this round
     * @param pg the problem generator for this round
     */
    public void setProbGen(ProblemGenerator pg) {
        ProblemSet ps = new ProblemSet(pg);
        problems = ps.getProblems();
    }

    /**
     * gets next problem in problemset
     * @return next problem in problem set
     */
    public Problem getNext() {
        if(problems.hasNext()) {
        	return problems.next();
        }
        return null;
    }
}