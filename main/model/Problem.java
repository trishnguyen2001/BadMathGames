package main.model;

import java.util.Objects;

/**
 * Problem object that holds a problem as a string and its answer as a double
 *
 */
public class Problem  {
    private String problem;		
    private double answer;

    /**
     * ctor 
     * @param problem given problem as a string
     * @param answer given answer as a double
     */
    public Problem(String problem, double answer) {
        this.problem = problem;
        this.answer = answer;
    }

    /*
     * @return the problem as a string
     */
    public String getProblem() {
        return problem;
    }

    /**
     * @return the answer as a double
     */
    public double getAnswer() {
        return answer;
    }

    /**
     * returns true if two problems are equal to each other
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem1 = (Problem) o;
        return Double.compare(problem1.answer, answer) == 0 &&
                Objects.equals(problem, problem1.problem);
    }

    /**
     * hashcode representation of problem object
     */
    public int hashCode() {
        int hash = 7;
        hash = (int) (hash * 32 * answer);
        hash = hash * problem.hashCode();
        return hash;
    }
    
    /**
     * string representation of problem object
     */
    public String toString() {
    	return problem + "," + answer;
    }
}