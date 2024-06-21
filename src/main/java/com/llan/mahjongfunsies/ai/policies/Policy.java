package com.llan.mahjongfunsies.ai.policies;

import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.ai.functions.ValueFunction;
import com.llan.mahjongfunsies.mahjong.commands.Command;

import java.util.List;

public abstract class Policy {
    ValueFunction function;

    public Policy(){
        function = getFunction();
    }

    public abstract ValueFunction getFunction();

    public abstract Command select(List<Command> actions, State currentState);
}
