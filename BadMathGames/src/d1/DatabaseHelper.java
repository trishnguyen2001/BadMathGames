package d1;
import java.util.ArrayList;

public interface DatabaseHelper {
	void addData(Problem p);
	void deleteData(int id) throws Exception;
	Problem getProblem(int id) throws Exception;
	Data search(int id) throws Exception;
}
