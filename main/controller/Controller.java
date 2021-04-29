package main.controller;

import main.model.AlgDB;
import main.model.MultDB;
import main.model.Problem;
import main.model.ProblemGenerator;
import main.model.Round;
import main.model.Score;
import main.view.*;

import java.util.concurrent.BlockingQueue;

public class Controller {
	BlockingQueue<Message> queue;
	Round gameModel;
	View currentV;
	EndView endV;
	HomeView homeV;
	ScoreboardView scoreboardV;
	RoundView roundV;
	AlgDB adb;
	MultDB mdb;
	int correctAnswers;
	String topic;

	public Controller(BlockingQueue<Message> queue, HomeView home, AlgDB adb, MultDB mdb) {
		this.queue = queue;
		this.currentV = home;
		this.adb = adb;
		this.mdb = mdb;
		
		this.homeV = home;
		scoreboardV = new ScoreboardView(queue);
		correctAnswers = 0;
	}

	/**
	 * Checks for new messages
	 * no messages --> program done
	 */
	public void mainLoop() {
		while (currentV.isEnabled()) {
			System.out.println("CONTROLLER: mainLoop entered");
			Message m = null;

			try {
				m = queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//topic is selected --> feed to round params
			//start round
			if (m.getClass() == TopicSelectMessage.class) {
				TopicSelectMessage startRoundMessage = (TopicSelectMessage) m;
				topic = startRoundMessage.getTopic();
				ProblemGenerator pg = null;
				if(topic.equalsIgnoreCase("alg")) {
					pg = new ProblemGenerator(adb);
				}
				else {
					pg = new ProblemGenerator(mdb);
				}
				gameModel = new Round();
				gameModel.setProbGen(pg);
				Problem first = gameModel.getNext();
				//view = HomeView --> change to RoundView
				roundV = new RoundView(queue, first);	//creates round view displaying first problem
				currentV = roundV;
				currentV.changeTo(roundV);
				
				
				System.out.println("CONTROLLER: topic selected! --> " + topic);
			}

			//answer submitted --> check answer
			if (m.getClass() == SubmitAnswerMessage.class) {
				SubmitAnswerMessage submitAnswerMessage = (SubmitAnswerMessage) m;
				double input = submitAnswerMessage.submitAnswer();
				double answer = submitAnswerMessage.getCurrent().getAnswer();
				if(input == answer) {
					correctAnswers++;
					
				}
				Problem next = gameModel.getNext();
				if(next != null) {
					roundV.updateRoundView(next);
				}
				else {
					//temporarily change to home
					//eventually chagne to display score
					Score s = new Score(topic, correctAnswers);
					endV = new EndView(queue, s);
					currentV.changeTo(endV);
					currentV = endV;
				}
				
				
				
				System.out.println("CONTROLLER: SubmitAnswerBtn pressed!");
			}

			//qualifying score --> write to .csv file
			if (m.getClass() == NewScoreboardEntryMessage.class) {
				NewScoreboardEntryMessage newScoreboardEntryMessage = (NewScoreboardEntryMessage) m;
				
				
				
				System.out.println("CONTROLLER: NewScoreboardEntry!");
			}

			//View scoreboard button pressed
			if (m.getClass() == ViewScoreboardMessage.class) {
				ViewScoreboardMessage viewScoreboardMessage = (ViewScoreboardMessage) m;
				currentV.changeTo(scoreboardV);
				currentV = scoreboardV;
				
				
				System.out.println("CONTROLLER: ViewScoreboardBtn pressed!");
			}
		}
	}
}