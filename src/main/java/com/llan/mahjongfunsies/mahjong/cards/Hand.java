package com.llan.mahjongfunsies.mahjong.cards;

import com.llan.mahjongfunsies.Constants;
import com.llan.mahjongfunsies.mahjong.environment.GameAction;
import com.llan.mahjongfunsies.util.CardUtil;
import com.llan.mahjongfunsies.util.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hand extends Subdeck{

    public Hand(){
        cards = new ArrayList<>(Constants.STARTING_CARDS);
    }

    public void reveal(List<Card> reveal){
        for(Card card : cards){
            if(!card.isHidden()){
                continue;
            }
            if(reveal.contains(card)){
                card.setHidden(false);
                reveal.remove(card);
            }
        }
        setChanged();
    }

    public List<Card> getSetOf(GameAction action, Card card, Optional<Triplet> triplet){
        List<Card> cards = new ArrayList<>(4);
        switch (action){
            case TRIPLE:
                for(int i = 0; i < 3; i++){
                    cards.add(Card.of(card.suit(), card.value(), card.honor()));
                }
                break;
            case QUAD:
                for(int i = 0; i < 4; i++){
                    cards.add(Card.of(card.suit(), card.value(), card.honor()));
                }
                break;
            case STRAIGHT:
                triplet.ifPresent(triplet1 -> cards.addAll(triplet1.getCards()));
                break;
            case WIN:
                cards.addAll(this.filterShown());
                break;
        }
        return cards;
    }

    public List<Triplet> getStraights(Card search){
        int value = search.value();
        Card.Suit suit = search.suit();
        List<Triplet> sets = new ArrayList<>();
        List<Card> filtered = this.filterShown();
        if(suit == Card.Suit.HONOR){
            return sets;
        }

        if(value - 2 >= 1){
            if(filtered.contains(Card.of(suit, value - 2)) && filtered.contains(Card.of(suit, value - 1))){
                sets.add(
                    new Triplet(
                        Card.of(suit, value - 2),
                        Card.of(suit, value - 1),
                        Card.of(suit, value)
                    )
                );
            }
        }
        if(value + 1 <= 9 && value - 1 >= 1){
            if(filtered.contains(Card.of(suit, value - 1)) && filtered.contains(Card.of(suit, value + 1))){
                sets.add(
                    new Triplet(
                        Card.of(suit, value - 1),
                        Card.of(suit, value),
                        Card.of(suit, value + 1)
                    )
                );
            }
        }
        if(value + 2 <= 9){
            if(filtered.contains(Card.of(suit, value + 1)) && filtered.contains(Card.of(suit, value + 2))){
                sets.add(
                    new Triplet(
                        Card.of(suit, value),
                        Card.of(suit, value + 1),
                        Card.of(suit, value + 2)
                    )
                );
            }
        }
        return sets;
    }

    //returns a list of winning cards if true
    public Optional<List<Card>> isOneAway(){
        Hand possibleHands = new Hand();
        List<Card> winningCards = new ArrayList<>();
        possibleHands.addCards(cards);
        for(Card card : Constants.allCards){
            possibleHands.addCard(card);
            if(CardUtil.isWinning(possibleHands)){
                winningCards.add(card);
            }
            possibleHands.removeCard(card);
        }
        if(winningCards.isEmpty()){
            return Optional.empty();
        } else {
            return Optional.of(winningCards);
        }
    }

    public boolean isWinning(){
        return CardUtil.isWinning(this);
    }
}
