package com.llan.mahjongfunsies.mahjong.environment;

public class Premove extends GameState{

    @Override
    public void periodic() {
        game.preTurn();
        shouldTransition = true;
    }

    @Override
    public void onTransition() {
        game.setState(new Checking());
    }
}
