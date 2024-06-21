package com.llan.mahjongfunsies.controllers;

import com.llan.mahjongfunsies.ai.Trainer;
import com.llan.mahjongfunsies.mahjong.Game;
import com.llan.mahjongfunsies.mahjong.players.Player;
import com.llan.mahjongfunsies.ui.Board;
import com.llan.mahjongfunsies.ui.DisplayConstants;
import com.llan.mahjongfunsies.util.DisplayUtil;
import com.llan.mahjongfunsies.util.SubjectBase;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameController {

    private Game currentGame;
    private boolean ended;
    private boolean training;
    private static GameController instance;

    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        return instance;
    }

    private GameController(){
//        currentGame = new Game();
    }

    public void initialize(){
        training = false;
        currentGame = new Game();
        currentGame.onStart();
        ended = false;
        Board.getInstance().displayState();
        Timeline periodic = new Timeline(new KeyFrame(Duration.millis(DisplayConstants.FRAME_RATE_MILLIS), actionEvent -> periodic()));
        periodic.setCycleCount(Timeline.INDEFINITE);
        periodic.play();
        Timeline observables = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> SubjectBase.periodicAll()));
        observables.setCycleCount(Timeline.INDEFINITE);
        observables.play();
    }

    public void initializeTraining(){
        training = true;
        currentGame = new Game();
        ended = false;
        currentGame.onStart();
        while(!ended){
            periodic();
        }
    }

    private void periodic(){
        if(!currentGame.isFinished()){
            currentGame.run();
            if(training){
                //do something
            }
        } else {
            if(!ended){
                currentGame.end();
                ended = true;
            }
        }
    }

    public boolean isTraining(){
        return training;
    }

    public Game getCurrentGame(){
        return currentGame;
    }

    public String getCurrentGameState(){
        return currentGame.getStatus();
    }

    public Player getPlayerByOrientation(DisplayUtil.Orientation orientation){
        return currentGame.getPlayerByOrientation(orientation);
    }
}