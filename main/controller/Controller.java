package main.controller;

import main.model.*;
import main.view.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * Controller class that connect model and view classes
 * @author Trish Nguyen, Dan Nguyen, Alan Nguyen
 *
 */
public class Controller {
	private Round gameModel;
	private EndView endV;
	private HomeView homeV;
	private ScoreboardView scoreboardV;
	private RoundView roundV;
	private AlgDB adb;
	private MultDB mdb;
	private ScoreboardHelper sbh;
	private String topic;
	private int correctAnswers;
	private ArrayList<Score> algScores, multScores;
	private List<Valve> valves = new LinkedList<Valve>();
	private BlockingQueue<Message> queue;

	/**
	 * controller ctor
	 * @param queue message queue that holds messages 
	 * @param home home view of game
	 * @param adb algebra database
	 * @param mdb multiplication database
	 * @param algScores algebra scoreboard database
	 * @param multScores multiplication scoreboard database
	 */
	public Controller(BlockingQueue<Message> queue, HomeView home, AlgDB adb, MultDB mdb, ArrayList<Score> algScores, ArrayList<Score> multScores) {
		//initialize instance vars
		this.queue = queue;
		this.adb = adb;
		this.mdb = mdb;
		this.algScores = algScores;
		this.multScores = multScores;
		this.homeV = home;
		this.sbh = new ScoreboardHelper();
		
		//initialize valves and add to valve list
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
				message = queue.take(); 		// <--- take next message from the queue
			} 
			catch (InterruptedException e) {
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
	

	/**
	 * topic selected on home view 	--> initializes round using problems from correct database
	 * 								--> 
	 *
	 */
	private class TopicSelectValve implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if (message.getClass() != TopicSelectMessage.class) {
				return ValveResponse.MISS;
			}
			
			correctAnswers = 0;				//initialized; keeps track of amount of correct answers for this round
			
			TopicSelectMessage startRoundMessage = (TopicSelectMessage) message;
			topic = startRoundMessage.getTopic();		//initializes topic for the round
			
			//initializes problem generator w/ correct problem database
			ProblemGenerator pg = null;
			if (topic.equalsIgnoreCase("alg")) {
				pg = new ProblemGenerator(adb);
			} else {
				pg = new ProblemGenerator(mdb);
			}
			
			//creates new round w/ correct problem database
			gameModel = new Round();
			gameModel.setProbGen(pg);
			Problem first = gameModel.getNext();		//initializes first problem to create roundV with
			
			//changes from home to round view
			roundV = new RoundView(queue, first);    //creates round view displaying first problem
			homeV.close();
			
			///TEST//////////////////////////////////////////////////////
			System.out.println("CONTROLLER: topic selected! --> " + topic);
			///TEST//////////////////////////////////////////////////////
			
			return ValveResponse.EXECUTED;
		}
	}

	/**
	 * view scoreboard btn pressed --> changes view to scoreboardView
	 *
	 */
	private class ViewScoreBoardValve implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if (message.getClass() != ViewScoreboardMessage.class) {
				return ValveResponse.MISS;
			}
			
			scoreboardV = new ScoreboardView(queue, algScores, multScores);
			homeV.close();


			System.out.println("CONTROLLER: ViewScoreboardBtn pressed!");
			return ValveResponse.EXECUTED;
		}
	}
	
	/**
	 * new answer submitted	--> checks answer 
	 * 						--> retrieves next problem + updates view w/ new problem
	 * 						--> once 10 problems reached: switch to endview
	 *
	 */
	private class SubmitAnswerValve implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if (message.getClass() != SubmitAnswerMessage.class) {
				return ValveResponse.MISS;
			}
			
			SubmitAnswerMessage submitAnswerMessage = (SubmitAnswerMessage) message;
			
			//retrieves answer and user's input from message
			double input = submitAnswerMessage.submitAnswer();
			double answer = submitAnswerMessage.getProblem().getAnswer();
			
		/////TEST//////////////////////////////////////////////////////
			System.out.println("CONTROLLER: Current Problem: " + submitAnswerMessage.getProblem());
			System.out.println("CONTROLLER: Correct Answer: " + answer);
			System.out.println("CONTROLLER: Inputted Answer: " + input);
			System.out.println("CONTROLLER: input == correct ? --> " + (input == answer));
		/////TEST//////////////////////////////////////////////////////
			
			//checks answer against user's input
			if (input == answer) {
				correctAnswers++;
			}
			
			//gets next problem
			Problem next = gameModel.getNext();
			if (next != null) {
				roundV.updateRoundView(next);
			} 
			else {
				//creates new score for this round
				Score s = new Score(topic, correctAnswers);	
				
				//changes view to end view for player to submit name
				endV = new EndView(queue, s);
				roundV.close();
				
				correctAnswers = 0;		//resets correctAnswers counter					
			}
			
		/////TEST//////////////////////////////////////////////////////
			System.out.println("CONTROLLER: SubmitAnswerBtn pressed!");
		/////TEST//////////////////////////////////////////////////////
			return ValveResponse.EXECUTED;
		}
	}
	
	/**
	 * exit to home message --> return to home
	 *
	 */
	private class ExitToHomeValve implements Valve {
		@Override
		public ValveResponse execute(Message message) {
			if (message.getClass() != ExitToHomeMessage.class) {
				return ValveResponse.MISS;
			}

			//switches views
			homeV = new HomeView(queue);
			scoreboardV.close();
			
			/////////TEST//////////////////////////////////////////////////////
			System.out.println("CONTROLLER: ExitToHomeBtn pressed!");
			////////TEST//////////////////////////////////////////////////////
			
			return ValveResponse.EXECUTED;
		}
	}

	/**
	 * name submitted at end screen --> updates model + database
	 * 								--> returns to home after
	 *
	 */
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
			if (topic.equalsIgnoreCase("alg")) {
				sbh.writeToAlgFile(s, s.getPlayer());
				algScores = sbh.updateAlgScores();
				
				///////TEST//////////////////////////////////////////////////////
				System.out.println("CONTROLLER: NEWSCOREBOARDENTRY: AlgScoreboard before: " + algScores.toString());
				///////TEST//////////////////////////////////////////////////////
				
			} else {
				sbh.writeToMultFile(s, s.getPlayer());
				multScores = sbh.updateMultScores();
				
				///////TEST//////////////////////////////////////////////////////
				System.out.println("CONTROLLER: NEWSCOREBOARDENTRY: MultScoreboard before: " + multScores.toString());
				///////TEST//////////////////////////////////////////////////////
			}
			
			//updates views
			homeV = new HomeView(queue);
			endV.close();
			
			///////TEST//////////////////////////////////////////////////////
			System.out.println("CONTROLLER: NewScoreboardEntry!");
			///////TEST//////////////////////////////////////////////////////
			
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