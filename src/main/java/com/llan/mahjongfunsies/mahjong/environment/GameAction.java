package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.Gameflow;
import com.llan.mahjongfunsies.mahjong.cards.Card;

public enum GameAction {
    CARD(Gameflow::play),
    TRIPLE((Gameflow::addCardToPlayer)),
    QUAD((Gameflow::addCardToPlayer)),
    STRAIGHT((Gameflow::addCardToPlayer)),
    WIN((Gameflow::addCardToPlayer)),
    NOTHING((card, player) -> {});

    Action action;

    GameAction(Action action){
        this.action = action;
    }

    public void play(Card card, int playerIndex){
        action.play(card, playerIndex);
    }
}
