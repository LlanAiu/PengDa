package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.mahjong.Gameflow;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.mahjong.environment.Move;

public class InputHandler {
    private Move lastInputMove;
    private int lastCardIndex = -1;

    private static InputHandler instance;

    public static InputHandler getInstance(){
        if(instance == null){
            instance = new InputHandler();
        }
        return instance;
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
