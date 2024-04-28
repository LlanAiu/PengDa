package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.cards.Card;

@FunctionalInterface
public interface Action {

    void play(Card card, int player);

}
