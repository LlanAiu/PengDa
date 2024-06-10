package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.ui.Board;

public class End extends GameState{

    @Override
    public void periodic() {
        System.out.println("Game Over!");
        Board.getInstance().finishGame(game.getWinningIndex());
    }

    @Override
    public void onTransition() {

    }
}
