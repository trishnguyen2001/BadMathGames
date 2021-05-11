package main.model;

/**
 * Object that takes problem and keeps track of its place in a database
 * @author Trish Nguyen, Dan Nguyen, Alan Nguyen
 *
 */
public class Data {
	int id;
	Problem p; 

	/*
	 * ctor
	 * @param id object's place in database
	 * @param p the problem the object holds
	 */
	public Data(int id, Problem p) {
		this.p = p;
		this.id = id;
	}

	/**
	 * @return object's place in database
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return object's problem
	 */
	public Problem getProblem() {
		return p;
	}

	/**
	 * @return true if this and that have the same instance vars
	 */
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Data that = (Data) o;
		return this.getId() == that.getId() && this.getProblem().equals(that.getProblem());
	}
}

