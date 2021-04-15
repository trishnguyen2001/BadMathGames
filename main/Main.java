package main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import main.model.AlgDB;
import main.model.MultDB;
import main.model.Problem;
import main.view.HomeView;
import main.view.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {


	public static void main(String args[]) {
		//read problems into databases
		AlgDB adb = new AlgDB();
		File alg1 = new File("C:\\Users\\Trish Nguyen\\Desktop\\CS151\\PROJECT\\BadMathGames\\BadMathGames\\src\\main\\Alg1.csv");
		Scanner in;
		try {
			in = new Scanner(alg1);
			in.nextLine(); 		//gets rid of first line
			while(in.hasNextLine()) {
				String current = in.nextLine();
				System.out.println("current = " + current);
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
		File mult = new File("C:\\Users\\Trish Nguyen\\Desktop\\CS151\\PROJECT\\BadMathGames\\BadMathGames\\src\\main\\MultTables.csv");
		try {
			in = new Scanner(mult);
			in.nextLine(); 		//gets rid of first line
			while(in.hasNextLine()) {
				String current = in.nextLine();
				System.out.println("current = " + current);
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
		//////////////////////////////////////PROBLEM PARSE TESTER//////////////////////////////////////////
		System.out.println("-------------------AlgDB Test Starting------------------------");
		int counter = 0;
		while(counter < adb.size()) {
			try {
				System.out.println(adb.getProblem(counter).toString());
				
				counter++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("-------------------MultDB Test Starting------------------------");
		counter = 0;
		while(counter < mdb.size()) {
			try {
				System.out.println(mdb.getProblem(counter).toString());
				
				counter++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////


		//set up message q
		BlockingQueue<Message> q = new LinkedBlockingQueue<>();

		//HomeView start = new HomeView(q);

	}
}
