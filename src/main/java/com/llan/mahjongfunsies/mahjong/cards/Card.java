package com.llan.mahjongfunsies.mahjong.cards;

public class Card {
    public enum Suit{
        TIAO(1),
        TONG(2),
        WAN(3);

        private final int priority;

        Suit(int priority){
            this.priority = priority;
        }

        public int getPriority(){
            return priority;
        }
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
