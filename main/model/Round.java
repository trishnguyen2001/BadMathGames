package main.model;

import java.util.ArrayList;

public class Round {
    private String topic;
    private ArrayList<Problem> problems;
    private DatabaseHelper db;
    
    public Round(String topic) {
        this.topic = topic;
        
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
        
    }
    
    public ArrayList<Problem> getProblems() {
        return problems;
    }
}
