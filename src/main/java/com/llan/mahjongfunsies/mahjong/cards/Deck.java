package com.llan.mahjongfunsies.mahjong.cards;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards;

    private static Deck instance;

    public static Deck getInstance(){
        if(instance == null){
            instance = new Deck();
        }
        return instance;
    }

    private Deck(){
        cards = new ArrayList<>(Constants.TOTAL_DECK_SIZE);
        this.reset();
    }

    public Card drawNext(){
        if(cards.size() > 0){
            return cards.removeFirst();
        }
        return null;
    }

    public List<Card> drawNext(int number){
        List<Card> drawn = new ArrayList<>(number);
        for(int i = 0; i < number; i++){
            drawn.add(drawNext());
        }
        return drawn;
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public void reset(){
        if(!cards.isEmpty()) {
            cards.removeAll(cards);
        }
        for(Card card : Constants.allCards){
            for(int i = 0; i < Constants.CARD_DUPLICATES; i++){
                cards.add(Card.copyOf(card));
            }
        }
        this.shuffle();
    }

    private void shuffle(){
        List<Card> shuffled = new ArrayList<>(108);
        while(cards.size() > 0){
            shuffled.add(cards.remove(MathUtil.randInt(0, cards.size() - 1)));
        }
        cards = shuffled;
    }
}
