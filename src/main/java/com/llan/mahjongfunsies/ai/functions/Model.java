package com.llan.mahjongfunsies.ai.functions;

import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.mahjong.commands.Command;

public interface Model {
    double getRewardOf(State state);
    State getResultingStateOf(State state, Command action);
}
