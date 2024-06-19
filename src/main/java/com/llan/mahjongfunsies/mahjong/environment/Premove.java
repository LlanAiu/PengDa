package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.ui.Board;

public class Premove extends GameStatus {

    boolean end;

    public Premove(){
        end = false;
    }

    @Override
    public void periodic() {
        Board.getInstance().resetPostMoves();
        if(game.hasWon()){
            end = true;
        } else {
            game.preTurn();
        }
        shouldTransition = true;
    }

    @Override
    public void onTransition() {
        if(end){
            game.setStatus(new End());
        } else {
            game.setStatus(new Checking());
        }
    }
}
