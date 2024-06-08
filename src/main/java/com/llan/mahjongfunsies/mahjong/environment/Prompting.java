package com.llan.mahjongfunsies.mahjong.environment;

public class Prompting extends GameState{
    private int index;

    public Prompting(int index){
        this.index = index;
        game.filterMoves(index);
    }

    @Override
    public void periodic() {

    }

    @Override
    public void onTransition() {

    }
}
