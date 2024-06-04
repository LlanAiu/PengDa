package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;

public class Triple extends PrioritizedPostMove{

    public Triple(int playerIndex){
        super(playerIndex);
    }

    @Override
    int setPriority() {
        return Constants.TRIPLE_PRIORITY;
    }
}
