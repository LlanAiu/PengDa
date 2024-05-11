package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.Gameflow;
import com.llan.mahjongfunsies.mahjong.cards.Card;

public enum GameAction {
    CARD(Gameflow::play, -1),
    TRIPLE((Gameflow::addCardToPlayer), 1),
    QUAD((Gameflow::addCardToPlayer), 0),
    STRAIGHT((Gameflow::addCardToPlayer), 2),
    NOTHING((card, player) -> {}, -1);

    Action action;
    int priority;

    GameAction(Action action, int priority){
        this.action = action;
        this.priority = priority;
    }

    public int getPriority(){
        return priority;
    }

    public void play(Card card, int playerIndex){
        action.play(card, playerIndex);
    }
}
