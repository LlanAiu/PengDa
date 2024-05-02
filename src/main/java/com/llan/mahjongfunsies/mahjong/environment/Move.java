package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.cards.Card;

public record Move(GameAction action, Card card, int playerIndex) {}
