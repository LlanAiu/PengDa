package com.llan.mahjongfunsies.controllers;

import com.llan.mahjongfunsies.mahjong.Gameflow;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Deck;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.environment.Move;
import com.llan.mahjongfunsies.ui.Board;
import com.llan.mahjongfunsies.ui.DisplayConstants;
import com.llan.mahjongfunsies.util.SubjectBase;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameController {

    private Move lastInputMove;
    private int lastCardIndex = -1;

    private static GameController instance;

    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        return instance;
    }

    private GameController(){
        lastInputMove = Move.none();
    }

    public void initialize(){
        Gameflow.reset();
        Board.getInstance().displayState();
        lastInputMove = Move.none();
        Timeline periodic = new Timeline(new KeyFrame(Duration.millis(DisplayConstants.frameRateMillis), actionEvent -> periodic()));
        periodic.setCycleCount(Timeline.INDEFINITE);
        periodic.play();
    }

    private void periodic(){
        Board.getInstance().periodic();
        if(!Deck.getInstance().isEmpty()){
            Gameflow.pollNextTurn();
        } else {
            System.out.println("No more cards remaining");
        }

        SubjectBase.periodicAll();
    }

    public void handleInput(int cardIndex, Card card, int index){
        if(cardIndex == lastCardIndex && lastInputMove.card().exactEquals(card) && lastInputMove.playerIndex() == index){
            Gameflow.shouldPlay();
        } else {
            lastInputMove = new Move(GameAction.CARD, card, index);
            lastCardIndex = cardIndex;
        }
    }

    public void clearRecordedInputs(){
        lastInputMove = Move.none();
    }

    public Move getLastInputMove(){
        return lastInputMove;
    }
}
