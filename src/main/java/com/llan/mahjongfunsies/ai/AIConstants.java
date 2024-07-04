package com.llan.mahjongfunsies.ai;

import com.llan.mahjongfunsies.Constants;

public final class AIConstants {
    public static final int FEATURE_LENGTH = Constants.ALL_CARDS.length * 6 + 6;
    public static final int SQUARED_SUBSET_INDEX = 4 * Constants.ALL_CARDS.length + 6;
    public static final int DISCARD_SUBSET_INDEX = 5 * Constants.ALL_CARDS.length + 6;

    public static final double DRAW_REWARD = -40;
    public static final double LOSS_REWARD = -50;
    public static final double WIN_REWARD = 50;

    public static final double STARTING_WEIGHT_MIN = -0.2;
    public static final double STARTING_WEIGHT_MAX = 0.2;

    public static final double DEFAULT_EPSILON = 0.1;
    public static final double DISCOUNT = 0.99;

    //probably an underestimate
    public static final double EXPECTED_FEATURE_TRANSPOSE_VALUE = 202.75;
    public static final double EXPERIENCES_NECESSARY = 15.0;
    public static final double STEP_SIZE = 1.0 / (EXPERIENCES_NECESSARY * EXPECTED_FEATURE_TRANSPOSE_VALUE);

}
