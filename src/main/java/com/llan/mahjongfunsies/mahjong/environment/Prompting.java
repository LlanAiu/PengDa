package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.commands.Ambiguous;
import com.llan.mahjongfunsies.mahjong.players.Player;
import com.llan.mahjongfunsies.ui.Board;

public class Prompting extends GameState{
    private Player player;

    public Prompting(int index){
        player = game.getPlayerByIndex(index);
        player.filterAmbiguousMoves();
        Board.getInstance().addPrompter(player);
    }

    @Override
    public void periodic() {
        Ambiguous move = (Ambiguous) player.getSelectedMove();
        if(move.isSelected()){
            shouldTransition = true;
        } else {
            player.trySelect();
        }
    }

    @Override
    public void onTransition() {
        player.getSelectedMove().execute();
        Board.getInstance().removePrompter();
        game.setState(new Premove());
    }
}
