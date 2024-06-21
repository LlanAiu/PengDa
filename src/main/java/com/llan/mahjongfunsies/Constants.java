package com.llan.mahjongfunsies;

import com.llan.mahjongfunsies.mahjong.cards.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Constants {
    public static final int CARD_DUPLICATES = 4;
    public static final int[] CARD_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static final int TOTAL_DECK_SIZE;
    public static final int STARTING_CARDS = 13;

    public static final int NUM_PLAYERS = 4;

    public static final int NULL_PRIORITY = 100000;
    public static final int STRAIGHT_PRIORITY = 4;
    public static final int TRIPLE_PRIORITY = 3;
    public static final int QUAD_PRIORITY = 2;
    public static final int WIN_PRIORITY = 1;

    public static final int DRAW = -1;

    public static final Card[] ALL_CARDS;
    public static final Map<Card, Integer> CARD_INDICES;

    static {
        List<Card> cardsList = new ArrayList<>();
        CARD_INDICES = new HashMap<>();
        int index = 0;
        for(Card.Suit suit : Card.Suit.values()){
            if(suit == Card.Suit.HONOR){
                for(Card.HonorType honor : Card.HonorType.values()){
                    if(honor != Card.HonorType.NONE){
                        cardsList.add(Card.of(honor));
                        CARD_INDICES.put(Card.of(honor), index++);
                    }
                }
            } else {
                for(int value : Constants.CARD_VALUES){
                    cardsList.add(Card.of(suit, value));
                    CARD_INDICES.put(Card.of(suit, value), index++);
                }
            }
        }
        ALL_CARDS = cardsList.toArray(new Card[cardsList.size()]);
        TOTAL_DECK_SIZE = ALL_CARDS.length * CARD_DUPLICATES;
    }
}
