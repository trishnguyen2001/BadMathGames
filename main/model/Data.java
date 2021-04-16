package main.model;

public class Data {
	int id;
	Problem p; 
	
	public Data(int id, Problem p) {
		this.p = p;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public Problem getProblem() {
		return p;
	}
}
