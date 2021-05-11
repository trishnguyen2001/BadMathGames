package main.model;

/**
 * object that holds player's name, score, and topic
 * @author Trish Nguyen, Dan Nguyen, Alan Nguyen
 *
 */
public class Score implements Comparable<Score> {
	String player;		//player's name
	String topic;		//round's topic
	int correct;		//number of problems answered correctly

	public Score(String topic, int correct) {
		this.correct = correct;		//number of problems answered correctly
		this.topic = topic;			//topic associated w/ this score
	}
	
	/**
	 * sets player with this score
	 * @param player given player
	 */
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
	
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) {
        	return false;
        }
        Score that = (Score) o;
        return this.compareTo(that) == 0;
	}

	/**
	 * string representation of score
	 */
	public String toString() {
		return player + "," + correct;
	}
	
	/**
	 * @return number of problems answered correctly
	 */
	public int getCorrect() {
		return correct;
	}
	
	/**
	 * @return player associated w/ score
	 */
	public String getPlayer() {
		return player;
	}
	
	/**
	 * @return topic associated w/ this score
	 */
	public String getTopic() {
		return topic;
	}
}
