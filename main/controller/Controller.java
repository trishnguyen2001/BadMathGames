package main.controller;

import main.model.Problem;
import main.model.Round;
import main.view.*;

import java.util.concurrent.BlockingQueue;

public class Controller {
    BlockingQueue<Message> queue;
    Round gameModel;
    View current;
    EndView end;
    HomeView home;
    ScoreboardView scoreboard;
    RoundView round;

    public Controller(BlockingQueue<Message> queue, Round gameModel, EndView end, HomeView home, RoundView round, ScoreboardView scoreboard) {
        this.queue = queue;
        this.gameModel = gameModel;
        //current = home;
        this.home = home;
        this.end = end;
        this.scoreboard = scoreboard;
        this.round = round;
    }

    /**
     * Checks for new messages
     * no messages --> program done
     */
    public void mainLoop() {
        //FIX LATER. Fixed, needed to import View from view folder
        while (current.isDisplayable()) {
            Message m = null;

            try {
                m = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //topic is selected --> feed to round params
            //start round
            if (m.getClass() == TopicSelectMessage.class) {
                TopicSelectMessage startRound = (TopicSelectMessage) m;
                String topic = startRound.getTopic();
                gameModel = new Round(topic);
                //view = HomeView --> change to RoundView
                home.change(new RoundView(queue, gameModel));
                this.round = new RoundView(queue, gameModel);
                // maybe more dispose/ set visible?
                // currently requires the user to type in (somewhere) alg or mult
            }

            //answer submitted --> check answer
            if (m.getClass() == SubmitAnswerMessage.class) {
                SubmitAnswerMessage attempt = (SubmitAnswerMessage) m;
                double answer = attempt.submitAnswer();
                if (answer == Problem.getAnswer()) {
                    // calls method that moves onto next question
                }

            }

            //qualifying score --> write to .csv file
            if (m.getClass() == NewScoreboardEntryMessage.class) {
                NewScoreboardEntryMessage scoreboardName = (NewScoreboardEntryMessage) m;
                // however writing works LOL

            }

            //View scoreboard button pressed
            if (m.getClass() == ViewScoreboardMessage.class) {
                //currentView.method <- method in homeview(?) that 1.)
                // disposes whatever the current view is + 2.) ScoreboardView.setVisible()

            }
        }
    }
}
