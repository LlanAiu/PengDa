package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.commands.Straight;
import com.llan.mahjongfunsies.ui.DisplayConstants;

public class Postchecking extends GameStatus {
    private int polledTries;

    public Postchecking(){
        polledTries = 0;
    }


    @Override
    public void periodic() {
        game.pollAll();
        if(GameController.getInstance().isTraining()){
            shouldTransition = true;
        } else {
            polledTries++;
            shouldTransition = polledTries >= DisplayConstants.MAX_POLLED_FRAMES;
        }
    }

    @Override
    public void onTransition() {
        var move = game.getPostMove();
        if(move.isPresent()){
            move.ifPresent(command -> {
                if(command instanceof Straight && !((Straight) command).isSelected()){
                    game.setStatus(new Prompting(((Straight) command).getPlayerIndex()));
                } else {
                    command.execute();
                    game.setStatus(new Premove());
                }
            });
        } else {
            if(game.noCardsLeft()){
                game.setStatus(new End());
            } else {
                game.nextTurn();
                game.setStatus(new Premove());
            }
        }
    }
}
