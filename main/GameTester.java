package main;

import main.model.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTester {


    @Test
    public void testProblemGenerator() {
        AlgDB adb = new AlgDB();
        File alg1 = new File("src/main/Alg1.csv");
        Scanner in;
        try {
            in = new Scanner(alg1);
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

    }
}
