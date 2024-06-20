package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Discard;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;

import java.util.Optional;

public abstract class PrioritizedPostMove extends CommandBase{

    final int priority;

    public PrioritizedPostMove(){
        priority = Constants.NULL_PRIORITY;
    }

    public PrioritizedPostMove(int playerIndex){
        super(Discard.getInstance().getLastPlayed(), playerIndex);
        priority = setPriority();
    }

    public int getPriority(){
        return priority;
    }

    public boolean isNull(){
        return priority == Constants.NULL_PRIORITY;
    }

    public void record(){
        currentGame.record(this, playerIndex);
    }

    //only called once, should just return a constant
    abstract int setPriority();

    abstract GameAction getActionType();

    @Override
    public void play() {
        currentGame.addLastCardToPlayer(getActionType(), playerIndex, Optional.empty());
    }
}
