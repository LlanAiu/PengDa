package com.llan.mahjongfunsies;

import com.llan.mahjongfunsies.mahjong.cards.Card;

import java.util.ArrayList;
import java.util.List;

public final class Constants {
    public static final int CARD_DUPLICATES = 4;
    public static final int[] CARD_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static final int TOTAL_DECK_SIZE = CARD_DUPLICATES * CARD_VALUES.length;
    public static final int STARTING_CARDS = 13;

    public static final int NUM_PLAYERS = 4;

    public static final Card[] allCards;

    static {
        List<Card> cardsList = new ArrayList<>();
        for(Card.Suit suit : Card.Suit.values()){
            for(int value : Constants.CARD_VALUES){
                cardsList.add(new Card(suit, value));
            }
        }
        allCards = cardsList.toArray(new Card[cardsList.size()]);
    }
}
