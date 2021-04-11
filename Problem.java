package BadMathGames;

import java.util.Objects;

public class Problem {
    private String problem;
    private double answer;

    public Problem(String problem, double answer) {
        this.problem = problem;
        this.answer = answer;
    }

    public String getProblem() {
        //get problem
        return problem;
    }

    public double getAnswer() {
        //get answer
        return answer;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem1 = (Problem) o;
        return Double.compare(problem1.answer, answer) == 0 &&
                Objects.equals(problem, problem1.problem);
    }

    public int hashCode() {
        int hash = 7;
        hash = (int) (hash * 32 * answer);
        hash = hash * problem.hashCode();
        return hash;
    }
}

