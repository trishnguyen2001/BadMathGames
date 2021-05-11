package main;

import main.model.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class GameTester {
	private AlgDB adb;
	
	/**
	 * Initializes databases that will be used in tester methods
	 */
	public GameTester() {
		adb = new AlgDB();
        File algFile = new File("src/main/Alg1.csv");
        Scanner in;
        try {
            in = new Scanner(algFile);
            in.nextLine();        //gets rid of first line
            while (in.hasNextLine()) {
                String current = in.nextLine();
                String[] items = current.split(",");
                String prob = items[0];
                String stringAns = items[1];
                double ans = Double.parseDouble(stringAns);
                Problem p = new Problem(prob, ans);
                adb.addData(p);
            }
            in.close();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
	}
	
    @Test
    public void testProblemGenerator() {
        ProblemGenerator firstSet = new ProblemGenerator(adb);
        ProblemGenerator secondSet = new ProblemGenerator(adb);
        firstSet.randomize();
        secondSet.randomize();
        assertTrue(firstSet != secondSet, "Sets should not be the same");
        assertTrue(firstSet.getSet().size() == 10, "Set returned should be size 10");
    }
    
    @Test
    public void testScore() {
        Score oldScore = new Score("alg", 5);
        oldScore.setPlayer("Tester");
        assertEquals(oldScore.getCorrect(), 5, "Score should be 5");
        assertEquals(oldScore.getTopic(), "alg", "Topic should be alg");
        assertEquals(oldScore.getPlayer(), "Tester", "Player should be Tester");
        Score betterScore = new Score("alg", 10);
        betterScore.setPlayer("Better");
        assertTrue(betterScore.compareTo(oldScore) == 5, "Score difference should be 5");
        Score oldScoreCopy = new Score("alg", 5);
        oldScoreCopy.setPlayer("Tester");
        assertTrue(oldScore.equals(oldScoreCopy), "Scores should be equal");
    }

    @Test
    public void testProblem() {
        Problem prob = new Problem("0 * 0", 0);
        Problem prob2 = new Problem("1 * 1", 1);
        assertNotEquals(prob, prob2, "Problems should not be equal");
        assertTrue(prob.getProblem().equalsIgnoreCase("0 * 0"), "Problem should be 0 * 0");
        assertEquals(prob.getAnswer(), 0, "Answer should be zero");
    }
    
    @Test
    public void testMultDB() {
    	MultDB mdb = new MultDB();
    	mdb.addData(new Problem("2x = 4", 2.0));
    	mdb.addData(new Problem("3x = 9", 3.0));
    	mdb.addData(new Problem("5x = 5", 1.0));
    	mdb.addData(new Problem("2x = 18", 9.0));
    	mdb.addData(new Problem("2x = 44", 22.0));
    	mdb.addData(new Problem("2x = 40", 20.0));
    	
    	assertEquals(mdb.size(), 6, "mdb should have max index = 5");
    	try {
			assertEquals(mdb.search(0), new Data(0, new Problem("2x = 4", 2.0)), "Data returned should be equal");
			assertEquals(mdb.getProblem(1), new Problem("3x = 9", 3.0), "Problem returned should be equal");
			mdb.deleteData(5);
			assertEquals(mdb.size(), 5, "mdb should have max index = 4");
    	} catch (Exception e) {
			assertEquals(1, 0, "testMuldDB: test failed");
		}
    }
    
    @Test
    public void testData() {
    	Problem testP1 = new Problem("2x = 4", 2.0);
    	Data testD1 = new Data(0, testP1);
    	assertTrue(testD1.getProblem().equals(testP1), "Should be equal");
    	assertEquals(testD1.getId(), 0, "Id should be 0");
    }
}