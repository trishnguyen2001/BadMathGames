package d1; 

import java.util.ArrayList;

public class Score {
    int numberOfAnswers;
    int time;
    String subject;
    ArrayList<Score> scores;

    public Score(String subject, int time, int numberOfAnswers) {
        this.numberOfAnswers = numberOfAnswers;
        this.time = time;
        this.subject = subject;
    }

    public void addScore(Score theScore) {
        scores.add(theScore);
    }

    public ArrayList<Score> getAllScores() {
        return scores;
    }


}
