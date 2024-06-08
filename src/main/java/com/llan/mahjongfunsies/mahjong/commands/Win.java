package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;

public class Win extends PrioritizedPostMove{
    public Win(int playerIndex){
        super(playerIndex);
    }

    @Override
    int setPriority() {
        return Constants.WIN_PRIORITY;
    }

    @Override
    GameAction getActionType() {
        return GameAction.WIN;
    }
}
