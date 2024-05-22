package com.llan.mahjongfunsies.mahjong.players;

import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.environment.Move;

public class Human extends Player{

    public Human(int index){
        super(index);
    }

    @Override
    public void select() {
        Move input = GameController.getInstance().getLastInputMove();
        if(input.playerIndex() == this.index && !input.equals(this.move)){
            this.move = input;
        }
    }
}
