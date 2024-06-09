package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.ui.Board;

public class End extends GameState{

    @Override
    public void periodic() {
        Board.getInstance().finishGame(game.getWinningIndex());
    }

    @Override
    public void onTransition() {

    }
}
