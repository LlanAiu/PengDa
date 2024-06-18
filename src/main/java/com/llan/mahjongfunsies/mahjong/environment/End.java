package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.ui.Board;

public class End extends GameStatus {

    public End(){
        Board.getInstance().finishGame(game.getWinningIndex());
    }

    @Override
    public void periodic() {

    }

    @Override
    public void onTransition() {

    }
}
