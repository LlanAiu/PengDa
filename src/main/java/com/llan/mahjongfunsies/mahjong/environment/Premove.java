package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.ui.Board;

public class Premove extends GameState{

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
            game.setState(new End());
        } else {
            game.setState(new Checking());
        }
    }
}
