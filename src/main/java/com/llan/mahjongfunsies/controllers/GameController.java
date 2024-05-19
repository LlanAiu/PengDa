package com.llan.mahjongfunsies.controllers;

import com.llan.mahjongfunsies.mahjong.Gameflow;
import com.llan.mahjongfunsies.ui.Board;

public class GameController {

    private static GameController instance;

    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        return instance;
    }

    public void initialize(){
        Gameflow.initialize();
        Gameflow.reset();
        Board.getInstance().displayState(Gameflow.getState());
    }
}
