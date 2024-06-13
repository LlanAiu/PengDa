package com.llan.mahjongfunsies.ui;

import com.llan.mahjongfunsies.controllers.GameController;
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
            if(GameController.getInstance().getCurrentGameState().equals("Checking")){
                shouldReturn = true;
            }
        } else {
            lastInputMove = new PlayCard(card, index);
            lastCardIndex = cardIndex;
        }
    }

    public void onPostMove(GameAction action, int index){
        shouldReturn = true;
        System.out.println("Post Move Queued");
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

    //used only by the prompter
    public void setLastInputMove(Ambiguous move){
        System.out.println("Last Input Move Disambiguated (Hopefully)");
        lastInputMove = (CommandBase) move;
        shouldReturn = true;
    }

    public void setDrawnQuadMove(){
        System.out.println("Last Input Move Set To DrawnQuad");
        lastInputMove = new DrawnQuad();
        shouldReturn = true;
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
