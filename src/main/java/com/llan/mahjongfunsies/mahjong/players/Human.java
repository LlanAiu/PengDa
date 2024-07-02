package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.ai.components.State;
import com.llan.mahjongfunsies.mahjong.commands.CommandBase;
import com.llan.mahjongfunsies.mahjong.commands.Nothing;
import com.llan.mahjongfunsies.mahjong.commands.NullCommand;
import com.llan.mahjongfunsies.ui.InputHandler;

public class Human extends Player{

    public Human(int index){
        super(index);
    }

    @Override
    public boolean shouldDisplay() {
        return true;
    }

    @Override
    public boolean trySelect(State gameState) {
        if(!legalMoves.isEmpty() && move instanceof NullCommand){
            move = new Nothing(index);
        }
        CommandBase input = InputHandler.getInstance().getLastInputMove();
        if(!(input instanceof NullCommand)){
            if(input.getPlayerIndex() == index){
                move = input;
                return true;
            }
        }
        return false;
    }
}
