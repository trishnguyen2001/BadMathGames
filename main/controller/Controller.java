package main.controller;

import main.model.*;
import main.view.*;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
	ScoreboardHelper sbh;
	String topic;
	ArrayList<Score> algScores, multScores;
	private List<Valve> valves = new LinkedList<Valve>();

	public Controller(BlockingQueue<Message> queue, HomeView home, AlgDB adb, MultDB mdb, ArrayList<Score> algScores, ArrayList<Score> multScores) {
		this.queue = queue;
		this.currentV = home;
		this.adb = adb;
		this.mdb = mdb;
		this.algScores = algScores;
		this.multScores = multScores;

		this.homeV = home;
		correctAnswers = 0;
		sbh = new ScoreboardHelper();
		valves.add(new ExitToHomeValve());
		valves.add(new NewScoreboardValve());
		valves.add(new SubmitAnswerValve());
		valves.add(new TopicSelectValve());
		valves.add(new ViewScoreBoardValve());

	}

	/**
	 * Checks for new messages
	 * no messages --> program done
	 */
	 public void mainLoop() {
		 ValveResponse response = ValveResponse.EXECUTED;
		 Message message = null;
		 while (response != ValveResponse.FINISH) {
			 try {
				 message = queue.take(); // <--- take next message from the queue
			 } catch (InterruptedException e) {
				 e.printStackTrace();
			 }
			 // Look for a Valve that can process a message
			 for (Valve valve : valves) {
				 response = valve.execute(message);
				 // if successfully processed or game over, leave the loop
				 if (response != ValveResponse.MISS) {
					 break;
				 }
			 }
		 }
	 }

	 private class ExitToHomeValve implements Valve {
		 @Override
		 public ValveResponse execute(Message message) {
			 if (message.getClass() != ExitToHomeMessage.class) {
				 return ValveResponse.MISS;
			 }
			 ExitToHomeMessage exitHomeMessage = (ExitToHomeMessage) message;

			 scoreboardV.setVisible(false);
			 homeV.setVisible(true);
			 currentV = homeV;
			 System.out.println("CONTROLLER: ExitToHomeBtn pressed!");
			 return ValveResponse.EXECUTED;
		 }
	 }

	 private class NewScoreboardValve implements Valve {
		 @Override
		 public ValveResponse execute(Message message) {
			 if (message.getClass() != NewScoreboardEntryMessage.class) {
				 return ValveResponse.MISS;
			 }
			 
			 
			 
			 NewScoreboardEntryMessage newScoreboardEntryMessage = (NewScoreboardEntryMessage) message;
			 Score s = newScoreboardEntryMessage.getScore();
			 s.setPlayer(newScoreboardEntryMessage.getName());
			 String topic = s.getTopic();

			 //writes new score to appropriate .csv file
			 String filename;
			 if (topic.equalsIgnoreCase("alg")) {
				 filename = "src/main/AlgScoreboard.csv";
				 
				 System.out.println("CONTROLLER: NEWSCOREBOARDENTRY: AlgScoreboard before: " + algScores.toString());
				 
			 } else {
				 filename = "src/main/MultScoreboard.csv";
				 
				 System.out.println("CONTROLLER: NEWSCOREBOARDENTRY: MultScoreboard before: " + multScores.toString());
			 }

			 try {

				 FileWriter writer = new FileWriter(filename,true);
				 String entry = s.toString();
				 writer.append(entry);
				 writer.append('\n');
				 writer.close();
				 
				 
				 
				 if (topic.equalsIgnoreCase("alg")) {
					 algScores = sbh.updateAlgScores();
					 System.out.println("CONTROLLER: NEWSCOREBOARDENTRY: AlgScoreboard after: " + algScores.toString());
					 
				 } else {
					 multScores = sbh.updateMultScores();
					 System.out.println("CONTROLLER: NEWSCOREBOARDENTRY: MultScoreboard after: " + multScores.toString());
				 }

			 } catch (Exception e) {
				 e.printStackTrace();
			 }

			 currentV.changeTo(homeV, currentV);
			 currentV.setVisible(false);
			 homeV.setVisible(true);
			 currentV = homeV;
			 System.out.println("CONTROLLER: NewScoreboardEntry!");
			 return ValveResponse.EXECUTED;
		 }
	 }

	 private class SubmitAnswerValve implements Valve {
		 @Override
		 public ValveResponse execute(Message message) {
			 if (message.getClass() != SubmitAnswerMessage.class) {
				 return ValveResponse.MISS;
			 }
			 SubmitAnswerMessage submitAnswerMessage = (SubmitAnswerMessage) message;
			 double input = submitAnswerMessage.submitAnswer();
			 double answer = submitAnswerMessage.getCurrent().getAnswer();
			 if (input == answer) {
				 correctAnswers++;
			 }
			 Problem next = gameModel.getNext();
			 //System.out.println("CONTROLLER: next problem = " + next.getProblem());
			 if (next != null) {
				 roundV.updateRoundView(next);
			 } else {
				 Score s = new Score(topic, correctAnswers);
				 endV = new EndView(queue, s);
				 //System.out.println("CONTROLLER: SubmitAnswerBtn: end of round --> correctAnswers = " + correctAnswers);
				 roundV.setVisible(false);
				 endV.setVisible(true);
				 currentV = endV;
				 System.out.println("CONTROLLER: SubmitAnswerBtn pressed!");

//				 System.out.println("CONTROLLER: SubmitAnswerBtn: problem = " + submitAnswerMessage.getCurrent());
//				 System.out.println("CONTROLLER: SubmitAnswerBtn: answer = " + answer);
//				 System.out.println("CONTROLLER: SubmitAnswerBtn: input = " + input);
			 }
			 return ValveResponse.EXECUTED;
		 }
	 }

	 private class TopicSelectValve implements Valve {
		 @Override
		 public ValveResponse execute(Message message) {
			 if (message.getClass() != TopicSelectMessage.class) {
				 return ValveResponse.MISS;
			 }
			 TopicSelectMessage startRoundMessage = (TopicSelectMessage) message;
			 topic = startRoundMessage.getTopic();
			 ProblemGenerator pg = null;
			 if (topic.equalsIgnoreCase("alg")) {
				 pg = new ProblemGenerator(adb);
			 } else {
				 pg = new ProblemGenerator(mdb);
			 }
			 gameModel = new Round();
			 gameModel.setProbGen(pg);
			 Problem first = gameModel.getNext();
			 //view = HomeView --> change to RoundView
			 roundV = new RoundView(queue, first);    //creates round view displaying first problem
			 homeV.setVisible(false);
			 roundV.setVisible(true);
			 currentV = roundV;

			 System.out.println("CONTROLLER: topic selected! --> " + topic);
			 return ValveResponse.EXECUTED;
		 }
	 }

	 private class ViewScoreBoardValve implements Valve {
		 @Override
		 public ValveResponse execute(Message message) {
			 if (message.getClass() != ViewScoreboardMessage.class) {
				 return ValveResponse.MISS;
			 }
			 ViewScoreboardMessage viewScoreboardMessage = (ViewScoreboardMessage) message;

			 scoreboardV = new ScoreboardView(queue, algScores, multScores);
			 homeV.setVisible(false);
			 scoreboardV.setVisible(true);
			 currentV = scoreboardV;


			 System.out.println("CONTROLLER: ViewScoreboardBtn pressed!");
			 return ValveResponse.EXECUTED;
		 }
	 }

	 private interface Valve {
		 /**
		  * Performs certain action in response to message
		  */
		 public ValveResponse execute(Message message);
	 }
}