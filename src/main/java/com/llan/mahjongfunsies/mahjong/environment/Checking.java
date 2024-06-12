package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.commands.DrawnQuad;

public class Checking extends GameState{
    private boolean premove;

    public Checking(){
        premove = false;
    }

    @Override
    public void periodic() {
        var move = game.pollCurrentTurn();
        if(move.isPresent()){
            move.ifPresent(command -> {
                if(command instanceof DrawnQuad){
                    ((DrawnQuad) command).play();
                    premove = true;
                } else {
                    command.execute();
                }
            });
            shouldTransition = true;
        }
    }

    @Override
    public void onTransition() {
        if(premove){
            game.drawCard();
            game.setState(new Premove());
        } else {
            game.postTurn();
            game.setState(new Postchecking());
        }
    }
}
