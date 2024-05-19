package com.llan.mahjongfunsies.mahjong.environment;

import com.llan.mahjongfunsies.mahjong.cards.Card;
import com.llan.mahjongfunsies.util.DisplayUtil;

public record PlayerHand(int index, Card[] hand, DisplayUtil.Orientation orientation) {}
