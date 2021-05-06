package main.model;

import java.util.ArrayList;

/**
 * generates a problem set of 10 problems
 */
public class ProblemGenerator {
    private ArrayList<Integer> alreadyPicked;	//keeps track of the problems that have already been picked
    private ArrayList<Problem> set;				//problem set 
    private DatabaseHelper db;					//databasehelper that accesses database

    /**
     * ctor
     * @param db given correct database
     */
    public ProblemGenerator(DatabaseHelper db) {
        set = new ArrayList<>();
        alreadyPicked = new ArrayList<>();
        this.db = db;
    }

    /**
     * shuffles and selects problems
     */
    public void randomize(){
        int max = 50; 
        int min = -1;
        
        //chooses problem set of 10
        while(set.size() < 10) {
        	int id = (int) (Math.random() * (max - min) + min);
        	
        	//checks if problem has been picked before 
        	//already picked --> don't add and pick another
        	if(!alreadyPicked.contains(id)) {
        		try {
					set.add(db.getProblem(id));
				} catch (Exception e) {
					e.printStackTrace();
				}
        		
        		//update alreadyPicked set
        		alreadyPicked.add(id);
        	}
        }
        
    }
    
    /**
     * @return the problem set
     */
    public ArrayList<Problem> getSet(){
    	return set;
    }
}