package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.commands.*;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;

public class InputHandler {
    private CommandBase lastInputMove;
    private int lastCardIndex;
    private boolean shouldReturn;


    private static InputHandler instance;

    public static InputHandler getInstance(){
        if(instance == null){
            instance = new InputHandler();
        }
        return instance;
    }

    private InputHandler(){
        lastInputMove = new NullCommand();
        lastCardIndex = -1;
        shouldReturn = false;
    }

    public void onCardPressed(int cardIndex, Card card, int index){
        if(lastCardIndex == cardIndex && lastInputMove.getCard().exactEquals(card) && lastInputMove.getPlayerIndex() == index){
            shouldReturn = true;
        } else {
            lastInputMove = new PlayCard(card, index);
            lastCardIndex = cardIndex;
        }
    }

    public void onPostMove(GameAction action, int index){
        shouldReturn = true;
        switch (action) {
            case STRAIGHT -> lastInputMove = new Straight(index);
            case TRIPLE -> lastInputMove = new Triple(index);
            case QUAD -> lastInputMove = new Quad(index);
            case WIN -> lastInputMove = new Win(index);
        }
    }

    public void clearRecordedInputs(){
        lastCardIndex = -1;
        lastInputMove = new NullCommand();
        shouldReturn = false;
    }

    public CommandBase getLastInputMove(){
        if(shouldReturn){
            CommandBase selected = lastInputMove;
            clearRecordedInputs();
            return selected;
        }
        return new NullCommand();
    }
}
