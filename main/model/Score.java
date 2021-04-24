package main.model; 

public class Score implements Comparable{
    int correct;
    String subject;
    String player;
    
    public Score(String player, String subject, int correct) {
    	this.player = player;
    	this.correct = correct;
        this.subject = subject;
    }
    
    public Score(String subject, int correct) {
        this.correct = correct;
        this.subject = subject;
    }

    public int getCorrect() {
    	return correct;
    }

	//compare by score first, then by name
    //only compare two scores w/ the same subject
    @Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
    
	public String toString() {
		return player + "," + subject + "," + correct;
	}
    
}
