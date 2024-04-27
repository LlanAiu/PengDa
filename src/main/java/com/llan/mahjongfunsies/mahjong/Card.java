package com.llan.mahjongfunsies.mahjong;

public class Card {
    public enum Suit{
        TIAO,
        TONG,
        WAN;
    }

    private final Suit suit;
    private final int value;

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }
}
