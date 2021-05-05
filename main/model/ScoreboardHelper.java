package main.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreboardHelper {
	ArrayList<Score> algScores, multScores;

	public ScoreboardHelper() {
		algScores = new ArrayList<>();
		multScores = new ArrayList<>();
	}


	public ArrayList<Score> updateAlgScores(){
		//read scores into arraylists
		File algScoreFile = new File("src/main/AlgScoreboard.csv");
		try {
			Scanner in = new Scanner(algScoreFile);
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


			System.out.println("LOADED ALG SCOREBOARD: " + algScores.toString());
			
			
			
		}catch (FileNotFoundException e1) {
			System.out.println("SCOREBOARD: UNABLE TO UPDATE ALG SCORES");
		}
		return algScores;

	}


	public ArrayList<Score> updateMultScores(){
		multScores = new ArrayList<>();
		File multScoreFile = new File("src/main/MultScoreboard.csv");
		try {
			Scanner in = new Scanner(multScoreFile);
			while(in.hasNextLine()) {
				String current = in.nextLine();
				String[] items = current.split(",");
				String player = items[0];
				String correctString = items[1];
				int correct = Integer.parseInt(correctString);
				Score s = new Score("alg", correct);
				s.setPlayer(player);
				multScores.add(s);
			} 
			in.close();


			System.out.println("LOADED MULT SCOREBOARD: " + multScores.toString());
			
			
			
			
		}catch (FileNotFoundException e1) {
			System.out.println("SCOREBOARD: UNABLE TO UPDATE MULT SCORES");
		}

		return multScores;
	}
}
	
