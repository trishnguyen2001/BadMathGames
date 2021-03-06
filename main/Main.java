package main;

import java.util.*;
import java.util.concurrent.*;
import main.controller.Controller;
import main.controller.Message;
import main.model.AlgDB;
import main.model.MultDB;
import main.model.Problem;
import main.model.Score;
import main.model.ScoreboardHelper;
import main.view.HomeView;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Main application that runs application
 * @author Trish Nguyen, Dan Nguyen, Alan Nguyen
 *
 */
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
		
		
		//initialize MVC
		BlockingQueue<Message> q = new LinkedBlockingQueue<>();
		ScoreboardHelper sbh = new ScoreboardHelper();
		ArrayList<Score> algScores = sbh.updateAlgScores();
		ArrayList<Score> multScores = sbh.updateMultScores();
		HomeView startV = new HomeView(q);
		Controller c = new Controller(q, startV, adb, mdb, algScores, multScores);
		c.mainLoop();
		
		
	}
}
