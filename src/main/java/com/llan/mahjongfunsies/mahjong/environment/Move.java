package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.cards.Card;

public record Move(GameAction action, Card card, int playerIndex) {
    public static Move none(){
        return new Move(GameAction.NOTHING, Card.of(Card.Suit.WAN, 1), -1);
    }
}
