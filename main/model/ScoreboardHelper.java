package main.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class ScoreboardHelper {
	ArrayList<Score> algScores, multScores;

	public ScoreboardHelper() {
		algScores = new ArrayList<>();
		multScores = new ArrayList<>();
	}
	
	//retrieves top five scores from algScores
	public ArrayList<Score> getTop5Scores(ArrayList<Score> scoreSet){
		ArrayList<Score> top5 = new ArrayList<>();
		int last = scoreSet.size() - 1;
		for(int i = 0; i < 5; i++) {
			top5.add(scoreSet.get(last));
			last--;
		}
		return top5;
	}
	
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
	
