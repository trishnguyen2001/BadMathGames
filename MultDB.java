package d1;

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
		String prob = p.getProblem();
		double ans = p.getAnswer();
		Data add = new Data(counter, prob, ans);
		db.add(add);
		counter++;
	}

	public Data search(int id) {
		for(Data d: db) {
			if(id == d.id) {
				return d;
			}
		}
		return new Data(-1, "wrong", -1);
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
	public String getProblem(int id) throws Exception {
		Data result = search(id);
		if(result.id > 0) {
			return result.problem;
		}
		throw new Exception("problem not found");
	}

	@Override
	public double getAnswer(int id) throws Exception {
		Data result = search(id);
		if(result.id > 0) {
			return result.answer;
		}
		throw new Exception("problem not found");
	}

}
