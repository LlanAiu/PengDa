package com.llan.mahjongfunsies.ai.functions;

import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.mahjong.commands.Command;

public class NullValueFunction extends ValueFunction{

    @Override
    public double valueOf(State state, Command command) {
        return 0;
    }
}
