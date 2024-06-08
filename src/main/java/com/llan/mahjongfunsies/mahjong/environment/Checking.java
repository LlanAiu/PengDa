package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.commands.Command;

public class Checking extends GameState{

    @Override
    public void periodic() {
        var move = game.pollCurrentTurn();
        if(move.isPresent()){
            move.ifPresent(Command::execute);
            shouldTransition = true;
        }
    }

    @Override
    public void onTransition() {
        game.postTurn();
        game.setState(new Postchecking());
    }
}
