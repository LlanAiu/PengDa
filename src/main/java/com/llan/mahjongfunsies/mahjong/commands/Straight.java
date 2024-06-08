package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;

public class Straight extends PrioritizedPostMove {
    //associated cards stored here (probably just a triplet data strucutre? or list?)

    public Straight(int playerIndex){
        super(playerIndex);
    }

    //if it has the associated cards attached
    public boolean isSelected(){
        return false;
    }

    @Override
    int setPriority() {
        return Constants.STRAIGHT_PRIORITY;
    }

    @Override
    GameAction getActionType() {
        return GameAction.STRAIGHT;
    }

    @Override
    public void play() {
        super.play();
    }
}
