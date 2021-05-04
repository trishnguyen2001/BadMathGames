package main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import main.controller.Controller;
import main.controller.Message;
import main.model.AlgDB;
import main.model.MultDB;
import main.model.Problem;
import main.model.ProblemGenerator;
import main.model.Round;
import main.model.Score;
import main.view.HomeView;
import main.view.RoundView;
import main.view.ScoreboardView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
	public static void main(String args[]) {

		//read problems into databases
		AlgDB adb = new AlgDB();
		File alg1 = new File("src/main/Alg1.csv");
		Scanner in;
		try {
			in = new Scanner(alg1);
			in.nextLine(); 		//gets rid of first line
			while(in.hasNextLine()) {
				String current = in.nextLine();
				String[] items = current.split(",");
				String prob = items[0];
				String stringAns = items[1];
				double ans = Double.parseDouble(stringAns);
				Problem p = new Problem(prob, ans);
				adb.addData(p);
			}
			in.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MultDB mdb = new MultDB();
		File mult = new File("src/main/MultTables.csv");
		try {
			in = new Scanner(mult);
			in.nextLine(); 		//gets rid of first line
			while(in.hasNextLine()) {
				String current = in.nextLine();
				String[] items = current.split(",");
				String prob = items[0];
				String stringAns = items[1];
				double ans = Double.parseDouble(stringAns);
				Problem p = new Problem(prob, ans);
				mdb.addData(p);
			} 
			in.close();
		}catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//read scores into arraylists
		ArrayList<Score> algScores = new ArrayList<>();
		File algScoreFile = new File("src/main/AlgScoreboard.csv");
		try {
			in = new Scanner(algScoreFile);
			in.nextLine(); 		//gets rid of first line
			while(in.hasNextLine()) {
				String current = in.nextLine();
				String[] items = current.split(",");
				String player = items[0];
				String correctString = items[1];
				int correct = Integer.parseInt(correctString);
				Score s = new Score("alg", correct);
				s.setPlayer(player);
				algScores.add(s);
			} 
			in.close();
		}catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ArrayList<Score> multScores = new ArrayList<>();
		File multScoreFile = new File("src/main/MultScoreboard.csv");
		try {
			in = new Scanner(multScoreFile);
			in.nextLine(); 		//gets rid of first line
			while(in.hasNextLine()) {
				String current = in.nextLine();
				String[] items = current.split(",");
				String player = items[0];
				String correctString = items[1];
				int correct = Integer.parseInt(correctString);
				Score s = new Score("alg", correct);
				s.setPlayer(player);
				algScores.add(s);
			} 
			in.close();
		}catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//initialize MVC
		BlockingQueue<Message> q = new LinkedBlockingQueue<>();
		Round r = new Round();
		HomeView startV = new HomeView(q);
		Controller c = new Controller(q, startV, adb, mdb, algScores, multScores);
		c.mainLoop();
		
		
	}
}
