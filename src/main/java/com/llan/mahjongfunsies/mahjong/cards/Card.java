package com.llan.mahjongfunsies.mahjong.cards;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Card {
    public enum Suit {
        HONOR(0),
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

    public enum HonorType {
        NORTH(1),
        EAST(3),
        SOUTH(5),
        WEST(7),
        RED(9),
        GREEN(11),
        BLANK(13),
        NONE(-1);

        private final int value;

        HonorType(int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }
    }

    @Contract("_, _ -> new")
    public static @NotNull Card of(Suit suit, int value){
        return new Card(suit, value);
    }

    @Contract("_, _, _ -> new")
    public static @NotNull Card of(Suit suit, int value, HonorType honor){
        return new Card(suit, value, honor);
    }

    @Contract("_ -> new")
    public static @NotNull Card copyOf(@NotNull Card card){
        return new Card(card.suit, card.value, card.honor);
    }

    private final Suit suit;
    private final int value;
    private final HonorType honor;
    private boolean hidden = true;

    public Card(Suit suit, int value, HonorType honor){
        if(suit == Suit.HONOR){
            if(honor != HonorType.NONE){
                this.suit = suit;
                this.honor = honor;
                this.value = honor.getValue();
            } else {
                throw new IllegalArgumentException("Cannot create honor card of no honor type");
            }
        } else {
            this.suit = suit;
            this.value = value;
            this.honor = HonorType.NONE;
        }
    }

    public Card(Suit suit, int value){
        this(suit, value, HonorType.NONE);
    }

    public Card(HonorType honorCard){
        this(Suit.HONOR, 0, honorCard);
    }

    public Suit suit(){
        return suit;
    }

    public int value(){
        return value;
    }

    public HonorType honor(){
        return honor;
    }

    public void setHidden(boolean hidden){
        this.hidden = hidden;
    }

    public boolean isHidden(){
        return hidden;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("suit=").append(suit);
        sb.append(", value=").append(value);
        sb.append(", honor=").append(honor);
        sb.append(", hidden=").append(hidden);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && suit == card.suit && honor == card.honor;
    }

    public boolean exactEquals(Card other){
        return this.equals(other) && this.isHidden() == other.isHidden();
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, value, honor);
    }
}
