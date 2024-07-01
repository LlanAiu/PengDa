package com.llan.mahjongfunsies.ai;

import com.llan.mahjongfunsies.Constants;

public final class AIConstants {
    public static final int FEATURE_LENGTH = Constants.ALL_CARDS.length * 5 + 6;
    public static final double DRAW_REWARD = 0.0;
    public static final double LOSS_REWARD = -1.0;
    public static final double WIN_REWARD = 1.0;

    public static final double STARTING_WEIGHT_MIN = -0.2;
    public static final double STARTING_WEIGHT_MAX = 0.2;

    public static final double DEFAULT_EPSILON = 0.05;
    public static final double DISCOUNT = 0.99;
}
