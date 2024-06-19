package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.commands.Ambiguous;
import com.llan.mahjongfunsies.mahjong.commands.CommandBase;

public class Checking extends GameStatus {
    private boolean prompting;
    private CommandBase toPrompt;

    public Checking(){
        prompting = false;
    }

    @Override
    public void periodic() {
        var move = game.pollCurrentTurn();
        if(move.isPresent()){
            move.ifPresent(command -> {
                if(command instanceof Ambiguous && !((Ambiguous) command).isSelected()){
                    toPrompt = (CommandBase) command;
                    prompting = true;
                } else {
                    command.execute();
                }
            });
            shouldTransition = true;
        }
    }

    @Override
    public void onTransition() {
        if(prompting){
            game.drawCard();
            game.setStatus(new Prompting(toPrompt.getPlayerIndex()));
        } else {
            game.postTurn();
            game.setStatus(new Postchecking());
        }
    }
}
