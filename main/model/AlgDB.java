package main.model;

import java.util.ArrayList;

/*
 * data structure for algebra problems in problem database (model)
 */
public class AlgDB implements DatabaseHelper{
	private int counter;			//counter for index of database --> id for next added data
	private ArrayList<Data> db;		//holds all problems
	
	/**
	 * ctor for alg database
	 */
	public AlgDB() {
		//initializes instance variables
		counter = 0;				 
		db = new ArrayList<>();
	}



	@Override
	/**
	 * adds data into data structure
	 */
	public void addData(Problem p) {
		Data add = new Data(counter, p);
		db.add(add);
		counter++;
	}

	/**
	 * searches database w/ given id
	 */
	public Data search(int id){
		for(Data d: db) {
			if(id == d.getId()) {
				return d;
			}
		}
		return null;
	}

	@Override
	/**
	 * deletes data from data structure
	 */
	public void deleteData(int id){
		Data result = search(id);
		if(result.id >= 0) {
			db.remove(id);
		}
	}

	@Override
	/**
	 * gets problem from data of given id
	 */
	public Problem getProblem(int id){
		Data result = search(id);
		if(result.id >= 0) {
			return result.p;
		}
		return null;
	}

	@Override
	/**
	 * returns size of current database
	 */
	public int size() {
		return db.size();
	}
}
