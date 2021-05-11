package main.model;

public interface DatabaseHelper {
	void addData(Problem p);
	void deleteData(int id);
	Problem getProblem(int id);
	Data search(int id);
	int size();
}
