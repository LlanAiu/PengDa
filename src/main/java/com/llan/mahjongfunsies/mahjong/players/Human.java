package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.mahjong.commands.CommandBase;
import com.llan.mahjongfunsies.mahjong.commands.NullCommand;
import com.llan.mahjongfunsies.ui.InputHandler;

public class Human extends Player{

    public Human(int index){
        super(index);
    }

    @Override
    public boolean trySelect() {
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
