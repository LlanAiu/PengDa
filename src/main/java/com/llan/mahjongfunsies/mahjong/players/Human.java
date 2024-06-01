package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.mahjong.environment.Move;
import com.llan.mahjongfunsies.ui.InputHandler;

public class Human extends Player{

    public Human(int index){
        super(index);
    }

    @Override
    public boolean trySelect() {
        Move input = InputHandler.getInstance().getLastInputMove();
        if(input.playerIndex() == this.index && !input.equals(this.move)){
            this.move = input;
            return false;
        } else if (input.equals(this.move)){
            return true;
        }
        return false;
    }
}
