package main.controller;

import main.model.AlgDB;
import main.model.MultDB;
import main.model.Problem;
import main.model.ProblemGenerator;
import main.model.Round;
import main.model.Score;
import main.view.*;

import java.io.FileWriter;
import java.util.ArrayList;
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
	ArrayList<Score> algScores, multScores;

	public Controller(BlockingQueue<Message> queue, HomeView home, AlgDB adb, MultDB mdb, ArrayList<Score> algScores, ArrayList<Score> multScores) {
		this.queue = queue;
		this.currentV = home;
		this.adb = adb;
		this.mdb = mdb;
		this.algScores = algScores;
		this.multScores = multScores;

		this.homeV = home;
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
				homeV.setVisible(false);
				roundV.setVisible(true);
				currentV = roundV;

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
				//System.out.println("CONTROLLER: next problem = " + next.getProblem());
				if(next != null) {
					roundV.updateRoundView(next);
				}
				else {
					Score s = new Score(topic, correctAnswers);
					endV = new EndView(queue, s);
					System.out.println("CONTROLLER: SubmitAnswerBtn: end of round --> correctAnswers = " + correctAnswers);
					roundV.setVisible(false);
					endV.setVisible(true);
					currentV = endV;
					System.out.println("CONTROLLER: SubmitAnswerBtn pressed!");

					System.out.println("CONTROLLER: SubmitAnswerBtn: problem = " + submitAnswerMessage.getCurrent());
					System.out.println("CONTROLLER: SubmitAnswerBtn: answer = " + answer);
					System.out.println("CONTROLLER: SubmitAnswerBtn: input = " + input);
				}
			}




			//new score --> store
			if (m.getClass() == NewScoreboardEntryMessage.class) {
				NewScoreboardEntryMessage newScoreboardEntryMessage = (NewScoreboardEntryMessage) m;
				Score s = newScoreboardEntryMessage.getScore();
				s.setPlayer(newScoreboardEntryMessage.getName());
				String topic = s.getTopic();

				String filename;
				if(topic.equalsIgnoreCase("alg")) {
					filename = "src/main/AlgScoreboard.csv";
				}
				else {
					filename = "src/main/MultScoreboard.csv";
				}

				try {

					FileWriter writer = new FileWriter(filename);
					String entry = s.toString();
					writer.write(entry);
					writer.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

				currentV.changeTo(homeV, currentV);
				currentV.setVisible(false);
				homeV.setVisible(true);
				currentV = homeV;
				System.out.println("CONTROLLER: NewScoreboardEntry!");
			}

			//View scoreboard button pressed
			if (m.getClass() == ViewScoreboardMessage.class) {
				ViewScoreboardMessage viewScoreboardMessage = (ViewScoreboardMessage) m;

				scoreboardV = new ScoreboardView(queue, algScores, multScores);
				homeV.setVisible(false);
				scoreboardV.setVisible(true);
				currentV = scoreboardV;


				System.out.println("CONTROLLER: ViewScoreboardBtn pressed!");
			}

			//exit to home btn pressed
			if(m.getClass() == ExitToHomeMessage.class) {
				ExitToHomeMessage exitHomeMessage = (ExitToHomeMessage) m;

				scoreboardV.setVisible(false);
				homeV.setVisible(true);
				currentV = homeV;
				System.out.println("CONTROLLER: ExitToHomeBtn pressed!");
			}
		}
	}
}