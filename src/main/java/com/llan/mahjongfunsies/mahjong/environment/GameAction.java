package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.cards.Card;

public enum GameAction {
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
