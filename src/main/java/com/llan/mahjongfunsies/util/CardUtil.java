package com.llan.mahjongfunsies.util;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.mahjong.cards.Subdeck;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CardUtil {

    public static boolean isWinning(@NotNull Subdeck hand){
        List<Card> filtered = hand.filterShown();
        List<Card> processed;
        List<Card> pairs = hand.findPairs(filtered);
        for(Card card : pairs){
            processed = new ArrayList<>();
            processed.addAll(filtered);
            processed.remove(card);
            processed.remove(card);
            int count = countSets(processed);
            if(count == ((double) filtered.size() - 2.0) / 3.0){
                return true;
            }
        }
        return false;
    }

    public static int countSets(@NotNull List<Card> deck){
        int count = 0;
        Card card1;
        Card card2;
        while(deck.size() >= 3){
            card1 = deck.removeFirst();
            card2 = deck.removeFirst();
            if(card1.equals(card2)){
                if(deck.contains(Card.of(card1.suit(), card1.value(), card1.honor()))){
                    deck.remove(Card.of(card1.suit(), card1.value(), card1.honor()));
                    count++;
                }
            } else if (card1.suit() == card2.suit() && card1.honor() == card2.honor()){
                if(card1.value() > 1 && deck.contains(Card.of(card1.suit(), card1.value() - 1))){
                    deck.remove(Card.of(card1.suit(), card1.value() - 1));
                    count++;
                } else if (card2.value() < 9 && deck.contains(Card.of(card2.suit(), card2.value() + 1))){
                    deck.remove(Card.of(card2.suit(), card2.value() + 2));
                    count++;
                }
            }
        }
        return count;
    }
}
