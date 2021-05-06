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
	public Data search(int id) throws Exception{
		for(Data d: db) {
			if(id == d.id) {
				return d;
			}
		}
		throw new Exception("problem not found");
	}

	@Override
	/**
	 * deletes data from data structure
	 */
	public void deleteData(int id) throws Exception{
		Data result = search(id);
		if(result.id >= 0) {
			db.remove(id);
		}
		throw new Exception("problem not found");
	}

	@Override
	/**
	 * gets problem from data of given id
	 */
	public Problem getProblem(int id) throws Exception {
		Data result = search(id);
		if(result.id >= 0) {
			return result.p;
		}
		throw new Exception("problem not found");
	}

	@Override
	/**
	 * returns size of current database
	 */
	public int size() {
		return db.size();
	}	
}
