package main.controller;

import java.util.concurrent.BlockingQueue;
import javax.swing.text.View;
import main.model.Round;
import main.view.EndView;
import main.view.HomeView;
import main.view.RoundView;
import main.view.ScoreboardView;

public class Controller {
	BlockingQueue<Message> queue;
	Round gameModel;
	View current;
	EndView end;
	HomeView home;
	ScoreboardView scoreboard;
	RoundView round;

	public Controller(BlockingQueue<Message> queue, Round gameModel, EndView end, HomeView home, RoundView round, ScoreboardView scoreboard) {
		this.queue = queue;
		this.gameModel = gameModel;
		//current = home;
		this.home = home;
		this.end = end;
		this.scoreboard = scoreboard;
		this.round = round;
	}

	/**
	 * Checks for new messages
	 * no messages --> program done
	 */
	public void mainLoop() {
		//FIX LATER
		while(true) {
			Message m = null;

			try {
				m = queue.take();
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}

			//topic is selected --> feed to round params
			//start round
			if(m.getClass() == TopicSelectMessage.class) {
				TopicSelectMessage startRound = (TopicSelectMessage) m;
				String topic = startRound.getTopic();
				gameModel = new Round(topic);
				//view = HomeView --> change to RoundView
				home.change(new RoundView(queue, gameModel));
				this.round = new RoundView(queue, gameModel);
			}

			//answer submitted --> check answer
			if(m.getClass() == SubmitAnswerMessage.class) {

			}

			//qualifying score --> write to .csv file
			if(m.getClass() == NewScoreboardEntryMessage.class) {

			}
		}
	}
}
