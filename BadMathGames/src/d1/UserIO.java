package d1;

import java.util.Scanner;
import javax.swing.*;

public class UserIO {
	private Problem current;
	private Score s;
	private double given;
	private JTextField field;
	private DatabaseHelper db;
	
	public UserIO(JTextField field, Problem current, Score s, DatabaseHelper db) {
		this.current = current;
		this.s = s;
		this.field = field;
		this.db = db;
		
		String input = field.getText();
		given = Double.parseDouble(input);
	}
	
	private boolean check() {
		double answer = db.getAnswer(current.id);
		if(answer == given) {
			s.update(); 	//calculates new score based off correct answer
			return true;
		}
		return false;
	}
	
	
}
