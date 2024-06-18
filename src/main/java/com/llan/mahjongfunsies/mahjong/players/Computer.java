package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.ai.Policy;
import com.llan.mahjongfunsies.ai.RandomPolicy;
import com.llan.mahjongfunsies.mahjong.commands.NullCommand;

public class Computer extends Player{
    //null for now
    Policy policy = new RandomPolicy();

    public Computer(int index){
        super(index);
    }

    @Override
    public boolean shouldDisplay() {
        return false;
    }

    @Override
    public boolean trySelect() {
        if(move instanceof NullCommand){
            move = policy.select(legalMoves);
            return true;
        }
        return false;
    }
}
