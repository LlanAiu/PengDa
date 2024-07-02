package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.ai.policies.Policy;
import com.llan.mahjongfunsies.mahjong.commands.NullCommand;

public class Computer extends Player{
    private Policy policy;

    public Computer(int index, Policy policy){
        super(index);
        this.policy = policy;
    }

    public Policy getPolicy(){
        return policy;
    }

    @Override
    public boolean shouldDisplay() {
        return false;
    }

    @Override
    public boolean trySelect(State gameState) {
        if(move instanceof NullCommand){
            move = policy.select(legalMoves, gameState);
            return true;
        }
        return false;
    }
}
