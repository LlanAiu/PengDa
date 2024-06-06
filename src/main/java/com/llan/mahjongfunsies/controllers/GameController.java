package com.llan.mahjongfunsies.controllers;

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

    private static GameController instance;

    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        return instance;
    }

    private GameController(){
        currentGame = new Game();
    }

    public void initialize(){
        currentGame.onStart();
        Board.getInstance().displayState();
        Timeline periodic = new Timeline(new KeyFrame(Duration.millis(DisplayConstants.frameRateMillis), actionEvent -> periodic()));
        periodic.setCycleCount(Timeline.INDEFINITE);
        periodic.play();
    }

    private void periodic(){
        Board.getInstance().periodic();
        if(!currentGame.isFinished()){
            currentGame.run();
        }

        SubjectBase.periodicAll();
    }

    public Game getCurrentGame(){
        return currentGame;
    }

    public int getCurrentTurnIndex(){
        return currentGame.getCurrentTurnIndex();
    }

    public Player getPlayerByOrientation(DisplayUtil.Orientation orientation){
        return currentGame.getPlayerByOrientation(orientation);
    }
}