package d1;

import java.util.Scanner;
import javax.swing.*;

public class UserIO {
	private Problem current;
	private Score s;
	private double given;
	private JTextField field;
	private DatabaseHelper db;
	private int correct;
	
	public UserIO(JTextField field, Problem current, Score s, DatabaseHelper db) {
		this.current = current;
		this.s = s;
		this.field = field;
		this.db = db;
		
		String input = field.getText();
		given = Double.parseDouble(input);
		correct = 0;
	}
	
	private void check() {
		double answer = current.answer;
		if(answer == given) {
			correct++;
		}
	}
	
	public int numCorrect() {
		return correct;
	}
	
	
}
