package main.model;

import java.util.ArrayList;

public class ProblemGenerator implements GameModel {
    private ArrayList<Integer> alreadyPicked;
    private ArrayList<Problem> set;
    private DatabaseHelper db;

    public ProblemGenerator(DatabaseHelper db) {
        set = new ArrayList<>();
        alreadyPicked = new ArrayList<>();
        this.db = db;
    }

    public void randomize(){
        int max = 50; //50 problems in set
        int min = -1;
        
        for(int i= 0; i <= 20; i++) {
        	int id = (int) (Math.random() * (max - min) + min);
        	if(!alreadyPicked.contains(id)) {
        		try {
					set.add(db.getProblem(id));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		alreadyPicked.add(id);
        	}
        }
        
    }
    
    public ArrayList<Problem> getSet(){
    	return set;
    }
}