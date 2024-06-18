package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.controllers.GameController;
import com.llan.mahjongfunsies.mahjong.Game;

public abstract class GameStatus implements Status {
    Game game;
    boolean shouldTransition;

    public GameStatus(){
        game = GameController.getInstance().getCurrentGame();
        shouldTransition = false;
    }

    @Override
    public boolean shouldTransition(){
        return shouldTransition;
    }
}
