package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.commands.Straight;
import com.llan.mahjongfunsies.ui.DisplayConstants;

public class Postchecking extends GameState{
    private int polledTries;

    public Postchecking(){
        polledTries = 0;
    }


    @Override
    public void periodic() {
        game.pollAll();
        polledTries++;
        shouldTransition = polledTries >= DisplayConstants.maxPolledFrames;
    }

    @Override
    public void onTransition() {
        var move = game.getPostMove();
        if(move.isPresent()){
            move.ifPresent(command -> {
                if(command instanceof Straight){
                    if(!((Straight) command).isSelected()) {
                        game.setState(new Prompting(((Straight) command).getPlayerIndex()));
                    }
                } else {
                    command.execute();
                    game.setState(new Premove());
                }
            });
        } else {
            game.nextTurn();
            game.setState(new Premove());
        }
    }
}
