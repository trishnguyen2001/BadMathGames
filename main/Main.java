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

		//initialize MVC
		//set up message q
		BlockingQueue<Message> q = new LinkedBlockingQueue<>();
		Round r = new Round();
		HomeView startV = new HomeView(q);
		Controller c = new Controller(q, startV, adb, mdb);
		c.mainLoop();
		
		///////////////////////////////////////////TESTER////////////////////////////////////////////////
		//////////////////////////////////////PROBLEM PARSE TESTER//////////////////////////////////////////
		//		System.out.println("-------------------AlgDB Test Starting------------------------");
		//		int counter = 0;
		//		while(counter < adb.size()) {
		//			try {
		//				System.out.println(adb.getProblem(counter).toString());
		//
		//				counter++;
		//			} catch (Exception e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//		}
		//		System.out.println("-------------------MultDB Test Starting------------------------");
		//		counter = 0;
		//		while(counter < mdb.size()) {
		//			try {
		//				System.out.println(mdb.getProblem(counter).toString());
		//
		//				counter++;
		//			} catch (Exception e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//		}
		////////////////////////////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////MODEL TESTER//////////////////////////////////////////
		//		ProblemGenerator pg = new ProblemGenerator(adb);
		//		pg.randomize();
		//		ArrayList<Problem> set = pg.getSet();
		//		System.out.println("//////////////////Test PG: alg/////////////////////////////");
		//		for(Problem p: set) {
		//			System.out.println(p.toString());
		//		}

		//		Problem p = new Problem("1 + 1 = x", 2.0);
		//		double testInputCorrect = 2.0;
		//		double testInputWrong = 14.0;
		//		if(p.getAnswer() == testInputCorrect) {
		//			System.out.println("correct");
		//		}
		//		else {
		//			System.out.println("model wrong");
		//		}
		//		if(p.getAnswer() != testInputWrong) {
		//			System.out.println("wrong");
		//		}
		//		else {
		//			System.out.println("model wrong");
		//		}
		//		



		//		ProblemGenerator pg1 = new ProblemGenerator(mdb);
		//		pg1.randomize();
		//		ArrayList<Problem> set1 = pg1.getSet();
		//		System.out.println("/////////////////////////Test PG: mult//////////////////////");
		//		for(Problem p: set1) {
		//			System.out.println(p.toString());
		//		}



		////////////////////////////////////////////////////////////////////////////////////////////////////




	}
}
