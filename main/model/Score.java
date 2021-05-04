package main.model;

public class Score implements Comparable<Score> {
	String player;		//player's name
	String topic;
	int correct;

	public Score(String topic, int correct) {
		this.correct = correct;
		this.topic = topic;
	}
	
	public void setPlayer(String player) {
		this.player = player;
	}

	//compare by # of correct problems first, then by name
	//only compare two scores w/ the same subject
	@Override
	public int compareTo(Score o) {
		if(this.correct == o.correct) {
			return this.player.compareTo(o.player);
		}
		return this.correct - o.correct;
	}

	public String toString() {
		return player + "," + correct;
	}
	
	public int getCorrect() {
		return correct;
	}
	
	public String getPlayer() {
		return player;
	}
	
	public String getTopic() {
		return topic;
	}
}
