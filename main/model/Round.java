package main.model;

import main.controller.UserIO;
import main.view.RoundView;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Round implements GameModel{
    private String topic;
    private RoundView v;
    private Timer t;
    private ArrayList<Problem> problems;
    private DatabaseHelper db;
    
    public Round(String topic) {
        this.topic = topic;
        v = new RoundView();
        
        //determines topic
        if(topic.equals("alg")) {
        	db = new AlgDB();
        }
        else if(topic.equals("mult")) {
        	db = new MultDB();
        }
        
        ProblemGenerator pg = new ProblemGenerator(db);
        pg.randomize();
        problems = pg.getSet();
        
        ActionListener listener = event -> v.dispose();
        t = new Timer(60000, listener);
       
    }
    
    public void start() {
    	t.start();
    	int problemCount = 0;
    	//display first problem
    	Problem current = problems.get(problemCount);
    	v.displayProblem(current);
    	problemCount++;
    	
    	while(t.getDelay() < 60000) {
    		UserIO io = new UserIO(field, current, db);
    		boolean isCorrect = io.check();
    		
    		if(isCorrect) {
    			current = problems.get(problemCount);
        		v.displayProblem(current);
        		problemCount++;
    		}
    	}
    }
}
