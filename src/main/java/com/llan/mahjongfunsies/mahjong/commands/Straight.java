package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;

public class Straight extends PrioritizedPostMove {

    public Straight(int playerIndex){
        super(playerIndex);
    }

    @Override
    int setPriority() {
        return Constants.STRAIGHT_PRIORITY;
    }
}
