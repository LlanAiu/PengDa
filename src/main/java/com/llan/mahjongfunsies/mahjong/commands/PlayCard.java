package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.mahjong.cards.Card;

public class PlayCard extends CommandBase{

    public PlayCard(Card card, int playerIndex){
        super(card, playerIndex);
    }

    @Override
    public void play() {
        currentGame.record(this, playerIndex);
        currentGame.playCard(card, playerIndex);
    }
}
