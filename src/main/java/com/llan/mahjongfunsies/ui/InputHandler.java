package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.commands.CommandBase;
import com.llan.mahjongfunsies.mahjong.commands.NullCommand;
import com.llan.mahjongfunsies.mahjong.commands.PlayCard;

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

    public void clearRecordedInputs(){
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
