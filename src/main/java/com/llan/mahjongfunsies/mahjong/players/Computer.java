package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.mahjong.Gameflow;
import com.llan.mahjongfunsies.util.MathUtil;

public class Computer extends Player{

    public Computer(int index){
        super(index);
    }

    @Override
    public void select() {
        if(!legalMoves.isEmpty()){
            int moveIndex = MathUtil.randInt(0, legalMoves.size() - 1);
            move = legalMoves.get(moveIndex);
            Gameflow.shouldPlay();
        }
    }
}
