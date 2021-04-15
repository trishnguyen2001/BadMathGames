package main.model; 

public class Score implements GameModel {
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
