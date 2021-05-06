package main.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ScoreboardHelper {
	ArrayList<Score> algScores, multScores;		//data structure that keeps data from .csv files

	public ScoreboardHelper() {
		algScores = new ArrayList<>();		//holds algebra scoreboard
		multScores = new ArrayList<>();		//holds multiplication table scoreboard
	}
	
	/**
	 * 
	 * @param scoreSet
	 * @return top 5 scores from given score set
	 */
	public ArrayList<Score> getTop5Scores(ArrayList<Score> scoreSet){
		ArrayList<Score> top5 = new ArrayList<>();
		
		//don't run if scoreSet empty
		if(scoreSet.size() > 0) {
			int last = scoreSet.size() - 1;
			for(int i = 0; i < 5; i++) {
				//if scoreSet doesn't have 5 entries --> only add entries up till last possible entry
				if(i >= scoreSet.size()) {
					break;
				}
				
				top5.add(scoreSet.get(last));
				last--;
				
			}
		}
		
		return top5;
	}

	/**
	 * updates AlgScoreboard.csv file
	 * @param s the given score
	 * @param name player's name associated with the score
	 */
	public void writeToAlgFile(Score s, String name) {
		String filename = "src/main/AlgScoreboard.csv";
		try {

			FileWriter writer = new FileWriter(filename,true);
			String entry = s.toString();
			writer.append(entry);
			writer.append('\n');
			writer.close();

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * updates MultScoreboard.csv file
	 * @param s the given score
	 * @param name player's name associated with the score
	 */
	public void writeToMultFile(Score s, String name) {
		String filename = "src/main/MultScoreboard.csv";
		try {

			FileWriter writer = new FileWriter(filename,true);
			String entry = s.toString();
			writer.append(entry);
			writer.append('\n');
			writer.close();

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * updates data structure with new data from .csv file
	 * @return updated data structure
	 */
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
		Collections.sort(algScores);
		return algScores;

	}


	/**
	 * updates data structure with new data from .csv file
	 * @return updated data structure
	 */
	public ArrayList<Score> updateMultScores(){
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
		Collections.sort(multScores);
		return multScores;
	}
}

