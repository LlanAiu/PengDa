package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;

public class NullCommand extends PrioritizedPostMove{

    public NullCommand(){
        super(-1);
    }

    @Override
    int setPriority() {
        return Constants.NULL_PRIORITY;
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Null Command Is Not Playable!");
    }
}
