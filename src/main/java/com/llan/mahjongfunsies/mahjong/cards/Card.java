package com.llan.mahjongfunsies.mahjong.cards;

public record Card(Suit suit, int value) {
    public enum Suit {
        TIAO(1),
        TONG(2),
        WAN(3);

        private final int priority;

        Suit(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }


}
