package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;

public class Quad extends PrioritizedPostMove{
    public Quad(int playerIndex){
        super(playerIndex);
    }

    @Override
    int setPriority() {
        return Constants.QUAD_PRIORITY;
    }
}
