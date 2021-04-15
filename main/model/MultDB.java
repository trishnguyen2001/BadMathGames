package main.model;

import java.util.ArrayList;

public class MultDB implements DatabaseHelper{

private int counter;
	
	private ArrayList<Data> db;
	
	public MultDB() {
		counter = 0;
		db = new ArrayList<>();
	}

	@Override
	public void addData(Problem p) {
		Data add = new Data(counter, p);
		db.add(add);
		counter++;
	}

	public Data search(int id) throws Exception{
		for(Data d: db) {
			if(id == d.id) {
				return d;
			}
		}
		throw new Exception("problem not found");
	}

	@Override
	public void deleteData(int id) throws Exception{
		Data result = search(id);
		if(result.id > 0) {
			db.remove(id);
		}
		throw new Exception("problem not found");
	}

	@Override
	public Problem getProblem(int id) throws Exception {
		Data result = search(id);
		if(result.id > 0) {
			return result.p;
		}
		throw new Exception("problem not found");
	}

}
