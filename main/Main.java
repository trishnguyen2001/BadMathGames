package main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import main.view.HomeView;
import main.view.Message;

public class Main {
	
	public static void main(String args[]) {
		BlockingQueue<Message> q = new LinkedBlockingQueue<>();
		
		HomeView start = new HomeView(q);
		
	}
}
