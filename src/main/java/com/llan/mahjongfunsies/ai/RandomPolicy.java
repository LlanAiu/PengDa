package com.llan.mahjongfunsies.ai;

import com.llan.mahjongfunsies.mahjong.commands.Command;
import com.llan.mahjongfunsies.mahjong.commands.NullCommand;
import com.llan.mahjongfunsies.util.MathUtil;

import java.util.List;

public class RandomPolicy extends Policy{
    @Override
    public Command select(List<Command> actions) {
        if(!actions.isEmpty()){
            int moveIndex = MathUtil.randInt(0, actions.size() - 1);
            return actions.get(moveIndex);
        }
        return new NullCommand();
    }
}
