package com.llan.mahjongfunsies.controllers;

import com.llan.mahjongfunsies.mahjong.Gameflow;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.environment.Move;
import com.llan.mahjongfunsies.ui.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameController {

    private Move lastInputMove;

    private static GameController instance;

    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        return instance;
    }

    public void initialize(){
        Gameflow.initialize();
        Gameflow.reset();
        Board.getInstance().displayState(Gameflow.getState());
        lastInputMove = Move.none();
        Timeline periodic = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> periodic()));
        periodic.setCycleCount(Timeline.INDEFINITE);
        periodic.play();
    }

    private void periodic(){
        Board.getInstance().periodic();
    }

    public void handleInput(GameAction action, Card card, int index){
        lastInputMove = new Move(action, card, index);
    }

    public void clearRecordedInputs(){
        lastInputMove = Move.none();
    }

    public Move getLastInputMove(){
        return lastInputMove;
    }
}
