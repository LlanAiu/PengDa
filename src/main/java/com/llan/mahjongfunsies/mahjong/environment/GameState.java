package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.Game;

public abstract class GameState implements State{
    Game game;
    boolean shouldTransition;

    public GameState(){
        game = GameController.getInstance().getCurrentGame();
        shouldTransition = false;
    }

    @Override
    public boolean shouldTransition(){
        return shouldTransition;
    }
}
