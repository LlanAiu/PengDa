package com.llan.mahjongfunsies.ai.policies;

import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.ai.functions.NullValueFunction;
import com.llan.mahjongfunsies.ai.functions.ValueFunction;
import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.mahjong.commands.NullCommand;
import com.llan.mahjongfunsies.util.MathUtil;

import java.util.List;

public class RandomPolicy extends Policy{
    private ValueFunction valueFunction;

    public RandomPolicy(){
        valueFunction = new NullValueFunction();
        setValueFunction();
    }

    @Override
    public ValueFunction getFunction() {
        return valueFunction;
    }

    @Override
    public void save(String filename) {}

    @Override
    public void load(String filename) {}

    @Override
    public Command select(List<Command> actions, State currentState) {
        if(!actions.isEmpty()){
            int moveIndex = MathUtil.randInt(0, actions.size() - 1);
            return actions.get(moveIndex);
        }
        return new NullCommand();
    }
}
