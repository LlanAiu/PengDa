package com.llan.mahjongfunsies.ai;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.ai.updaters.MahjongUpdater;
import com.llan.mahjongfunsies.ai.updaters.Updater;
import com.llan.mahjongfunsies.mahjong.Game;
import com.llan.mahjongfunsies.mahjong.players.Computer;
import com.llan.mahjongfunsies.util.GameRecord;

public class Trainer {
    private Updater[] updaters;
    private Computer[] players;
    private Game currentGame;
    private GameRecord record;

    public Trainer(Game game){
        currentGame = game;
        players = game.getPlayers();
        updaters = new MahjongUpdater[Constants.NUM_PLAYERS];
        for(int i = 0; i < updaters.length; i++){
            updaters[i] = new MahjongUpdater(players[i].getPolicy(), i);
        }
        record = game.getRecord();
    }

    public void update(){
        if(currentGame.getStatus().equals("Checking")){
            int index = currentGame.currentTurnIndex();
            updaters[index].update(players[index].getLegalMoves(), currentGame.getState(), record);
        } else if (currentGame.getState().equals("Postchecking")){
            for(int i = 0; i < Constants.NUM_PLAYERS; i++){
                updaters[i].update(players[i].getLegalMoves(), currentGame.getState(), record);
            }
        }
    }

    public void onEnd(){
        for(Updater updater : updaters){
            ((MahjongUpdater) updater).saveStepSize();
        }
    }
}
