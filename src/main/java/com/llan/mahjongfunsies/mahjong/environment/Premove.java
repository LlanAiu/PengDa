package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.ui.Board;

public class Premove extends GameState{

    @Override
    public void periodic() {
        Board.getInstance().resetPostMoves();
        game.preTurn();
        shouldTransition = true;
    }

    @Override
    public void onTransition() {
        game.setState(new Checking());
    }
}
