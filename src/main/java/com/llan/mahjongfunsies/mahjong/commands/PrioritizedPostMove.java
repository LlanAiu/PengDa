package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Card;

public abstract class PrioritizedPostMove extends CommandBase{

    final int priority;

    public PrioritizedPostMove(int playerIndex){
        super(Card.none(), playerIndex);
        priority = setPriority();
    }

    public int getPriority(){
        return priority;
    }

    public boolean isNull(){
        return priority == Constants.NULL_PRIORITY;
    }

    //only called once, should just return a constant
    abstract int setPriority();

    @Override
    public void play() {
        currentGame.addLastCardToPlayer(playerIndex);
    }
}
