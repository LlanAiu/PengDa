package com.llan.mahjongfunsies.ai.updaters;

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
    private int stepSizeCount;

    public Updater(Policy policy){
        this.policy = policy;
        this.function = policy.getFunction();
        stepSizeCount = 1;
    }

    //this is bad design but too lazy to abstract record right now
    public void update(List<Command> actions, State currentState, GameRecord record) {
        NumericMatrix update = getUpdate(actions, currentState, record);
        if(update != null){
            policy.getFunction().updateWeights(update);
        }
    }

    public double getStepSize(){
        return 1.0 / ((double) stepSizeCount++);
    }

    public abstract NumericMatrix getUpdate(List<Command> actions, State currentState, GameRecord record);
}
