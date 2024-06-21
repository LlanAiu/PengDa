package com.llan.mahjongfunsies.util;

import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.mahjong.commands.Command;

public record TurnRecord (
    int turnNumber,
    State gameState,
    Command selectedAction,
    double reward
){}
