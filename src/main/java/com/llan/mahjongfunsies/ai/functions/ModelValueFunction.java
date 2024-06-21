package com.llan.mahjongfunsies.ai.functions;

import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.mahjong.commands.Command;

public abstract class ModelValueFunction extends ValueFunction{
    Model model;

    public ModelValueFunction(){
        model = getModel();
    }

    public abstract Model getModel();

    public abstract double stateValue(State state);

    @Override
    public double valueOf(State state, Command command) {
        State resultingState = model.getResultingStateOf(state, command);
        return stateValue(resultingState) + model.getRewardOf(resultingState);
    }
}
