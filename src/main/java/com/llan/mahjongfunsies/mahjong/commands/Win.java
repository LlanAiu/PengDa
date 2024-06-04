package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;

public class Win extends PrioritizedPostMove{
    public Win(int playerIndex){
        super(playerIndex);
    }

    @Override
    int setPriority() {
        return Constants.WIN_PRIORITY;
    }
}
