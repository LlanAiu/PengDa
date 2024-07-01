package com.llan.mahjongfunsies.ai;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.ai.Iterators.MahjongUpdater;
import com.llan.mahjongfunsies.ai.Iterators.Updater;
import com.llan.mahjongfunsies.mahjong.Game;
import com.llan.mahjongfunsies.mahjong.players.Computer;

public class Trainer {
    private Updater[] updaters;
    private Computer[] players;
    private Game currentGame;

    public Trainer(Game game){
        currentGame = game;
        players = game.getPlayers();
        updaters = new MahjongUpdater[Constants.NUM_PLAYERS];
        for(int i = 0; i < updaters.length; i++){
            updaters[i] = new MahjongUpdater(players[i].getPolicy(), i);
        }
    }

    public void update(){
        if(currentGame.getStatus().equals("Checking")){

        }
    }
}
