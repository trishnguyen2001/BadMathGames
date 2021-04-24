package main.model; 

public class Score  {
    int correct;
    String subject;

    public Score(String subject, int correct) {
        this.correct = correct;
        this.subject = subject;
    }

    public int getCorrect() {
    	return correct;
    }

}
