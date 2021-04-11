package d1;
import java.util.ArrayList;

public interface DatabaseHelper {
	void addData(Problem p);
	void deleteData(int id) throws Exception;
	String getProblem(int id) throws Exception;
	double getAnswer(int id) throws Exception;
	Data search(int id);
}
