package com.llan.mahjongfunsies.ai.Iterators;

import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.ai.functions.ValueFunction;
import com.llan.mahjongfunsies.ai.policies.Policy;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.util.GameRecord;
import com.llan.mahjongfunsies.util.NumericMatrix;

import java.util.List;

public abstract class Updater {
    Policy policy;
    ValueFunction function;

    public Updater(Policy policy){
        this.policy = policy;
        this.function = policy.getFunction();
    }

    //this is bad design but too lazy to abstract record right now
    public void update(List<Command> actions, State currentState, GameRecord record) {
        NumericMatrix update = getUpdate(actions, currentState, record);
        policy.getFunction().updateWeights(update);
    }

    public abstract NumericMatrix getUpdate(List<Command> actions, State currentState, GameRecord record);
}
