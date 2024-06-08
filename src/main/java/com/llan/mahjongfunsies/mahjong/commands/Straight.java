package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.util.Triplet;

import java.util.Optional;

public class Straight extends PrioritizedPostMove {
    private Triplet cards;

    public Straight(int playerIndex){
        super(playerIndex);
    }

    public Straight(int playerIndex, Triplet cards){
        super(playerIndex);
        this.cards = cards;
    }

    public Triplet getCards(){
        return cards;
    }

    public void setCards(Triplet cards){
        this.cards = cards;
    }


    public boolean isSelected(){
        return cards != null;
    }

    @Override
    int setPriority() {
        return Constants.STRAIGHT_PRIORITY;
    }

    @Override
    GameAction getActionType() {
        return GameAction.STRAIGHT;
    }

    @Override
    public void play() {
        currentGame.addLastCardToPlayer(getActionType(), playerIndex, Optional.of(cards));
    }
}
