package com.llan.mahjongfunsies.ai;

import com.llan.mahjongfunsies.mahjong.commands.Command;

import java.util.List;

public abstract class Policy {
    ValueFunction function;

    public abstract Command select(List<Command> actions);
}
