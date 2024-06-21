package com.llan.mahjongfunsies.mahjong.commands;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.util.Triplet;

import java.util.Optional;

public class Straight extends PrioritizedPostMove implements Ambiguous{
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

    @Override
    public boolean isSelected(){
        return cards != null;
    }

    @Override
    public String getText() {;
        StringBuilder sb = new StringBuilder("{ ");
        for(Card card : cards.getCards()){
            sb.append(card.value() + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" }");
        return sb.toString();
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
        currentGame.updateState();
    }
}
