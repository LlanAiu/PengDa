package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.ui.Board;

public class End extends GameStatus {

    public End(){
        if(!GameController.getInstance().isTraining()){
            Board.getInstance().finishGame(game.getWinningIndex());
        }
    }

    @Override
    public void periodic() {

    }

    @Override
    public void onTransition() {

    }
}
