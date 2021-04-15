package main.controller;

import javax.swing.*;

import main.model.Data;
import main.model.DatabaseHelper;
import main.model.Problem;
import main.model.Score;

public class UserIO {
	private Problem current;
	private double given;
	private JTextField field;
	private DatabaseHelper db;
	
	public UserIO(JTextField field, Problem current, DatabaseHelper db) {
		this.current = current;
		this.field = field;
		this.db = db;
		
		String input = field.getText();
		given = Double.parseDouble(input);
	}
	
	public boolean check()  {
		double answer = current.getAnswer();
		if(answer == given) {
			return true;
		}
		return false;
	}
	
	
}
