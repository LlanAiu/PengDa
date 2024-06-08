package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;

public class NullCommand extends PrioritizedPostMove{

    @Override
    int setPriority() {
        return Constants.NULL_PRIORITY;
    }

    @Override
    GameAction getActionType() {
        return null;
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Null Command Is Not Playable!");
    }
}
