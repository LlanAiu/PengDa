package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.cards.Card;

public record GameState (
    int turnNumber,
    int turnIndex,
    Card lastCardPlayed,
    PlayerHand[] hands,
    Card[] discard,
    Card[] remainingDeck
) {}